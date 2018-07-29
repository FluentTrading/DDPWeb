package com.ddp.nfl.web.analytics.summary;

import java.util.*;

public class ScoreSummary {

    private final String type;
    private final String desc;
    private final int qtr;
    private final String team;
    private final Map<String, String> players;
    
    public ScoreSummary( String type, String desc, int qtr, String team, Map<String, String> players ){
        this.type = type;
        this.desc = desc;
        this.qtr = qtr;
        this.team = team;
        this.players = players;
    }

    public final String getType( ) {
        return type;
    }

    public final String getDesc( ) {
        return desc;
    }

    public final int getQtr( ) {
        return qtr;
    }

    public final String getTeam( ) {
        return team;
    }

    public final Map<String, String> getPlayers( ) {
        return players;
    }

    
    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "ScoreSummary [type=" );
        builder.append( type );
        builder.append( ", desc=" );
        builder.append( desc );
        builder.append( ", qtr=" );
        builder.append( qtr );
        builder.append( ", team=" );
        builder.append( team );
        builder.append( ", players=" );
        builder.append( players  );
        builder.append( "]" );
        return builder.toString( );
    }

         
    
}