package com.ddp.nfl.web.winnings;

import org.slf4j.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class WinnerManager{
    
    private final Map<Integer, WinnerSummary> summaryMap;
    
    private final int[] pastWeekArray;
    private final Map<Integer, Set<WinnerResult>> weeklyResultMap;
    private final Map<DDPPlayer, Integer> playerTotalScoreMap;
    private final Map<Integer, Collection<DDPPick>> picksPerWeekMap;
    
                
    private final static String NAME    = WinnerManager.class.getSimpleName( );
    private final static Logger LOGGER  = LoggerFactory.getLogger( NAME );
           
    
    public WinnerManager( DDPMeta ddpMeta, DBService service ){
        this.pastWeekArray      = getPastWeeks( ddpMeta.getGameWeek( ) );
        this.picksPerWeekMap    = getPicksPerWeek( ddpMeta, pastWeekArray, service );
        this.weeklyResultMap    = prepareWinner( ddpMeta, service );
        this.playerTotalScoreMap= createPlayerTotalScoreMap( weeklyResultMap );
        this.summaryMap         = prepareWinnerSummary( weeklyResultMap );
        
    }
  

    public final Map<Integer, WinnerSummary> getWinSummary( ){
        return summaryMap;
    }
   
    
    public final Map<DDPPlayer, Integer> getPlayTotalScoreMap( ){
        return playerTotalScoreMap;
    }
    
    
    protected final Map<Integer, WinnerSummary> prepareWinnerSummary( Map<Integer, Set<WinnerResult>> weeklyResultMap ){
        
        Map<Integer, WinnerSummary> map = new TreeMap<>( Collections.reverseOrder( ));
        Map<DDPPlayer, List<WinnerResult>> resultPerPlayer = prepareResultPerPlayer( weeklyResultMap );
        
        for( Entry<DDPPlayer, List<WinnerResult>> entry : resultPerPlayer.entrySet( ) ){
        
            int totalPoint  = 0;
            DDPPlayer player= entry.getKey( );
            Set<Integer> weeksWon = new TreeSet<>();
                        
            for( WinnerResult result : entry.getValue( ) ){
                totalPoint += result.getTotalScore( );
                if( result.isWinner( ) ){
                    weeksWon.add( result.getWeekNumber( ) );
                }
            }

            map.put( totalPoint, new WinnerSummary(player, totalPoint, weeksWon, entry.getValue( )) );
        
        }
        
        
        return map;
                
    }
    
    
    protected final Map<Integer, Set<WinnerResult>> prepareWinner( DDPMeta ddpMeta, DBService service ){
        Map<Integer, Collection<Schedule>> resultsPerWeekMap  = getResultPerWeek( ddpMeta, pastWeekArray, service );
        Map<Integer, Set<WinnerResult>> winnerMap = getWeeklyResult( picksPerWeekMap, resultsPerWeekMap );
                
        return winnerMap;
        
    }
    
    
    protected final Map<Integer, Set<WinnerResult>> getWeeklyResult( Map<Integer, Collection<DDPPick>> picksPerWeekMap, 
            Map<Integer, Collection<Schedule>> resultsPerWeekMap ){
        
        Map<Integer, Set<WinnerResult>> winnerResultMap   = new TreeMap<>( );
        Comparator<WinnerResult> resultComparator = new WinnerComparator();
        
        for( Entry<Integer, Collection<DDPPick>> entry : picksPerWeekMap.entrySet( ) ) {
            
            int weekNumber       = entry.getKey( );
            Collection<DDPPick> picks   = entry.getValue( );
            Set<WinnerResult> set = winnerResultMap.get( weekNumber );
            if( set == null ) {
                set = new TreeSet<>( resultComparator );
            }
            
            for( DDPPick ddpPick : picks ){
                Map<NFLTeam, Integer> points = getTeamPoints( weekNumber, ddpPick, resultsPerWeekMap );
                int totalPoints     = getTotal( points );
                WinnerResult result = createWinnerResult( weekNumber, ddpPick, totalPoints, points );
                                
                set.add( result );
                winnerResultMap.put( weekNumber, set );    
            }
            
            //Since the set is sorted, the first entry is the winner.
            //NOTE: Tie is also handled in the comparator
            WinnerResult first = (WinnerResult) (set.toArray( )[0]);
            first.markWinner( );

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
        
        int nflYear         = ddpMeta.getGameYear( );
        String seasonType   = ddpMeta.getSeasonType( );
        Map<String, NFLTeam> teamMap = service.getAllTeams( );
        
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
    
    
    public static void main( String[] args ){

        System.setProperty("RDS_USERNAME", "ddpweb" );
        System.setProperty("RDS_PASSWORD", "1whynopass2");
        
        DDPMeta ddpMeta     = new DDPMeta( "1.0", false, "REG", 2018, 3, 50);
        DBService service   = new DBService( ddpMeta, "com.mysql.cj.jdbc.Driver",
                                        "aa15utan83usopw.ceanhhiadqb0.us-east-2.rds.amazonaws.com", "3306", "WonneDB" );
        
        WinnerManager cashMan = new WinnerManager( ddpMeta, service );
        Map<Integer, WinnerSummary> result = cashMan.getWinSummary( );
        
        for( Entry<Integer, WinnerSummary> entry : result.entrySet( ) ) {
            
            WinnerSummary summary = entry.getValue( );
            
            System.out.println( summary.getPlayer( ).getName( ) + " " + summary.getTotalScore( ) + " " 
                    + summary.getWeeksWon( ));

            for( WinnerResult winResult : summary.getResults( ) ) {
                System.out.println( "Week: " + winResult.getWeekNumber( ) + " " + winResult.getTeam1( ).getCamelCaseName( ) 
                        + " : " +  winResult.get_1Score( ) +
                        " " + winResult.getTeam2( ).getCamelCaseName( ) + " : " +  winResult.get_3Score( ) +
                        " " + winResult.getTeam3( ).getCamelCaseName( ) + " : " +  winResult.get_3Score( ) + ", Total: " + winResult.getTotalScore( ) +
                        " " + winResult.getCardLink( ) );    
            }
            
        }
        
        
    }
        
}
