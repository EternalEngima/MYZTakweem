package com.myz.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Vector;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import myzComponent.myzComponent;
import myzComponent.myzLabel;

/**
 * Class Description :
 * this class represent the image area at the main frame 
 * initCenter () ---> this function initialize the main member of the image panel 
 * 1- stack panel : handle the (1) image view , (2) blank image view , (3) label .
 * then add it's drag and drop 
 * inserImage ( file ) -----> this function get the file then initialize the image view and add the event listener 
 * 1- on click event 
 * 2- on scroll event
 * 
 * @author Montazar
 */

public class ImagePanel extends StackPane implements myzComponent
{
    
    //Constructor
    public ImagePanel ()
    {
        super();
        initCenter();
    }
    // Class Members 
    private static final double IMAGE_VIEW_WIDTH   = 600 ;
    private static final double IMAGE_VIEW_HEIGHT  = 500 ;

    
    //Data Members
    Image           m_selectedImage    = null;
    StackPane       m_centerPane       = new StackPane();
    myzLabel        m_centerLabel      = new myzLabel();
    int             m_rotateAngle      = 0;
    ImageView       m_imageView        ;
    ImageView       m_blankImageView   ;

    
    //TODO temp
    Vector vLinePointes       = new Vector ();
    
    //Class Methods 
    public void insertImage( Image image ) 
    {   
        String blankImageUrl      = "src\\blank600x500.png" ;
        try 
        {
            m_imageView      =  new ImageView() ;
            m_blankImageView =  new ImageView() ;
            
            m_centerPane.getChildren().addAll( getImageView() , getBlankImageView());

            setSelectedImage( image );
            Image  blankImage  = new Image(new FileInputStream( new File (blankImageUrl)));
            
            getImageView().setFitWidth(IMAGE_VIEW_WIDTH);
            getImageView().setFitHeight(IMAGE_VIEW_HEIGHT);
            
                        
            m_imageView.setImage(getSelectedImage());
            m_blankImageView.setImage(blankImage);
            
            EventHandler<MouseEvent> eventHandler = (MouseEvent e ) -> 
            {
                paint( e , m_blankImageView ) ;
            };
            m_blankImageView.addEventHandler(MouseEvent.MOUSE_CLICKED , eventHandler );
            //it's to make the transparent image writable 
            m_blankImageView.setPickOnBounds(true);
        }
        catch (Exception ex)
        {
            System.out.println("getImagePanel ( String imageUrl ) : " + ex.getMessage() );
            ex.printStackTrace();
        }
    }    

    //TODO 
    public void paint ( MouseEvent event , ImageView blankimageView)
    {
        int   x      = new Double ( ( event.getX()  )  ).intValue();
        int   y      = new Double ( ( event.getY()  )  ).intValue();
        Point point  = new Point ( x , y ) ;
        point.draw(blankimageView);
        vLinePointes.addElement(point);
        Line line  = null ;
        if ( vLinePointes.size() == 2)
        {
            line = new Line ( (Point) vLinePointes.get(0) , (Point) vLinePointes.get(1) );
            line.draw(blankimageView);
            vLinePointes.removeAllElements();
        }
    }
    
    
    private void initCenter()
    {
        m_centerLabel.setCaption("label.drag.image");
        m_centerLabel.setParentPane(m_centerPane);
        m_centerPane.setMaxSize(IMAGE_VIEW_WIDTH  , IMAGE_VIEW_HEIGHT);

        m_centerPane.getChildren().addAll(getCenterLabel());

        m_centerPane.setOnDragOver(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(DragEvent event) 
            {
                m_centerPane.setStyle("-fx-background-color:#fffff2;");
                mouseDragOver(event);
            }
            
        });
        
        m_centerPane.setOnDragDropped(new EventHandler<DragEvent>() 
        {
            @Override
            public void handle(final DragEvent event)
            {
                mouseDragDropped(event);
            }
        });

