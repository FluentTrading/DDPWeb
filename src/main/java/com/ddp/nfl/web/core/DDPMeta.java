package com.ddp.nfl.web.core;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class DDPMeta{

    private final String version;
    private final String seasonType;
    private final int year;
    private final int week;
    
    public DDPMeta( String version, String seasonType, int year, int week ){
        this.version    = version;
        this.seasonType = seasonType;
        this.year       = year;
        this.week       = week;
        
        validate( seasonType, year, week );
    }

   
    public final String getVersion( ){
        return version;
    }

    
    public final String getSeasonType( ){
        return seasonType;
    }

    
    public final int getYear( ){
        return year;
    }

    
    public final int getWeek( ){
        return week;
    }
    
    
    protected final void validate( String seasonType, int year, int week ){
        
        if( !isValid(seasonType) ){
            throw new RuntimeException("SeasonType " + seasonType + " is invalid" );
        }
        
        if( !(year > 2010 && year < 2050) ){
            throw new RuntimeException("NFL Year " + year + " is invalid" );
        }
        
        if( !(week >= 1 && week < 20) ){
            throw new RuntimeException("NFL Week " + week + " is invalid" );
        }
          
    }
    
    
    @Override
    public final String toString( ){
        StringBuilder builder = new StringBuilder( 32 );
        builder.append( "DDPMeta [version=" ).append( version ).append( ", seasonType=" ).append( seasonType );
        builder.append( ", year=" ).append( year ).append( ", week=" ).append( week ).append( "]" );
    
        return builder.toString( );
    }
    
    
    
}
