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
public class Air {
    public double quantity; //Ft3
    public double temperature; //F
    public double relativeHumidity; //%

    public Air() {
    }

    public Air(double quantity, double temperature, double relativeHumidity) {
        this.quantity = quantity;
        this.temperature = temperature;
        this.relativeHumidity = relativeHumidity;
    }
    
}
