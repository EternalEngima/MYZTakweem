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
        m_helperX     = point.m_helperX;
        m_helperY     = point.m_helperY;
    }
    
    //Members
    public String m_name;
    public String m_symbol;
    public String m_description;
    public int    m_helperX;
    public int    m_helperY;
    
    //Methods
    @Override
    public void initialize( Attributes attributes )  
    {
        m_name        = getAttributeAsString( attributes , "name" );
        m_symbol      = getAttributeAsString( attributes , "symbol" );
        m_description = getAttributeAsString( attributes , "description" );
        m_helperX     = getAttributeAsInt(attributes, "helperX");
        m_helperY     = getAttributeAsInt(attributes, "helperY");
    }
    
    @Override
    public String toXml(int tabCount)
    {
        String XML = getTabsString(tabCount) + "<Point "
                   + setAttribute( "name" , m_name )               
                   + setAttribute( "symbol" , m_symbol )          
                   + setAttribute( "description" , m_description ) 
                   + setAttribute( "helperX" , m_helperX ) 
                   + setAttribute( "helperY" , m_helperY ) 
                   + "/>"  + "\n";
        return XML;
    }
    @Override
    public void append( MYZXmlObject xmlObject ){}
   
    //Setter methods
    public void setHelperX(int helperX)
    {
        m_helperX = helperX ;
    }
    public void setHelperY(int helperY)
    {
        m_helperY = helperY ;
    }
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
    public int getHelperX()
    {
        return m_helperX ;
    }
    public int getHelperY()
    {
        return m_helperY ;
    }
    
}
