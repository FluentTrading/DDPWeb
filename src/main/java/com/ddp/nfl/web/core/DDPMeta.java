package com.ddp.nfl.web.core;

import static com.ddp.nfl.web.util.DDPUtil.*;


public final class DDPMeta{

    private final String version;
    private final boolean seasonOver;
    private final String seasonType;
    private final int gameYear;
    private final int gameWeek;
    private final int cashPerWeek;
    
    public DDPMeta( String version, boolean seasonOver, String seasonType, int gameYear, int gameWeek, int cashPerWeek ){
        
        this.version    = version;
        this.seasonOver = seasonOver;
        this.seasonType = seasonType;
        this.gameYear   = gameYear;
        this.gameWeek   = gameWeek;
        this.cashPerWeek= cashPerWeek;
                        
        validate( );
    }

   
    public final String getVersion( ){
        return version;
    }

    
    public final String getSeasonType( ){
        return seasonType;
    }
  
    
    public final int getGameYear( ){
        return gameYear;
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
    
    
    protected final void validate(  ){
        
        if( !isValid(seasonType) ){
            throw new RuntimeException("SeasonType " + seasonType + " is invalid" );
        }
        
        if( !(gameYear > 2010 && gameYear < 2050) ){
            throw new RuntimeException("NFL game year " + gameYear + " is invalid" );
        }
        
        if( gameWeek < ONE ){
            throw new RuntimeException("NFL game week " + gameWeek + " is invalid" );
        }        
        
        if( cashPerWeek <= ONE ){
            throw new RuntimeException("Cash Per Week " + cashPerWeek + " is invalid" );
        }
          
    }


    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 32 );
        
        builder.append( "DDPMeta [version=" ).append( version )
        .append( ", IsSeasonOver=" ).append( seasonOver )
        .append( ", SeasonType=" ).append( seasonType )
        .append( ", GameYear=" ).append( gameYear )
        .append( ", GameWeek=" ).append( gameWeek )
        .append( ", CashPerWeek=" ).append( cashPerWeek )
        .append( "]" );
        
        return builder.toString( );
    }
       
        
    
}
