package com.ddp.nfl.web.util;

import java.time.format.*;
import java.util.concurrent.*;

import com.google.gson.*;
import com.ddp.nfl.web.core.*;
import com.ddp.nfl.web.parser.*;


public final class DDPUtil{

    public final static int ZERO                    = 0;
    public final static int ONE                     = 1;
    public final static int TWO                     = 2;
    public final static int THREE                   = 3;
    public final static int TEN                     = 10;
    public final static int NEGATIVE_ONE            = -1;
    
    public final static String DOT                  = ".";
    public final static String EMPTY                = "";
    public final static String COMMA                = ",";
    public final static String SPACE                = " ";
    public final static String COLON                = ":";    
    public final static String PM_TIME              = "PM";
    public final static String L_BRACKET            = "[";
    public final static String R_BRACKET            = "]";
    public final static String NEWLINE              = "<br />";
    public final static String HTML_SPACE           = "&nbsp";
    public final static String PRINT_NEWLINE        = "\n";
    
    public final static String LOGIN_RESULT_KEY     = "LoginResultKey";
    public final static String DB_SERVICE_KEY       = "DBServiceKey";
    public final static String META_INFO_KEY        = "MetaInfoKey";
    public final static String SCHEDULE_KEY         = "ScheduleKey";
    public final static String WINNINGS_MAP_KEY     = "WinningsMapKey";
    public final static String RESULT_MANAGER_KEY   = "ResultManagerKey";
    public final static String GAME_ANALYTICS_KEY   = "GameAnalyticsKey";
    public final static String ANALYTICS_GAME_ID_KEY= "gameId";
    
    public final static String PICK_RESULT_KEY      = "PickResultKey";
    public final static String PICK_MANAGER_KEY     = "PickManagerKey";
        
    public final static String GRR_IMG_PREFIX       = "images/GRR/GRR";
    public final static String GRR_IMG_POSTFIX      = ".gif";
    public final static String BLINK_GREEN_DOT      = "<span class=\"dotBlink\"/>";
        
    public final static String GAME_SERVLET_LINK    = "/game";
    public final static String DDP_GAME_PAGE_LINK   = "/WEB-INF/nfl.jsp";
    public final static String PICK_TAB_LINK        = "/pick.jsp";
    
    public final static String UNSET_GAME_ID_VALUE  = "UNSET";
    
    public final static Gson GSON_INSTANCE          = new GsonBuilder().create();
    public final static JsonParser JSON_PARSER      = new JsonParser();
    
    public final static String RED_ZONE_ICON        = "images/result/touchdown.gif";
    public final static DateTimeFormatter _YYYYMMDDD= DateTimeFormatter.ofPattern("yyyyMMdd");
    public final static DateTimeFormatter _HH_MM    = DateTimeFormatter.ofPattern("hh:mm");
    
    
    //TODO: Replace with real link
    public final static String createLiveScoreUrl( DDPMeta meta ) {
        //return "http://localhost:8080/web/testdata/Live_Score_Reg_2018_1.xml";
        return LiveScoreParser.createLiveScoreUrl( meta.getSeasonType( ), meta.getYear( ), meta.getWeek( ) );
    }
        
    
    public final static boolean isValid( String data ){
        return !( data == null || data.trim( ).isEmpty( ) );            
    }
    
    
    public final static String padRight( String string, int n ){
        return String.format("%1$-" + n + "s", string);  
    }
    

    public final static String padLeft( String string, int n ){
        return String.format("%1$" + n + "s", string);  
    }
    
    
    public final static String toCamelCase( String word ){
        if( !isValid(word) ) return word;
        
        if( word.length( ) == 1) return word.toUpperCase( );
        
        StringBuilder builder= new StringBuilder( word.length() );
        builder.append( Character.toUpperCase( word.charAt(0) ) );
        builder.append( word.substring( 1, word.length( )).toLowerCase( ) );
        
        return builder.toString( );
    }
    
    
    public final static String generateGRRImage( ){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1);
        return GRR_IMG_PREFIX + randomNum + GRR_IMG_POSTFIX;
    }

    
    public final static int safeParseInt( JsonObject gameObj, String key ) {
        if( !gameObj.has(key) ) return NEGATIVE_ONE;
        
        JsonElement element = gameObj.get( key );
        if( element == null ) return NEGATIVE_ONE;
        if( element.isJsonNull( ) ) return NEGATIVE_ONE;
        
        return element.getAsInt( );
    }
    
    
    public final static String safeParse( JsonObject gameObj, String key ) {
        if( !gameObj.has(key) ) return EMPTY;
        
        JsonElement element = gameObj.get( key );
        if( element == null ) return EMPTY;
        if( element.isJsonNull( ) ) return EMPTY;
        
        return element.getAsString( );
    }
    

    public final static boolean safeParseBoolean( JsonObject gameObj, String key ) {
       if( !gameObj.has(key) ) return false;
        
        JsonElement element = gameObj.get( key );
        if( element == null ) return false;
        if( element.isJsonNull( ) ) return false;
        
        return element.getAsBoolean( );
    }
    
    
    public final static void main( String[] args ) {
        for( int i =0; i<100; i++ ) {
        System.out.println(  generateGRRImage( ) );
        }
    }
    
}
