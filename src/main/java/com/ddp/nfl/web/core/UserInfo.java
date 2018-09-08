package com.ddp.nfl.web.core;


public final class UserInfo{
    
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