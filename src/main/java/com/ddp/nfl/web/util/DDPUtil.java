package com.ddp.nfl.web.util;

import java.time.format.*;
import java.util.concurrent.*;

import com.google.gson.*;


public final class DDPUtil{

    public final static int ZERO                    = 0;
    public final static int ONE                     = 1;
    public final static int TWO                     = 2;
    public final static int THREE                   = 3;
    public final static int FOUR                    = 4;
    public final static int TEN                     = 10;
    public final static int NEGATIVE_ONE            = -1;
    
    public final static String DOT                  = ".";
    public final static String EMPTY                = "";
    public final static String COMMA                = ",";
    public final static String SPACE                = " ";
    public final static String SPACE_DASH           = " - ";
    public final static String COLON                = ":";
    public final static String AMPERSAND            = "&";
    public final static String AT                   = "at";  
    public final static String PM_TIME              = "PM";
    public final static String L_BRACKET            = "[";
    public final static String R_BRACKET            = "]";
    public final static String NEWLINE              = "<br />";
    public final static String HTML_SPACE           = "&nbsp";
    public final static String PRINT_NEWLINE        = "\n";
    
    public final static String DB_SERVICE_KEY       = "DBServiceKey";
    public final static String META_INFO_KEY        = "MetaInfoKey";
    public final static String SCHEDULE_KEY         = "ScheduleKey";
    public final static String CASH_MANAGER_KEY     = "CashManagerKey";
    public final static String ARCHIVE_MANAGER_KEY  = "ArchiveManagerKey";
    
    public final static String RESULT_MANAGER_KEY   = "ResultManagerKey";
    public final static String GAME_ANALYTICS_KEY   = "GameAnalyticsKey";
    public final static String ANALYTICS_GAME_ID_KEY= "gameId";
            
    public final static String PICK_RESULT_KEY      = "PickResultKey";
    public final static String PICK_MANAGER_KEY     = "PickManagerKey";
    
    public final static String LIVE_SCORE_PARSER_KEY= "LiveScoreParserKey";
    public final static String BLINK_GREEN_DOT      = "<span class=\"dotBlink\"/>";
        
    public final static String GAME_SERVLET_LINK    = "/game";
    public final static String DDP_GAME_PAGE_LINK   = "/WEB-INF/nfl.jsp";
    public final static String PICK_TAB_LINK        = "/pick.jsp";
    
    public final static Gson GSON_INSTANCE          = new GsonBuilder().create();
    public final static JsonParser JSON_PARSER      = new JsonParser();
    
    public final static String RED_ZONE_ICON        = "images/misc/touchdown.gif";
    public final static DateTimeFormatter _YYYYMMDDD= DateTimeFormatter.ofPattern("yyyyMMdd");
    public final static DateTimeFormatter _HH_MM    = DateTimeFormatter.ofPattern("hh:mm");
    
   // public final static String JSON_MINI_SCORE_URL  = "http://localhost:8080/web/testdata/Live_Game_Day_Week3.json";
    public final static String JSON_MINI_SCORE_URL  = "http://static.nfl.com/liveupdate/scores/scores.json";
        
    public final static boolean isValid( String data ){
        return !( data == null || data.trim( ).isEmpty( ) );            
    }
       
    
    public final static String toCamelCase( String word ){
        if( !isValid(word) ) return word;
        
        if( word.length( ) == 1) return word.toUpperCase( );
        
        StringBuilder builder= new StringBuilder( word.length() );
        builder.append( Character.toUpperCase( word.charAt(0) ) );
        builder.append( word.substring( 1, word.length( )).toLowerCase( ) );
        
        return builder.toString( );
    }
    
       
    public final static String generateMainImage(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 7 + 1);
        return "images/main/" + randomNum + ".gif";
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
        
        return element.getAsString( ).trim( );
    }
    
    
    public final static String safeParse( JsonObject gameObj, String key1, String key2 ) {
        if( !gameObj.has(key1) ) return EMPTY;
        
        JsonObject object1 = gameObj.getAsJsonObject( key1 );
        if( object1 == null ) return EMPTY;
        if( object1.isJsonNull( ) ) return EMPTY;
        
        JsonElement element =   object1.get( key2 );
        if( element == null ) return EMPTY;
        if( element.isJsonNull( ) ) return EMPTY;
        
        return element.getAsString( ).trim( );
    }

    
    public final static boolean safeParseBoolean( JsonObject gameObj, String key ) {
       if( !gameObj.has(key) ) return false;
        
        JsonElement element = gameObj.get( key );
        if( element == null ) return false;
        if( element.isJsonNull( ) ) return false;
        
        return element.getAsBoolean( );
    }
    
    
}

