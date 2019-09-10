package com.ddp.nfl.web.core;

import static com.ddp.nfl.web.util.DDPUtil.*;

import java.time.*;


public final class DDPMeta{

    private final String version;
    private final String seasonType;
    private final LocalDate startDate;
    private final int gameWeek;
    private final int cashPerWeekT1;
    private final int cashPerWeekT2;
    private final boolean seasonStarted;
    private final boolean seasonOver;
    
    
    public DDPMeta( String version, boolean seasonOver, String seasonType, LocalDate startDate, int gameWeek, int cashPerWeekT1, int cashPerWeekT2 ){
        
        this.version        = version;
        this.seasonOver     = seasonOver;
        this.seasonType     = seasonType;
        this.startDate      = startDate;
        this.gameWeek       = gameWeek;
        this.cashPerWeekT1  = cashPerWeekT1;
        this.cashPerWeekT2  = cashPerWeekT2;
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
    
    
    public final int getCashPerWeekT1( ){
        return cashPerWeekT1;
    }
    
    
    public final int getCashPerWeekT2( ){
        return cashPerWeekT2;
    }
    
    
    protected final void validate(  ){
        
        if( !isValid(seasonType) ){
            throw new RuntimeException("SeasonType " + seasonType + " is invalid" );
        }
        
        if( gameWeek < ONE ){
            throw new RuntimeException("NFL game week " + gameWeek + " is invalid" );
        }        
        
        if( cashPerWeekT1 <= ONE ){
            throw new RuntimeException("T1 Cash Per Week " + cashPerWeekT1 + " is invalid" );
        }
        
        if( cashPerWeekT2 <= ONE ){
            throw new RuntimeException("T2 Cash Per Week " + cashPerWeekT2 + " is invalid" );
        }
          
    }


    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 32 );
        
        builder.append( "DDPMeta [version=" ).append( version )
        .append( ", IsSeasonOver=" ).append( seasonOver )
        .append( ", SeasonType=" ).append( seasonType )
        .append( ", StartDate=" ).append( startDate )
        .append( ", GameWeek=" ).append( gameWeek )
        .append( ", CashPerWeekT1=" ).append( cashPerWeekT1 )
        .append( ", CashPerWeekT2=" ).append( cashPerWeekT2 )
        .append( "]" );
        
        return builder.toString( );
    }
       
        
    
}
