package com.ddp.nfl.web.analytics.drives;

import com.ddp.nfl.web.analytics.plays.*;


public final class Drive{
    
    private final String posteam;
    private final int qtr;
    private final boolean redzone;
    private final int fds;
    private final String result;
    private final int penyds;
    private final int ydsgained;
    private final int numplays;
    private final String postime;
    private final Start start;
    private final End end;
    private final PlayManager plays;  
    
    public Drive( String posteam, int qtr, boolean redzone, int fds, String result, int penyds, int ydsgained,
            int numplays, String postime, Start start, End end, PlayManager plays ){
        
        this.posteam = posteam;
        this.qtr = qtr;
        this.redzone = redzone;
        this.fds = fds;
        this.result = result;
        this.penyds = penyds;
        this.ydsgained = ydsgained;
        this.numplays = numplays;
        this.postime = postime;
        this.start = start;
        this.end = end;
        this.plays = plays;
    
    }

    public final String getPosteam( ) {
        return posteam;
    }

    public final int getQtr( ) {
        return qtr;
    }

    public final boolean isRedzone( ) {
        return redzone;
    }

    public final int getFds( ) {
        return fds;
    }

    public final String getResult( ) {
        return result;
    }

    public final int getPenyds( ) {
        return penyds;
    }

    public final int getYdsgained( ) {
        return ydsgained;
    }

    public final int getNumplays( ) {
        return numplays;
    }

    public final String getPostime( ) {
        return postime;
    }

    public final Start getStart( ) {
        return start;
    }

    public final End getEnd( ) {
        return end;
    }

    public final PlayManager getPlayManager( ) {
        return plays;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 1024 );
        builder.append( "Drive [posteam=" );
        builder.append( posteam );
        builder.append( ", qtr=" );
        builder.append( qtr );
        builder.append( ", redzone=" );
        builder.append( redzone );
        builder.append( ", fds=" );
        builder.append( fds );
        builder.append( ", result=" );
        builder.append( result );
        builder.append( ", penyds=" );
        builder.append( penyds );
        builder.append( ", ydsgained=" );
        builder.append( ydsgained );
        builder.append( ", numplays=" );
        builder.append( numplays );
        builder.append( ", postime=" );
        builder.append( postime );
        builder.append( ", start=" );
        builder.append( start );
        builder.append( ", end=" );
        builder.append( end );
        builder.append( ", plays=" );
        builder.append( plays );
        builder.append( "]" );
        
        return builder.toString( );
    }

   
    
}
