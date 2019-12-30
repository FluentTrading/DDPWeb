package com.ddp.nfl.web.archive;

import org.slf4j.*;
import java.util.*;


public final class ArchiveManager{
    
    private final List<String> archiveImages;
    
    private final static String LOCATION= "images/archive/2019";
    private final static String NAME    = ArchiveManager.class.getSimpleName( );
    private final static Logger LOGGER  = LoggerFactory.getLogger( NAME );
           
    
    public ArchiveManager( ){
        this.archiveImages = loadArchiveImages( );
    }
  
    
    public final List<String> getArchivedImages( ){
        return archiveImages;
    }
    
    
    private final static List<String> loadArchiveImages( ){
        
        List<String> imageList  = new ArrayList<>();
                
        try {
            
            for( int i=1; i<=9; i++ ) {
                imageList.add( LOCATION + "/" + i + ".png" );
            }
                        
            LOGGER.info( "Read {}", imageList);
            
        }catch (Exception e) {
            LOGGER.error( "FAILED to read archive images from {}.", LOCATION, e );
        }
                
        return imageList;
    }

    public final static void main( String[] args ) {
        new ArchiveManager( );
    }
        
}
