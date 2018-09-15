package com.ddp.nfl.web.analytics.core;


public final class Analytics{

    private final String passName;
    private final String passDesc;
    
    private final String rushName;
    private final String rushDesc;
    
    private final String recvName;
    private final String recvDesc;
    
    public Analytics( String[ ] pass, String[ ] rush, String[ ] recv ){
        this( pass[0], pass[1], rush[0], rush[1], recv[0], recv[1] );
    }
    
    
    public Analytics( String passName, String passDesc, String rushName, String rushDesc, String recvName,
            String recvDesc ){
        this.passName = passName;
        this.passDesc = passDesc;
        this.rushName = rushName;
        this.rushDesc = rushDesc;
        this.recvName = recvName;
        this.recvDesc = recvDesc;
    }



    public final String getPassName( ) {
        return passName;
    }

    public final String getPassDesc( ) {
        return passDesc;
    }

    public final String getRushName( ) {
        return rushName;
    }

    public final String getRushDesc( ) {
        return rushDesc;
    }

    public final String getRecvName( ) {
        return recvName;
    }

    public final String getRecvDesc( ) {
        return recvDesc;
    }

    
    @Override
    public final String toString( ) {
        
        StringBuilder builder = new StringBuilder( 32);
        builder.append( "Analytics [passName=" ).append( passName ).append( ", passDesc=" ).append( passDesc )
        .append( ", rushName=" ).append( rushName ).append( ", rushDesc=" ).append( rushDesc )
        .append( ", recvName=" ).append( recvName ).append( ", recvDesc=" ).append( recvDesc )
        .append( "]" );
        
        return builder.toString( );
    }
    
    
    
}
