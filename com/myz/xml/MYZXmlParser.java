package com.myz.xml;

import java.io.File;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

/**
 * @author Zaid 
 */

public class MYZXmlParser extends DefaultHandler2
{
    //Driver
    public static void main( String[] args )
    {
        MYZXmlParser parser = new MYZXmlParser();
        System.out.print( "s");
    }
    //Constructor
    public MYZXmlParser()
    {
        m_stack = new Stack<>();
        
        //init parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating( true );
        SAXParser saxParser = null;
        XMLReader xmlReader = null;
        try
        {
            saxParser = factory.newSAXParser();
            xmlReader = saxParser.getXMLReader();
            xmlReader.setProperty( "http://xml.org/sax/properties/lexical-handler" , this );
            saxParser.parse( new File( "src\\Takweem.xml" ) , this );
        }
        catch( Exception e ){ e.printStackTrace(); }
        
    }
        
    //Members
    Stack< MYZXmlObject > m_stack;
            
    //Methods
    @Override
    public void startElement( String uri , String localName , String tag , Attributes attributes )
    {
        MYZXmlObject xmlObject = MYZXmlObject.create( tag );
        if( xmlObject == null )
            return;
        
        xmlObject.initialize( attributes );
        if( ! m_stack.empty() )
        {
            MYZXmlObject top = m_stack.peek();
            top.append( xmlObject );
        }
        m_stack.push( xmlObject );
    }
    
    @Override
    public void endElement( String uri, String localName, String tag )
    {
        if( !m_stack.isEmpty() )
            m_stack.pop();
    }
    
}
