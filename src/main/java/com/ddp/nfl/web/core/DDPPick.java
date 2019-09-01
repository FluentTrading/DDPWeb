package com.ddp.nfl.web.core;

import java.util.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

public final class DDPPick{
    
    private final int pickOrder;
    private final DDPPlayer player;
    private final NFLTeam[] teams;
    
    public DDPPick( int pickOrder, DDPPlayer player, NFLTeam[] teams ){
        this.pickOrder  = pickOrder;
        this.player     = player;
        this.teams      = teams;
    }
    
    
    public final int getPickOrder( ){
        return pickOrder;
    }
    
    public final DDPPlayer getPlayer( ){
        return player;
    }
    
    public final NFLTeam[] getTeams( ){
        return teams;
    }    
    
    
    public final String toTeamString( ) {
        
        if( teams == null || teams.length == 0 ) {
            return "Teams Empty";
        }
        
        StringBuilder builder = new StringBuilder( );
        builder.append( teams[0].getLowerCaseName( ) ).append( SPACE );
        builder.append( teams[1].getLowerCaseName( ) ).append( SPACE );
        
        if( teams[2] != null ) {
            builder.append( teams[2].getLowerCaseName( ) );
        }
        
        return builder.toString( );
    
    }
    

    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( );
        
        builder.append( "DDPPick [Order=" ).append( pickOrder );
        builder.append( ", Player=" ).append( player.getName( ) );
        builder.append( ", Teams: ");
        
        for( int i=0; i< teams.length; i++ ) {
            if( teams[i] != null ) {
                builder.append( teams[i].getLowerCaseName( ) ).append( " " );
            }
        }
        
        builder.append( "]" );        
        return builder.toString( );
    }

    
    
    @Override
    public final int hashCode( ) {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( teams );
        return result;
    }


    @Override
    public final boolean equals( Object obj ) {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass( ) != obj.getClass( ) )
            return false;
        DDPPick other = (DDPPick) obj;
        if( !Arrays.equals( teams, other.teams ) )
            return false;
        return true;
    }

}
