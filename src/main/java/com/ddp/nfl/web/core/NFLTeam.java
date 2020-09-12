package com.ddp.nfl.web.core;

import java.util.*;

public final class NFLTeam{

    private final int id;
    private final String lowerCaseName;
    private final String upperCaseName;
    private final String camelCaseName;
    private final String nickName;
    private final String division;
    private final String conference;
    private final String city;    
    private final String teamSquareIcon;
   
    
    private final static String NFL_SQUARE_ICON_ID   = "";
    private final static String NFL_LOGO_PREFIX      = "images/teams/";
    private final static String NFL_LOGO_SUFFIX      = ".png";
    private final static String MISSING_TEAM_LOGO    = NFL_LOGO_PREFIX + "missing" + NFL_LOGO_SUFFIX;
    private final static String GAME_LOST_LOGO       = NFL_LOGO_PREFIX + "loss.png";
    private final static Map<String, String> NAME_OVERRIDE_MAP = new HashMap<>();
    
    
    static {
        NAME_OVERRIDE_MAP.put( "football team", "Redskins");
        NAME_OVERRIDE_MAP.put( "Football Team", "Redskins");
    }
    
    
    public NFLTeam( int id, String camelCaseName, String nickName, String division, String conference, String city ){
        
        this.id             = id;
        this.camelCaseName  = camelCaseName;
        this.lowerCaseName  = camelCaseName.toLowerCase( );
        this.upperCaseName  = camelCaseName.toUpperCase( );
        this.nickName       = nickName;
        this.division       = division;
        this.conference     = conference;
        this.city           = city;
        this.teamSquareIcon = createSquareIcon( lowerCaseName );
        
    }
    
    public final int getId( ) {
        return id;
    }
    
    public final String getNickName( ) {
        return nickName;
    }
    
    public final String getLowerCaseName( ) {
        return lowerCaseName;
    }
        
    public final String getUpperCaseName( ) {
        return upperCaseName;
    }

    public final String getCamelCaseName( ) {
        return camelCaseName;
    }
    
    public final String getSquareTeamIcon( ) {
        return teamSquareIcon;
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
           
    public final static String getMissingTeamLogo( ){
        return MISSING_TEAM_LOGO;
    }
    
    public final static String getGameLostLogo( ){
        return GAME_LOST_LOGO;
    }
    
    protected final static String createSquareIcon( String teamName ){
        return NFL_LOGO_PREFIX + teamName + NFL_SQUARE_ICON_ID + NFL_LOGO_SUFFIX;
    }
    
    
    public static final String resolveOverriddenName( String name ){
        String lookup = NAME_OVERRIDE_MAP.get( name );
        return ( lookup == null ) ? name : lookup;
    }
    

    @Override
    public final int hashCode( ) {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ( (lowerCaseName == null) ? 0 : lowerCaseName.hashCode( ));
        return result;
    }

    @Override
    public final boolean equals( Object obj ) {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass( ) != obj.getClass( ) )
            return false;
        NFLTeam other = (NFLTeam) obj;
        if( id != other.id )
            return false;
        if( lowerCaseName == null ){
            if( other.lowerCaseName != null )
                return false;
        }else if( !lowerCaseName.equals( other.lowerCaseName ) )
            return false;
        
        return true;
    
    }
 
    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "NFLTeam [id=" );
        builder.append( id );
        builder.append( ", name=" );
        builder.append( camelCaseName );
        builder.append( ", nickName=" );
        builder.append( nickName );
        builder.append( ", division=" );
        builder.append( division );
        builder.append( ", conference=" );
        builder.append( conference );
        builder.append( ", city=" );
        builder.append( city );        
        builder.append( "]" );
        
        return builder.toString( );
    }
    
}
