package com.ddp.nfl.web.core;

import com.ddp.nfl.web.util.*;

public final class NFLTeam{

    private final int id;
    private final String name;
    private final String nickName;
    private final String displayName;
    private final String division;
    private final String conference;
    private final String city;
    private final String state;
    private final String rosterLink;
    private final String teamIcon;
        
    public NFLTeam( int id, String name, String nickName, String division, String conference, 
                    String city, String state, String rosterLink ){
        
        this.id         = id;
        this.name       = name;
        this.displayName= name.toUpperCase( );
        this.nickName   = nickName;
        this.division   = division;
        this.conference = conference;
        this.city       = city;
        this.state      = state;
        this.rosterLink = rosterLink;
        this.teamIcon   = DDPUtil.getTeamIcon( name );
        
    }
    
    public final int getId( ) {
        return id;
    }
    
    public final String getName( ) {
        return name;
    }
        
    public final String getDisplayName( ) {
        return displayName;
    }
    
    public final String getNickName( ) {
        return nickName;
    }
    
    public final String getIcon( ) {
        return teamIcon;
    }
    
    public final String getDivision( ) {
        return division;
    }
    
    public final String getConference( ) {
        return conference;
    }
    
    public final String getCity( ) {
        return city;
    }
    
    public final String getState( ) {
        return state;
    }
    
    public final String getRosterLink( ) {
        return rosterLink;
    }
       
    
    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "NFLTeam [id=" );
        builder.append( id );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", nickName=" );
        builder.append( nickName );
        builder.append( ", division=" );
        builder.append( division );
        builder.append( ", conference=" );
        builder.append( conference );
        builder.append( ", city=" );
        builder.append( city );
        builder.append( ", state=" );
        builder.append( state );
        builder.append( ", rosterLink=" );
        builder.append( rosterLink );
        builder.append( "]" );
        
        return builder.toString( );
    }

    @Override
    public int hashCode( ) {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ( (name == null) ? 0 : name.hashCode( ));
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass( ) != obj.getClass( ) )
            return false;
        NFLTeam other = (NFLTeam) obj;
        if( id != other.id )
            return false;
        if( name == null ){
            if( other.name != null )
                return false;
        }else if( !name.equals( other.name ) )
            return false;
        return true;
    }
 
   
    
}
