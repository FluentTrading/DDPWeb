package com.ddp.nfl.web.analytics.home;

public final class Puntret{

    private final String name;
    private final int    ret;
    private final int    avg;
    private final int    tds;
    private final int    lng;
    private final int    lngtd;
    
    public Puntret( String name, int ret, int avg, int tds, int lng, int lngtd ){
        this.name = name;
        this.ret = ret;
        this.avg = avg;
        this.tds = tds;
        this.lng = lng;
        this.lngtd = lngtd;
    }

    public final String getName( ) {
        return name;
    }

    public final int getRet( ) {
        return ret;
    }

    public final int getAvg( ) {
        return avg;
    }

    public final int getTds( ) {
        return tds;
    }

    public final int getLng( ) {
        return lng;
    }

    public final int getLngtd( ) {
        return lngtd;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "Puntret [name=" ).append( name ).append( ", ret=" ).append( ret ).append( ", avg=" ).append( avg );
        builder.append( ", tds=" ).append( tds ).append( ", lng=" ).append( lng ).append( ", lngtd=" ).append( lngtd ).append( "]" );
        return builder.toString( );
    }
    
    
}
