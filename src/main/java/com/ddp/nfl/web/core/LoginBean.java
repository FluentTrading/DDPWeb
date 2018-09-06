package com.ddp.nfl.web.core;

import com.ddp.nfl.web.util.DDPUtil;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class LoginBean {
 
    private volatile String gameId;
    
    private final boolean isValid;
    private final UserInfo userInfo;
    private final DDPPlayer player;
    private final String message;
    
    
    public LoginBean( boolean isValid, UserInfo userInfo, DDPPlayer player, String message ) {
        this.isValid    = isValid;
        this.userInfo   = userInfo;
        this.player     = player;
        this.message    = message;
    }
    

    public final static LoginBean createError( String message, UserInfo userInfo ) {
        return new LoginBean( false, userInfo, null, message );
    }

    
    public final static LoginBean createValid( UserInfo userInfo, DDPPlayer player ){
        return new LoginBean( true, userInfo, player, "" );
    }
        

    public final boolean isValid( ){
        return isValid;
    }

    
    public final UserInfo getUserInfo() {
        return userInfo;
    }


    public final DDPPlayer getPlayer() {
        return player;
    }


    public final String getMessage() {
        return message;
    }
    
    public final void setGameId( String gameId ) {
        this.gameId = gameId;
    }
    
    public final String getGameId() {
        return gameId;
    }
    
    public final boolean isGameIdSet() {
        return DDPUtil.isValid( gameId );
    }
    
    
    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( );
        builder.append( "LoginBean [isValid=" ).append( isValid );
        if( player != null ) {
            builder.append( ", player=" ).append( player ).append( SPACE );
        }
        
        builder.append( userInfo );
        builder.append( ", message=" ).append( message ).append( "]" );
        
        return builder.toString( );
    }

    
    public final static class UserInfo{
        
        private final String userName;
        private final String agent;
        private final String os;
        private final String browser;
        private final String ipAddress;
        private final String fullUrl;
        private final String referrer;
        
        public UserInfo( String userName, String agent, String os, String browser, String ipAddress, String fullUrl, String referrer ){
            this.userName   = userName; 
            this.agent = agent;
            this.os = os;
            this.browser = browser;
            this.ipAddress = ipAddress;
            this.fullUrl = fullUrl;
            this.referrer = referrer;
        }

        public final String getUserName( ) {
            return userName;
        }

        public final String getAgent( ) {
            return agent;
        }

        public final String getOs( ) {
            return os;
        }

        public final String getBrowser( ) {
            return browser;
        }

        public final String getIpAddress( ) {
            return ipAddress;
        }

        public final String getFullUrl( ) {
            return fullUrl;
        }

        public final String getReferrer( ) {
            return referrer;
        }

        @Override
        public final String toString( ) {
            StringBuilder builder = new StringBuilder( 32);
            
            builder.append( "UserInfo [userName=" ).append( userName ).append( ", agent=" ).append( agent )
            .append( ", os=" ).append( os ).append( ", browser=" ).append( browser )
            .append( ", ipAddress=" ).append( ipAddress ).append( ", fullUrl=" ).append( fullUrl )
            .append( ", referrer=" ).append( referrer ).append( "]" );
            
            return builder.toString( );
        }
        
        
     
    }
          
    
}