package com.myz.calculable;

import com.myz.image.Line;
import com.myz.image.Point;
import com.myz.xml.XmlOperation;
import com.myz.xml.XmlPoint;
import java.io.Serializable;
import java.util.Vector;
import javafx.scene.image.ImageView;

/**
 * @author Zaid
 */

public class MYZOperation extends XmlOperation implements Serializable
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
        result = operation.calculateAndDraw(null);        
        //Vertical line
        lPoint1 = new Point( 1 , 1 );
        lPoint2 = new Point( 1 , 5 );
        point1  = new Point( 4 , 2 );
        operation.m_vPoint = new Vector<>();
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        result = operation.calculateAndDraw(null);
        //testCase#1
        lPoint1 = new Point( 1 , 1 );
        lPoint2 = new Point( 1 , 5 );
        point1  = new Point( 1 , 6 );
        operation.m_vPoint = new Vector<>();
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        result = operation.calculateAndDraw(null);
        lPoint1 = new Point( 1 , 1 );
        lPoint2 = new Point( 2 , 2 );
        point1  = new Point( 3 , 3 );
        operation.m_vPoint = new Vector<>();
        operation.m_vPoint.addElement( new MYZPoint( lPoint1 ) );
        operation.m_vPoint.addElement( new MYZPoint( lPoint2 ) );
        operation.m_vPoint.addElement( new MYZPoint( point1 ) );
        result = operation.calculateAndDraw(null);        
        
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
        result = operation.calculateAndDraw(null);                
    }
    
    //Constructor    
    MYZOperation()
    {
        super();
        m_vPoint = new Vector<>();
    }
    MYZOperation(Vector<XmlPoint> vXmlPoint)
    {
        super();
        m_vPoint = new Vector<>();
        for(XmlPoint xmlPoint :vXmlPoint)
            m_vPoint.addElement(new MYZPoint(xmlPoint));
    }
    MYZOperation(XmlOperation operation)
    {
        m_name         = operation.m_name ;
        m_description  = operation.m_description ;
        m_correctValue = operation.m_correctValue;
        m_type         = operation.m_type;
        for(XmlPoint xmlPoint :operation.getVXmlPoint())
            m_vPoint.addElement(new MYZPoint(xmlPoint));
    }

    public MYZOperation(String name ,double value , double correctValue) 
    {
        m_name         = name ; 
        m_value        = value;
        m_correctValue = correctValue ;
    }
    
    //Members
    double             m_value;
    Vector< MYZPoint > m_vPoint = new Vector();
    //it would help me to undraw lines when the analysis change And when undo for point 
    Vector<Line>       m_vLine  = new Vector(); 
    
    //Methods
    public double calculateAndDraw(ImageView image)
    {
        m_value = 0;
        
        if( TYPE_DISTANCE_BETWEEN_TWO_POINTS.equals( m_type ) )
        {
            Point point1  = m_vPoint.elementAt( 0 ).m_point ;
            Point point2  = m_vPoint.elementAt( 1 ).m_point ;
            Line  line    = new Line(point1, point2);
            line.draw(image , Line.ANALYSIS_LINE_COLOR);
            addLine(line);
            m_value       = MYZMathTools.calculateDistanceBetweenTwoPoints( point1 , point2 );                           
        }
        else if( TYPE_DISTANCE_BETWEEN_LINE_AND_POINT.equals( m_type ) )
        {
            //Assuming that the first two element are the line and the last element is the point
            Point point1  = m_vPoint.elementAt( 0 ).m_point ;
            Point point2  = m_vPoint.elementAt( 1 ).m_point ;
            Line  line    = new Line(point1, point2);
            line.draw(image , Line.ANALYSIS_LINE_COLOR);
            addLine(line);
            Point projectedPoint = MYZMathTools.calculateProjectedPointOnLine( point1 , point2 , m_vPoint.elementAt( 2 ).m_point );
            
            m_value = MYZMathTools.calculateDistanceBetweenTwoPoints( projectedPoint , m_vPoint.elementAt( 2 ).m_point );            
        }
        else if( TYPE_DISTANCE_BETWEEN_TWO_LINES.equals( m_type ) )
        {
            Point point1  = m_vPoint.elementAt( 0 ).m_point ;
            Point point2  = m_vPoint.elementAt( 1 ).m_point ;
            Point point3  = m_vPoint.elementAt( 2 ).m_point ;
            Point point4  = m_vPoint.elementAt( 3 ).m_point ;
            Line  line1   = new Line(point1, point2);
            Line  line2   = new Line(point3, point4);
            line1.draw(image , Line.ANALYSIS_LINE_COLOR);
            line2.draw(image , Line.ANALYSIS_LINE_COLOR);
            addLine(line1);
            addLine(line2);
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
        else if( TYPE_ANGLE_BETWEEN_THREE_POINTS.equals( m_type ) )
        {
            Point point1  = m_vPoint.elementAt( 0 ).m_point ;
            Point point2  = m_vPoint.elementAt( 1 ).m_point ;
            Point point3  = m_vPoint.elementAt( 2 ).m_point ;
            Line  line1   = new Line(point1, point2);
            Line  line2   = new Line(point2, point3);
            line1.draw(image , Line.ANALYSIS_LINE_COLOR);
            line2.draw(image , Line.ANALYSIS_LINE_COLOR);
            addLine(line1);
            addLine(line2);
            Point firstPointL1  = m_vPoint.elementAt( 0 ).m_point;
            Point secondPointL1 = m_vPoint.elementAt( 1 ).m_point;
            
            Point firstPointL2  = m_vPoint.elementAt( 2 ).m_point;
            Point secondPointL2 = m_vPoint.elementAt( 1 ).m_point;
            
            m_value = MYZMathTools.calculateAngelBetweenTwoLines( firstPointL1 , secondPointL1 , firstPointL2 , secondPointL2 );            
        }
        else if( TYPE_ANGLE_BETWEEN_TWO_LINES.equals( m_type ) )
        {
            Point point1  = m_vPoint.elementAt( 0 ).m_point ;
            Point point2  = m_vPoint.elementAt( 1 ).m_point ;
            Point point3  = m_vPoint.elementAt( 2 ).m_point ;
            Point point4  = m_vPoint.elementAt( 3 ).m_point ;
            Line  line1   = new Line(point1, point2);
            Line  line2   = new Line(point3, point4);
            line1.draw(image , Line.ANALYSIS_LINE_COLOR);
            line2.draw(image , Line.ANALYSIS_LINE_COLOR);
            addLine(line1);
            addLine(line2);
            Point firstPointL1  = m_vPoint.elementAt( 0 ).m_point;
            Point secondPointL1 = m_vPoint.elementAt( 1 ).m_point;
            
            Point firstPointL2  = m_vPoint.elementAt( 2 ).m_point;
            Point secondPointL2 = m_vPoint.elementAt( 3 ).m_point;
            
            m_value = MYZMathTools.calculateAngelBetweenTwoLines( firstPointL1 , secondPointL1 , firstPointL2 , secondPointL2 );            
        }
        else if( TYPE_RATIO_BETWEEN_TWO_LINES.equals( m_type ) )
        {
            Point point1  = m_vPoint.elementAt( 0 ).m_point ;
            Point point2  = m_vPoint.elementAt( 1 ).m_point ;
            Point point3  = m_vPoint.elementAt( 2 ).m_point ;
            Point point4  = m_vPoint.elementAt( 3 ).m_point ;
            Line  line1   = new Line(point1, point2);
            Line  line2   = new Line(point3, point4);
            line1.draw(image , Line.ANALYSIS_LINE_COLOR);
            line2.draw(image , Line.ANALYSIS_LINE_COLOR);
            addLine(line1);
            addLine(line2);
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
    public Vector<MYZPoint> getVMYZPoint()
    {
        return m_vPoint ;
    }
    public Vector<Line> getVLine()
    {
        return m_vLine ;
    }
    public void addLine(Line line)
    {
        m_vLine.addElement(line);
    }
    public void removeAllLines(ImageView image)
    {
        for(int i = 0 ; i < m_vLine.size() ; i++)
        {
            m_vLine.elementAt(i).erase(image);
        }
            m_vLine.removeAllElements();
    }
    //Getter for analysis table
    public String getM_value()
    {
        return String.valueOf(m_value);
    }
    public String getM_name()
    {
        return m_name ;
    }
    public String getM_correctValue()
    {
        return String.valueOf(m_correctValue);
    }
    public String getM_errorRange()
    {
        return String.valueOf(m_errorRange);
    }
}
