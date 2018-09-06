package com.ddp.nfl.web.servlet;

import org.slf4j.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.core.LoginBean.*;
import com.ddp.nfl.web.pickem.*;
import com.ddp.nfl.web.util.*;

import static com.ddp.nfl.web.util.DDPUtil.*;


@WebServlet(name = "LoginServlet", description = "Servlet to handle user login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{
    
    private final static long serialVersionUID  = 1L;
    private final static  int _5_DAYS_IN_SECONDS= 5 * 24 * 60 * 60;
    private final static String LOGIN_ERROR_MSG = "Username is invalid bad boy!";
    private final static Logger LOGGER          = LoggerFactory.getLogger( "LoginServlet" );
    
   
    @Override
    protected final void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        handleUserLogin( request, response );
    }
     
    
    protected final void handleUserLogin( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        
        ServletContext context  = request.getServletContext( );
        
        PickManager pickManager = (PickManager) context.getAttribute( PICK_MANAGER_KEY );
        if( pickManager == null ){
            handleError( "Internal Error! Pick manager is missing.", request, response );
            return;
        }
        
        String userName         = request.getParameter( "username" );
        if( !isValid(userName) ){
            handleError( LOGIN_ERROR_MSG, request, response );
            return;
        }
        
        String camelCaseUser    = DDPUtil.toCamelCase( userName );
        DDPPlayer player        = pickManager.getAllPlayers( ).get( camelCaseUser );
        if( player == null ){
            String errorMessage = userName + COLON + SPACE + LOGIN_ERROR_MSG;
            handleError( errorMessage, request, response );
            return;
        }

        UserInfo userInfo       = RequestUtil.createUserInfo( userName, request );
                
        //get the old session and invalidate
        HttpSession oldSession  = request.getSession(false);
        if( oldSession != null ){
            oldSession.invalidate();
        }

        //generate a new session
        HttpSession newSession = request.getSession(true);
        newSession.setMaxInactiveInterval( _5_DAYS_IN_SECONDS );
        
        LoginBean loginBean     = LoginBean.createValid( userInfo, player );
        handleSuccess( loginBean, request, response );
        
    }
        

    protected final void handleSuccess( LoginBean loginBean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOGGER.info( "Successfully logged in {} [{}]", loginBean.getUserInfo( ).getUserName( ), loginBean );
        request.getSession( ).setAttribute( LOGIN_RESULT_KEY, loginBean );
        response.sendRedirect( request.getContextPath() + GAME_SERVLET_LINK );
    }
    
    
    protected final void handleError( String errorReason, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOGGER.warn( "Error [{}]",  errorReason );
        request.setAttribute( LOGIN_RESULT_KEY, LoginBean.createError(errorReason, null) );
        response.sendRedirect( request.getContextPath() + GAME_SERVLET_LINK );
    }
   
    
}
