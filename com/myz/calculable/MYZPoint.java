package com.myz.calculable;

import com.myz.image.Point;
import com.myz.xml.XmlPoint;

/**
 * @author Zaid
 */

public class MYZPoint extends XmlPoint
{
    //Constructor
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
    Point m_point;
    
}
