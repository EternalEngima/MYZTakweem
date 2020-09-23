package com.myz.image;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import myzComponent.myzComponent;
import myzComponent.myzLabel;

/**
 * This class represent helper image for user to detect the point position 
 * @author Montazar hamoud
 */
public class ImagePanelHelper extends StackPane implements myzComponent
{
    //Constructore
    public ImagePanelHelper ()
    {
        String blankImageUrl      = "src\\blank250x150.png" ;
        try
        {
            Image  blankImage  = new Image(new FileInputStream( new File (blankImageUrl)));
            m_blankImageView.setImage(blankImage);    
            m_centerLabel.setCaption("Image.helper");
            getChildren().addAll(m_centerLabel ,m_imageView , m_blankImageView);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    //Class members
    private static final double IMAGE_VIEW_WIDTH    = 250 ;
    private static final double IMAGE_VIEW_HEIGHT   = 150 ;
    
    //Data members
    ImageView        m_imageView       = new  ImageView();
    ImageView        m_blankImageView  = new  ImageView() ;
    Point            m_previousePoint  = new Point(0,0);
    myzLabel         m_centerLabel     = new myzLabel();

    //Getter methods
    public ImageView getBlankImageView()
    {
        return m_blankImageView;
    }
    public ImageView getImageView()
    {
        return m_imageView ;
    }
    
    //Methods
    public void setImageView(String imageHelperPath)
    {
        try
        {
            m_imageView.setImage(new Image(new FileInputStream( new File (imageHelperPath))) );
            m_imageView.setFitWidth(IMAGE_VIEW_WIDTH);
            m_imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void setPreviousePoint(int x , int y)
    {
        m_previousePoint.setX(x);
        m_previousePoint.setY(y);
    }
    public void removePreviousePoint()
    {
        m_previousePoint.erase(m_blankImageView);
    }

    @Override
    public void refreshCaption() 
    {
        try
        {
            Field []     buttons   = ImagePanelHelper.class.getDeclaredFields();
            myzComponent component = null ;
            for( Field field : buttons)
            {
                Class className = field.getType() ;
                if ( Class.forName("myzComponent.myzComponent").isAssignableFrom(className) )
                {
                    component = (myzComponent)field.get(this);
                    component.refreshCaption();
                }
            }
        }
        catch(SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
    }
}
