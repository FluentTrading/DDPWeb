package com.ddp.nfl.web.core;

import java.util.*;


public final class NFLStadium{
    
    private final static Map<String, String> FMT_STADIUM_NAME_MAP = new HashMap<>( );

    static {
        FMT_STADIUM_NAME_MAP.put( "Mercedes-Benz Superdome", "Superdome" );
        FMT_STADIUM_NAME_MAP.put( "Oakland-Alameda County Coliseum", "Oakland Coliseum" );
        FMT_STADIUM_NAME_MAP.put( "Los Angeles Memorial Coliseum", "LA Memorial Coliseum" );
        FMT_STADIUM_NAME_MAP.put( "Broncos Stadium at Mile High", "Mile High" );
        FMT_STADIUM_NAME_MAP.put( "ROKiT Field at Dignity Health Sports Park", "ROKiT Field" );
    }
    
    
    public final static String getFormattedName( String stadiumName ){
        String fmtName  = FMT_STADIUM_NAME_MAP.get( stadiumName );
        fmtName         = ( fmtName == null ) ? stadiumName : fmtName;
        
        return fmtName;                
    }
    
    
}
