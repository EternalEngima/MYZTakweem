package com.myz.image;

/**
 *
 * @author Montazar Hamoud 10.5.2020
 */
import javax.swing.*; 
import java.awt.*; 
public class Magnifier extends JPanel
{ 

    // default constrcutor 
    public Magnifier() 
    { 
        setMinimumSize(new Dimension(200, 200));
        show(); 
    } 
  
    //Data members
    Image m_image;
    
    public void work(int zoomValue) 
    { 
        try 
        { 
            // create a robot 
            Robot r = new Robot(); 
            // get the position of mouse 
            java.awt.Point p = MouseInfo.getPointerInfo().getLocation(); 

            // create a screen capture around the mouse pointer 
            m_image = r.createScreenCapture(new Rectangle((int)p.getX() - zoomValue, 
                                                    (int)p.getY() - zoomValue , zoomValue * 2 , zoomValue * 2)); 

            // repaint the conatiner 
            repaint(); 
  


        } 
        catch (Exception e) { 
            System.err.println(e.getMessage()); 
        } 
    } 
    
    public void stopWork()
    {
        m_image = new ImageIcon("src\\icon\\magnifierStopScreen.png").getImage();
        repaint();
    }
    // paint function 
    @Override
    public void paint(Graphics g) 
    { 
  
        // draw the image 
        g.drawImage(m_image, 0, 0, 200, 200, this); 
    } 

} 