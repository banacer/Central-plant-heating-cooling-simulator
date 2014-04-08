/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author nkhalil
 */
public class Section implements Playable{
    private static double heatGeneratedByPerson = 9;
     int students;
     int studentsInClass;
     Building location;
     Calendar startTime;
     Calendar endTime;

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
        
    }
    public void studentsEnterClass(int number)
    {
        studentsInClass += number;
    }
    
    public void studentsExitClass(int number)
    {
        studentsInClass -= number;
    }
    
    public double getHeatGeneratedInClass()
    {
        return (double ) studentsInClass * heatGeneratedByPerson;
    }
    
    public double getHeatGeneratedFromDoor()
    {
        //NOT YET IMPLEMENTED
        return 0;
    }
    public int doorOpen()
    {
        return 0;
    }
}
