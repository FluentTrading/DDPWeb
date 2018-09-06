package com.ddp.nfl.web.core;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class DDPMeta{

    private final String version;
    private final String seasonType;
    private final int year;
    private final int week;
    private final int cashPerWeek;
    
    public DDPMeta( String version, String seasonType, int year, int week, int cashPerWeek ){
        
        this.version    = version;
        this.seasonType = seasonType;
        this.year       = year;
        this.week       = week;
        this.cashPerWeek= cashPerWeek;
                        
        validate( seasonType, year, week, cashPerWeek );
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
    
    
    public final int getCashPerWeek( ){
        return cashPerWeek;
    }
    
    
    protected final void validate( String seasonType, int year, int week, int cashPerWeek ){
        
        if( !isValid(seasonType) ){
            throw new RuntimeException("SeasonType " + seasonType + " is invalid" );
        }
        
        if( !(year > 2010 && year < 2050) ){
            throw new RuntimeException("NFL Year " + year + " is invalid" );
        }
        
        if( week < ONE ){
            throw new RuntimeException("NFL Week " + week + " is invalid" );
        }
        
        if( cashPerWeek <= ONE ){
            throw new RuntimeException("Cash Per Week " + cashPerWeek + " is invalid" );
        }
          
    }
    
    
    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 32 );
        builder.append( "DDPMeta [version=" ).append( version ).append( ", seasonType=" ).append( seasonType );
        builder.append( ", year=" ).append( year ).append( ", week=" ).append( week );
        builder.append( ", CashPerWeek=" ).append( cashPerWeek ).append( "]" );
    
        return builder.toString( );
    
    }
        
    
}
