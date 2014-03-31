package com.mycompany.app;


import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nkhalil
 */
public class Functions {
    private static Random r = new Random();
    public static double GaussianRand(double mean, double sd)
    {
        
        double rand = r.nextGaussian();
        rand = (rand)*sd + mean;
        return rand;
    }
    public static int EquiRand(int start, int end)
    {       
        return r.nextInt(end) + start;        
    }
}
