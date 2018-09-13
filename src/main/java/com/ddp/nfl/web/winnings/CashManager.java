package com.ddp.nfl.web.winnings;

import org.slf4j.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.parser.*;
import com.ddp.nfl.web.schedule.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class CashManager{
    
    private final Map<Integer, CashWin> winnings;
    
    private final static String NAME    = CashManager.class.getSimpleName( );
    private final static Logger LOGGER  = LoggerFactory.getLogger( NAME );
           
    public CashManager( DDPMeta ddpMeta, DBService service ) {
        this.winnings   = prepareWinnerPerWeek( ddpMeta, service );
    }
  
    
    public final Map<Integer, CashWin> getWinnings( ){
        return winnings;
    }
        
    
    protected final Map<Integer, CashWin> prepareWinnerPerWeek( DDPMeta ddpMeta, DBService service ){
        
        Map<Integer, CashWin> map   = new TreeMap<>( );
        int[] pastWeekArray         = getPastWeeks( ddpMeta.getWeek( ) );
                
        //Players picks for all the weeks ( 1 to last week)
        Map<Integer, Collection<DDPPick>> picksPerWeekMap   = getPicksPerWeek( ddpMeta, pastWeekArray, service );
        
        //Results for all the weeks ( 1 to last week)
        Map<Integer, Collection<Schedule>> resultsPerWeekMap = getResultPerWeek( ddpMeta, pastWeekArray, service );
        
        
        for( Entry<Integer, Collection<DDPPick>> entry : picksPerWeekMap.entrySet( ) ) {
            
            int highestPoints    = 0;
            DDPPick winPick      = null;
            Map<NFLTeam, Integer> winScores = null;
            int weekNumber       = entry.getKey( );
                        
            for( DDPPick ddpPick : entry.getValue( ) ){
                
                Map<NFLTeam, Integer> points = getTeamPoints( weekNumber, ddpPick, resultsPerWeekMap );
                int totalPoints     = getTotal( points );

                //Winner has higher total points than everyone else
                if( totalPoints > highestPoints ){
                    winPick      = ddpPick;
                    winScores    = points;
                    highestPoints= totalPoints;
                }

                //Points are Tied
                boolean pointsTie   = (totalPoints == highestPoints);
                boolean pickedLater = (winPick != null && (ddpPick.getPickOrder( ) > winPick.getPickOrder( )));
                if( pointsTie && pickedLater ) {
                    winPick      = ddpPick;
                    winScores    = points;
                    highestPoints= totalPoints;
                }
            }
            
            CashWin cashWin      = createCashWin( weekNumber, ddpMeta.getCashPerWeek( ), winPick, highestPoints, winScores );
            if( cashWin != null ) {
                map.put( weekNumber, cashWin );    
            }
                                
        }
                
        return map;
        
    }

    
    protected final CashWin createCashWin( int week, int cashPerWeek, DDPPick winPick, int totalScore, Map<NFLTeam, Integer> map ){
        
        if( winPick == null ){
            LOGGER.warn("FAILED to find DDP winner for Week [{}]", week );
            return null;
        }
        
        NFLTeam[] teams     = map.keySet( ).toArray( new NFLTeam[map.size( )]);
        Integer[] scores    = map.values( ).toArray( new Integer[map.size( )] );
        
        boolean hasThree    = map.size( ) == THREE;
        NFLTeam thirdTeam   = hasThree ? teams[TWO] : null;
        int thirdScore      = hasThree ? scores[TWO] : ZERO;
        
        CashWin cashWin     = new CashWin( week, winPick, cashPerWeek, totalScore, 
                                            teams[0], scores[0], teams[1], scores[1], thirdTeam, thirdScore );
        
        return cashWin;
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
        
        int nflYear         = ddpMeta.getYear( );
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
        
        int nflYear                 = ddpMeta.getYear( );
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

    
    
    public static void main( String[] args ){

        System.setProperty("RDS_USERNAME", "ddpweb" );
        System.setProperty("RDS_PASSWORD", "1whynopass2");
        
        DDPMeta ddpMeta     = new DDPMeta( "1.0", "REG", 2018, 1, 50);
        DBService service   = new DBService( ddpMeta, "com.mysql.cj.jdbc.Driver",
                                        "aa15utan83usopw.ceanhhiadqb0.us-east-2.rds.amazonaws.com", "3306", "WonneDB" );
        
        CashManager cashMan = new CashManager( ddpMeta, service );
        Map<Integer, CashWin> winnings     = cashMan.getWinnings( );
        
        System.out.println( winnings );
            
    }
        
}
