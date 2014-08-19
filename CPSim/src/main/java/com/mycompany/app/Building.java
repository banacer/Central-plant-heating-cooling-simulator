package com.mycompany.app;

import java.util.Vector;

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
    double currentHumidity;
    Weather weather;
    private Psychometric psychometric;
    
    
    public Building(Tunnel tunnel, Weather weather)
    {
        status = null;
        people = 0;
        sections = new Vector<Section>();
        int index = Functions.EquiRand(0, Tunnel.size);
        System.out.println("Index = "+index);
        stage = tunnel.stage[index];
        System.out.println(stage.toString());
        ahus = new Vector<AHU>();
        
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
        totalVolume = 800000;
        currentTemperature = 69;
        currentHumidity = 0.5;
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
        coolBuilding();
    }
    public void heatBuilding(double btu)
    {
        double totalHeat = (totalVolume * airDensity * currentTemperature + btu);
        double newTemp = totalHeat / (totalVolume * airDensity);
        currentTemperature = newTemp;
    }
    public void coolBuilding()
    {
        for(AHU ahu: ahus)
        {
            //SEND THE AIR TO AHU
            Air air = ahu.coolAir(currentTemperature, currentHumidity);
            //MIX IT WITH CURRENT AIR
            mixAir(air);
        }
    }
    public void mixAir(Air air)
    {
        double cooledAirMass = air.quantity*airDensity;
        double existingAirMass = (totalVolume-air.quantity)*airDensity;
        //COMPUTE NEW HUMIDITY RATIO
        double newHumidity = (cooledAirMass*psychometric.getHumidityRatio((int) Math.floor(air.temperature))+existingAirMass*
                psychometric.getHumidityRatio((int) Math.floor(currentTemperature)))/(existingAirMass+cooledAirMass);
        //COMPUTE NEW TEMPERATURE
        double newTemp = ((totalVolume-air.quantity)*airDensity*currentTemperature + air.quantity*airDensity*air.temperature)/(totalVolume*airDensity);
        
        //STORE AS CURRENT PARAMETERS
        currentTemperature = newTemp;
        currentHumidity = newHumidity/psychometric.getHumidityRatio((int) currentTemperature);
    }
    
}

