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
    Vector <XmlAnalysis> m_vXmlAnalysis  = new Vector<XmlAnalysis>();
    XmlPointsPool        m_xmlPointsPool = new XmlPointsPool() ;
    
    //Methods
    @Override
    public void initialize(Attributes attributes)
    {
        
    }

    @Override
    public void append(MYZXmlObject xmlObject) 
    {
        if( xmlObject instanceof XmlAnalysis )
            m_vXmlAnalysis.addElement( (XmlAnalysis) xmlObject);
        else if (xmlObject instanceof XmlPointsPool)
            m_xmlPointsPool = (XmlPointsPool) xmlObject ;
        
    }

    @Override
    public String toXml() 
    {
        String XML = "<Takweem> ";
        for( XmlAnalysis analysis : m_vXmlAnalysis )
            XML += analysis.toXml();
        XML += "/Takweem>";
        return XML;
    }
    
    
}
