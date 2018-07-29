package com.ddp.nfl.web.analytics.home;

import java.util.*;

public enum Quarter {

    FIRST   ("1"),
    SECOND  ("2"),
    THIRD   ("3"),
    FOURTH  ("4"),
    OVERTIME("5"),
    TOTAL   ("T"),
    UNKNOWN ("");
    
    private final String quarterKey;
    private final static Map<String, Quarter> MAP = new HashMap<>( );
    
    static {
        for( Quarter qEnum : Quarter.values( ) ) {
            MAP.put( qEnum.quarterKey, qEnum );
        }
    }
    
    private Quarter( String quarterKey ){
        this.quarterKey = quarterKey;
    }
    
    public final String get( ){
        return quarterKey;
    }

    
    public final static Quarter get( String quarterKey ){
        Quarter qEnum = MAP.get( quarterKey );
        qEnum = (qEnum == null ) ? UNKNOWN : qEnum;
        
        return qEnum;
    }
    
    
    
}
