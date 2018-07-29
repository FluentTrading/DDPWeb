package com.ddp.nfl.web.analytics.plays;

import java.util.*;

public final class Play{
    
    private final int sp;
    private final int qtr;
    private final int down;
    private final String time;
    private final String yrdln;
    private final int    ydstogo;
    private final int    ydsnet;
    private final String    posteam;
    private final String    desc;
    private final String    note;
    private final Map<Integer, Player> players;
    
    public Play( int sp, int qtr, int down, String time, String yrdln, int ydstogo, int ydsnet, String posteam,
            String desc, String note, Map<Integer, Player> players ){
        this.sp = sp;
        this.qtr = qtr;
        this.down = down;
        this.time = time;
        this.yrdln = yrdln;
        this.ydstogo = ydstogo;
        this.ydsnet = ydsnet;
        this.posteam = posteam;
        this.desc = desc;
        this.note = note;
        this.players = players;
    }

    public final int getSp( ) {
        return sp;
    }

    public final int getQtr( ) {
        return qtr;
    }

    public final int getDown( ) {
        return down;
    }

    public final String getTime( ) {
        return time;
    }

    public final String getYrdln( ) {
        return yrdln;
    }

    public final int getYdstogo( ) {
        return ydstogo;
    }

    public final int getYdsnet( ) {
        return ydsnet;
    }

    public final String getPosteam( ) {
        return posteam;
    }

    public final String getDesc( ) {
        return desc;
    }

    public final String getNote( ) {
        return note;
    }

    public final Map<Integer, Player> getPlayers( ) {
        return players;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 128);
        builder.append( "Play [sp=" ).append( sp ).append( ", qtr=" ).append( qtr ).append( ", down=" ).append( down );
        builder.append( ", time=" ).append( time ).append( ", yrdln=" ).append( yrdln ).append( ", ydstogo=" ).append( ydstogo );
        builder.append( ", ydsnet=" ).append( ydsnet ).append( ", posteam=" ).append( posteam ).append( ", desc=" ).append( desc );
        builder.append( ", note=" ).append( note ).append( ", players=" ).append( players ).append( "]" );
        
        return builder.toString( );
    }
    
    
    

}
