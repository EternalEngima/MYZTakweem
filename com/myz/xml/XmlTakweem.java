package com.myz.xml;

import java.io.Serializable;
import java.util.Vector;
import org.xml.sax.Attributes;
import java.io.PrintWriter;

/**
 * @author Montazar Hamoud
 *
 */
public class XmlTakweem extends MYZXmlObject implements Serializable
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
    public String toXml(int tabCount) 
    {
        tabCount = 0;
        int sonTabCount = tabCount + 1 ;
        String XML = "<Takweem> "  + "\n";
        for( XmlCategory category : m_vXmlCategory )
            XML += category.toXml(sonTabCount);
        XML += "</Takweem>";
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
    
    public void saveToFile(String filePath)
    {
        if(filePath == null)
            filePath = "D:\\Takweem.xml";
        
        String xmlString  = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
        xmlString        += toXml(0);
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(filePath);
            writer.print(xmlString);
            writer.flush();
            writer.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
