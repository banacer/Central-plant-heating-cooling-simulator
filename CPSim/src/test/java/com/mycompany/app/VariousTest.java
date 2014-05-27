/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author nkhalil
 */
public class VariousTest {
    
    public static void main(String[] args)
    {
        testMixAirAHU();
    }
    public static void timeDifference()
    {
        Calendar t1 = new GregorianCalendar(2014, 06, 05, 6, 0);
        Calendar t2 = new GregorianCalendar(2014, 06, 05, 8, 59);
        
        int minutes = minutesDiff(new Date(t1.getTimeInMillis()), new Date(t2.getTimeInMillis()));
        System.out.println("Minutes diff: "+minutes);
    }
    public static int minutesDiff(Date earlierDate, Date laterDate)
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/60000) - (earlierDate.getTime()/60000));
    }
    public static void normalDistribution()
    {
        NormalDistribution normalDistribution = new NormalDistribution(-2, 2.5);
        System.out.println(normalDistribution.density(-3)*25);
        
    }
    public static void testMixAirAHU()
    {
        try {
            Weather weather = new Weather();
            Building building = new Building(new Tunnel(),weather);
            AHU ahu = new AHU(building);
            weather.init();
            building.init();
            weather.nextStep();
            Air res = ahu.coolAir(72, .5);
            System.out.println(res.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
