/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

/**
 *
 * @author nkhalil
 */
public class AHU {
    //CLASS DATA
    private static double maxCoolingCapacity = 14; //KW
    private static double sensibleCoolingCapacity = 10.2; //KW
    private static double waterflow = 608; //gallons per hour
    private static double maxCFM = 1353; //CFM
    private static double chilledWaterTank = 15; //Gal    
    
    private static double targetAirTemperature = 70; //F    
    private static double airDensity = 0.075; //lb/ft3
    private static double waterDensity = 8.329;
    private static double waterSpecificHeat = 1.002;
    private static double hwe = 1075.66;    
    //OBJECT DATA
    private double chilledWaterTankTemperature;
    private double temperatureChangeSetPoint;
    private double temperatureKeepSetPoint;
    private Psychometric psychometric;
    private double currentCFM;
    private Building building;
    private double previousTemp;

    public AHU(Building building) {
        temperatureKeepSetPoint = 55;
        temperatureChangeSetPoint = 55.1;
        chilledWaterTankTemperature = temperatureKeepSetPoint;
        //LOAD PSYCHOMERIC TABLE
        psychometric = new Psychometric();
        psychometric.loadTable();
        currentCFM = 1000;
        this.building = building;
        previousTemp = 0;
    }
    
    /**
     * 
     * @param supplyAir
     * @return 
     */
    public Air coolAir(Air supplyAir)
    {
        //FIRST YOU SHOULD THROW 1/3 AND GET IT FROM THE OUTSIDE
        
        //THEN COOL THE AIR
        Air returnAir = new Air();
        returnAir.quantity = supplyAir.quantity;
        returnAir.temperature = chilledWaterTankTemperature;
        returnAir.relativeHumidity = psychometric.getHumidityRatio((int) returnAir.temperature);
        double mass = building.getVolumePerAHU()*airDensity*(targetAirTemperature - supplyAir.temperature)/
                (returnAir.temperature-supplyAir.temperature);
        currentCFM = mass/airDensity;
        if(currentCFM > maxCFM)
            currentCFM = maxCFM;
        //NOW compute total heat transfered to ChilledWater
        //sensible heat computation
        double sensibleHeat = building.getVolumePerAHU()*airDensity*(supplyAir.temperature - returnAir.temperature);
        //latent heat computation
        double latentHeat = building.getVolumePerAHU()*airDensity*hwe*(supplyAir.relativeHumidity - returnAir.relativeHumidity);
        
        
        return returnAir;
    }
    /**
     * This function takes the heat in BTU and heats the chilled water
     * @param heat in BTU
     */
    private void heatChilledWater(double heat)
    {
        chilledWaterTankTemperature = heat/(chilledWaterTank*waterDensity*waterSpecificHeat) + chilledWaterTankTemperature;
        coolChilledWaterTank();        
    }
    
    /**
     * This function feeds from the tunnel, takes the necessary chilled water to keep 
     * the tank at the target set point     
     */
    private void coolChilledWaterTank()
    {
        TunnelStage tunnelStage = building.stage;
        Water returnedWater = new Water();
        
        returnedWater.temperature = chilledWaterTankTemperature;
        double tunnelTemp = tunnelStage.supplyTemp;
        double waterMass = chilledWaterTank*waterDensity;
        double waterMassNeeded = waterMass* ((temperatureKeepSetPoint - chilledWaterTankTemperature)/(tunnelTemp - chilledWaterTankTemperature));
        double waterVolumeNeeded = waterMassNeeded/waterDensity;
        Water suppliedWater = tunnelStage.supplyWater(waterVolumeNeeded);
        
        returnedWater.amount = waterVolumeNeeded;
        tunnelStage.returnWater(returnedWater);        
    }
    
    
    
}