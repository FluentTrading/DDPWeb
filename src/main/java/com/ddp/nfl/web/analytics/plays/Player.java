package com.ddp.nfl.web.analytics.plays;

public final class Player{

    private final String playerId;
    private final String clubcode;
    private final String playerName;
    private final int statId;
    private final int yards;
    
    public Player( String playerId, String clubcode, String playerName, int statId, int yards ){
        this.playerId = playerId;
        this.clubcode = clubcode;
        this.playerName = playerName;
        this.statId = statId;
        this.yards = yards;
    }

    public final String getPlayerId( ) {
        return playerId;
    }

    public final String getClubcode( ) {
        return clubcode;
    }

    public final String getPlayerName( ) {
        return playerName;
    }

    public final int getStatId( ) {
        return statId;
    }

    public final int getYards( ) {
        return yards;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 32 );
        builder.append( "Players [playerId=" ).append( playerId ).append( ", clubcode=" ).append( clubcode );
        builder.append( ", playerName=" ).append( playerName ).append( ", statId=" ).append( statId );
        builder.append( ", yards=" ).append( yards ).append( "]" );
        
        return builder.toString( );
    }
    
    
    
}
