package com.ddp.nfl.web.core;


public final class DDPPlayer{
    
    private final int id;
    private final String name;
    private final String nickName;    
    private final String icon;
    private final int deposit;
    
    private final static String PLAYER_LOGO_PREFIX   = "images/players/";
    private final static String PLAYER_LOGO_SUFFIX   = ".ico";
    
    public DDPPlayer( int id, String name, String nickName, int deposit ){
        this.id         = id;
        this.name       = name;
        this.nickName   = nickName;        
        this.deposit    = deposit;    
        this.icon       = createPlayerIcon( name );
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
    
    
    public final int getDepositAmount( ) {
        return deposit;
    }
           
    
    protected final static String createPlayerIcon( String playerName ){
        return PLAYER_LOGO_PREFIX + playerName + PLAYER_LOGO_SUFFIX;
    }
    
    
    @Override
    public final String toString( ){
        StringBuilder builder = new StringBuilder( 32 );
        builder.append( "DDPPlayer [Id=" ).append( id );
        builder.append( ", Name=" ).append( name );
        builder.append( ", NickName=" ).append( nickName );        
        builder.append( ", Amount=" ).append( deposit );
        builder.append( "]" );
        
        return builder.toString( );
    }
    
}

