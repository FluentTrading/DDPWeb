package com.ddp.nfl.web.servlet;

import org.slf4j.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.ddp.nfl.web.core.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


@WebServlet(name = "AnalyticsServlet", description = "Servlet to track game analytics", urlPatterns = {"/analytics"})
public class AnalyticsServlet extends HttpServlet{
        
    private final static long serialVersionUID  = 1L;
    private final static Logger LOGGER          = LoggerFactory.getLogger( "AnalyticsServlet" );
    
   
    @Override
    protected final void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        handleAnalyticsRequest( request, response );
    }
     
    protected final void handleAnalyticsRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        String gameId           = request.getParameter( ANALYTICS_GAME_ID_KEY );
        boolean isGameIdUnset   = UNSET_GAME_ID_VALUE.equals( gameId );
        
        if( isGameIdUnset ){
            //handleGameIdUnset( request, response );
            //This will simply close the window now
                 
        }else if( isValid(gameId) ) {
            handleGameIdSet( gameId, request, response );
            
        }else {
            LOGGER.warn( "Analytics servlet not called from set/unset buttons.");
        }
        
    }
    
    
    protected final void handleGameIdSet( String gameId, HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        LOGGER.info( "User has asked to set analytics for gameId [{}]", gameId );
        
        if( !isValid(gameId) ){
            handleError( "GameId selected is invalid.", request, response );
            return;
        }
        
        //LOOK up GameAnalticsManager and call a method to return the html
        handleSuccess( request, response );
        
    }
        

    protected final void handleSuccess( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.sendRedirect( request.getContextPath() + GAME_SERVLET_LINK );
    }
    
    
    protected final void handleError( String errorReason, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOGGER.warn( "Error [{}]",  errorReason );
        response.sendRedirect( request.getContextPath() + GAME_SERVLET_LINK );
    }
   
    
}
