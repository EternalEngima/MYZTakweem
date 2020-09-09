package com.myz.xml;

import com.myz.calculable.MYZPoint;
import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Montazar Hamoud
 *
 */
public class XmlPointsPool extends MYZXmlObject
{
    //Data members
    Vector<XmlPoint> m_vXmlPoints   = new Vector<>();
    
    //Methods
    @Override
    public void initialize(Attributes attributes)
    {
        
    }

    @Override
    public void append(MYZXmlObject xmlObject) 
    {
        if( xmlObject instanceof XmlPoint )
            m_vXmlPoints.addElement( (XmlPoint) xmlObject);
    }

    @Override
    public String toXml() 
    {
        String XML = "<PointsPool> ";
        for( XmlPoint point : m_vXmlPoints )
            XML += point.toXml();
        XML += "/PointsPool>";
        return XML;
    }
    
    //Getter Methods
    public Vector<XmlPoint> getVPoints()
    {
        return m_vXmlPoints ;
    }
}
