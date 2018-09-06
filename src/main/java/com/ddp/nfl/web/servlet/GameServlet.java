package com.ddp.nfl.web.servlet;

import java.io.*;
import java.util.*;
import org.slf4j.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.match.*;
import com.ddp.nfl.web.parser.*;
import com.ddp.nfl.web.schedule.*;

import static com.ddp.nfl.web.util.DDPUtil.*;
import static com.ddp.nfl.web.match.ResultCode.*;


@WebServlet(name = "GameServlet", description = "Servlet to keep track of score", urlPatterns = {"/Game"})
public class GameServlet extends HttpServlet {

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
                
        ServletContext context  = request.getServletContext( );
        
        DDPMeta metaInfo        = (DDPMeta) context.getAttribute( META_INFO_KEY );
        if( metaInfo == null ){
            handleError( metaInfo, META_MISSING, "Internal Error! Failed to locate Meta Info!", request, response );
        }
        
        int weekNumber          = metaInfo.getWeek( );
        DBService service       = (DBService) context.getAttribute( DB_SERVICE_KEY );
        if( service == null || !service.isValid( ) ) {
            handleError( metaInfo, DB_ERROR, "Internal DB Error! Server on FIRE mon!", request, response );
            return;
        }
        
        ScheduleManager schMan  =  (ScheduleManager) context.getAttribute( SCHEDULE_KEY );
        if( !schMan.isValid() ) {
            handleError( metaInfo, SCHEDULE_MISSING, "Internal Error! Failed to find Schedule for week " + weekNumber + "!", request, response );
            return;
        }
                
        boolean pickMade        = service.isPicksMade( );
        if( !pickMade ) {
            handleError( metaInfo, PICKS_NOT_MADE, "Picks for Week " + weekNumber + " hasn't been made!", request, response );
            return;
        }
                
        String gameData         = NFLDataParser.loadGameData( NFL_DATA_URL );
        if( !isValid(gameData) ){
            handleError( metaInfo, PARSE_ERROR, "Internal Error! FAILED to read NFL data!", request, response );
            return;
        }
        
        Map<NFLTeam, MatchScore> map= NFLDataParser.parseGameData( gameData, schMan, service );
        if( map.isEmpty( ) ) {
            handleError( metaInfo, PARSE_ERROR, "Internal Error! FAILED to parse NFL data!", request, response );
            return;
        }
                
        GameResultManager result= MatchManager.packData( metaInfo, map, service );
        handleSuccess( result, request, response );   
        
    }
    

    protected final void handleSuccess( GameResultManager resultMan, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute( RESULT_MANAGER_KEY, resultMan );
        request.getRequestDispatcher(NFL_TAB_LINK).forward(request, response);
    }
    
    
    protected final void handleError(  DDPMeta metaInfo, ResultCode code, String errorReason, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOGGER.warn("ERROR! {} {}", code, errorReason );
        request.setAttribute( RESULT_MANAGER_KEY, GameResultManager.createInvalid( metaInfo, code, errorReason ) );
        request.getRequestDispatcher(NFL_TAB_LINK).forward(request, response);
    }
    
    
    
}
