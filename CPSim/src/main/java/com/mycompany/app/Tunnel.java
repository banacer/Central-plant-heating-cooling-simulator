/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

/**
 *
 * @author Nacer Khalil
 */
public class Tunnel implements Playable{
    
    public TunnelStage[] stage;
    public TunnelStage cpWater;
    public static final int size = 30;
    public Tunnel() {
        stage = new TunnelStage[size];
        cpWater = new TunnelStage(12000-200*30, 30*200, 42+0.1*30, 55.1+0.08*30);
    }
    
    
    @Override
    public void init() {
        for(int i = 0; i < size; i++)
        {
            stage[i] = new TunnelStage(12000-200*i, i*200, 42+0.1*i, 55.1+0.08*i);
        }
    }

    @Override
    public void nextStep() {
        cpWater = stage[size - 1];
        
        for(int i = size - 1; i > 0; i--)
        {
            stage[i] = stage[i - 1];
            //Temperature increases in the tunnel
            stage[i].returnTemp +=0.08; // THIS IS HERE TO MAKE SURE THAT RETURN WATER GETS HEATED BY 2.4F AT THE END OF THE TUNNEL
            stage[i].supplyTemp +=0.1; // THIS IS HERE TO MAKE SURE THAT SUPPLY WATER GETS HEATED BY 3F AT THE END OF THE TUNNEL
        }
    }
    
    public TunnelStage CPreturn()
    {
        return cpWater;
    }
    
    public void CPsupply(TunnelStage tunnelStage)
    {
        stage[0] = tunnelStage;
    }
    
    public double getWaterTemp(int s)
    {
        return stage[s].supplyTemp;
    }
    
    public Water supplyWater(int s,double gallons)
    {
        stage[s].supplyFlow-=gallons;
        return new Water(gallons,stage[s].supplyTemp);
    }
    public boolean returnWater(int s, Water water)
    {
        //compute new return water temperature
        stage[s].returnTemp = (stage[s].returnTemp * stage[s].returnFlow + water.amount * water.temperature)/ (stage[s].returnFlow + water.amount);
        //compute new return water flow
        stage[s].returnFlow += water.amount;
        
        return true;
    }
}
