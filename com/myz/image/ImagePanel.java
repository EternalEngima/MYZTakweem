package com.myz.image;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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

public class ImagePanel 
{
    
    //Constructor
    public ImagePanel ()
    {
        initCenter();
    }
    // Class Members 
    private static final double IMAGE_VIEW_WIDTH  = 500 ;
    private static final double IMAGE_VIEW_HEIGHT = 600 ;
    
    //Data Members
    Image        m_selectedImage         = null;
    StackPane    m_centerPane            = new StackPane();
    myzLabel     m_centerLabel           = new myzLabel();
    ImageView    m_imageView             = new ImageView();
    ImageView    m_blankImageView        = new ImageView();
    //TODO temp
    Vector vLinePointes       = new Vector ();
    
    // Class Methods 
    public void insertImage( File file ) 
    {   
        String blankImageUrl      = "src\\blank.png" ;
        try 
        {
            setSelectedImage(new Image ( new FileInputStream (file) ));
            Image  blankImage  = new Image(new FileInputStream( new File (blankImageUrl)));            
            m_imageView        = new ImageView( getSelectedImage() );
            m_blankImageView   = new ImageView( blankImage );
            getImageView().setFitWidth(IMAGE_VIEW_WIDTH);
            getImageView().setFitHeight(IMAGE_VIEW_HEIGHT);
            
            EventHandler<MouseEvent> eventHandler = (MouseEvent e ) -> 
            {
                paint( e , m_blankImageView ) ;
            };
            m_blankImageView.addEventHandler(MouseEvent.MOUSE_CLICKED , eventHandler );
            //it's to make the transparent image writable 
            m_blankImageView.setPickOnBounds(true);
            //TODO
            m_centerPane.getChildren().add( 1 , getImageView() );
            m_centerPane.getChildren().add( 2 , getBlankImageView() );
        }
        catch (Exception ex)
        {
            System.out.println("getImagePanel ( String imageUrl ) : " + ex.getMessage() );
        }
    }    
    
    public void paint ( MouseEvent event , ImageView blankimageView)
    {
        int x = new Double ( ( event.getX()  )  ).intValue();
        int y = new Double ( ( event.getY()  )  ).intValue();
        
        Image         tmp         = blankimageView.getImage();
        double width              = tmp.getWidth()  ;
        double height             = tmp.getHeight() ;
        PixelReader   pixelReader = tmp.getPixelReader() ;
        WritableImage wImage      = new WritableImage( (int) width , (int) height);
        Point point               = new Point ( x , y ) ;
        //Temp
        vLinePointes.addElement(point);
        Line line  ;
        if ( vLinePointes.size() == 2)
        {
        line = new Line ( (Point) vLinePointes.get(0) , (Point) vLinePointes.get(1) );
        vLinePointes.removeAllElements();

        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();           

        for( int X = 0 ; X < width ; X++ )
        {
            for( int Y = 0 ; Y < height ; Y++ )
            {
                //Retrieving the color of the pixel of the loaded image
                Color color = pixelReader.getColor( X , Y );
                if ( line.isInLine( X , Y ) )
                  writer.setColor( X , Y , Color.AQUA);                
                else
                  writer.setColor( X , Y , color);
            } 
        }
        blankimageView.setImage(wImage);
        }
    }
    
    
    private void initCenter()
    {
        m_centerLabel.setCaption("label.drag.image");
        m_centerLabel.setParentPane(m_centerPane);
        m_centerPane.getChildren().addAll( getCenterLabel()  );
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

         /////////////////////////////////////////////////////////////////
         //
         //                         ZOOM TESTING 
         //
         ////////////////////////////////////////////////////////////////
        // Create operator
        AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator();

        // Listen to scroll events (similarly you could listen to a button click, slider, ...)
        m_centerPane.setOnScroll(new EventHandler<ScrollEvent>()
        {
            @Override
            public void handle(ScrollEvent event) 
            {
                double zoomFactor = 1.5;
                if (event.getDeltaY() <= 0) 
                {
                    // zoom out
                    zoomFactor = 1 / zoomFactor;
                }
                zoomOperator.zoom( m_centerPane , zoomFactor , event.getSceneX(), event.getSceneY());
            }
        });
    }
    
    private void mouseDragDropped(final DragEvent e)
    {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) 
        {
            success = true;
            insertImage( db.getFiles().get(0));
           
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
    
 class AnimatedZoomOperator 
 {

    private Timeline timeline;

    public AnimatedZoomOperator() 
    {         
         this.timeline = new Timeline(60);
    }

    public void zoom(Node node, double factor, double x, double y) {    
        // determine scale
        double oldScale = node.getScaleX();
        double scale    = oldScale * factor;
        double f        = (scale / oldScale) - 1;

        // determine offset that we will have to move the node
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
//        double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
//        double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));

        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), node.getTranslateX() - f  )),
            new KeyFrame(Duration.millis(200), new KeyValue(node.translateYProperty(), node.getTranslateY() - f )),
            new KeyFrame(Duration.millis(200), new KeyValue(node.scaleXProperty(), scale)),
            new KeyFrame(Duration.millis(200), new KeyValue(node.scaleYProperty(), scale))
        );
        timeline.play();
    }
}
}
