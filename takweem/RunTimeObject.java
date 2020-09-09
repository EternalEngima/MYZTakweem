/*
 *
 */
package takweem;

import com.myz.calculable.MYZAnalysis;
import com.myz.calculable.MYZOperation;
import com.myz.calculable.MYZPoint;
import com.myz.calculable.MYZPointsPool;
import com.myz.image.ImagePanel;
import com.myz.image.Line;
import com.myz.image.Point;
import com.myz.xml.MYZXmlParser;
import com.myz.xml.XmlAnalysis;
import com.myz.xml.XmlCategory;
import com.myz.xml.XmlClassification;
import com.myz.xml.XmlPointsPool;
import com.myz.xml.XmlTakweem;
import static takweem.Takweem.m_calculateTable;

/**
 *
 * @author Montazar Hamoud
 */
public class RunTimeObject 
{

    public RunTimeObject() 
    {
        m_parser    = new MYZXmlParser();
        m_xmlObject = (XmlTakweem) m_parser.getParsedObject();
    }
    
    
    //Data member
    MYZXmlParser      m_parser    ;
    XmlTakweem        m_xmlObject ;
    XmlCategory       m_RunTimeCategory       ;
    XmlClassification m_RunTimeClassification ;
    MYZPointsPool     m_RunTimePointsPool;
    MYZAnalysis       m_RunTimeAnalysis ;
    //ImagePanel
    public ImagePanel   m_imagePanel     = new ImagePanel();//add by montazar 
    
    //Methods
    public void setRunTimeCategoryByName(String categoryName)
    {
        if(getRunTimeTakweem() != null)
            setRunTimeCategory(getRunTimeTakweem().getCategoryByName(categoryName));
    }
    public void setRunTimeClassifiactionByName(String classificationName)
    {
        if(getRunTimeCategory() != null)
            setRunTimeClassification(getRunTimeCategory().getClassificationByName(classificationName));
    }
    public void setRunTimeAnalysisByName(String analysisName)
    {
        if(getRunTimeClassification() != null)
        {
            setRunTimeAnalysis(getRunTimeClassification().getAnalysisByName(analysisName));
        }
    }
    //To remove the lines if the analysis has been changed
    public void erasePreviouseAnalysis()
    {
        for(MYZOperation operation : m_RunTimeAnalysis.getVOperation())
        {
            System.out.println("V operation size : "+m_RunTimeAnalysis.getVOperation().size());
            operation.removeAllLines(m_imagePanel.getBlankImageView());
        }
    }
    
    public void eraseLine(Point point )
    {
        Line line = null ; 
        for(MYZOperation operation : m_RunTimeAnalysis.getVOperation())
        {
            for(int i = 0 ; i < operation.getVLine().size() ; i++)
            {
                line = operation.getVLine().elementAt(i);
                //That's mean it's the first time to choose analysis
                if(point.equals(line.getStartPoint()) || point.equals(line.getEndPoint()) )
                {
                    line.erase(m_imagePanel.getBlankImageView());//TODO repaint the not removed point 
                    operation.getVLine().removeElementAt(i);
                    i -= 1 ;
                }
            }
        }
    }
    
    public void Undo()
    {
        if(getRunTimePointsPool() != null && !getRunTimePointsPool().getVMYZPointValue().isEmpty())
        {
            int      size  =  getRunTimePointsPool().getVMYZPointValue().size() - 1;
            MYZPoint point = getRunTimePointsPool().getVMYZPointValue().remove(size) ;
            getRunTimePointsPool().getVMYZPoint().add(0, point);
            point.getPoint().erase(m_imagePanel.getBlankImageView());
            //Remove the line that have this point
            if(getRunTimeAnalysis() != null)
            {
                eraseLine(point.getPoint());
            }
        }
    }
     /*
     *This method will used on analysis combobox selection change and image panel paint
     * case 1 : the points have been set then the analysis choosen ( i will calculate on combobox selection change )
     * case 2 : the analysis is choosen but the points don't set yet ( i will calculate on last poin will set )
     */
    public void calculateOperationsAndShow( )
    {
        
        for(MYZOperation operation : m_RunTimeAnalysis.getVOperation())
        {
            for(MYZPoint point :operation.getVMYZPoint())
            {
                m_RunTimePointsPool.setPointValue(point);
            }
            System.out.println("operation name : "  + operation.m_name +"value = " + operation.calculateAndDraw(m_imagePanel.getBlankImageView()));
        }
        m_calculateTable.setTableData(m_RunTimeAnalysis.getVOperation());
        
    }
    public void initRunTimePointsPool()
    {
        setRunTimePointsPool(m_RunTimeClassification.getPointsPool());
    }
    
    //Setter 
    public void setRunTimeCategory( XmlCategory xmlCategory)
    {
        m_RunTimeCategory = xmlCategory ;
    }
    public void setRunTimeClassification( XmlClassification xmlClassification)
    {
        m_RunTimeClassification = xmlClassification ;
    }
    public void setRunTimePointsPool(XmlPointsPool xmlPointsPool)
    {
        m_RunTimePointsPool =  new MYZPointsPool(xmlPointsPool); 
    }
    public void setRunTimeAnalysis(XmlAnalysis xmlAnalysis)
    {
        m_RunTimeAnalysis =  new MYZAnalysis(xmlAnalysis);
    }
    //Getter 
    public MYZXmlParser getParser()
    {
        return m_parser;
    }
    public XmlTakweem getRunTimeTakweem()
    {
        return m_xmlObject ;
    }
    public XmlCategory getRunTimeCategory()
    {
        return m_RunTimeCategory ;
    }
    public XmlClassification getRunTimeClassification()
    {
        return m_RunTimeClassification ;
    }
    public MYZPointsPool getRunTimePointsPool()
    {
        return m_RunTimePointsPool;
    }
    public MYZAnalysis getRunTimeAnalysis()
    {
        return m_RunTimeAnalysis ;
    }
    public ImagePanel getImagePanel()
    {
        return m_imagePanel;
    }
    

}
