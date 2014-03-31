/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 *
 * @author nkhalil
 */
public class Weather implements Playable{
    
    public double currentTemp;
    
    
    private static double[][] dailyChange;
    private static double[][] monthlyChange;
    private int month;
    private int day;
    private int hour;
    private double dayTemp;
    
    public static void main(String[] args)
    {
        Weather w = null;
        
        
        try {
            w = new Weather();
            w.init();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        for(int i =0; i < 360; i++)
        {  
           if(i%12 == 0)
               System.out.println();
           w.nextStep();
           System.out.printf("%5.2f  ",w.currentTemp);
           
        }
    }
    public Weather() throws Exception
    {   
        //Init variables;
        dailyChange = new double[12][2];
        monthlyChange = new double[12][2];
        
        //Read Data;
        BufferedReader bf = null;
        String line = null;
        StringTokenizer tok = null;
        int i = 0;
        bf = new BufferedReader(new InputStreamReader(new FileInputStream("data/daily_temp_change.csv")));
        
        while((line = bf.readLine()) != null)
        {
            tok = new StringTokenizer(line, ",");
            dailyChange[i][0] = Double.valueOf(tok.nextToken());
            dailyChange[i][1] = Double.valueOf(tok.nextToken());
            i++;
        }
        
        bf = new BufferedReader(new InputStreamReader(new FileInputStream("data/monthly_temp_change.csv")));
        i = 0;
        while((line = bf.readLine()) != null)
        {
            tok = new StringTokenizer(line, ",");
            monthlyChange[i][0] = Double.valueOf(tok.nextToken());
            monthlyChange[i][1] = Double.valueOf(tok.nextToken());            
            i++;
        }
        
    }    
    @Override
    public void nextStep()
    {
        //This condition is for changing day or even month if it is the end
        if(hour == 11)
        {
            //Use calendar variable to know if today is the end of the month
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2013);
            calendar.set(Calendar.MONTH, month);
            if(calendar.getActualMaximum(Calendar.DATE) == day)
            {
                month += 1;
                day = 0;
            }
            
            day+=1;
            dayTemp = Functions.GaussianRand(monthlyChange[month][0], monthlyChange[month][1]);
            dayTemp /= 1.2;    
            hour = -1;
        }
        hour++;
        do 
        {
            double change = Functions.GaussianRand(dailyChange[hour][0], dailyChange[hour][1]);
            currentTemp = dayTemp * (1 + change);
        }while(currentTemp > 105);
        
        
    }
    
    public void init()
    {
        month = 5;
        day = 1;
        hour = 0;
        dayTemp = Functions.GaussianRand(monthlyChange[month][0], monthlyChange[month][1]);
    }
}
