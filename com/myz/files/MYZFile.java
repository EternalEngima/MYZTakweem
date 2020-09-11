/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.files;

import com.myz.image.ImagePanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.swing.ImageIcon;
import takweem.RunTimeObject;
import takweem.Takweem;

/**
 *
 * @author user
 */
public class MYZFile implements Serializable
{
    //Class members
    public static final String FILE_EXTENSION  = "MYZ"; 
    
    public MYZFile()
    {

    }
    public MYZFile(String hmde)
    {
        
    }
    //Data members
    RunTimeObject m_runTimeObject ; 
    ImageIcon     m_imageIcon ;
    
    //Methods
    public void write(File file)
    {
        m_runTimeObject = Takweem.m_runTimeObject ;
        m_imageIcon         = new ImageIcon(getBufferedImage());
        try
        {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream( file ));
            os.writeObject(this);
            os.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    public void read(File file)
    {
        ObjectInputStream is       = null;
        MYZFile           temp     = null;
        Image             awtImage = null;
        WritableImage     wImage   = null;
        try
        {
            is       = new ObjectInputStream( new FileInputStream(file)); 
            temp     = (MYZFile) is.readObject();
            awtImage = temp.getImageIcon().getImage();
            wImage   =  SwingFXUtils.toFXImage(convertImageToBuffered(awtImage), null);
            
            Takweem.m_runTimeObject              = temp.m_runTimeObject ;
            Takweem.m_runTimeObject.m_imagePanel = new ImagePanel();
            Takweem.m_runTimeObject.m_imagePanel.insertImage(wImage);
            is.close();
        }
        catch(IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        
    }
    private BufferedImage getBufferedImage()
    {
        return SwingFXUtils.fromFXImage(m_runTimeObject.m_imagePanel.getImageView().getImage(), null) ;
    }
    public ImageIcon getImageIcon()
    {
        return m_imageIcon;
    }
    private BufferedImage convertImageToBuffered(Image awtImage)
    {
        BufferedImage bi = new BufferedImage(awtImage.getWidth(null),awtImage.getHeight(null),BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(awtImage, 0, 0, null);
        g2.dispose();
        
        return bi ;
    }

}
