package com.myz.xml;

import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class Operation extends MYZXmlObject
{
    //Constructor
    public Operation()
    {
        m_vPoint = new Vector< Point >();
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
    protected String m_name;
    protected String m_description;
    protected String m_type;
    protected double m_correctValue;
    //String m_classification; TODO 
    //m_errorRange TODO
    Vector< Point > m_vPoint;
    
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
        if( xmlObject instanceof Point )
            m_vPoint.addElement( (Point) xmlObject );        
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
        for( Point point : m_vPoint )
            XML += point.toXml();
        XML += "</Operation>";
        return XML;
    }
}
