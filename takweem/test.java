/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package takweem;

import com.myz.image.ImagePanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class test implements Serializable
{
    public ImageIcon icon ;
    ImagePanel       image = new ImagePanel() ;
    String string  = "hmde";
    public test()
    {
        try
        {
            Image         image = new Image(new FileInputStream(new File("D:\\rotatedImage.jpg"))) ;
            BufferedImage bf    = SwingFXUtils.fromFXImage(image, null) ;
            icon                = new ImageIcon(bf);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void main(String [] args)
    {
        test test = new test() ;
        try
        {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File ("D:\\test.myz" ) ));
            os.writeObject(test);
//            ObjectInputStream is  = new ObjectInputStream( new FileInputStream(new File("D:\\test.myz"))); 
//            test =(test) is.readObject();
//            java.awt.Image img = test.icon.getImage();
//            System.out.println(test.string);
//            BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
//
//            Graphics2D g2 = bi.createGraphics();
//            g2.drawImage(img, 0, 0, null);
//            g2.dispose();
//            ImageIO.write( bi, "jpg", new FileOutputStream(new File("D:\\objectImage.jpg")));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
