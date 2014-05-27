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
    private static final double maxCoolingCapacity = 14; //KW
    private static final double sensibleCoolingCapacity = 10.2; //KW
    private static final double waterflow = 608; //gallons per hour
    private static final double maxCFM = 1353; //CFM
    private static final double chilledWaterTank = 15; //Gal    
    
    private static final double targetAirTemperature = 70; //F    
    private static final double airDensity = 0.075; //lb/ft3
    private static final double waterDensity = 8.329;
    private static final double waterSpecificHeat = 1.002;
    private static final double throwAirRatio = 1/3;
    private static final double hwe = 1075.66;
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
        previousTemp = 70;
    }
    
    /**
     * 
     * @param supplyAir
     * @return 
     */
    public Air coolAir(double Temperature, double humidity)
    {
        Air supplyAir = new Air(currentCFM, Temperature, humidity);
        //FIRST YOU SHOULD THROW 1/3 AND GET IT FROM THE OUTSIDE
        Air outsideAir = new Air(supplyAir.quantity, building.weather.currentTemp, building.weather.currentHum);
        mixAir(supplyAir, throwAirRatio, outsideAir);
        //THEN COOL THE AIR
        Air returnAir = new Air();
        returnAir.quantity = supplyAir.quantity;
        returnAir.temperature = chilledWaterTankTemperature;
        returnAir.relativeHumidity = psychometric.getHumidityRatio((int) returnAir.temperature);
        //NOW compute total heat transfered to ChilledWater
        //sensible heat computation
        double sensibleHeat = currentCFM*airDensity*(supplyAir.temperature - returnAir.temperature);
        //latent heat computation
        double latentHeat = currentCFM*airDensity*hwe*(supplyAir.relativeHumidity - returnAir.relativeHumidity);
        
        //heat the chilled water
        heatChilledWater(sensibleHeat + latentHeat);
        //change chilled water
        coolChilledWaterTank();
        
        // Adjust CFM for next time
    	chooseFanSpeed();
    	
    	previousTemp = building.currentTemperature;
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
    //This function should be changed to match how it works in real-world. This means 
    private void chooseFanSpeed()
    {
    	double multiplier = 5;
    	if(building.currentTemperature < targetAirTemperature)
    	{
    		currentCFM = 0;
    	}
    	else
    	{
            if(currentCFM == 0)
                    currentCFM = 1000;
            else
            {
                    double changeRate = (building.currentTemperature - previousTemp) / previousTemp;
                    currentCFM *= (1 + changeRate*multiplier);
            }
    	}
    }
    public Air mixAir(Air air1, double throwRatio, Air air2)
    {
        double air1Hum = psychometric.getHumidityRatio((int) air1.temperature) * air1.relativeHumidity;
        double air2Hum = psychometric.getHumidityRatio((int) air2.temperature) * air2.relativeHumidity;        
        
        double newTemp =  air1.temperature*(1 - throwRatio) + air2.temperature * throwRatio;
        System.out.println(newTemp);
        double humRatio = air1Hum*(1 - throwRatio) + air2Hum * throwRatio;
        
        double newHum = humRatio / psychometric.getHumidityRatio((int) newTemp);
        
        return new Air(air1.quantity, newTemp, newHum);
    }
    
    
    
}
