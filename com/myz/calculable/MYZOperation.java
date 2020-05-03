package com.myz.calculable;

import com.myz.image.Line;
import com.myz.xml.Operation;
import java.util.Vector;

/**
 * @author Zaid
 */

public class MYZOperation extends Operation
{
    //Constructor
    MYZOperation()
    {
        m_vLine = new Vector< Line >();
        
    }
    
    //Members
    double         m_value;
    Vector< Line > m_vLine;
    
    //Methods
    public double calucate()
    {
        if( m_type == TYPE_DISTANCE_BETWEEN_TWO_POINTS )
        {
            //m_value = MYZMathTools.calucateDistanceBetweenTwoPoints( x1, y1, x2, y2 )
        }
        return m_value;
    }
}
