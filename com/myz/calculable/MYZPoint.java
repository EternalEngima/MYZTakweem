package com.myz.calculable;

import com.myz.image.Point;
import com.myz.xml.XmlPoint;
import java.io.Serializable;

/**
 * @author Zaid
 */

public class MYZPoint extends XmlPoint implements Serializable
{
    //Constructor
    public MYZPoint(String name , String symbol , String description)
    {
        super();
        m_name        = name ;
        m_symbol      = symbol ;
        m_description = description;
    }
    //Class member
    public static final int SELECTED   = 1 ;
    public static final int SELECT_NOW = 2 ;
    
    //Data members
    Point m_point = null;
    int   m_state ;
    
    public MYZPoint( XmlPoint point )
    {        
        super( point );
         
    }
    public MYZPoint( Point point )
    {
        super();
        m_point = point;        
    }
    
    
    //Methods
    public void setPoint(Point point)
    {
        m_point = point ;
    }
    public void setState(int state)
    {
        m_state = state ;
    }
    
    public Point getPoint()
    {
        return m_point ; 
    }
    public int getState()
    {
        return m_state ;
    }
}
