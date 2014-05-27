/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author nkhalil
 */
public class Psychometric {
    public static double[][] table;
    
    public void loadTable()
    {
        if(isTableLoaded())
            return;
        
        table = new double[350][23];
        BufferedReader br = null;
        String line = null;
        StringTokenizer tok = null;
        int y  = 0, x= 0;
        try 
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("data/psychometric.csv")));
            
            while((line = br.readLine()) != null)
            {
                tok = new StringTokenizer(line,",");
                x = 0;
                while(tok.hasMoreTokens())
                {
                    String val = tok.nextToken();
                    if(val == null || val.length() == 0)
                        val = "0";
                    if(!Character.isDigit(val.charAt(0)))
                        val = "-"+val.substring(1);                    
                    table[y][x] = Double.parseDouble(val);
                    x++;
                }
                y++;
            }
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
            System.err.println("y = "+y+", x = "+x);
        }
    }
    public boolean isTableLoaded()
    {
        if(table == null)
            return false;
        return true;
    }
    public double getHumidityRatio(int temp)
    {        
        int index = temp + 80 - 1;
        int difference = temp - (int) table[index][0];
        return table[index + difference][1];
    }
}
