package com.ddp.nfl.web.analytics.home;

public final class Kicking{

    private final String name;
    private final int    fgm;
    private final int    fga;
    private final int    fgyds;
    private final int    totpfg;
    private final int    xpmade;
    private final int    xpmissed;
    private final int    xpa;
    private final int    xpb;
    private final int    xptot;
    
    public Kicking( String name, int fgm, int fga, int fgyds, int totpfg, int xpmade, int xpmissed, int xpa, int xpb, int xptot ){
        this.name = name;
        this.fgm = fgm;
        this.fga = fga;
        this.fgyds = fgyds;
        this.totpfg = totpfg;
        this.xpmade = xpmade;
        this.xpmissed = xpmissed;
        this.xpa = xpa;
        this.xpb = xpb;
        this.xptot = xptot;
    }

    public final String getName( ) {
        return name;
    }

    public final int getFgm( ) {
        return fgm;
    }

    public final int getFga( ) {
        return fga;
    }

    public final int getFgyds( ) {
        return fgyds;
    }

    public final int getTotpfg( ) {
        return totpfg;
    }

    public final int getXpmade( ) {
        return xpmade;
    }

    public final int getXpmissed( ) {
        return xpmissed;
    }

    public final int getXpa( ) {
        return xpa;
    }

    public final int getXpb( ) {
        return xpb;
    }

    public final int getXptot( ) {
        return xptot;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "Kicking [name=" ).append( name ).append( ", fgm=" ).append( fgm ).append( ", fga=" ).append( fga );
        builder.append( ", fgyds=" ).append( fgyds ).append( ", totpfg=" ).append( totpfg ).append( ", xpmade=" ).append( xpmade );
        builder.append( ", xpmissed=" ).append( xpmissed ).append( ", xpa=" ).append( xpa ).append( ", xpb=" );
        builder.append( xpb ).append( ", xptot=" ).append( xptot ).append( "]" );
        
        return builder.toString( );
    }
    
    
    

}
