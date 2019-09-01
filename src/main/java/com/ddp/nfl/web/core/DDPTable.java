package com.ddp.nfl.web.core;

public enum DDPTable{
    
    NFL_TEAM    ( "NFLTeam"),
    TEAM_RECORD ( "TeamRecord"),
    DDP_PLAYER  ( "DDPPlayer"),
    DDP_PICK    ( "DDPPick"),
    UNKNOWN     ("");
    

    private final String tableName;
    
    private DDPTable(  String tableName ){
        this.tableName = tableName;
    }
    
    public final String getTableName( ){
        return tableName;
    }
    
    public final static DDPTable fromCode( String tableName ){
        for( DDPTable type : DDPTable.values( ) ) {
            if( type.tableName.equalsIgnoreCase(tableName) ){
                return type;
            }
        }
        
        return UNKNOWN;
    }


}
