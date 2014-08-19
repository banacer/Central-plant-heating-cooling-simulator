package com.mycompany.app;

/**
 *
 * @author nkhalil
 */
public class CentralPlant implements Playable{
    private double chw;
    private double tons;
    private double enterTemp;
    private double exitTemp;
    private double cutProduction;

    public CentralPlant(double chw, double tons, double enterTemp, double exitTemp, double cutProduction) {
        this.chw = chw;
        this.tons = tons;
        this.enterTemp = enterTemp;
        this.exitTemp = exitTemp;
        this.cutProduction = cutProduction;
    }
    
    
    @Override
    public void init() {
        chw = 35000;
        tons = 10000;
        enterTemp = 53;
        exitTemp = 44;
    }

    @Override
    public void nextStep() {
        
    }
    
    
}