         m_centerPane.setOnDragExited(new EventHandler<DragEvent>() 
         {
            @Override
            public void handle(final DragEvent event)
            {
                m_centerPane.setStyle("-fx-border-color: #C6C6C6;");
            }
        });
         getChildren().addAll(getCenterPane());
    }
    

    private void mouseDragDropped(final DragEvent e)
    {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) 
        {
            success = true;
            ImageEditorStage imageEditorStage = new ImageEditorStage(db.getFiles().get(0) , this);
        }
        e.setDropCompleted(success);
        e.consume();
    }
    
    private  void mouseDragOver(final DragEvent e) 
    {
        final Dragboard db = e.getDragboard();
 
        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg")
                || db.getFiles().get(0).getName().endsWith(".Gif");

 
        if (db.hasFiles())
        {
            if (isAccepted)
            {
                e.acceptTransferModes(TransferMode.COPY);
            }
        }
        else 
        {
            e.consume();
        }
    }
    @Override
    public void refreshCaption()
    {  
        try
        {
            Field []     buttons   = ImagePanel.class.getDeclaredFields();
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
    //TODO open file choser and notify the image saved  
    public void saveImage ()
    {
        
        new Thread()
        {
              @Override
              public void run()
              {
                BufferedImage         bImage       = null ;
                ByteArrayOutputStream outputStream = null ;
                InputStream           inputStream  = null ;

                try
                {
                    if(m_selectedImage == null)
                        return ;
                    bImage       = SwingFXUtils.fromFXImage(m_blankImageView.getImage(), null);
                    outputStream = new ByteArrayOutputStream();
                    ImageIO.write(bImage , "png" , outputStream);//TODO png
                    byte[] res  = outputStream.toByteArray();
                    inputStream = new ByteArrayInputStream(res);
                    Image tmp   = new Image ( inputStream , m_selectedImage.getWidth(), m_selectedImage.getHeight() , false , false );

                    PixelReader   pixelReader1 = tmp.getPixelReader() ;
                    PixelReader   pixelReader2 = m_selectedImage.getPixelReader() ;

                    double width               = tmp.getWidth()  ;
                    double height              = tmp.getHeight() ;
                    WritableImage wImage       = new WritableImage((int) width , (int) height );
                    PixelWriter writer         = wImage.getPixelWriter();   

                    for (int x = 0 ; x < width ; x++)
                    {
                        for(int y = 0 ; y < height ; y++)
                        {
                            Color color1  = pixelReader1.getColor(x, y);
                            Color color2  = pixelReader2.getColor(x, y);
                            if(!color1.equals(Color.TRANSPARENT))
                                writer.setColor(x, y, color1);
                            else
                                writer.setColor(x, y, color2);                            
                        }
                    }

                    ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", new File("D:\\temp.png"));//TODO
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            
                
        }.start();

    }
    //Setter methods
    public void setSelectedImage ( Image selectedImage )
    {
        m_selectedImage = selectedImage ;
    }
    void setCenterPane ( StackPane centerPane )
    {
        m_centerPane = centerPane ;
    }
    void setCenterLabel ( myzLabel centerLabel )
    {
        m_centerLabel = centerLabel ;
    }
    void setImageView ( ImageView imageView)
    {
        m_imageView = imageView ;
    }
    void setBlankImageView ( ImageView blankImageView )
    {
        m_blankImageView = blankImageView ;
    }
    void setRotateAngle(int rotateAngle)
    {
        m_rotateAngle = rotateAngle ;
    }
    //Getter Methods
    public Image getSelectedImage ( )
    {
        return m_selectedImage ;
    }
    public StackPane getCenterPane ()
    {
        return m_centerPane ;
    }
    public myzLabel getCenterLabel ()
    {
        return m_centerLabel ;
    }
    public ImageView getImageView ()
    {
        return m_imageView ;
    }
    public ImageView getBlankImageView ()
    {
        return m_blankImageView ;
    }
    public int getRotateAngle()
    {
        return m_rotateAngle ;
    }
}
