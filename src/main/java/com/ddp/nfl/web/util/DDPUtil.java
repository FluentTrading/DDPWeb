package com.ddp.nfl.web.util;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import com.ddp.nfl.web.core.*;
import com.google.gson.*;


public final class DDPUtil{

    public final static int ZERO                    = 0;
    public final static int ONE                     = 1;
    public final static int TWO                     = 2;
    public final static int THREE                   = 3;
    public final static int NEGATIVE_ONE            = -1;
        
    public final static String EMPTY                = "";
    public final static String COMMA                = ",";
    public final static String SPACE                = " ";
    public final static String NEWLINE              = "<br />";
    public final static String PRINT_NEWLINE        = "\n";
        
    public final static String DB_SERVICE_KEY       = "DBServiceKey";
    public final static String META_INFO_KEY        = "MetaInfoKey";
    public final static String JSON_PARSER_KEY      = "JsonParserKey";
    public final static String SCHEDULE_KEY         = "ScheduleKey";
    public final static String WINNINGS_MAP_KEY     = "WinningsMapKey";
    public final static String RESULT_MANAGER_KEY   = "ResultManagerKey";
    
    public final static String PICK_RESULT_KEY      = "PickResultKey";
    public final static String PICK_MANAGER_KEY     = "PickManagerKey";
        
    public final static String NFL_TAB_LINK         = "/WEB-INF/DDPNFL.jsp";
    public final static String CASH_TAB_LINK        = "/WEB-INF/DDPCash.jsp";
    public final static String RULES_TAB_LINK       = "/WEB-INF/DDPRules.jsp";
    
    public final static String PLAYER_LOGO_PREFIX   = "images/players/";
    public final static String PLAYER_LOGO_SUFFIX   = ".ico";
    public final static String ANON_PLAYER_ICON     = PLAYER_LOGO_PREFIX + "Anon" + PLAYER_LOGO_SUFFIX;
    
    public final static String NFL_LOGO_PREFIX      = "images/teams/";
    public final static String ROUND_ICON_SUFFIX    = "2";
    public final static String SQUARE_ICON_SUFFIX   = "";
    public final static String NFL_LOGO_SUFFIX      = ".png";
    public final static String MISSING_TEAM_LOGO    = NFL_LOGO_PREFIX + "Missing" + NFL_LOGO_SUFFIX;
    
    //public final static String PROD_URL_PREFIX    = "http://localhost:8084/DDPNFLWeb";
    public final static String NFL_DATA_URL       = "http://localhost:8080/web/testdata/2018Week1Data.json";
    //public final static String NFL_DATA_URL         = "http://www.nfl.com/liveupdate/scorestrip/ss.json";
    //public final static String PROD_URL_PREFIX    = "http://ddpscore.us-east-2.elasticbeanstalk.com";
    
    public final static String GRR_IMG_PREFIX       = "../web/images/GRR/GRR";
    public final static String GRR_IMG_POSTFIX      = ".gif";
    
    public final static Gson GSON_INSTANCE          = new GsonBuilder().create();
    public final static JsonParser JSON_PARSER      = new JsonParser();
        
    
    public final static boolean isValid( String data ){
        return !( data == null || data.trim( ).isEmpty( ) );            
    }
    
    
    public final static String padRight( String string, int n ){
        return String.format("%1$-" + n + "s", string);  
    }
    

    public final static String padLeft( String string, int n ){
        return String.format("%1$" + n + "s", string);  
    }
   
    
    public final static String getPlayerIcon( String playerName ){
        return PLAYER_LOGO_PREFIX + playerName + PLAYER_LOGO_SUFFIX;
    }
    
    
    public final static String getTeamRoundIcon( String team ){
        return NFL_LOGO_PREFIX + team + ROUND_ICON_SUFFIX + NFL_LOGO_SUFFIX;
    }
    
    public final static String getTeamSquareIcon( String team ){
        return NFL_LOGO_PREFIX + team + SQUARE_ICON_SUFFIX + NFL_LOGO_SUFFIX;
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
        int randomNum = ThreadLocalRandom.current().nextInt(1, 7 + 1);
        return GRR_IMG_PREFIX + randomNum + GRR_IMG_POSTFIX;
    }

    
    public final static String getFormattedTeam( NFLTeam team ) {
        return (team == null) ? "" : DDPUtil.toCamelCase( team.getName( ) );
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
        
    
    public final static String readUrl( String urlString ) throws Exception{
        
        BufferedReader reader   = null;
        StringBuilder buffer    = new StringBuilder( 1024 );
        
        try{
            
            URL url             = new URL(urlString);
            reader              = new BufferedReader(new InputStreamReader(url.openStream()));
            
            int read;
            char[] chars        = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        
        return buffer.toString();
        
    }
    
    
    public static void main( String[] args ) {
        for( int i=0; i< 200; i ++ ) {
            String name = generateGRRImage( );
            System.out.println( name );
        }
    }
    
}
