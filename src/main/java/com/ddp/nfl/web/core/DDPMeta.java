package com.ddp.nfl.web.core;

import static com.ddp.nfl.web.util.DDPUtil.*;

import java.time.*;

import io.github.cdimascio.dotenv.*;


public final class DDPMeta{

    private final String version;
    private final String seasonType;
    private final LocalDate startDate;
    private final int gameWeek;
    private final int cashPerWeek;
    private final boolean seasonStarted;
    private final boolean seasonOver;
    private final Dotenv dotConfig;
    
    public DDPMeta( String version, boolean seasonOver, String seasonType, LocalDate startDate, int gameWeek, int cashPerWeek, Dotenv config ){
        
        this.version        = version;
        this.seasonOver     = seasonOver;
        this.seasonType     = seasonType;
        this.startDate      = startDate;
        this.gameWeek       = gameWeek;
        this.cashPerWeek    = cashPerWeek;
        this.seasonStarted  = startDate.isEqual(LocalDate.now( )) || startDate.isBefore(LocalDate.now( )); 
        this.dotConfig         = config;
        
        validate( );
    }
    
    
    public final boolean hasSeasonStarted( ){
        return seasonStarted;
    }

   
    public final String getVersion( ){
        return version;
    }

    
    public final String getSeasonType( ){
        return seasonType;
    }
    
    
    public final LocalDate getStartDate( ){
        return startDate;
    }
  
    
    public final int getGameYear( ){
        return startDate.getYear( );
    }

    
    public final int getGameWeek( ){
        return gameWeek;
    }
    
    
    public final boolean isSeasonOver( ){
        return seasonOver;
    }
    
    
    public final int getCashPerWeek( ){
        return cashPerWeek;
    }
    
    
    public final String getDriverName( ){
        return dotConfig.get( "RDS_DRIVER", "MISSING");
    }
    
    
    public final String getDBHostname( ){
        return dotConfig.get( "RDS_HOST", "MISSING");
    }
    
    
    public final String getDBName( ){
        return dotConfig.get( "RDS_DB_NAME", "MISSING");
    }
    
    
    public final int getDBPort( ){
        return Integer.parseInt( dotConfig.get( "RDS_PORT", "-1") );
    }
    
    
    public final String getDBUserName( ){
        return dotConfig.get( "RDS_USERNAME", "MISSING");
    }
    
    public final String getDBPassword( ){
        return dotConfig.get( "RDS_PASSWORD", "MISSING");
    }
    
    
    protected final void validate(  ){
        
        if( !isValid(seasonType) ){
            throw new RuntimeException("SeasonType " + seasonType + " is invalid" );
        }
        
        if( gameWeek < ONE ){
            throw new RuntimeException("NFL game week " + gameWeek + " is invalid" );
        }        
        
        if( cashPerWeek <= ONE ){
            throw new RuntimeException("Cash Per Week " + cashPerWeek + " is invalid" );
        }
        
        if( dotConfig == null ) {
            throw new RuntimeException("Environment config is invalid" );
        }
                  
    }


    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 32 );        
        builder.append( "DDPMeta [version= " ).append( version )
        .append( ", IsSeasonOver= " ).append( seasonOver )
        .append( ", SeasonType= " ).append( seasonType )
        .append( ", StartDate= " ).append( startDate )
        .append( ", GameWeek= " ).append( gameWeek )
        .append( ", CashPerWeek= " ).append( cashPerWeek )
        .append( "]" );
        
        return builder.toString( );
        
    }
       
        
    
}
