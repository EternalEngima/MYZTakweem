package com.myz.xml;

import org.xml.sax.Attributes;

/**
 * @author Zaid
 */

public abstract class MYZXmlObject
{
    //Constructor
    public MYZXmlObject()
    {
        
    }
    
    //Statics
    
    //Members
    
    //Methods
    public static MYZXmlObject create( String tag )
    {
        if( "Analysis".equals( tag ) )
            return new XmlAnalysis();
        if( "Operation".equals( tag ) )
            return new XmlOperation();
        if( "Point".equals( tag ) )
            return new XmlPoint();
        return null;
    }
    
    public abstract void   initialize( Attributes attributes );
    public abstract void   append( MYZXmlObject xmlObject );
    public abstract String toXml();
    
    //Class methods   
    public static String setAttribute( String key , String value )
    {
        return key + " = \"" + value + "\" ";
    }

    public static String setAttribute( String key , boolean value )
    {
        return key + " = \"" + ( value ? "true" : "false" ) + "\" ";
    }

    public static String setAttribute( String key , int value )
    {
        return key + " = \"" + Integer.toString( value ) + "\" ";
    }
   
    public static String setAttribute( String key , double value )
    {
        return key + " = \"" + Double.toString( value ) + "\" ";
    }
    
    public static double getAttributeAsDobule( Attributes attributes , String key )
    {
        String strValue = getAttributeAsString( attributes , key );
        double dValue = 0;

        if( strValue == null || strValue.isEmpty() )
        {
            return dValue;
        }
        try
        {
            dValue = Double.valueOf( strValue );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            return dValue;
        }
    }
    
    public static boolean getAttributeAsBoolean( Attributes attributes , String key )
    {
        String strValue = getAttributeAsString( attributes , key );
        boolean bValue = false;

        if( strValue != null && strValue.equals( "true") )
            bValue =  true;

        return bValue;
    }
    
    public static String getAttributeAsString( Attributes attributes, String key )
    {
        return attributes.getValue( key );
    }

    public static int getAttributeAsInt( Attributes attributes, String key )
    {
        String strValue = getAttributeAsString( attributes , key );
        int  iValue= 0;

        if( strValue == null || strValue.isEmpty() )
        {
            return iValue;
        }
        try
        {
            iValue = Integer.valueOf( strValue );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            return iValue;
        }
    }
}
