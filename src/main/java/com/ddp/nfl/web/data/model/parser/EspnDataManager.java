package com.ddp.nfl.web.data.model.parser;

import org.slf4j.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import com.ddp.nfl.web.analytics.home.Quarter;
import com.ddp.nfl.web.analytics.home.TeamInfo;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.Competition;
import com.ddp.nfl.web.data.model.Competitor;
import com.ddp.nfl.web.data.model.EspnData;
import com.ddp.nfl.web.data.model.Event;
import com.ddp.nfl.web.data.model.Linescore;
import com.ddp.nfl.web.data.model.Situation;
import com.ddp.nfl.web.data.model.Team;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.schedule.EspnSchedule;
import com.ddp.nfl.web.schedule.EspnScheduleManager;
import com.ddp.nfl.web.util.DDPUtil;


public final class EspnDataManager{
    
    private final static String URL_PREFIX  = "http://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard?";
    private final static String SEASON_PART = "&seasontype=";
    private final static String WEEK_PART   = "&week=";
    
    private final static Logger LOGGER = LoggerFactory.getLogger( "EspnDataManager" );
    
    
    public static final EspnData loadData( DDPMeta ddpMeta, DBService service ){
        	
    	EspnData espnData	= null;
    	try {
    		
    		String dataUrl	= createEspnDataUrl( ddpMeta );	
    		String jsonData = readUrl(dataUrl);
    		espnData 		= DDPUtil.JSON_INSTANCE.readValue(jsonData, EspnData.class);
    	
    	}catch( Exception e ) {
    		LOGGER.error("FAILED to read data from ESPN", e );
    	}
    	
    	return espnData;
    }
    
    
    
    public static final Map<NFLTeam, EspnLiveScore> parseLiveScore(  DDPMeta ddpMeta, DBService service, EspnScheduleManager smanager ){
        
        Map<NFLTeam, EspnLiveScore> scores = new HashMap<>();
        
        try {
        	
        	EspnData espnData		 	= loadData( ddpMeta, service);
        	if( espnData == null ) return Collections.emptyMap();
        	
            //long startTime          = System.nanoTime( );
            for( Event event : espnData.getEvents() ){
            	
            	Competition competition = event.getFirstCompetition();
            	Situation situation		= competition.getSituation();
            	Competitor homeComp		= competition.getHomeCompetitor();
            	Competitor awayComp		= competition.getAwayCompetitor();
            	            	
            	String gameId			= event.getId();            	                
                int quarter				= competition.getStatus().getPeriod();                
                String stadium      	= competition.getVenue().getFullName();                
                String timeRemaining	= competition.getStatus().getDisplayClock();
                                                        
                EspnSchedule schedule 	= smanager.getSchedules().get(gameId);
                TeamInfo home       	= createTeamInfo( true, homeComp );
                int homeScore        	= ( home != null ? home.getTotalScore( ) : 0);
                
                TeamInfo away       	= createTeamInfo( false, awayComp );                                                
                int awayScore        	= ( away != null ? away.getTotalScore( ) : 0);
                
                String yl           	= (situation != null ? String.valueOf(situation.getYardLine()) : "");
                String note         	= (situation != null ? situation.getLastPlay().toString() : "");
                int down            	= (situation != null ? situation.getDown() : 0 );
                int togo            	= (situation != null ? situation.getDistance() : 0 );
                boolean isRedzone   	= (situation != null ? situation.isRedZone() : false );
                String teamPossession	= (situation != null ? situation.getPossessionText() : "" );
                
                EspnGameState gameState = EspnGameState.parseState( competition.getStatus() );
                EspnLiveScore liveScore = new EspnLiveScore( gameId, schedule, gameState, homeScore, home,
                                                          awayScore, away, teamPossession, timeRemaining, isRedzone, quarter,
                                                          yl, togo, down, stadium, note, competition.getSituation());
                
                scores.put( liveScore.getHomeTeam( ), liveScore );
                scores.put( liveScore.getAwayTeam( ), liveScore );
                
            }
                
            //long timeTaken          = System.nanoTime( ) - startTime;
            //LOGGER.info( "Time taken to parse live score [{}] ms", TimeUnit.MILLISECONDS.convert( timeTaken, TimeUnit.NANOSECONDS ));
            
            
        }catch( Exception e ) {
            LOGGER.warn( "FAILED to parse live score from ESPN", e);
        }
    
        return scores;
        
    }
    
         
              
    private static final String readUrl( String urlString ) throws Exception{
    	//LOGGER.info( "Loading schedule from {}", urlString );
    	
        BufferedReader reader   = null;
        StringBuilder buffer    = new StringBuilder( 2048 );
        
        try{
            
            URL url             = new URL(urlString);
            reader              = new BufferedReader(new InputStreamReader(url.openStream()));
            
            int read;
            char[] chars        = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        
        return buffer.toString();
        
    }    
    
    
    private static final String createEspnDataUrl( DDPMeta ddpMeta ){
    	int seasonTypeInt = "REG".equalsIgnoreCase(ddpMeta.getSeasonType( )) ? 2 : 1;
    
        StringBuilder builder = new StringBuilder( );
        builder.append( URL_PREFIX ).append( ddpMeta.getGameYear( ) );
        builder.append( SEASON_PART ).append( seasonTypeInt );
        builder.append( WEEK_PART ).append( ddpMeta.getGameWeek( ) );
        
        return builder.toString( );
        
    }
    
    
    private final static TeamInfo createTeamInfo( boolean isHome, Competitor competitor ){
        
        TeamInfo teamInfo       = null; 
        
        try{
           
        	Team team			= competitor.getTeam();
            String teamName     = ( team != null && team.getName() != null ) ? team.getName().toLowerCase() : "Missing";                        
            Map<Quarter, Integer> sMap = parseScores( competitor.getLineScores() );
            teamInfo            = new TeamInfo( isHome, teamName, sMap, "", competitor.getRecords() );
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse TeamInfo for [{}]", competitor, e );
        }
                
        return teamInfo;
    }
    
    
    public final static Map<Quarter, Integer> parseScores( List<Linescore> scores ){

    	Map<Quarter, Integer> map = new HashMap<>( );
        
        try{
            
        	if( scores != null ) {
        		for( int i=0; i<scores.size(); i++ ){
        			Linescore score = scores.get(i);
        			Quarter quarter = Quarter.get( String.valueOf(i+1) );
        			int quarterScore= (int)score.getValue();                
        			map.put( quarter, quarterScore );
        		}
        	}            
            
        }catch(Exception e ){
            LOGGER.warn( "FAILED to parse Scores", e );
        }
          
        return map;
    }
    
}
