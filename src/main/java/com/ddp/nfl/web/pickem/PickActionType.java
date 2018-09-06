package com.ddp.nfl.web.pickem;

public enum PickActionType {
    
    SAVE,
    LOAD,
    UNKNOWN;
    
    
    public final static PickActionType get( String name ){
    
        for( PickActionType type : PickActionType.values( ) ) {
            if( type.name( ).equalsIgnoreCase( name )) {
                return type;
            }
        }
        
        return PickActionType.UNKNOWN;
        
    }
    
}
