package com.ddp.nfl.web.analytics.home;

public final class Defense{

    private final String name;
    private final int    tkl;
    private final int    ast;
    private final int    sk;
    private final int    intr;
    private final int    ffum;
    
    public Defense( String name, int tkl, int ast, int sk, int intr, int ffum ){
        this.name = name;
        this.tkl = tkl;
        this.ast = ast;
        this.sk = sk;
        this.intr = intr;
        this.ffum = ffum;
    }

    public final String getName( ) {
        return name;
    }

    public final int getTkl( ) {
        return tkl;
    }

    public final int getAst( ) {
        return ast;
    }

    public final int getSk( ) {
        return sk;
    }

    public final int getIntr( ) {
        return intr;
    }

    public final int getFfum( ) {
        return ffum;
    }

    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "Defense [name=" ).append( name ).append( ", tkl=" ).append( tkl ).append( ", ast=" ).append( ast );
        builder.append( ", sk=" ).append( sk ).append( ", intr=" ).append( intr ).append( ", ffum=" ).append( ffum ).append( "]" );
        return builder.toString( );
    }

    
}
