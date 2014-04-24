/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

import java.util.Vector;

/**
 *
 * @author nkhalil
 */
public class Building implements Playable{

    int number;
    TunnelStage stage; // this refers to what part of the tunnel will it be fed from
    BuildingStatus status;
    int people;
    Vector<Section> sections;
    Vector<AHU> ahus;
    double totalVolume;
    double currentTemperature;
    
    public Building(Tunnel tunnel)
    {
        status = null;
        people = 0;
        sections = new Vector<Section>();
        stage = tunnel.stage[Functions.EquiRand(0, Tunnel.size)];
        ahus = new Vector<AHU>();
        totalVolume = 800000;
        for(int i = 0; i < 12; i++)
            ahus.add(new AHU(this));
    }

    public Building(int number) {
        this.number = number;
        status = null;
        people = 0;
        sections = new Vector<Section>();
    }
    
    
    @Override
    public void init() throws Exception{
        
    }

    @Override
    public void nextStep() throws Exception {
        //heat building
    	
    	int seconds = 0;
        for(Section s: sections)
        {
            s.nextStep();
            seconds += s.doorOpen();            
        }
        //cool building
        
    }    
}

