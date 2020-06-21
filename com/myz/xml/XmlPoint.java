package com.myz.xml;

import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class XmlPoint extends MYZXmlObject
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
    String m_name;
    String m_symbol;
    String m_description;
    
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
    
}
