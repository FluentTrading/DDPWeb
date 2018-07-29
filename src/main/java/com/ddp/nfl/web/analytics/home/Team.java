package com.ddp.nfl.web.analytics.home;

public final class Team {

    private final int totfd;
    private final int totyds;
    private final int pyds;
    private final int ryds;
    private final int pen;
    private final int penyds;
    private final int trnovr;
    private final int pt;
    private final int ptyds;
    private final int ptavg;
    private final String top;
    
    public Team( int totfd, int totyds, int pyds, int ryds, int pen, int penyds, int trnovr, int pt, int ptyds,
            int ptavg, String top ){
        this.totfd = totfd;
        this.totyds = totyds;
        this.pyds = pyds;
        this.ryds = ryds;
        this.pen = pen;
        this.penyds = penyds;
        this.trnovr = trnovr;
        this.pt = pt;
        this.ptyds = ptyds;
        this.ptavg = ptavg;
        this.top = top;
    }

    public final int getTotfd( ) {
        return totfd;
    }

    public final int getTotyds( ) {
        return totyds;
    }

    public final int getPyds( ) {
        return pyds;
    }

    public final int getRyds( ) {
        return ryds;
    }

    public final int getPen( ) {
        return pen;
    }

    public final int getPenyds( ) {
        return penyds;
    }

    public final int getTrnovr( ) {
        return trnovr;
    }

    public final int getPt( ) {
        return pt;
    }

    public final int getPtyds( ) {
        return ptyds;
    }

    public final int getPtavg( ) {
        return ptavg;
    }

    public final String getTop( ) {
        return top;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "Team [totfd=" ).append( totfd ).append( ", totyds=" ).append( totyds ).append( ", pyds=" ).append( pyds );
        builder.append( ", ryds=" ).append( ryds ).append( ", pen=" ).append( pen ).append( ", penyds=" ).append( penyds );
        builder.append( ", trnovr=" ).append( trnovr ).append( ", pt=" ).append( pt ).append( ", ptyds=" ).append( ptyds );
        builder.append( ", ptavg=" ).append( ptavg ).append( ", top=" ).append( top ).append( "]" );
        
        return builder.toString( );
    }
    
    
}