package com.ddp.nfl.web.analytics.drives;

public final class End {

    private final int qtr;
    private final String time;
    private final String yrdln;
    private final String team;

    public End( int qtr, String time, String yrdln, String team ){
        this.qtr = qtr;
        this.time = time;
        this.yrdln = yrdln;
        this.team = team;
    }

    public final int getQtr( ) {
        return qtr;
    }

    public final String getTime( ) {
        return time;
    }

    public final String getYrdln( ) {
        return yrdln;
    }

    public final String getTeam( ) {
        return team;
    }

    @Override
    public final String toString( ){
        StringBuilder builder = new StringBuilder( 32);
        builder.append( "DriveInfo [qtr=" );
        builder.append( qtr );
        builder.append( ", time=" );
        builder.append( time );
        builder.append( ", yrdln=" );
        builder.append( yrdln );
        builder.append( ", team=" );
        builder.append( team );
        builder.append( "]" );
        return builder.toString( );
    }
    
    


}