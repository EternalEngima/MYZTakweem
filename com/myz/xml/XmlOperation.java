package com.myz.xml;

import java.io.Serializable;
import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class XmlOperation extends MYZXmlObject implements Serializable
{
    //Constructor
    public XmlOperation()
    {
        m_vXmlPoint = new Vector< XmlPoint >();
    }
    
    public XmlOperation(XmlOperation operation)
    {
        this.m_name         = operation.m_name;
        this.m_type         = operation.m_type;
        this.m_correctValue = operation.m_correctValue;
        this.m_description  = operation.m_description;
        this.m_errorRange   = operation.m_errorRange;
        this.m_vXmlPoint    = new Vector< XmlPoint >();
        for ( int i = 0 ; i < operation.m_vXmlPoint.size() ; i++)
        {
            XmlPoint point = (XmlPoint)operation.m_vXmlPoint.elementAt(i);
            XmlPoint myPoint = new XmlPoint(point);
            this.m_vXmlPoint.add(myPoint);
        }
    }
    
    //Statics
     //Type
    public static final String TYPE_DISTANCE_BETWEEN_TWO_POINTS                   = "MYZOperationTypeDistanceBetweenTwoPoints";
    public static final String TYPE_DISTANCE_BETWEEN_TWO_LINES                    = "MYZOperationTypeDistanceBetweenTwoLines";
    public static final String TYPE_DISTANCE_BETWEEN_LINE_AND_POINT               = "MYZOperationTypeDistanceBetweenLineAndPoint";
    public static final String TYPE_DISTANCE_BETWEEN_TWO_PROJECTED_POINTS_ON_LINE = "MYZOperationTypeDistanceBetweenTwoProjectedPointsOnLine";
    public static final String TYPE_ANGLE_BETWEEN_THREE_POINTS                    = "MYZOperationTypeAngleBetweenThreePoints";
    public static final String TYPE_ANGLE_BETWEEN_TWO_LINES                       = "MYZOperationTypeAngleBetweenTwoLines";
    public static final String TYPE_RATIO_BETWEEN_TWO_LINES                       = "MYZOperationTypeRatioBetweenTwoLines";

    public static Vector getOperationType()
    {
        Vector type = new Vector();
        type.add(TYPE_DISTANCE_BETWEEN_TWO_POINTS);
        type.add(TYPE_DISTANCE_BETWEEN_TWO_LINES);
        type.add(TYPE_DISTANCE_BETWEEN_LINE_AND_POINT);
        type.add(TYPE_DISTANCE_BETWEEN_TWO_PROJECTED_POINTS_ON_LINE);
        type.add(TYPE_ANGLE_BETWEEN_THREE_POINTS);
        type.add(TYPE_ANGLE_BETWEEN_TWO_LINES);
        type.add(TYPE_RATIO_BETWEEN_TWO_LINES);
        return type;
    }
    
    public static int getOperationNeededPoint(String operationType)
    {
        if (operationType.equals(TYPE_DISTANCE_BETWEEN_TWO_POINTS))
            return 2;
        if (operationType.equals(TYPE_DISTANCE_BETWEEN_TWO_LINES))
            return 4;
        if (operationType.equals(TYPE_DISTANCE_BETWEEN_LINE_AND_POINT))
            return 3;
        if (operationType.equals(TYPE_DISTANCE_BETWEEN_TWO_PROJECTED_POINTS_ON_LINE))// TODO
            return 2;
        if (operationType.equals(TYPE_ANGLE_BETWEEN_THREE_POINTS))
            return 3;
        if (operationType.equals(TYPE_ANGLE_BETWEEN_TWO_LINES))
            return 4;
        if (operationType.equals(TYPE_RATIO_BETWEEN_TWO_LINES))
            return 2;
        
        return 0;
    }
    
     //Classification TODO
//    public static final String CLASSIFICATION_SKELETON = "MYZOperationClassificationSkeleton";
//    public static final String CLASSIFICATION_1        = "MYZOperationClassification";
//    public static final String CLASSIFICATION_2        = "MYZOperationClassification";
    
    //Members
    public String m_name;
    public String m_description;
    public String m_type;
    public double m_correctValue;
    //String m_classification; TODO 
    public double m_errorRange;
    protected Vector< XmlPoint > m_vXmlPoint;
    
    //Methods
    @Override
    public void initialize( Attributes attributes )
    {
        m_name           = getAttributeAsString( attributes , "name" );
        m_description    = getAttributeAsString( attributes , "description" );
        m_type           = getAttributeAsString( attributes , "type" );
        m_correctValue   = getAttributeAsDobule( attributes , "correctValue" );
        //m_classification = getAttributeAsString( attributes ,  );
        m_errorRange     = getAttributeAsDobule( attributes , "errorRange" );
    }

    @Override
    public void append( MYZXmlObject xmlObject ) 
    {
        if( xmlObject instanceof XmlPoint )
            m_vXmlPoint.addElement( (XmlPoint) xmlObject );        
    }
    
    @Override
    public String toXml(int tabCount)
    {
        int    sonTabCount = tabCount + 1;
        String tabString   = getTabsString(tabCount);
        String XML = tabString + "<Operation "      
                    + setAttribute( "name" , m_name )               
                    + setAttribute( "description" , m_description )    
                    + setAttribute( "type" , m_type )                  
                    + setAttribute( "correctValue" , m_correctValue )  
                    + setAttribute( "errorRange" , m_errorRange )     
                   //+ setAttribute( "classification" , m_classification )
                   + " > "  + "\n";      
        for( XmlPoint point : m_vXmlPoint )
            XML += point.toXml(sonTabCount);
        XML += tabString + "</Operation>"  + "\n";
        return XML;
    }
    //Getter Methods
    public Vector<XmlPoint> getVXmlPoint()
    {
        return m_vXmlPoint ;
    }

    public String getM_name() 
    {
        return m_name;
    }

    public String getM_description() 
    {
        return m_description;
    }

    public String getM_type() 
    {
        return m_type;
    }

    public double getM_correctValue() 
    {
        return m_correctValue;
    }

    public double getM_errorRange() 
    {
        return m_errorRange;
    }
    
    
}
