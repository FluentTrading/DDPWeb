package com.ddp.nfl.web.core;

import java.time.*;

import static com.ddp.nfl.web.util.DDPUtil.*;

public final class DDPMeta{

    private final String version;
    private final String seasonType;
    private final LocalDate startDate;
    private final String dbConnectionURL;
    private final int gameWeek;
    private final int cashPerWeek;
    private final boolean seasonStarted;
    private final boolean seasonOver;

    public DDPMeta( String version, boolean seasonOver, String seasonType, LocalDate startDate, int gameWeek, int cashPerWeek ){
        
        this.version        = version;
        this.seasonOver     = seasonOver;
        this.seasonType     = seasonType;
        this.startDate      = startDate;
        this.gameWeek       = gameWeek;
        this.cashPerWeek    = cashPerWeek;
        this.dbConnectionURL= loadDBConnectionURL();        
        this.seasonStarted  = startDate.isEqual(LocalDate.now( )) || startDate.isBefore(LocalDate.now( )); 

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
    
    
    public final String getDBConnectionURL( ){
        return dbConnectionURL;
    }

    
    protected final void validate(  ){
        
        if( !isValid(seasonType) ){
            throw new RuntimeException("SeasonType " + seasonType + " is invalid!" );
        }
        
        if( gameWeek < ONE ){
            throw new RuntimeException("NFL game week " + gameWeek + " is invalid!" );
        }        
        
        if( cashPerWeek <= ONE ){
            throw new RuntimeException("Cash Per Week " + cashPerWeek + " is invalid!" );
        }
        
        if( !isValid(dbConnectionURL) ){
            throw new RuntimeException("DB Connection URL is invalid!" );
        }
                  
    }


    private static String loadDBConnectionURL() {
    	String url = System.getenv("JDBC_DATABASE_URL");
    	if( url == null ){
    		url = "jdbc:postgresql://ec2-18-215-44-132.compute-1.amazonaws.com:5432/d612rk4pqn5nqi?sslmode=require&user=rosrespdgzgaxg&password=62c1dd13ee11b0591bc4539e70b6b4c2d3d3a798377e9d968bc3c4e499b7b8de";           
    	}
    	
    	return url;
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
