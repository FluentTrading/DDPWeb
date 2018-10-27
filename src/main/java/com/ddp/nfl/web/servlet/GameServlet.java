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
        
        long startTimeNanos         = System.nanoTime( );
        ServletContext context      = request.getServletContext( );
        
        DDPMeta metaInfo            = (DDPMeta) context.getAttribute( META_INFO_KEY );
        if( metaInfo == null ){
            handleError( metaInfo, META_MISSING, "Failed to locate Meta Info!", request, response );
            return;
        }
        
        int weekNumber              = metaInfo.getWeek( );
        DBService service           = (DBService) context.getAttribute( DB_SERVICE_KEY );
        if( service == null || !service.isValid( ) ) {
            handleError( metaInfo, DB_ERROR, "Failed to created DB connection!", request, response );
            return;
        }
        
        
        boolean pickMade            = service.isPicksMade( );
        if( !pickMade ) {
            handleError( metaInfo, PICKS_NOT_MADE, "Picks for week " + weekNumber + " hasn't been made yet!", request, response );
            return;
        }

        
        LiveScoreParser jsonParser  = (LiveScoreParser) context.getAttribute( LIVE_SCORE_PARSER_KEY );
        Map<NFLTeam, LiveScore> map = jsonParser.parseLiveScore( );
        if( map.isEmpty( ) ) {
            handleError( metaInfo, PARSE_ERROR, "FAILED to read NFL data!", request, response );
            return;
        }
        
        
        AnalyticsManager analytics  = (AnalyticsManager) context.getAttribute( GAME_ANALYTICS_KEY );
        if( analytics != null ){
            analytics.gameStatusUpdate( map );
        }
        
        GameResultManager result    = GameResultFactory.packData( metaInfo, map, service );
        handleSuccess( startTimeNanos, result, request, response );   
        
    }
    

    protected final void handleSuccess( long startTimeNanos, GameResultManager result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute( RESULT_MANAGER_KEY, result );
        request.getRequestDispatcher(DDP_GAME_PAGE_LINK).forward(request, response);
    }
    

    protected final void handleError(  DDPMeta metaInfo, ResultCode code, String errorReason, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOGGER.warn("{} {}", code, errorReason );
        
        request.setAttribute( RESULT_MANAGER_KEY, GameResultManager.createInvalid( metaInfo, code, errorReason ) );
        request.getRequestDispatcher(DDP_GAME_PAGE_LINK).forward(request, response);
    }
    
    
    
}
