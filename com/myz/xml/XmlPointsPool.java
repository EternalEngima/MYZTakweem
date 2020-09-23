package com.myz.xml;

import java.io.Serializable;
import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Montazar Hamoud
 *
 */
public class XmlPointsPool extends MYZXmlObject implements Serializable
{
    //Data members
    Vector<XmlPoint> m_vXmlPoints      = new Vector<>();
    public String    m_helperImagePath = "";  
    
    //Methods
    @Override
    public void initialize(Attributes attributes)
    {
        m_helperImagePath = getAttributeAsString(attributes, "helperImagePath");
    }

    @Override
    public void append(MYZXmlObject xmlObject) 
    {
        if( xmlObject instanceof XmlPoint )
            m_vXmlPoints.addElement( (XmlPoint) xmlObject);
    }

    @Override
    public String toXml(int tabCount) 
    {
        int    sonTabCount = tabCount + 1;
        String tabString   = getTabsString(tabCount);
        
        String XML  = tabString + "<PointsPool  ";
               XML += setAttribute("helperImagePath" , m_helperImagePath);
               XML += ">" + "\n";

        for( XmlPoint point : m_vXmlPoints )
            XML += point.toXml(sonTabCount);
        XML += tabString + "</PointsPool>" + "\n";
        return XML;
    }
    
    //Getter Methods
    public Vector<XmlPoint> getVPoints()
    {
        return m_vXmlPoints ;
    }

    
    public void setVPoints(Vector vXmlPoints)
    {
        m_vXmlPoints = vXmlPoints;
    }
    public String getHelperImagePath()
    {
        return m_helperImagePath ;

    }
}
