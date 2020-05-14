/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;

import com.myz.image.Magnifier;
import java.lang.reflect.Field;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Montazar Hamoud 10.5.2020
 */
public class myzMagnifier extends VBox implements Runnable , myzComponent
{
    
    public myzMagnifier()
    {
        super();
    }
    //Class Members
    public static  Thread THREAD           = null;
    public static  int    ZOOM_RATIO       = 5   ;
    public static  int    ENABLE           = 1   ;
    public static  int    STOP             = 2   ;
    public static  int    DISABLE          = 3   ;
    
    //Data Members
    Magnifier m_magnifier    = new Magnifier();
    int       m_status       = 0;
    int       m_zoomValue    = 25 ;
    SwingNode m_swingNode    = new SwingNode();
    HBox      m_zoomBox      = new HBox(10);
    myzButton m_zoomIn       = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            int newValue = getZoomValue() - ZOOM_RATIO ;
            if (newValue > 0)
                setZoomValue(newValue);
        }
    };
    myzButton m_zoomOut   = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            int newValue = getZoomValue() + ZOOM_RATIO ;
            if (newValue < 500)
                setZoomValue(newValue);
        }
    };
    myzButton m_controller    = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            if(getStatus() == ENABLE)
            {
                m_controller.setCaption("enable.magnifier");
                setStatus(DISABLE);
            }
            else
            {
                m_controller.setCaption("disable.magnifier");
                setStatus(ENABLE);
            }
        }
    };
    
    @Override
    public void run ()
    {
        while(getStatus() != STOP)
        {
            if (getStatus() == ENABLE)
                m_magnifier.work(getZoomValue());
//                m_imageView.setImage(SwingFXUtils.toFXImage(magnifier.getRImage(getZoomValue()) , null) );
            else
                m_magnifier.removeAll();
//                m_imageView.setImage(null);
        }
    }
    
    public void startRunning()
    {
        String cssLayout = "-fx-border-color: #f0f0f0;\n" +
                           "-fx-border-width: 4;\n" ;
        
        m_zoomIn.setGraphic(new ImageView("icon\\zoom_in.png"));
        m_zoomIn.setStyle("-fx-border-color: #ccc; -fx-border-width: 2px;-fx-background-color:#ffffff;");
        m_zoomIn.setMaxSize(10 , 18);
        m_zoomIn.setParentPane(this);
        m_zoomIn.setReSizeOnParentSize(true);
        
        m_zoomOut.setGraphic(new ImageView("icon\\zoom_out.png"));
        m_zoomOut.setStyle("-fx-border-color: #ccc; -fx-border-width: 2px;-fx-background-color:#ffffff;");
        m_zoomOut.setMaxSize(10 , 18);
        m_zoomOut.setParentPane(this);
        m_zoomOut.setReSizeOnParentSize(true);
        
        m_controller.setCaption("disable.magnifier");
        m_controller.setStyle("-fx-border-color: #ccc; -fx-border-width: 2px;-fx-background-color:#ffffff;");
        m_controller.setMaxSize(100 , 18);
        m_controller.setParentPane(this);
        m_controller.setReSizeOnParentSize(true);
        
        m_zoomBox.setAlignment(Pos.BOTTOM_CENTER);
        m_zoomBox.getChildren().addAll(m_zoomIn , m_controller , m_zoomOut);

        //Add JPanel node to swingNode then add swingNode to stackPane 
        m_swingNode.setContent(m_magnifier);
        
        ImageView  plusSign = new ImageView("icon\\plus_sign.png") ;
        StackPane  stack    = new StackPane(m_swingNode , plusSign);
        plusSign.resize(25 , 25);
        plusSign.setFitHeight(50);
        plusSign.setFitWidth(50);
        stack.setAlignment(Pos.CENTER);
        stack.setMaxSize(150, 150);
        
        setStyle(cssLayout);
        setMaxSize(50, 50);
        getChildren().addAll( stack , m_zoomBox );
        setStatus(ENABLE);
        
        THREAD = new Thread(this);
        THREAD.setDaemon(true);
        THREAD.start();

    }
    public void stopRunning()
    {
        setStatus(STOP);
    }
    @Override
    public void refreshCaption()
    {  
        try
        {
            Field []     buttons   = myzMagnifier.class.getDeclaredFields();
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
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //Setter Methods 
    void setZoomValue(int zoomValue)
    {
        m_zoomValue = zoomValue ;
    }
    void setStatus(int status)
    {
        m_status = status ;
    }
    //Getter Methods
    public int getZoomValue()
    {
        return m_zoomValue ;
    }
    public int getStatus()
    {
        return m_status ;
    }
    
}
