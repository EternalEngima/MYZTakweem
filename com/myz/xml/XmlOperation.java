package com.myz.xml;

import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class XmlOperation extends MYZXmlObject
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
    public static final String TYPE_ANGEL_BETWEEN_THREE_POINTS                    = "MYZOperationTypeAngelBetweenThreePoints";
    public static final String TYPE_ANGEL_BETWEEN_TWO_LINES                       = "MYZOperationTypeAngelBetweenTwoLines";
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
    //m_errorRange TODO
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
    }

    @Override
    public void append( MYZXmlObject xmlObject ) 
    {
        if( xmlObject instanceof XmlPoint )
            m_vXmlPoint.addElement( (XmlPoint) xmlObject );        
    }
    
    @Override
    public String toXml()
    {
        String XML = "<Operation "
                   + setAttribute( "name" , m_name )
                   + setAttribute( "description" , m_description )
                   + setAttribute( "type" , m_type )
                   + setAttribute( "correctValue" , m_correctValue )
                   //+ setAttribute( "classification" , m_classification )
                   + " > ";
        for( XmlPoint point : m_vXmlPoint )
            XML += point.toXml();
        XML += "</Operation>";
        return XML;
    }
    //Getter Methods
    public Vector<XmlPoint> getVXmlPoint()
    {
        return m_vXmlPoint ;
    }
}
