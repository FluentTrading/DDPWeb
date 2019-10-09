package com.ddp.nfl.web.servlet;

import java.io.*;
import java.util.*;
import org.slf4j.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.pickem.*;
import javax.servlet.annotation.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


@WebServlet(name = "PickServlet", description = "Servlet to store the Team Picks", urlPatterns = {"/pick"})
public class PickServlet extends HttpServlet {

    private final static long serialVersionUID  = 1L;
    private final static String EXPECTED_PASS   = "1whynopass";
    private final static String PASS_SESSION_KEY= "isLoggedIn";
    private final static String ERROR_MESSAGE   = "Internal Error! ";
    private final static Logger LOGGER          = LoggerFactory.getLogger( "PickServlet" );

    
    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
            
    
    @Override
    public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                        
        ServletContext context  = request.getServletContext( );
        
        PickManager pickManager = (PickManager) context.getAttribute( PICK_MANAGER_KEY );
        if( pickManager == null ){
            handleError( ERROR_MESSAGE, request, response );
        }
         
        PickActionType action   = PickActionType.get(request.getParameter("action"));
        boolean isPasswordValid = isPasswordValid( request );
        boolean enforcePassword = (PickActionType.SAVE == action && !isPasswordValid);
        if( enforcePassword ) {
            handleError( "Unauthorized! You are not permissioned to save picks.", request, response );            
            return;
        }
                
        int pickForWeek         = parseWeekParam( request.getParameter("weekNumber") );
        if( pickForWeek == NEGATIVE_ONE ){
            handleError( ERROR_MESSAGE + "Week number parameter is missing.", request, response );
            return;
        }
                
        PickResult pickResult   = handleAction( action, pickForWeek, pickManager, request );
        if( pickResult == null ){
            handleError( ERROR_MESSAGE + "Failed to handle action " + action, request, response );
            return;
        }
        
        if( !pickResult.isValid( ) ) {
            handleError( pickResult.getMessage( ), request, response );
            return;
        }
        
        handleSuccess( pickResult, request, response );
                
    }
    

    protected final PickResult handleAction( PickActionType type, int pickForWeek, PickManager pickManager, HttpServletRequest request ) {
        
        PickResult result   = null;
                
        switch( type ) {
            
            case LOAD:{
                String msg  = "Picks successfully loaded for week " + pickForWeek;
                result      = loadPicks( msg, pickForWeek, pickManager );
                break;
                
            }case SAVE:{
                result      = savePicks( pickForWeek, pickManager, request );
                if( result.isValid() ){
                    String msg= "Picks successfully saved for week " + pickForWeek;
                    result    = loadPicks( msg, pickForWeek, pickManager ); 
                }
                break;
                
            }default:
                result      = PickResult.createInvalid( type + " is unsupported" );
        }
        
        return result;
        
    }
    

    protected final PickResult loadPicks( String headerMsg, int pickForWeek, PickManager pickManager ){
        
        PickResult pickResult       = null;
        
        try {
            
            Collection<DDPPick> picks= pickManager.loadTeamsPicked( pickForWeek );
            boolean picksNotMade     = ( picks.isEmpty( ) );
            if( picksNotMade ){
                return PickResult.createInvalid( "Picks have not been made for week " + pickForWeek );
            }
        
            pickResult               = PickResult.createValid( headerMsg, picks );
        
        }catch( Exception e) {
            LOGGER.warn( "FAILED to load picks", e );
        }
        
        return pickResult;
    }
    
    

    protected final PickResult savePicks( int pickForWeek, PickManager pickManager, HttpServletRequest request ){
        
        PickResult pickResult   = null;
        
        try {            
            
            String player       = request.getParameter("player");
            String team1        = request.getParameter("team1");
            String team2        = request.getParameter("team2");
            String team3        = request.getParameter("team3");
            boolean isValid     = isValid(player) && isValid(team1) && isValid(team2) && isValid(team3);
            if( !isValid ) {
                return PickResult.createInvalid( "Invalid selection, Player and all 3 teams must be selected!" );
            }
            
            pickResult          = pickManager.savePicks( pickForWeek, player, team1, team2, team3 );
            
        }catch( Exception e) {
            LOGGER.warn( "FAILED to process picks", e );
        }
        
        return pickResult;
        
    }
    
    
    protected final boolean isPasswordValid( HttpServletRequest request ){
        
        Object isLoggedInStr    = request.getSession( ).getAttribute( PASS_SESSION_KEY );
        if( isLoggedInStr != null ) {
            boolean isLoggedIn      = (Boolean) isLoggedInStr;
            if( isLoggedIn ) return true;
        }
                
        String password         = request.getParameter( "password" );
        boolean isPassValid     = EXPECTED_PASS.equals( password );
        if( isPassValid ) {
            request.getSession( ).setAttribute( PASS_SESSION_KEY, Boolean.TRUE );
        }
               
        return isPassValid;
        
        
    }
        
    
    protected final void handleSuccess( PickResult result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute( PICK_RESULT_KEY, result );
        request.getRequestDispatcher(PICK_TAB_LINK).forward(request, response);
    }
    
    
    protected final void handleError( String errorMessage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOGGER.warn("ERROR! {} ", errorMessage );
        request.setAttribute( PICK_RESULT_KEY, PickResult.createInvalid( errorMessage ));
        request.getRequestDispatcher(PICK_TAB_LINK).forward(request, response);        
    }


    protected final int parseWeekParam( String weekString ){
        int paramWeekNumber  = NEGATIVE_ONE;
        try {
            paramWeekNumber = Integer.parseInt( weekString );
        }catch (Exception e) {
            LOGGER.warn("FAILED to parse week number param [{}]", weekString );
        }
        
        return paramWeekNumber;
    }
    
}