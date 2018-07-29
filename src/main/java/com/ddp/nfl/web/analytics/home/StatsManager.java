package com.ddp.nfl.web.analytics.home;

import java.util.*;

public final class StatsManager {

    private final Map<String, Passing> passing;
    private final Map<String, Rushing> rushing;
    private final Map<String, Receiving> receiving;
    private final Map<String, Fumbles> fumbles;
    private final Map<String, Kicking> kicking;
    private final Map<String, Punting> punting;
    private final Map<String, Kickret> kickret;
    private final Map<String, Puntret> puntret;
    private final Map<String, Defense> defense;
    private final Team team;
    
    public StatsManager(   Map<String, Passing> passing, Map<String, Rushing> rushing, Map<String, Receiving> receiving, 
                    Map<String, Fumbles> fumbles, Map<String, Kicking> kicking, Map<String, Punting> punting, 
                    Map<String, Kickret> kickret, Map<String, Puntret> puntret, Map<String, Defense> defense, Team team ){
        
        this.passing = passing;
        this.rushing = rushing;
        this.receiving = receiving;
        this.fumbles = fumbles;
        this.kicking = kicking;
        this.punting = punting;
        this.kickret = kickret;
        this.puntret = puntret;
        this.defense = defense;
        this.team       = team;
    }

    public final Map<String, Passing> getPassing( ) {
        return passing;
    }

    public final Map<String, Rushing> getRushing( ) {
        return rushing;
    }

    public final Map<String, Receiving> getReceiving( ) {
        return receiving;
    }

    public final Map<String, Fumbles> getFumbles( ) {
        return fumbles;
    }

    public final Map<String, Kicking> getKicking( ) {
        return kicking;
    }

    public final Map<String, Punting> getPunting( ) {
        return punting;
    }

    public final Map<String, Kickret> getKickret( ) {
        return kickret;
    }

    public final Map<String, Puntret> getPuntret( ) {
        return puntret;
    }

    public final Map<String, Defense> getDefense( ) {
        return defense;
    }

    public final Team getTeam( ) {
        return team;
    }

    @Override
    public final String toString( ){
        
        StringBuilder builder = new StringBuilder( 1024 );
        builder.append( "Stats [passing=" );
        builder.append( passing );
        builder.append( ", rushing=" );
        builder.append( rushing );
        builder.append( ", receiving=" );
        builder.append( receiving );
        builder.append( ", fumbles=" );
        builder.append( fumbles );
        builder.append( ", kicking=" );
        builder.append( kicking );
        builder.append( ", punting=" );
        builder.append( punting );
        builder.append( ", kickret=" );
        builder.append( kickret );
        builder.append( ", puntret=" );
        builder.append( puntret );
        builder.append( ", defense=" );
        builder.append( defense );
        builder.append( ", team=" );
        builder.append( team );
        builder.append( "]" );
        
        return builder.toString( );
    
    }
    
    
}