package com.ddp.nfl.web.analytics.drives;

import static com.ddp.nfl.web.util.DDPUtil.*;

import java.util.*;
import java.util.Map.*;

public final class DriveManager {

    private final int crntdrv;
    private final Map<Integer, Drive> dMap;
    
    public DriveManager( int crntdrv, Map<Integer, Drive> dMap ){
        this.crntdrv  = crntdrv;
        this.dMap= dMap;
    }


    public final int getCrntDrive( ) {
        return crntdrv;
    }

    
    public final Map<Integer, Drive> getDriveMap(  ){
        return dMap;                
    }
    
    
    public final Drive getDrives( int driveNum ){
        return dMap.get( driveNum );                
    }


    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( 64 );
        builder.append( "Drives [CrntDrive=" ).append( crntdrv );
        builder.append( PRINT_NEWLINE );
        for( Entry<Integer, Drive> entry : dMap.entrySet( ) ) {
            builder.append( entry.getKey( ) ).append( " ==> " );
            builder.append( entry.getValue( ) ).append( PRINT_NEWLINE );
        }
        
        builder.append( "]" );
        return builder.toString( );
    }
    
    
    
}