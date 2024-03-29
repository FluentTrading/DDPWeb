package com.ddp.nfl.web.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.data.model.parser.*;
import com.ddp.nfl.web.database.*;
import com.ddp.nfl.web.match.*;
import com.ddp.nfl.web.schedule.EspnScheduleManager;
import com.ddp.nfl.web.winnings.*;

import static com.ddp.nfl.web.util.DDPUtil.*;
import static com.ddp.nfl.web.match.ResultCode.*;


@WebServlet(name = "EspnGameServlet", description = "Servlet to keep track of score", urlPatterns = {"/game"})
public class EspnGameServlet extends HttpServlet{

    private final static long serialVersionUID  = 1L;
        
    
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

        EspnScheduleManager schedule= (EspnScheduleManager) context.getAttribute( ESPN_SCHEDULE_KEY );
        Map<NFLTeam, EspnLiveScore> map = EspnDataManager.parseLiveScore(metaInfo, dbService, schedule);        
        if( map.isEmpty( ) ) {
            handleError( metaInfo, PARSE_ERROR, "NFL data for week " +  metaInfo.getGameWeek( ) + " is not yet available.", request, response );
            return;
        }
        
        
        WinnerManager cashManager   = (WinnerManager) context.getAttribute( CASH_MANAGER_KEY );
                
        EspnGameResultManager result    = EspnGameResultFactory.packData( metaInfo, map, dbService, cashManager );
        handleSuccess( result, request, response );   
        
    }



    protected final void handleSuccess( EspnGameResultManager result, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute( ESPN_RESULT_MANAGER_KEY, result );
        request.getRequestDispatcher(ESPN_DDP_GAME_PAGE_LINK).forward(request, response);
    }
    

    protected final void handleError( DDPMeta metaInfo, ResultCode code, String errorReason, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //LOGGER.warn("{} {}", code, errorReason );
        
        request.setAttribute( ESPN_RESULT_MANAGER_KEY, EspnGameResultManager.createInvalid( metaInfo, code, errorReason ) );
        request.getRequestDispatcher(ESPN_DDP_GAME_PAGE_LINK).forward(request, response);
    }
    
    
}
