/*
 * This class present Five Classifications for each category
 *
 */
package com.myz.xml;

import java.io.Serializable;
import java.util.Vector;
import org.xml.sax.Attributes;

/**
 *
 * @author Montazar Hamoud
 */
public class XmlClassification extends MYZXmlObject implements Serializable
{

    //Data member
    Vector <XmlAnalysis> m_vXmlAnalysis  = new Vector();
    XmlPointsPool        m_XmlpointsPool = new XmlPointsPool();
    String               m_name ;
    
    @Override
    public void initialize(Attributes attributes) 
    {
        m_name = getAttributeAsString(attributes, "name");
    }

    @Override
    public void append(MYZXmlObject xmlObject)
    {
        if( xmlObject instanceof XmlAnalysis )
            m_vXmlAnalysis.addElement( (XmlAnalysis) xmlObject );
        else if (xmlObject instanceof XmlPointsPool)
            m_XmlpointsPool = (XmlPointsPool) xmlObject ;
    }

    @Override
    public String toXml(int tabCount) 
    {
        int    sonTabCount = tabCount + 1;
        String tabString   = getTabsString(tabCount);
        String XML =   tabString + "<Classification "          
                   + setAttribute("name", m_name)
                   +">"                                + "\n";
        
        XML += m_XmlpointsPool.toXml(sonTabCount);

        for( XmlAnalysis xmlAnalysis : m_vXmlAnalysis )
            XML += xmlAnalysis.toXml(sonTabCount);
        XML += tabString + "</Classification>"  + " \n";
        return XML;
    }
    //Getter methods
    public String getName()
    {
        return m_name ;
    }
    public Vector<XmlAnalysis> getVAnalysis()
    {
        return m_vXmlAnalysis ;
    }
    public XmlPointsPool getPointsPool()
    {
        return m_XmlpointsPool;
    }
    
    public XmlAnalysis getAnalysisByName (String analysisName)
    {
        for(XmlAnalysis analysis : m_vXmlAnalysis)
        {
            if(analysisName.equals(analysis.getName()))
                return analysis ;
        }
        return null ; 
    }
}
