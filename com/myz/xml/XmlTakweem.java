package com.myz.xml;

import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Montazar Hamoud
 *
 */
public class XmlTakweem extends MYZXmlObject
{
    
    //Data mambers 
    Vector <XmlCategory> m_vXmlCategory  = new Vector<>();
    
    //Methods
    @Override
    public void initialize(Attributes attributes)
    {
        
    }

    @Override
    public void append(MYZXmlObject xmlObject) 
    {
        if( xmlObject instanceof XmlCategory )
            m_vXmlCategory.addElement( (XmlCategory) xmlObject);
        
    }

    @Override
    public String toXml() 
    {
        String XML = "<Takweem> ";
        for( XmlCategory category : m_vXmlCategory )
            XML += category.toXml();
        XML += "/Takweem>";
        return XML;
    }
    //Getter methods
    public Vector<XmlCategory> getVCategory()
    {
        return m_vXmlCategory ;
    }
    
    public XmlCategory getCategoryByName(String categoryName)
    {
        for(XmlCategory category : m_vXmlCategory)
        {
            if(categoryName.equals(category.getName()))
                return category ;
        }
        return null ;
    }
    
}
