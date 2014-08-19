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
public class TunnelStage {
    double supplyFlow;
    double returnFlow;
    double supplyTemp;
    double returnTemp;

    public TunnelStage(double supplyFlow, double returnFlow, double supplyTemp, double returnTemp) {
        this.supplyFlow = supplyFlow;
        this.returnFlow = returnFlow;
        this.supplyTemp = supplyTemp;
        this.returnTemp = returnTemp;
    }
    
    public Water supplyWater(double gallons)
    {
        supplyFlow-=gallons;
        return new Water(gallons,supplyTemp);
    }
    public boolean returnWater(Water water)
    {
        //compute new return water temperature
        returnTemp = (returnTemp * returnFlow + water.amount * water.temperature)/ (returnFlow + water.amount);
        //compute new return water flow
        returnFlow += water.amount;
        
        return true;
    }

    @Override
    public String toString() {
        return "TunnelStage{" + "supplyFlow=" + supplyFlow + ", returnFlow=" + returnFlow + ", supplyTemp=" + supplyTemp + ", returnTemp=" + returnTemp + '}';
    }
    
           
}
