package com.myz.xml;

import java.io.Serializable;
import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class XmlPoint extends MYZXmlObject implements Serializable
{
    //Constructor
    public XmlPoint()
    {
        super();
    }
    public XmlPoint( XmlPoint point )
    {
        super();
        m_name        = point.m_name;
        m_symbol      = point.m_symbol;
        m_description = point.m_description;
    }
    
    //Members
    public String m_name;
    public String m_symbol;
    public String m_description;
    
    //Methods
    @Override
    public void initialize( Attributes attributes )  
    {
        m_name        = getAttributeAsString( attributes , "name" );
        m_symbol      = getAttributeAsString( attributes , "symbol" );
        m_description = getAttributeAsString( attributes , "description" );
    }
    
    @Override
    public String toXml()
    {
        String XML = "<Point "
                   + setAttribute( "name" , m_name )
                   + setAttribute( "symbol" , m_symbol )
                   + setAttribute( "description" , m_description )
                   + "/>";
        return XML;
    }
    @Override
    public void append( MYZXmlObject xmlObject ){}
   
    //it's used for PointsTable 
    public String getM_name()
    {
        return m_name ;
    }
    public String getM_symbol()
    {
        return m_symbol ;
    }

    public String getM_description() 
    {
        return m_description;
    }
}
