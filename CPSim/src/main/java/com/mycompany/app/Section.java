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
     int students;
     Building location;
     Calendar startTime;
     Calendar endTime;

    public Section(int Students, Building location, Calendar startTime, Calendar endTime) {
        this.students = Students;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Section() {
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
    
    public int doorOpen()
    {
        return 0;
    }
}
