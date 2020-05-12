package com.myz.image;

/**
 *
 * @author Montazar Hamoud 10.5.2020
 */

import java.awt.*; 
import java.awt.image.BufferedImage;

public class Magnifier 
{ 

  
    //Default constrcutor 
    Magnifier() 
    { 
        
    } 
  

    public static BufferedImage getRImage( int zoomValue )
    {
        try
        {
            GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
            Robot robot = new Robot(screens[0]); 
            // get the position of mouse 
            java.awt.Point p = MouseInfo.getPointerInfo().getLocation(); 
  
            // create a screen capture around the mouse pointer 
            return toBufferedImage(robot.createScreenCapture(new Rectangle((int)p.getX(), 
                                                        (int)p.getY() , zoomValue , zoomValue))) ;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        return null ;
    }
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

} 
