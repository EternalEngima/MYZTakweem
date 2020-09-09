/*
 *This class present Four categories 1. X-ray
                                     2. Face
                                     3. Intraoral
                                     4. Gypsum

 *
 *
 */
package com.myz.xml;

import java.util.Vector;
import org.xml.sax.Attributes;

/**
 *
 * @author Montazar Hamoud
 */
public class XmlCategory extends MYZXmlObject
{

    //Data members
    Vector <XmlClassification> m_vXmlClassification = new Vector();
    String m_name ;
    
    @Override
    public void initialize(Attributes attributes) 
    {
        m_name = getAttributeAsString(attributes, "name");
    }

    @Override
    public void append(MYZXmlObject xmlObject)
    {
        if( xmlObject instanceof XmlClassification )
            m_vXmlClassification.addElement( (XmlClassification) xmlObject );
    }

    @Override
    public String toXml() 
    {
        String XML = "<Category "
                    + setAttribute("name", m_name)
                    + ">";
        for( XmlClassification classification : m_vXmlClassification )
            XML += classification.toXml();
        XML += "/Category>";
        return XML;
       
    }
    //Getter methods
    public String getName()
    {
        return m_name ;
    }
    public Vector<XmlClassification> gteVClassification()
    {
        return m_vXmlClassification;
    }
    public XmlClassification getClassificationByName (String classificationName)
    {
        for(XmlClassification classification : m_vXmlClassification)
        {
            if(classificationName.equals(classification.getName()))
                return classification ;
        }
        return null ; 
    }
}
