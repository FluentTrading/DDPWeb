package com.ddp.nfl.web.core;

public final class TeamRecord{
    
    private final String team;
    private final int won;
    private final int lost;
    private final int tied;
    private final String record;
    private final String bgColor;
        
    private final static int TOTAL_GAMES = 16;

    public TeamRecord( String team, int won, int lost, int tied ){
        this.team       = team;
        this.won        = won;
        this.lost       = lost;
        this.tied       = tied;
        this.record     = "("+ won + "-" + lost + "-" + tied + ")";
        this.bgColor    = calcBGColor( won, lost, tied );
        
    }


    public final String getTeam( ) {
        return team;
    }
    

    public final int getWon( ) {
        return won;
    }
    

    public final int getLost( ) {
        return lost;
    }

    
    public final int getTied( ) {
        return tied;
    }

    
    public final String getRecord( ) {
        return record;
    }
    
    
    public final String getBgcolor( ) {
        return bgColor;
    }
    
    
    public final int getSeasonTotalGames( ) {
        return TOTAL_GAMES;
    }
   
    private final static String calcBGColor( int won, int lost, int tied  ){
        
        int total       = (won + lost + tied);
        if( total == 0 || won > lost ){
            return "#3D9970";
        }else{
            return "#FF4136";
        }
    }

    
    @Override
    public final String toString( ) {
        StringBuilder builder = new StringBuilder( );
        builder.append( "TeamRecord [team=" ).append( team ).append( ", won=" ).append( won ).append( ", lost=" ).append( lost ).append( ", tied=" ).append( tied ).append( ", record=" ).append( record ).append( "]" );
        return builder.toString( );
    }
    

}
