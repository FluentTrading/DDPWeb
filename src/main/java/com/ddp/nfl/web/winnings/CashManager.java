package com.ddp.nfl.web.winnings;

import org.slf4j.*;
import java.util.*;
import java.util.Map.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class CashManager{
    
    private final Map<Integer, CashWin> winnings;
    
    private final static int CASH_PER_WEEK  = 50;
    private final static String NAME        = CashManager.class.getSimpleName( );
    private final static Logger LOGGER      = LoggerFactory.getLogger( NAME );
           
    
    public CashManager(  DDPMeta ddpMeta, DBService service ) {
        this.winnings   = prepareWinnerPerWeek( ddpMeta, service );
    }
    
    
    public final Map<Integer, CashWin> getWinnings( ){
        return winnings;
    }
    
    
    protected final Map<Integer, CashWin> prepareWinnerPerWeek( DDPMeta ddpMeta, DBService service ){
    
        Map<Integer, CashWin> map   = new TreeMap<>( );
        int[] weekArray             = prepareWeekArray( ddpMeta.getWeek( ) );
                
        //Players picks for all the weeks ( 1 to last week)
        Map<Integer, Collection<DDPPick>> picksPerWeek= getPicksPerWeek( ddpMeta, weekArray, service );
        //Results for all the weeks ( 1 to last week)
        Map<Integer, Collection<NFLSchedule>> resultsPerWeek = getResultPerWeek( ddpMeta, weekArray, service );
        
        for( Entry<Integer, Collection<DDPPick>> entry : picksPerWeek.entrySet( ) ) {
            
            int highestPoints    = 0;
            DDPPick winPick      = null;
            Map<NFLTeam, Integer> winScores = null;
            int weekNumber       = entry.getKey( );
                        
            for( DDPPick ddpPick : entry.getValue( ) ){
                Map<NFLTeam, Integer> points = getTeamPoints( weekNumber, ddpPick, resultsPerWeek );
                int totalPoints  = getTotal( points );
                             
                if( totalPoints > highestPoints ) {
                    winPick      = ddpPick;
                    winScores    = points;
                    highestPoints= totalPoints;
                }
            }
            
            CashWin cashWin      = createCashWin( weekNumber, winPick, highestPoints, winScores );
            if( cashWin != null ) {
                map.put( weekNumber, cashWin );    
            }
                                
        }
                
        return map;
        
    }

    
    protected final CashWin createCashWin( int week, DDPPick winPick, int totalScore, Map<NFLTeam, Integer> map ){
        
        if( winPick == null ){
            LOGGER.warn("FAILED to find winner for Week [{}]", week );
            return null;
        }
        
        NFLTeam[] teams     = map.keySet( ).toArray( new NFLTeam[map.size( )]);
        Integer[] scores    = map.values( ).toArray( new Integer[map.size( )] );
        
        boolean hasThree    = map.size( ) == THREE;
        NFLTeam thirdTeam   = hasThree ? teams[TWO] : null;
        int thirdScore      = hasThree ? scores[TWO] : ZERO;
        
        CashWin cashWin     = new CashWin( week, winPick, CASH_PER_WEEK, totalScore, 
                                            teams[0], scores[0], teams[1], scores[1], thirdTeam, thirdScore );
        
        return cashWin;
    }
    
    
    protected final Map<NFLTeam, Integer> getTeamPoints( int week, DDPPick ddpPick, Map<Integer, Collection<NFLSchedule>> results ){
        
        NFLTeam[] myPickedTeams     = ddpPick.getTeams( );
        Map<NFLTeam, Integer> scores= new LinkedHashMap<>( );
        
        Collection<NFLSchedule> res = results.get( week );
        
        for( NFLTeam nflTeam : myPickedTeams ){
            String myTeam   = nflTeam.getName( );
            
            for( NFLSchedule result : res ){
                
                if( myTeam.equalsIgnoreCase(result.getHomeTeam( ).getName( )) ){
                    scores.put( result.getHomeTeam( ), result.getHomeScore( ) );
                
                }else if( myTeam.equalsIgnoreCase(result.getAwayTeam( ).getName( )) ){
                    scores.put( result.getAwayTeam( ), result.getAwayScore( ) );
                }
                
            }
        }
        
        return scores;
        
    }
            
    
    
    protected final Map<Integer, Collection<NFLSchedule>> getResultPerWeek( DDPMeta ddpMeta, int[] weekArray, DBService service ){
        
        int nflYear         = ddpMeta.getYear( );
        String seasonType   = ddpMeta.getSeasonType( );
        Map<Integer, Collection<NFLSchedule>> resultPerWeek = new HashMap<>( );
        
        for( int week : weekArray ){
            String scheduleUrl  = ScheduleParser.prepareUrl( seasonType, nflYear, week );
            Map<String, NFLSchedule> weekResult = ScheduleParser.parseSchedule( scheduleUrl, service.getAllTeams( ) );
            if( weekResult != null ) {
                resultPerWeek.put( week, weekResult.values( ) );
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



    protected final int[] prepareWeekArray( int currentWeek ){
        int priorWeek   = (currentWeek == 1 ? 1 : currentWeek);
        int[] weekArray = new int[ priorWeek];
        
        for( int i=0; i<priorWeek; i++ ){
            weekArray[i]= i + 1;
        }
            
        LOGGER.info( "Will find winners for weeks {}", weekArray);
        
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
        
        System.setProperty("RDS_USERNAME", "hrayapudi" );
        System.setProperty("RDS_PASSWORD", "Wonne22#");
        
        DDPMeta ddpMeta     = new DDPMeta( "1.0", "REG", 2017, 6 );
        DBService service   = new DBService( ddpMeta, "com.mysql.cj.jdbc.Driver",
                                        "wonnedevdb.cmtwfoal5czm.us-east-2.rds.amazonaws.com", "3306", "WonneDB" );
        
        CashManager cashMan = new CashManager( ddpMeta, service );
        Map<Integer, CashWin> winnings     = cashMan.getWinnings( );
        
        System.out.println( winnings );
            
    }
    
}
