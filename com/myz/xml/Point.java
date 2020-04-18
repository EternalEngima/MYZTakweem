package com.myz.xml;

import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class Point extends MYZXmlObject
{
    //Constructor
    public Point()
    {
        
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
    public void append(MYZXmlObject xmlObject){}
    
}
