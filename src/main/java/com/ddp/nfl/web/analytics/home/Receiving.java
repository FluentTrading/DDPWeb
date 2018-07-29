package com.ddp.nfl.web.analytics.home;


public final class Receiving{

    private final String name;
    private final int    rec;
    private final int    yards;
    private final int    tds;
    private final int    lng;
    private final int    lngtd;
    private final int    twopta;
    private final int    twoptm;
    
    public Receiving( String name, int rec, int yards, int tds, int lng, int lngtd, int twopta, int twoptm ){
        this.name = name;
        this.rec = rec;
        this.yards = yards;
        this.tds = tds;
        this.lng = lng;
        this.lngtd = lngtd;
        this.twopta = twopta;
        this.twoptm = twoptm;
    }

    public final String getName( ) {
        return name;
    }

    public final int getRec( ) {
        return rec;
    }

    public final int getYards( ) {
        return yards;
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

    public final int getTwopta( ) {
        return twopta;
    }

    public final int getTwoptm( ) {
        return twoptm;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64);
        builder.append( "Receiving [name=" ).append( name ).append( ", rec=" ).append( rec ).append( ", yards=" );
        builder.append( yards ).append( ", tds=" ).append( tds ).append( ", lng=" ).append( lng ).append( ", lngtd=" );
        builder.append( lngtd ).append( ", twopta=" ).append( twopta ).append( ", twoptm=" ).append( twoptm ).append( "]" );
        
        return builder.toString( );
    }

    
    
}
