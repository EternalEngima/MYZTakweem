package com.myz.calculable;

import com.myz.image.Point;
import com.myz.xml.XmlOperation;
import java.util.Vector;

/**
 * @author Zaid
 */

public class MYZOperation extends XmlOperation
{
    //Constructor
    MYZOperation()
    {
        super();
        m_vPoint = new Vector<>();
    }
    
    //Members
    double             m_value;
    Vector< MYZPoint > m_vPoint;
    
    //Methods
    public double calucate()
    {
        m_value = 0;
        
        if( TYPE_DISTANCE_BETWEEN_TWO_POINTS.equals( m_type ) )
        {
            m_value = MYZMathTools.calculateDistanceBetweenTwoPoints( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point );                           
        }
        else if( TYPE_DISTANCE_BETWEEN_LINE_AND_POINT.equals( m_type ) )
        {
            //Assuming that the first two element are the line and the last element is the point
            Point projectedPoint = MYZMathTools.caluclateProjectedPointOnLine( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point , m_vPoint.elementAt( 2 ).m_point );
            
            m_value = MYZMathTools.calculateDistanceBetweenTwoPoints( projectedPoint , m_vPoint.elementAt( 2 ).m_point );            
        }
        else if( TYPE_DISTANCE_BETWEEN_TWO_LINES.equals( m_type ) )//No fucking idea
        {
            
        }
        else if( TYPE_DISTANCE_BETWEEN_TWO_PROJECTED_POINTS_ON_LINE.equals( m_type ) )
        {
            //Assuming the first two element are the line and the other elements are the points
            Point firstProjectedPoint  = MYZMathTools.caluclateProjectedPointOnLine( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point , m_vPoint.elementAt( 2 ).m_point );
            Point secondProjectedPoint = MYZMathTools.caluclateProjectedPointOnLine( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point , m_vPoint.elementAt( 3 ).m_point );
            
            m_value = MYZMathTools.calculateDistanceBetweenTwoPoints( firstProjectedPoint , secondProjectedPoint );
        }
        else if( TYPE_ANGEL_BETWEEN_THREE_POINTS.equals( m_type ) )
        {
            Point firstPointL1  = m_vPoint.elementAt( 0 ).m_point;
            Point secondPointL1 = m_vPoint.elementAt( 1 ).m_point;
            
            Point firstPointL2  = m_vPoint.elementAt( 2 ).m_point;
            Point secondPointL2 = m_vPoint.elementAt( 1 ).m_point;
            
            m_value = MYZMathTools.calculateAngelBetweenTwoLines( firstPointL1 , secondPointL1 , firstPointL2 , secondPointL2 );            
        }
        else if( TYPE_ANGEL_BETWEEN_TWO_LINES.equals( m_type ) )
        {
            Point firstPointL1  = m_vPoint.elementAt( 0 ).m_point;
            Point secondPointL1 = m_vPoint.elementAt( 1 ).m_point;
            
            Point firstPointL2  = m_vPoint.elementAt( 2 ).m_point;
            Point secondPointL2 = m_vPoint.elementAt( 3 ).m_point;
            
            m_value = MYZMathTools.calculateAngelBetweenTwoLines( firstPointL1 , secondPointL1 , firstPointL2 , secondPointL2 );            
        }
        else if( TYPE_RATIO_BETWEEN_TWO_LINES.equals( m_type ) )
        {
            Point firstPointL1  = m_vPoint.elementAt( 0 ).m_point;
            Point secondPointL1 = m_vPoint.elementAt( 1 ).m_point;
            double lengthL1     = MYZMathTools.calculateDistanceBetweenTwoPoints( firstPointL1 , secondPointL1 );
            
            Point firstPointL2  = m_vPoint.elementAt( 2 ).m_point;
            Point secondPointL2 = m_vPoint.elementAt( 3 ).m_point;
            double lengthL2     = MYZMathTools.calculateDistanceBetweenTwoPoints( firstPointL2 , secondPointL2 );
            
            m_value = lengthL1 / lengthL2;
        }
        
        return m_value;
    }
}
