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
    
    //Statics
     //Type
    public static final String TYPE_DISTANCE_BETWEEN_TWO_POINTS                   = "MYZOperationTypeDistanceBetweenTwoPoints";
    public static final String TYPE_DISTANCE_BETWEEN_TWO_LINES                    = "MYZOperationTypeDistanceBetweenTwoLines";
    public static final String TYPE_DISTANCE_BETWEEN_LINE_AND_POINT               = "MYZOperationTypeDistanceBetweenLineAndPoint";
    public static final String TYPE_DISTANCE_BETWEEN_TWO_PROJECTED_POINTS_ON_LINE = "MYZOperationTypeDistanceBetweenTwoProjectedPointsOnLine";
    public static final String TYPE_ANGLE_BETWEEN_THREE_POINTS                    = "MYZOperationTypeAngleBetweenThreePoints";
    public static final String TYPE_ANGLE_BETWEEN_TWO_LINES                       = "MYZOperationTypeAngleBetweenTwoLines";
    public static final String TYPE_RATIO_BETWEEN_TWO_LINES                       = "MYZOperationTypeRatioBetweenTwoLines";

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

    public String getM_name() {
        return m_name;
    }

    public String getM_description() {
        return m_description;
    }

    public String getM_type() {
        return m_type;
    }

    public double getM_correctValue() {
        return m_correctValue;
    }

    public double getM_errorRange() {
        return m_errorRange;
    }
    
}
