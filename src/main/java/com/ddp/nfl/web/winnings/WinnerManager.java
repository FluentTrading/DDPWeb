package com.ddp.nfl.web.winnings;

import org.slf4j.*;

import java.time.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class WinnerManager{
    
    private final List<WinnerSummary> winnerSummaryList;
    
    private final int[] pastWeekArray;
    
    private final WinnerSummaryComparator summaryComparator;
    private final Map<Integer, Set<WinnerResult>> weeklyResultMap;
    private final Map<DDPPlayer, Integer> playerTotalScoreMap;
    private final Map<Integer, Collection<DDPPick>> picksPerWeekMap;
                    
    private final static String NAME    = WinnerManager.class.getSimpleName( );
    private final static Logger LOGGER  = LoggerFactory.getLogger( NAME );
           
    public WinnerManager( DDPMeta ddpMeta, DBService service ){
        this.summaryComparator  = new WinnerSummaryComparator( );
        this.pastWeekArray      = getPastWeeks( ddpMeta.getGameWeek( ) );
        this.picksPerWeekMap    = getPicksPerWeek( ddpMeta, pastWeekArray, service );
        this.weeklyResultMap    = prepareWinner( ddpMeta, service );
        this.playerTotalScoreMap= createPlayerTotalScoreMap( weeklyResultMap );
        this.winnerSummaryList  = prepareWinnerSummary( ddpMeta, weeklyResultMap );
        
    }
  
    
    public final List<WinnerSummary> getWinSummary( ){
        return winnerSummaryList;
    }
   
    
    public final Map<DDPPlayer, Integer> getPlayTotalScoreMap( ){
        return playerTotalScoreMap;
    }
    
    
    protected final List<WinnerSummary> prepareWinnerSummary( DDPMeta ddpMeta, Map<Integer, Set<WinnerResult>> weeklyResultMap ){
        
        List<WinnerSummary> winnerSummaryList               = new ArrayList<>( );
        Map<DDPPlayer, List<WinnerResult>> resultPerPlayer  = prepareResultPerPlayer( weeklyResultMap );
        
        for( Entry<DDPPlayer, List<WinnerResult>> entry : resultPerPlayer.entrySet( ) ){
        
            int totalPoint          = 0;
            DDPPlayer player        = entry.getKey( );
            Set<Integer> weeksWon   = new TreeSet<>();
                        
            for( WinnerResult result : entry.getValue( ) ){
                totalPoint          += result.getTotalScore( );
                
                if( result.isWinner( ) ){
                     weeksWon.add( result.getWeekNumber( ) );
                }
                
            }

            WinnerSummary winnerSummary = new WinnerSummary( ddpMeta, player, totalPoint, weeksWon, entry.getValue( ) );
            winnerSummaryList.add( winnerSummary );      
            
        }
         
        Collections.sort( winnerSummaryList, summaryComparator );
        return winnerSummaryList;
                
    }
    
    
    protected final Map<Integer, Set<WinnerResult>> prepareWinner( DDPMeta ddpMeta, DBService service ){
        Map<Integer, Collection<Schedule>> resultsPerWeekMap    = getResultPerWeek( ddpMeta, pastWeekArray, service );
        Map<Integer, Set<WinnerResult>> winnerMap               = getWeeklyResult( picksPerWeekMap, resultsPerWeekMap );
                
        return winnerMap;
        
    }
    
    
    protected final Map<Integer, Set<WinnerResult>> getWeeklyResult( Map<Integer, Collection<DDPPick>> picksPerWeekMap, Map<Integer, Collection<Schedule>> resultsPerWeekMap ){
        
        Map<Integer, Set<WinnerResult>> winnerResultMap     = new TreeMap<>( );
        Comparator<WinnerResult> resultComparator           = new WinnerComparator();
        
        try {
        
            for( Entry<Integer, Collection<DDPPick>> entry : picksPerWeekMap.entrySet( ) ) {
            
                int weekNumber              = entry.getKey( );
                Collection<DDPPick> picks   = entry.getValue( );
                Set<WinnerResult> winnerSet = winnerResultMap.get( weekNumber );
                if( winnerSet == null ) {
                    winnerSet = new TreeSet<>( resultComparator );
                }
            
                for( DDPPick ddpPick : picks ){
                    Map<NFLTeam, Integer> points = getTeamPoints( weekNumber, ddpPick, resultsPerWeekMap );
                    int totalPoints     = getTotal( points );
                    WinnerResult result = createWinnerResult( weekNumber, ddpPick, totalPoints, points );
                                    
                    winnerSet.add( result );
                    winnerResultMap.put( weekNumber, winnerSet );    
                }
                
                //Since the set is sorted, the first entry is the winner.
                //NOTE: Tie is also handled in the comparator
                
                if( !winnerSet.isEmpty( ) ){
                    WinnerResult first   = (WinnerResult) (winnerSet.toArray( )[0]);                  
                    if( first.getTotalScore( ) > 0 ) {
                        first.markWinner( );              
                    }
                }
            }
            
        }catch( Exception e ) {
            LOGGER.error( "FAILED to prepare weekly result", e );
        }
        
        return winnerResultMap;
        
    }
    
    
    protected final WinnerResult createWinnerResult( int week, DDPPick ddpPick, int totalPoints, Map<NFLTeam, Integer> map ){
        
        NFLTeam[] teams     = map.keySet( ).toArray( new NFLTeam[map.size( )]);
        Integer[] scores    = map.values( ).toArray( new Integer[map.size( )] );
        WinnerResult result = new WinnerResult( week, ddpPick, totalPoints, teams, scores );
                                                    
        return result;
    }
    
     
    
    protected final Map<NFLTeam, Integer> getTeamPoints( int week, DDPPick ddpPick, Map<Integer, Collection<Schedule>> results ){
        
        NFLTeam[] myPickedTeams     = ddpPick.getTeams( );
        Map<NFLTeam, Integer> scores= new LinkedHashMap<>( );
        
        Collection<Schedule> res   = results.get( week );
        
        for( NFLTeam nflTeam : myPickedTeams ){
            if( nflTeam == null) {
                continue;
            }
            
            String myTeam   = nflTeam.getLowerCaseName( );
            
            for( Schedule result : res ){
                
                if( myTeam.equalsIgnoreCase(result.getHomeTeam( ).getLowerCaseName( )) ){
                    scores.put( result.getHomeTeam( ), result.getHomeScore( ) );
                
                }else if( myTeam.equalsIgnoreCase(result.getAwayTeam( ).getLowerCaseName( )) ){
                    scores.put( result.getAwayTeam( ), result.getAwayScore( ) );
                }
                
            }
        }
        
        return scores;
        
    }
                
    
    protected final Map<Integer, Collection<Schedule>> getResultPerWeek( DDPMeta ddpMeta, int[] weekArray, DBService service ){
        
        int nflYear                     = ddpMeta.getGameYear( );
        String seasonType               = ddpMeta.getSeasonType( );
        Map<String, NFLTeam> teamMap    = service.getAllTeams( );
        
        Map<Integer, Collection<Schedule>> resultPerWeek = new HashMap<>( );
        
        for( int week : weekArray ){
            
            String scheduleUrl  = ScheduleManager.createScheduleUrl( seasonType, nflYear, week );
            
            try {
            
                LOGGER.info( "Parsing schedule data to calculate winnings." );
                Map<String, Schedule> scheduleMap = ScheduleManager.parseSchedule( scheduleUrl, teamMap );
                resultPerWeek.put( week, scheduleMap.values( ) );
            
            }catch (Exception e) {
                LOGGER.warn( "FAILED to parse result for week [{}] using url [{}]", week, scheduleUrl );
            }
            
        }
        
        return resultPerWeek;
        
    }


    protected final Map<Integer, Collection<DDPPick>> getPicksPerWeek( DDPMeta ddpMeta, int[] weekArray, DBService service ){
        
        int nflYear                 = ddpMeta.getGameYear( );
        DBConnection connection     = service.getConnection( );
        Map<Integer, Collection<DDPPick>> pickPerWeek = new HashMap<>( );
        
        for( int week : weekArray ) {
            Map<Integer, DDPPick> pMap = connection.loadPicks( nflYear, week, service.getAllTeams( ), service.getAllPlayers( ) );
            if( pMap != null ){
                pickPerWeek.put( week, pMap.values( ) );
            }
        }
        
        return pickPerWeek;
    
    }

    
    protected final Map<DDPPlayer, Integer> createPlayerTotalScoreMap( Map<Integer, Set<WinnerResult>> weeklyResultMap ){
        
        Map<DDPPlayer, Integer> totalScoreMap = new HashMap<>();
        
        for( Set<WinnerResult> resultSet : weeklyResultMap.values( ) ) {
            
            for( WinnerResult result : resultSet ) {
                DDPPlayer player    = result.getPlayer( );
                int weekTotalScore  = result.getTotalScore( );
                
                Integer existingScore = totalScoreMap.get( player );
                int allTotalScore     = (existingScore == null ) ? weekTotalScore : (weekTotalScore + existingScore);
                totalScoreMap.put( player, allTotalScore );                
            }
        }
        
        return totalScoreMap;
    }
    


    protected final int[] getPastWeeks( int currentWeek ){
        
        int weekCount   = (currentWeek == ONE ? ONE : currentWeek - ONE);
        int[] weekArray = new int[weekCount];
        
        for( int i=0; i<weekCount; i++ ){
            weekArray[i]= i + ONE;
        }
            
        LOGGER.info( "Will find winners for following weeks {}", weekArray);
        
        return weekArray;
    
    }
    
    
    protected final int getTotal( Map<NFLTeam, Integer> scores ){
        int total = 0;
        for( int score : scores.values( ) ) {
            total += score;
        }
    
        return total; 
               
    }
     
    
    
    protected final Map<DDPPlayer, List<WinnerResult>> prepareResultPerPlayer( Map<Integer, Set<WinnerResult>> weeklyResultMap ){
        
        Map<DDPPlayer, List<WinnerResult>> map = new HashMap<>( );
        
        for( Entry<Integer, Set<WinnerResult>> entry : weeklyResultMap.entrySet( ) ){
            
            for( WinnerResult result : entry.getValue( ) ){
                
                DDPPlayer player    = result.getPlayer( );
                                
                List<WinnerResult> list = map.get( player );
                if( list == null ){
                    list = new ArrayList<>( );
                }
                
                list.add( result );
                map.put( player, list );
                
            }

        }
        
        return map;
        
    }
    
    
    
    public final static void main( String[] args ) {
        System.setProperty("RDS_USERNAME", "Administrator" );
        System.setProperty("RDS_PASSWORD", "$one1a1-moti2");
    
        DDPMeta ddpMeta     = new DDPMeta( "1.0", false, "REG", LocalDate.now( ), 1, 50 );
        DBService service   = new DBService( ddpMeta, "com.mysql.cj.jdbc.Driver",
                                        "ddp-nfl-db-instance.czsstheiepfi.us-east-1.rds.amazonaws.com", "3306", "ddpnfldb" );
            
        String scheduleUrl = "http://static.nfl.com/ajax/scorestrip?season=2020&seasonType=REG&week=1";

        //Map<String, Schedule> scheduleMap = ScheduleManager.parseSchedule( scheduleUrl, service.getAllTeams( ));
        //System.out.println( scheduleMap );

        WinnerManager manager   = new WinnerManager( ddpMeta, service );
        Map<DDPPlayer, Integer> map = manager.getPlayTotalScoreMap( );
        for( Entry<DDPPlayer, Integer> entry : map.entrySet( ) ){
            System.out.println( entry.getKey( ).getName( ) + " ==> " + entry.getValue( ).intValue( ) );
        }
        
        /*
        LiveScoreParser parser = new LiveScoreParser(  new ScheduleManager(ddpMeta, service)  );
        parser.parseLiveScore( );
        */
        
    }
    
    
    

        
}
