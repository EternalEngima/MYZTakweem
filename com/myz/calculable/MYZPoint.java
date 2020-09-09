package com.myz.calculable;

import com.myz.image.Point;
import com.myz.xml.XmlPoint;

/**
 * @author Zaid
 */

public class MYZPoint extends XmlPoint
{
    //Constructor
    public MYZPoint(String name , String symbol , String description)
    {
        super();
        m_name        = name ;
        m_symbol      = symbol ;
        m_description = description;
    }
    
    public MYZPoint( XmlPoint point )
    {        
        super( point );
         
    }
    public MYZPoint( Point point )
    {
        super();
        m_point = point;        
    }
    

    //Members
    Point m_point = null;
    
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
