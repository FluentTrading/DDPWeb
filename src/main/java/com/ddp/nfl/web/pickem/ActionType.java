package com.ddp.nfl.web.pickem;

public enum ActionType {
    
    SAVE,
    LOAD,
    UNKNOWN;
    
    
    public final static ActionType get( String name ){
    
        for( ActionType type : ActionType.values( ) ) {
            if( type.name( ).equalsIgnoreCase( name )) {
                return type;
            }
        }
        
        return ActionType.UNKNOWN;
        
    }
    
}
