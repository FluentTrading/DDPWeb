package com.ddp.nfl.web.analytics.home;



public final class Passing{
     
    private final String name;
    private final int att;
    private final int cmp;
    private final int yards;
    private final int tds;
    private final int ints;
    private final int twopta;
    private final int twoptm;
    
    public Passing( String name, int att, int cmp, int yards, int tds, int ints, int twopta, int twoptm ){
        this.name = name;
        this.att = att;
        this.cmp = cmp;
        this.yards = yards;
        this.tds = tds;
        this.ints = ints;
        this.twopta = twopta;
        this.twoptm = twoptm;
    }

    public final String getName( ) {
        return name;
    }

    public final int getAtt( ) {
        return att;
    }

    public final int getCmp( ) {
        return cmp;
    }

    public final int getYards( ) {
        return yards;
    }

    public final int getTds( ) {
        return tds;
    }

    public final int getInts( ) {
        return ints;
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
        builder.append( "Passing [name=" );
        builder.append( name );
        builder.append( ", att=" );
        builder.append( att );
        builder.append( ", cmp=" );
        builder.append( cmp );
        builder.append( ", yards=" );
        builder.append( yards );
        builder.append( ", tds=" );
        builder.append( tds );
        builder.append( ", ints=" );
        builder.append( ints );
        builder.append( ", twopta=" );
        builder.append( twopta );
        builder.append( ", twoptm=" );
        builder.append( twoptm );
        builder.append( "]" );
        return builder.toString( );
    }
    
    
    

}