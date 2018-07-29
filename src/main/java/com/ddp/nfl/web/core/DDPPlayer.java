package com.ddp.nfl.web.core;

import com.ddp.nfl.web.util.*;

public final class DDPPlayer{
    
    private final int id;
    private final String name;
    private final String nickName;
    private final String email;
    private final String icon;
    
    public DDPPlayer( int id, String name, String nickName, String email ){
        this.id         = id;
        this.name       = name;
        this.nickName   = nickName;
        this.email      = email;
        this.icon       = DDPUtil.getPlayerIcon( name );
    }
    
        
    public final int getId( ){
        return id;
    }
    
    public final String getName( ){
        return name;
    }
    
    
    public final String getIcon( ) {
        return icon;
    }
    
    public final String getNickName( ) {
        return nickName;
    }
    
    public final String getEmail( ) {
        return email;
    }

    
    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 32 );
        builder.append( "DDPPlayer [Id=" ).append( id );
        builder.append( ", Name=" ).append( name );
        builder.append( ", NickName=" ).append( nickName );
        builder.append( ", Email=" ).append( email );
        builder.append( "]" );
        return builder.toString( );
    }
}
