package com.myz.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @author Montazar
 */

public class Point 
{
    //Constructor
    public Point ( double x , double y )
    {
        m_x = x ;
        m_y = y ;
    }
    
    //Members
    double m_x , m_y ;
    

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
    
    public void draw (ImageView image)
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
                if ( x == getX() && y == getY() )
                  writer.setColor( x , y , Color.AQUA);                
                else
                  writer.setColor( x , y , color);
            } 
        }
        image.setImage(wImage);
    }
}
