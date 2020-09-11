package com.myz.calculable;

import com.myz.xml.XmlAnalysis;
import com.myz.xml.XmlOperation;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Montazar Hamoud
 */
public class MYZAnalysis extends XmlAnalysis implements Serializable
{
    public MYZAnalysis(XmlAnalysis analysis)
    {
        m_name        = analysis.m_name;
        m_description = analysis.m_description;
        
        for(XmlOperation operation : analysis.getVXmlOperation())
            m_vOperation.addElement(new MYZOperation(operation));
    
    }
    
    //Data Member
    Vector <MYZOperation> m_vOperation     = new Vector();
    
    //Methods

    public Vector<MYZOperation> getVOperation()
    {
        return m_vOperation;
    }
}
