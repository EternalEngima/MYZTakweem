package com.myz.xml;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
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
        parser.getParsedObject();
        System.out.print( "s");
    }
    //Constructor
    public MYZXmlParser()
    {
        m_stack     = new Stack<>();
        m_xmlObject = new XmlTakweem();
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
        catch( ParserConfigurationException | SAXException | IOException ex )
        {
            ex.printStackTrace(); 
        }
        
    }
        
    //Members
    Stack< MYZXmlObject > m_stack ;
    MYZXmlObject          m_xmlObject ;
            
    //Methods
    
    public MYZXmlObject getParsedObject()
    {
        return m_xmlObject;
    }
    
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
        {
            MYZXmlObject peekedObject = m_stack.peek();
            if( m_xmlObject.getClass().isInstance( peekedObject ) )
            {
                m_xmlObject = peekedObject;
            }
            String peekedObjectClass = peekedObject.getClass().toString().substring(6);
            if( peekedObjectClass.equals( tag ) )            
                m_stack.pop();            
            else
                System.out.println( "Error while parsing : trying to endElement peekedObject : " + peekedObjectClass + " and the qName is : " + tag );
        
        }
            m_stack.pop();
    }
    
}
