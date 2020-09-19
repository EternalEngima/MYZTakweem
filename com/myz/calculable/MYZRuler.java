/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.calculable;

import com.myz.image.Point;
import java.io.Serializable;

/**
 *
 * @author Montazar Hamoud
 */
public class MYZRuler implements Serializable
{
    public MYZRuler()
    {
        
    }
    public MYZRuler( int unitType , int pixelsPerUnit)
    {
        m_unitType      = unitType;
        m_pixelsPerUnit = pixelsPerUnit ;
    }
    
    //Class member
    public  static final int MM = 1 ;
    public  static final int CM = 2 ;
    private static       int m_pixelsPerUnit = -1 ;
    
    //Data member 
    
    int   m_unitType ; 
    Point m_startPoint ;
    Point m_endPoint ;
    
    //Method 
    public void setUnitType(int unitType)
    {
        m_unitType = unitType ; 
    }
    public void setPixelsPerUnit(int pixelsPerUnit)
    {
        m_pixelsPerUnit = pixelsPerUnit ;
    }
    public void setStartPoint (Point startPoint)
    {
        m_startPoint = startPoint ;
    }
    public void setEndPoint(Point endPoint)
    {
        m_endPoint = endPoint ;
    }
    
    public int getUnitType()
    {
        return m_unitType ;
    }
    public int getPixelsPerUnit()
    {
        return m_pixelsPerUnit ; 
    }
    public Point getStartPoint()
    {
        return m_startPoint ;
    }
    public Point getEndPoint()
    {
        return m_endPoint ; 
    }
    
    public static double convertToRealSize(double pixelsSize)
    {
        return ( pixelsSize / m_pixelsPerUnit );
    }
}
