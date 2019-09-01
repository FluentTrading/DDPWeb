package com.ddp.nfl.web.archive;

import org.slf4j.*;

import java.util.*;

import com.ddp.nfl.web.core.*;


public final class ArchiveManager{
    
    private final List<String> archiveImages;
    
    private final static String NAME    = ArchiveManager.class.getSimpleName( );
    private final static Logger LOGGER  = LoggerFactory.getLogger( NAME );
           
    
    public ArchiveManager( DDPMeta ddpMeta ){
        this.archiveImages = createArchiveImages( ddpMeta.getGameYear( ), 8);
    }
  
    
    public final List<String> getArchivedImages( ){
        return archiveImages;
    }
    
    
    private final static List<String> createArchiveImages( int gameYear, int fileCount ){
        
        List<String> files  = new ArrayList<>();
        int archiveYear     = gameYear -1;
        String location     = "images/history/" + archiveYear;
        
        try {
            
            for( int i=1; i<=fileCount; i++ ) {
                files.add( location + "/" + i + ".png" );
            }
            LOGGER.info( "Read [{}] images for year [{}]", files.size( ), archiveYear );
            
        }catch (Exception e) {
            LOGGER.error( "FAILED to read archive images for {} from {}.", archiveYear, location, e );
        }
                
        return files;
    }
    

        
}
