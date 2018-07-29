package com.ddp.nfl.web.analytics.home;

public final class Fumbles{

    private final String name;
    private final int    tot;
    private final int    rcv;
    private final int    trcv;
    private final int    yds;
    private final int    lost;
    
    public Fumbles( String name, int tot, int rcv, int trcv, int yds, int lost ){
        this.name = name;
        this.tot = tot;
        this.rcv = rcv;
        this.trcv = trcv;
        this.yds = yds;
        this.lost = lost;
    }

    public final String getName( ) {
        return name;
    }

    public final int getTot( ) {
        return tot;
    }

    public final int getRcv( ) {
        return rcv;
    }

    public final int getTrcv( ) {
        return trcv;
    }

    public final int getYds( ) {
        return yds;
    }

    public final int getLost( ) {
        return lost;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "Fumbles [name=" ).append( name ).append( ", tot=" ).append( tot ).append( ", rcv=" ).append( rcv );
        builder.append( ", trcv=" ).append( trcv ).append( ", yds=" ).append( yds ).append( ", lost=" ).append( lost ).append( "]" );
        return builder.toString( );
    }
    
    
    
}
