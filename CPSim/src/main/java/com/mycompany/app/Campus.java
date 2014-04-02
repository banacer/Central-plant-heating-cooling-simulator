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
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author nkhalil
 */
public class Campus {
    Vector<Building> buildings;
    Vector<Section> sections;
    CentralPlant centralPlant;
    Tunnel tunnel;
    Weather weather;
    public static Calendar time;

    public Campus() {
        tunnel = new Tunnel();
        buildings = new Vector<>();
        sections = new Vector<>();
        time = new GregorianCalendar();
    }
    
    public void init()
    {
        tunnel.init();
        for(int i = 0; i < buildings.size(); i++)
        {
            buildings.add(new Building(tunnel));            
        }
        
    }
    public void initSections() throws Exception
    {
        BufferedReader bf = null;
        String line = null;
        StringTokenizer tok = null;
        int i = 0;
        bf = new BufferedReader(new InputStreamReader(new FileInputStream("data/sections.csv")));
        Calendar stime = new GregorianCalendar(2013, 6, 1, 8, 0, 0);
        Calendar etime = new GregorianCalendar(2013, 6, 1, 9, 30, 0);
        while((line = bf.readLine()) != null)
        {
            tok = new StringTokenizer(line, ",");            
            i++;
            if(i == 4)
            {
                stime.add(Calendar.MINUTE, 30);           
                etime.add(Calendar.MINUTE, 30);
            }
            int sec = Integer.parseInt(tok.nextToken());
            int bldg = Integer.parseInt(tok.nextToken());
            if(buildings.size() < bldg)
            {
                int b = buildings.size();
                for(int  k = 0; k < (bldg - b); k++)
                {
                    buildings.add(new Building(buildings.size() + k + 1));
                }
            }
            for(int k = 0; k < sec; k++)
            {
                Section s = new Section();
                s.students = (int) Math.floor(Functions.GaussianRand(25, 5));               
                s.location = buildings.elementAt(Functions.EquiRand(0, bldg));
                s.startTime = (Calendar) stime.clone();
                s.endTime = (Calendar) etime.clone();
                s.location.sections.add(s);
                sections.add(s);
            }
            stime.add(Calendar.HOUR, 1);
            stime.add(Calendar.MINUTE, 30);
            etime.add(Calendar.HOUR, 1);
            etime.add(Calendar.MINUTE, 30);
        }
        
        for(Section s: sections)
        {
            System.out.println(s.ToString());
        }
    }
    
    public void initCP()
    {
        
    }
    
    public void initTunnel()
    {
        
    }
    
    public void nextStep()
    {
        //increment time
        time.add(Calendar.MINUTE, 1);
        //next step tunnel
        
        //next step central plant
                
        //next step buildings
        
                
             
    }
    
    
    public static Calendar getTime() {
		return time;
	}

	public static void main(String[] args)
    {
        try {
            Campus campus = new Campus();
            campus.initSections();
        } catch (Exception ex) {
        }
    }
}
