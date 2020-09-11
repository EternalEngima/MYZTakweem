/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.calculable;

import com.myz.xml.XmlPoint;
import com.myz.xml.XmlPointsPool;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Montazar Hamoud
 */
public class MYZPointsPool extends XmlPointsPool implements Serializable
{
    public MYZPointsPool (XmlPointsPool pointsPool)
    {
        for(XmlPoint xmlPoint :pointsPool.getVPoints())
        {
            m_vMYZPoint.addElement(new MYZPoint(xmlPoint));
        }
    }
    
    //Data members 
    Vector<MYZPoint> m_vMYZPoint      = new Vector();
    Vector<MYZPoint> m_vMYZPointValue = new Vector();
    
    //Getter methods
    public Vector<MYZPoint> getVMYZPoint()
    {
        return m_vMYZPoint ;
    }
    public Vector<MYZPoint> getVMYZPointValue()
    {
        return m_vMYZPointValue ;
    }
    
    public void setPointValue(MYZPoint point)
    {
        //TODO make sure the vmyzpoint is empty
        for(MYZPoint myzPoint : m_vMYZPointValue)
        {
            if(point.getM_name().equals(myzPoint.getM_name()))
            {
                point.m_point = myzPoint.m_point ;
                return ; 
            }
        }
    }
}
