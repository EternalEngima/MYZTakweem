package com.myz.xml;

import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class Analysis extends MYZXmlObject
{
    //Constructor
    public Analysis()
    {
        m_vOperation = new Vector< Operation >();
    }
    
    //Statics for categories ? ?
    
    //Members
    public int          m_category;
    public String       m_name;
    public String       m_description;
    Vector< Operation > m_vOperation;
    
    //Methods

    @Override
    public void initialize( Attributes attributes ) 
    {
        m_category    = getAttributeAsInt( attributes , "category" );
        m_name        = getAttributeAsString( attributes , "name" );
        m_description = getAttributeAsString( attributes , "description" );
    }
    
    @Override
    public void append( MYZXmlObject xmlObject )
    {
        if( xmlObject instanceof Operation )
            m_vOperation.addElement( (Operation) xmlObject );
    }
    
    @Override
    public String toXml()
    {
        String XML = "<Analysis"
                   + setAttribute( "name" , m_name )
                   + setAttribute( "description" , m_description )
                   + setAttribute( "category" , m_category )
                   + " > ";
        for( Operation operation : m_vOperation )
            XML += operation.toXml();
        XML += "/Analysis>";
        return "";
    }
}
