package com.ddp.nfl.web.servlet;

import java.io.*;
import java.util.*;

import org.slf4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.ddp.nfl.web.analytics.core.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.match.*;
import com.ddp.nfl.web.parser.*;
import com.ddp.nfl.web.winnings.*;

import static com.ddp.nfl.web.util.DDPUtil.*;
import static com.ddp.nfl.web.match.ResultCode.*;


@WebServlet(name = "GameServlet", description = "Servlet to keep track of score", urlPatterns = {"/game"})
public class GameServlet extends HttpServlet{

    private final static long serialVersionUID  = 1L;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "GameServlet" );
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performService( request, response);
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performService( request, response );
    }
    
         
    protected final void performService( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        ServletContext context      = request.getServletContext( );
        
        DDPMeta metaInfo            = (DDPMeta) context.getAttribute( META_INFO_KEY );
        if( metaInfo == null ){
            handleError( metaInfo, META_MISSING, "Failed to locate Meta Info!", request, response );
            return;
        }
        
        
        DBService dbService         = (DBService) context.getAttribute( DB_SERVICE_KEY );
        if( dbService == null || !dbService.isValid( ) ) {
            handleError( metaInfo, DB_ERROR, "Failed to created DB connection!", request, response );
            return;
        }
        
        boolean isSeasonOver        = metaInfo.isSeasonOver( );
        if( isSeasonOver ){
            handleError( metaInfo, SEASON_FINISHED, "DDP NFL Season " + metaInfo.getGameYear( ) + " is over!", request, response );
            return;
        }
        
       
        boolean hasSeasonStarted    = metaInfo.hasSeasonStarted( );
        if( !hasSeasonStarted ) {
            handleError( metaInfo, SEASON_NOT_STARTED, "DDP NFL starts on " + metaInfo.getStartDate( ), request, response );
            return;
        }
                      
        
        boolean pickMade            = dbService.isPicksMade( );
        if( !pickMade ) {
            handleError( metaInfo, PICKS_NOT_MADE, "Picks for week " + metaInfo.getGameWeek( ) + " hasn't been made yet!", request, response );
            return;
        }

        
        LiveScoreParser jsonParser  = (LiveScoreParser) context.getAttribute( LIVE_SCORE_PARSER_KEY );
        Map<NFLTeam, LiveScore> map = jsonParser.parseLiveScore( );
        if( map.isEmpty( ) ) {
            handleError( metaInfo, PARSE_ERROR, "NFL data for week " +  metaInfo.getGameWeek( ) + " is not yet available.", request, response );
            return;
        }
                
        
        AnalyticsManager analytics  = (AnalyticsManager) context.getAttribute( GAME_ANALYTICS_KEY );
        if( analytics != null ){
            analytics.gameStatusUpdate( map );
        }
        
        
        WinnerManager cashManager   = (WinnerManager) context.getAttribute( CASH_MANAGER_KEY );
                
        GameResultManager result    = GameResultFactory.packData( metaInfo, map, dbService, cashManager );
        handleSuccess( result, request, response );   
        
    }



    protected final void handleSuccess( GameResultManager result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute( RESULT_MANAGER_KEY, result );
        request.getRequestDispatcher(DDP_GAME_PAGE_LINK).forward(request, response);
    }
    

    protected final void handleError( DDPMeta metaInfo, ResultCode code, String errorReason, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //LOGGER.warn("{} {}", code, errorReason );
        
        request.setAttribute( RESULT_MANAGER_KEY, GameResultManager.createInvalid( metaInfo, code, errorReason ) );
        request.getRequestDispatcher(DDP_GAME_PAGE_LINK).forward(request, response);
    }
    
    
}
