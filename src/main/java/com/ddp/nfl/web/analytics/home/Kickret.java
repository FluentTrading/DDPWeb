package com.ddp.nfl.web.analytics.home;


public final class Kickret{

    private final String name;
    private final int    pts;
    private final int    ygs;
    private final int    avg;
    private final int    i20;
    private final int    lng;
    
    public Kickret( String name, int pts, int ygs, int avg, int i20, int lng ){
        this.name = name;
        this.pts = pts;
        this.ygs = ygs;
        this.avg = avg;
        this.i20 = i20;
        this.lng = lng;
    }

    public final String getName( ) {
        return name;
    }

    public final int getPts( ) {
        return pts;
    }

    public final int getYgs( ) {
        return ygs;
    }

    public final int getAvg( ) {
        return avg;
    }

    public final int getI20( ) {
        return i20;
    }

    public final int getLng( ) {
        return lng;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "Kickret [name=" ).append( name ).append( ", pts=" ).append( pts ).append( ", ygs=" ).append( ygs );
        builder.append( ", avg=" ).append( avg ).append( ", i20=" ).append( i20 ).append( ", lng=" ).append( lng ).append( "]" );
        
        return builder.toString( );
    }
    

}
