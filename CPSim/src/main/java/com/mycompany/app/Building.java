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

    private static double airDensity = 0.075; //lb/ft3
    int number;
    TunnelStage stage; // this refers to what part of the tunnel will it be fed from
    BuildingStatus status;
    int people;
    Vector<Section> sections;
    Vector<AHU> ahus;
    double totalVolume;
    double currentTemperature;
    Weather weather;
    
    public Building(Tunnel tunnel, Weather weather)
    {
        status = null;
        people = 0;
        sections = new Vector<Section>();
        stage = tunnel.stage[Functions.EquiRand(0, Tunnel.size)];
        ahus = new Vector<AHU>();
        totalVolume = 800000;
        for(int i = 0; i < 12; i++)
            ahus.add(new AHU(this));
        this.weather = weather;
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
    	
    	double totalHeat = 0;
        for(Section s: sections)
        {
            s.nextStep();
            totalHeat += s.generatedHeat;
        }
        //heat the building from the generatedHeat
        heatBuilding(totalHeat);        
        //cool building
        
        
    }
    public void heatBuilding(double btu)
    {
        double totalHeat = (totalVolume * airDensity * currentTemperature + btu);
        double newTemp = totalHeat / (totalVolume * airDensity);
        currentTemperature = newTemp;
    }
}

