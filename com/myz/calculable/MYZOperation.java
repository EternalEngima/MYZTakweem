package com.myz.calculable;

import com.myz.image.Point;
import com.myz.xml.XmlOperation;
import java.util.Vector;

/**
 * @author Zaid
 */

public class MYZOperation extends XmlOperation
{
    //Driver
    public static void main( String[] args )
    {
        Point        lPoint1, lPoint2, point1, point2;
        MYZOperation operation;
        double       result;
        
        //Distance between a line and a point
        operation = new MYZOperation();
        operation.m_type = TYPE_DISTANCE_BETWEEN_LINE_AND_POINT;
        //Horizontal line
        lPoint1 = new Point( 1 , 1 );
        lPoint2 = new Point( 5 , 1 );        
        point1  = new Point( 3 , 4 );                
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        result = operation.calculate();        
        //Vertical line
        lPoint1 = new Point( 1 , 1 );
        lPoint2 = new Point( 1 , 5 );
        point1  = new Point( 4 , 2 );
        operation.m_vPoint = new Vector<>();
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        result = operation.calculate();
        //testCase#1
        lPoint1 = new Point( 1 , 1 );
        lPoint2 = new Point( 1 , 5 );
        point1  = new Point( 1 , 6 );
        operation.m_vPoint = new Vector<>();
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        result = operation.calculate();
        lPoint1 = new Point( 1 , 1 );
        lPoint2 = new Point( 2 , 2 );
        point1  = new Point( 3 , 3 );
        operation.m_vPoint = new Vector<>();
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        result = operation.calculate();        
        
        //Distance between two projected points on a line
        operation = new MYZOperation();
        operation.m_type = TYPE_DISTANCE_BETWEEN_TWO_PROJECTED_POINTS_ON_LINE;
        lPoint1 = new Point( 5 , 1 );
        lPoint2 = new Point( 5 , 9 );
        point1  = new Point( 2 , 3 );
        point2  = new Point( 7 , 8 );
        operation.m_vPoint = new Vector<>();
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        operation.m_vPoint.addElement( new MYZPoint( point2 ) );
        result = operation.calculate();                
    }
    
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
    public double calculate()
    {
        m_value = 0;
        
        if( TYPE_DISTANCE_BETWEEN_TWO_POINTS.equals( m_type ) )
        {
            m_value = MYZMathTools.calculateDistanceBetweenTwoPoints( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point );                           
        }
        else if( TYPE_DISTANCE_BETWEEN_LINE_AND_POINT.equals( m_type ) )
        {
            //Assuming that the first two element are the line and the last element is the point
            Point projectedPoint = MYZMathTools.calculateProjectedPointOnLine( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point , m_vPoint.elementAt( 2 ).m_point );
            
            m_value = MYZMathTools.calculateDistanceBetweenTwoPoints( projectedPoint , m_vPoint.elementAt( 2 ).m_point );            
        }
        else if( TYPE_DISTANCE_BETWEEN_TWO_LINES.equals( m_type ) )
        {
            double firstLineLength  = MYZMathTools.calculateDistanceBetweenTwoPoints( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point );            
            double secondLineLength = MYZMathTools.calculateDistanceBetweenTwoPoints( m_vPoint.elementAt( 2 ).m_point , m_vPoint.elementAt( 3 ).m_point );
            
            m_value = Math.abs( firstLineLength - secondLineLength );
        }
        else if( TYPE_DISTANCE_BETWEEN_TWO_PROJECTED_POINTS_ON_LINE.equals( m_type ) )
        {
            //Assuming the first two element are the line and the other elements are the points
            Point firstProjectedPoint  = MYZMathTools.calculateProjectedPointOnLine( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point , m_vPoint.elementAt( 2 ).m_point );
            Point secondProjectedPoint = MYZMathTools.calculateProjectedPointOnLine( m_vPoint.elementAt( 0 ).m_point , m_vPoint.elementAt( 1 ).m_point , m_vPoint.elementAt( 3 ).m_point );
            
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
