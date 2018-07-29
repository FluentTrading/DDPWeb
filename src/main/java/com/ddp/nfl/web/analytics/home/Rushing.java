package com.ddp.nfl.web.analytics.home;


public final class Rushing {

    private final String name;
    private final int att;
    private final int yards;
    private final int tds;
    private final int lng;
    private final int lngtd;
    private final int twopta;
    private final int twoptm;
    
    public Rushing( String name, int att, int yards, int tds, int lng, int lngtd, int twopta, int twoptm ){
        this.name = name;
        this.att = att;
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

    public final int getAtt( ) {
        return att;
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
    public final String toString( ){
        StringBuilder builder = new StringBuilder( 64);
        builder.append( "Rushing [name=" ).append( name ).append( ", att=" ).append( att ).append( ", yards=" ).append( yards );
        builder.append( ", tds=" ).append( tds ).append( ", lng=" ).append( lng ).append( ", lngtd=" ).append( lngtd );
        builder.append( ", twopta=" ).append( twopta ).append( ", twoptm=" ).append( twoptm ).append( "]" );
        
        return builder.toString( );
    }
      
    
    

}