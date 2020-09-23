package com.myz.image;

import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @author Montazar
 */

public class Point implements Serializable
{
    //Constructor
    public Point ( double x , double y )
    {
        m_x = x ;
        m_y = y ;
    }
    public Point ()
    {
        
    }
    
    //Class member
    public static Color ANALYSIS_POINT_COLOR = Color.BLACK ;
    public static Color RULER_POINT_COLOR    = Color.SPRINGGREEN ;
    
    //Members
    double m_x , m_y ;
    
    @Override
    public String toString()
    {
        return "x = " + m_x + " y = " + m_y;
    }
    
    // Setter Methods
    void setX ( double x )
    {
        m_x = x ;
    }
    void setY ( double y )
    {
        m_y = y ;
    }
    
    // Getter Methods
    public double getX ()
    {
        return m_x ;
    }    
    public double getY ()
    {
        return m_y ;
    }
    
    public void draw (ImageView image , Color poinrColor)
    {
        if(image == null)
            return;
        Image  tmp                = image.getImage();
        double width              = tmp.getWidth()  ;
        double height             = tmp.getHeight() ;
        PixelReader   pixelReader = tmp.getPixelReader() ;
        WritableImage wImage      = new WritableImage( (int) width , (int) height);
        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();           
        Color       color;
        for( int x = 0 ; x < width ; x++ )
        {
            for( int y = 0 ; y < height ; y++ )
            {
                //Retrieving the color of the pixel of the loaded image
                color = pixelReader.getColor( x , y );
                if ( isDraw( x , y ) )
                  writer.setColor( x , y , poinrColor);                
                else
                  writer.setColor( x , y , color);
            } 
        }
        image.setImage(wImage);
    }
    public void erase(ImageView image)
    {
        Image  tmp                = image.getImage();
        double width              = tmp.getWidth()  ;
        double height             = tmp.getHeight() ;
        PixelReader   pixelReader = tmp.getPixelReader() ;
        WritableImage wImage      = new WritableImage( (int) width , (int) height);
        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();           

        for( int x = 0 ; x < width ; x++ )
        {
            for( int y = 0 ; y < height ; y++ )
            {
                //Retrieving the color of the pixel of the loaded image
                Color color = pixelReader.getColor( x , y );
                if ( isDraw( x , y ) )
                  writer.setColor( x , y , Color.TRANSPARENT);                
                else
                  writer.setColor( x , y , color);
            } 
        }
        image.setImage(wImage);
    }
    //Draw the all point beside this point
    public boolean isDraw(int x , int y)
    {
        if(x == getX() && y == getY())
            return true;
        else if(x == getX() && (y+1) == getY())
            return true;
        else if(x == getX() && (y-1) == getY())
            return true;
        else if((x+1) == getX() && y == getY())
            return true ;
        else if((x-1) == getX() && y == getY())
            return true;
        else if((x+1) == getX() && (y+1) == getY())
            return true;
        else if((x-1) == getX() && (y-1) == getY())
            return true;
        else if((x-1) == getX() && (y+1) == getY())
            return true;
        else if((x+1) == getX() && (y-1) == getY())
            return true;
        else
            return false;
    }
    public boolean equals(Point point)
    {
        if(point.m_x == m_x && point.m_y == m_y)
            return true;
        return false ;
    }
}
