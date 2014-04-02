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
    public double currentHum;
    
    private static double[][] tempDailyChange;
    private static double[][] tempMonthlyChange;
    private static double[][] humDailyChange;
    private int month;
    private int day;
    private int hour;
    private double dayTemp;
    private double dayHum;
    
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
    public Weather() 
    {   
        //Init variables;
        tempDailyChange = new double[12][2];
        tempMonthlyChange = new double[12][2];
        humDailyChange = new double[12][2];
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
            dayTemp = Functions.GaussianRand(tempMonthlyChange[month][0], tempMonthlyChange[month][1]);
            dayTemp /= 1.2;    
            hour = -1;
        }
        hour++;
        do 
        {
            double tempChange = Functions.GaussianRand(tempDailyChange[hour][0], tempDailyChange[hour][1]);
            double humChange = Functions.GaussianRand(humDailyChange[hour][0], humDailyChange[hour][1]);
            currentTemp = dayTemp * (1 + tempChange);
            currentHum = dayHum * (1 + humChange);
        }while(currentTemp > 105);
        
        
    }
    
    public void init() throws Exception
    {
        month = 5;
        day = 1;
        hour = 0;
        
        //Read Data for CSV files
        BufferedReader bf = null;
        String line = null;
        StringTokenizer tok = null;
        int i = 0;
        bf = new BufferedReader(new InputStreamReader(new FileInputStream("data/daily_temp_change.csv")));
        
        while((line = bf.readLine()) != null)
        {
            tok = new StringTokenizer(line, ",");
            tempDailyChange[i][0] = Double.valueOf(tok.nextToken());
            tempDailyChange[i][1] = Double.valueOf(tok.nextToken());
            i++;
        }
        
        bf = new BufferedReader(new InputStreamReader(new FileInputStream("data/monthly_temp_change.csv")));
        i = 0;
        while((line = bf.readLine()) != null)
        {
            tok = new StringTokenizer(line, ",");
            tempMonthlyChange[i][0] = Double.valueOf(tok.nextToken());
            tempMonthlyChange[i][1] = Double.valueOf(tok.nextToken());            
            i++;
        }
        bf = new BufferedReader(new InputStreamReader(new FileInputStream("data/daily_hum_change.csv")));
        i = 0;
        while((line = bf.readLine()) != null)
        {
            tok = new StringTokenizer(line, ",");
            humDailyChange[i][0] = Double.valueOf(tok.nextToken());
            humDailyChange[i][1] = Double.valueOf(tok.nextToken());            
            i++;
        }
        //init variables
        dayTemp = Functions.GaussianRand(tempMonthlyChange[month][0], tempMonthlyChange[month][1]);
        dayHum = 75;
    }
}
