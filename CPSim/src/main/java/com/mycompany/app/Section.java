/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author nkhalil
 */
public class Section implements Playable{
    private static NormalDistribution normalDistribution = new NormalDistribution(-2, 2.5);
    private static double heatGeneratedByPerson = 9;
    private static double doorHeight = 10;
    private static double doorWidth = 8;
    private static double dischargeCoefficent = 0.6;
    private static double gravity = 32.2;
    
     int students;
     int studentsInClass;
     Building location;
     Calendar startTime;
     Calendar endTime;
     double generatedHeat;

    public Section(int Students, Building location, Calendar startTime, Calendar endTime) {
        studentsInClass = 0;
        this.students = Students;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Section() {
        studentsInClass = 0;
        students = 0;
        location = null;
        startTime = endTime = new  GregorianCalendar();
    }
    
    public String ToString()
    {
        return location.number+", "+ students+ ", "+startTime.getTime().toString()+", "+endTime.getTime().toString();
    }

    @Override
    public void init() throws Exception {
        
    }

    @Override
    public void nextStep() throws Exception {
        double heat = 0;
        heat += getHeatGeneratedInClass();
        heat += getHeatGeneratedFromDoor();
        generatedHeat = heat;
    }
    public void studentsEnterClass()
    {
        double density = normalDistribution.density(minutesDiff(new Date(startTime.getTimeInMillis()), new Date(Campus.time.getTimeInMillis())));
        studentsInClass += (int) Math.round(density*(double)students);
    }
    
    public void studentsExitClass(int number)
    {
        double density = normalDistribution.density(minutesDiff(new Date(Campus.time.getTimeInMillis()),new Date(endTime.getTimeInMillis())));
        studentsInClass -= (int) Math.round(density*(double)students);
    }
    
    public double getHeatGeneratedInClass()
    {
        return (double ) studentsInClass * heatGeneratedByPerson;
    }
    
    public double getHeatGeneratedFromDoor()
    {
        int studentEnter = (int) Math.round(normalDistribution.density(minutesDiff(new Date(Campus.time.getTimeInMillis()),new Date(endTime.getTimeInMillis())))*(double)students);
        int studentExit = (int) Math.round(normalDistribution.density(minutesDiff(new Date(Campus.time.getTimeInMillis()),new Date(endTime.getTimeInMillis())))*(double)students);
        //Take the sum of the times and passe it to heatbyTime
        return heatbyTime(studentEnter + studentExit);
    }
    public int doorOpen()
    {
        return 0;
    }
    private int minutesDiff(Date earlierDate, Date laterDate)
    {        
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/60000) - (earlierDate.getTime()/60000));
    }
    private double heatbyTime(int seconds)
    {
        double inTemp = FtoK(location.currentTemperature);
        double exTemp = FtoK(location.weather.currentTemp);
        double airEscaping = (1/3)*(doorHeight * doorWidth * dischargeCoefficent)*Math.sqrt(gravity * doorHeight * (exTemp - inTemp)/ +inTemp);
        double btuPerSec = airEscaping * 1.08 * (exTemp - inTemp);
        return btuPerSec * seconds;
    }
    public double FtoK(double F)
    {        
        return (F - 32)/1.8 + 273.15;
    }
}
