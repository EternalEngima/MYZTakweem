package com.myz.xml;

import java.io.Serializable;
import java.util.Vector;
import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public class XmlAnalysis extends MYZXmlObject implements Serializable
{
    //Constructor
    public XmlAnalysis()
    {
        m_vOperation = new Vector< XmlOperation >();
    }
    public XmlAnalysis ( XmlAnalysis analysis )
    {
        super();
        m_name        = analysis.m_name;
        m_description = analysis.m_description;
    }
    //Statics
     //Category
    public static String CATEGORY_RAYS      = "MYZAnalysisCategoryRays";
    public static String CATEGORY_FACE      = "MYZAnalysisCategoryFace";
    public static String CATEGORY_INTRAORAL = "MYZAnalysisCategoryIntraoral";
    public static String CATEGORY_GYPSUM    = "MyzAnalysisCategoryGypsum";
    
     //Type
       //Rays
    public static String TYPE_RAYS_LATERAL_CEPHALOMETRIC = "MYZAnalysisTypeRaysLateralCephalometric";
    public static String TYPE_RAYS_FRONTAL_CEPHALOMETRIC = "MYZAnalysisTypeRaysFrontalCephalometric";
    public static String TYPE_RAYS_PANORAMIC_RADIOLOGY   = "MYZAnalysisTypeRaysPanoramicRadiology";
    public static String TYPE_RAYS_BONE_AGE              = "MYZAnalysisTypeRaysBoneAge";
    public static String TYPE_RAYS_CBCT_RADIO            = "MYZAnalysisTypeRaysCBCTRadio";
       //Face
    public static String TYPE_FACE_PROFILE       = "MYZAnalysisTypeFaceProfile";
    public static String TYPE_FACE_FRONT         = "MYZAnalysisTypeFaceFront";
    public static String TYPE_FACE_LIPS_IN_REST  = "MYZAnalysisTypeFaceLipsInRest";
    public static String TYPE_FACE_SMILE_FRONT   = "MYZAnalysisTypeFaceSmileFront";
    public static String TYPE_FACE_SMILE_PROFILE = "MYZAnalysisTypeFaceSmileProfile";
       //Intraoral
    public static String TYPE_INTRAORAL_BUCCAL_FRONT  = "MYZAnalysisTypeIntraoralBuccalFront";
    public static String TYPE_INTRAORAL_BUCCAL_RIGHT  = "MYZAnalysisTypeIntraoralBuccalRight";
    public static String TYPE_INTRAORAL_BUCCAL_LEFT   = "MYZAnalysisTypeIntraoralBuccalLeft";
    public static String TYPE_INTRAORAL_OCCLUSAL_UP   = "MYZAnalysisTypeIntraoralOcclusalUp";
    public static String TYPE_INTRAORAL_OCCLUSAL_DOWN = "MYZAnalysisTypeIntraoralOcclusalDown";    
       //Gypsum
    public static String TYPE_GYPSUM_MAXILLARY_CAST   = "MYZAnalysisTypeGypsumMaxillaryCast";
    public static String TYPE_GMPSUM_MANDIBULAR_CAST  = "MYZAnalysisTypeGypsumMandibularCast";
    public static String TYPE_GYPSUM_                 = "MYZAnalysisTypeGypsum_";
    public static String TYPE_GYPSUM_OCCLUSAL_CANTING = "MYZAnalysisTypeGypsumOcclusalCanting";
    public static String TYPE_GYPSUM_BOLTON           = "MYZAnalysisTypeGypsumBolton";
            
    //Members
    public int             m_category;
    public int             m_type;
    public String          m_name;
    public String          m_description;
    Vector< XmlOperation > m_vOperation;
    
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
        if( xmlObject instanceof XmlOperation )
            m_vOperation.addElement( (XmlOperation) xmlObject );
    }
    
    @Override
    public String toXml(int tabCount)
    {
        int    sonTabCount = tabCount + 1;
        String tabString   = getTabsString(tabCount);
        String XML = tabString + "<Analysis "
                   + setAttribute( "name" , m_name )
                   + setAttribute( "description" , m_description )
                   + setAttribute( "category" , m_category )
                   + " > " + "\n";
        for( XmlOperation operation : m_vOperation )
            XML += operation.toXml(sonTabCount);
        XML += tabString + "</Analysis>"  + "\n";
        return XML;
    }
    
    //Getter Methods
    public String getName()
    {
        return m_name ;
    }
    public Vector<XmlOperation> getVXmlOperation()
    {
        return m_vOperation ; 
    }

    public void setM_vOperation(Vector<XmlOperation> m_vOperation) 
    {
        this.m_vOperation = m_vOperation;
    }
    
}
