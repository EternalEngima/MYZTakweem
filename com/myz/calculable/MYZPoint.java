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

    
    //Data members
    Point m_point = null;
    
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

    
    public Point getPoint()
    {
        return m_point ; 
    }

}
