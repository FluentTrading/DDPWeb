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
        
        LoginBean loginBean     = ( LoginBean) request.getSession( ).getAttribute( LOGIN_RESULT_KEY );
        if( loginBean == null ){
            handleError( "User must be logged in before executog analytics calls.", request, response );
            return;
        }
        
        String gameId           = request.getParameter( ANALYTICS_GAME_ID_KEY );
        boolean isGameIdUnset   = UNSET_GAME_ID_VALUE.equals( gameId );
        
        if( isGameIdUnset ){
            handleGameIdUnset( loginBean, request, response );
                 
        }else if( isValid(gameId) ) {
            handleGameIdSet( gameId, loginBean, request, response );
            
        }else {
            LOGGER.warn( "Analytics servlet not called from set/unset buttons.");
        }
        
    }
    
    
    protected final void handleGameIdSet( String gameId, LoginBean loginBean, HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        LOGGER.info( "[{}] has asked to set analytics for gameId [{}]", loginBean.getPlayer( ).getName( ), gameId );
        
        if( !isValid(gameId) ){
            handleError( "GameId selected is invalid.", request, response );
            return;
        }
        
        loginBean.setGameId( gameId );
        LOGGER.info( "Analytics gameId set to [{}] for user [{}]", gameId, loginBean.getPlayer( ).getName( ) );

        handleSuccess( request, response );
        
    }
    
    
    protected final void handleGameIdUnset( LoginBean loginBean, HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        LOGGER.info( "[{}] has asked to unset analytics", loginBean.getPlayer( ).getName( ) );
        
        String oldGameId    = loginBean.getGameId( );
        loginBean.setGameId( null );
        LOGGER.info( "Unset GameId for user {} from [{} -> {}]", loginBean.getPlayer( ).getName( ), oldGameId, loginBean.getGameId( ) );
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
