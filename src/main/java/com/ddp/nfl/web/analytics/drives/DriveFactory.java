package com.ddp.nfl.web.analytics.drives;

import java.util.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

import org.slf4j.*;
import java.util.Map.*;

import com.ddp.nfl.web.analytics.plays.*;
import com.google.gson.*;


public final class DriveFactory{
    
    private final static String DRIVES_KEY      = "drives";
    private final static String CURR_DRIVE_KEY  = "crntdrv";
    private final static Logger LOGGER          = LoggerFactory.getLogger( "DriveFactory" );    
    
    
    public final static DriveManager create( JsonObject gameObj ){
                
        DriveManager driveManager   = null;
        
        try {

            boolean exists          = gameObj.has( DRIVES_KEY );
            if( !exists ) return null;
            
            JsonElement currDriveE  = gameObj.get( CURR_DRIVE_KEY );
            int currentDrive        = (currDriveE == null || currDriveE.isJsonNull( )) ? -1 : currDriveE.getAsInt( );
            
            JsonElement driveElement= gameObj.get( DRIVES_KEY );
            Map<Integer, Drive> dMap= parseDriveList( driveElement );
            driveManager            = new DriveManager( currentDrive, dMap );
                        
        }catch( Exception e ){
            LOGGER.warn( "FAILED to parse ", e );
        }
        
        return driveManager;
    }
    
    
    protected final static Map<Integer, Drive> parseDriveList( JsonElement driveElement ){
        
        Map<Integer, Drive> map = new TreeMap<>( );
        JsonObject inner        = driveElement.getAsJsonObject( );
        
        try {
            
            for( Entry<String, JsonElement> entry : inner.entrySet( ) ) {
                
                String key          = entry.getKey( );
                JsonElement driveE  = entry.getValue( );
                if( driveE.isJsonPrimitive( ) ) continue;
            
                int driveNum        = Integer.parseInt( key );
                Drive drive         = parseDrive( driveE );
                if( drive != null ) {
                    map.put( driveNum, drive );
                }
            }
            
        }catch( Exception e ){
            LOGGER.warn( "FAILED to parse {}", driveElement, e );
        }
      
        return map;
    }
    
    
    protected final static Drive parseDrive( JsonElement driveElement ){
        
        Drive drive             = null;
        
        try {
            
            JsonObject driveObj = driveElement.getAsJsonObject( );
            String posteam      = safeParse( driveObj, "posteam" );
            int qtr             = safeParseInt( driveObj, "qtr" );
            boolean redZone     = safeParseBoolean( driveObj, "redzone" );
            
            int fds             = safeParseInt( driveObj, "fds" );
            String result       = safeParse( driveObj, "result" );
            int penyds          = safeParseInt( driveObj, "penyds" );
            int ydsgained       = safeParseInt( driveObj, "ydsgained" );
            int numplays        = safeParseInt( driveObj, "numplays" );
            String postime      = safeParse( driveObj, "postime" );
            Start start         = GSON_INSTANCE.fromJson( driveObj.getAsJsonObject( "start" ), Start.class );
            End end             = GSON_INSTANCE.fromJson( driveObj.getAsJsonObject( "end" ), End.class );
            
            PlayManager playMan = parsePlayManager( driveObj );
            
            drive               = new Drive( posteam, qtr, redZone, fds, result, penyds, ydsgained,
                                            numplays, postime, start, end, playMan );
                    
        }catch( Exception e ){
            LOGGER.warn( "FAILED to parse Drive {}", driveElement, e );
        }
        
        return drive;
    }


    protected final static PlayManager parsePlayManager( JsonObject driveObj ) {

        Map<Integer, Play> playMap  = new HashMap<>();
        
        for( Entry<String, JsonElement> entry : driveObj.getAsJsonObject( "plays" ).entrySet( ) ) {
            
            int playTime        = Integer.parseInt(entry.getKey( ));
            JsonObject playObj  = entry.getValue( ).getAsJsonObject( );
            
            int sp              = safeParseInt( playObj, "sp" );
            int qtr             = safeParseInt( playObj, "qtr" );
            int down            = safeParseInt( playObj, "down" );
            
            String time         = safeParse( playObj, "time" );
            String yrdln        = safeParse( playObj, "yrdln" );
            
            int ydstogo         = safeParseInt( playObj, "ydstogo" );
            int ydsnet          = safeParseInt( playObj, "ydsnet" );
            String posteam      = safeParse( playObj, "posteam" );
            String desc         = safeParse( playObj, "desc" );
            String note         = safeParse( playObj, "note" );
            Map<Integer, Player> players = parsePlayerMap( playObj );
            
            Play play           = new Play(sp, qtr, down, time, yrdln, ydstogo, ydsnet, posteam,
                                            desc, note,  players );
             
            playMap.put( playTime, play );
        }
        
        PlayManager playManager = new PlayManager(playMap );
        
        return playManager;
    }

    
    //What does 00-0031203 represent and how do we use it to store Players info?
    protected final static Map<Integer, Player> parsePlayerMap( JsonObject playObj  ){
        Map<Integer, Player> map = new TreeMap<>( );
        
        try {
            
            for( Entry<String, JsonElement> entry : playObj.getAsJsonObject( "players" ).entrySet( ) ) {
                String playerId     = entry.getKey( );
                JsonArray playerArr = entry.getValue( ).getAsJsonArray( );
                            
                int sequence        = 0;
                int statId          = 0;
                int yards           = 0;
                String clubcode     = "";
                String playerName   = "";
                
                for( JsonElement arrElem : playerArr ) {
                    JsonObject arrO = arrElem.getAsJsonObject( );
                    
                    sequence = arrO.get( "sequence" ).getAsInt( );
                    clubcode = arrO.get( "clubcode" ).getAsString( );
                    playerName = arrO.get( "playerName" ).getAsString( );
                    statId      = arrO.get( "statId" ).getAsInt( );
                    yards      = arrO.get( "yards" ).getAsInt( );
                                        
                }
                
                Player player       = new Player( playerId, clubcode, playerName, statId, yards );
                map.put( sequence, player );
                //LOGGER.info( "Seq: " + sequence + " => " + player );
            }
            
        }catch (Exception e) {
            LOGGER.warn( "FAILED to parse player map", e );
        }
        return map;
    }

  
}
