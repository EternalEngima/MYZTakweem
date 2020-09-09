package com.myz.image;


import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import myzComponent.myzButton;
import myzComponent.myzIntegerField;
import myzComponent.myzLabel;
import myzMessage.myzMessage;
import static takweem.Takweem.ARABIC_BUNDLE;
import static takweem.Takweem.m_bundle;
import static takweem.Takweem.m_runTimeObject;

/**
 *
 * @author Mont@z@r Hamoud 18.5.2020
 */
public class ImageEditorStage 
{

    public ImageEditorStage(File file ) 
    {  
        try
        {
            m_imageView.setImage(new Image ( new FileInputStream (file)  ));
            m_imageView.setFitWidth(IMAGE_VIEW_WIDTH);
            m_imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        initHeader();
        reverseHeader();
        m_imageViewParent.getChildren().add(m_imageView);
        
        m_imagePanel =  m_runTimeObject.getImagePanel() ;
        
        m_container.setTop(m_headerPane);
        m_container.setCenter(m_imageViewParent);
        
        m_stage.setTitle("Image Editor");
        m_stage.getIcons().add(new Image("icon\\imageEditor.png"));
        m_stage.setScene(new Scene(m_container , 1000 , 1000));
        m_stage.setOnCloseRequest(new EventHandler <WindowEvent>()
        {
            @Override
            public void handle(WindowEvent widowEvent) 
            {    
                closeMe( widowEvent );
            }
        });
        m_stage.show();  

    }
    
    //Class members
    public static double  IMAGE_VIEW_WIDTH   = 500 ;
    public static double  IMAGE_VIEW_HEIGHT  = 500 ;
    public int            IMAGE_ROTATE_VALUE = 1 ; 
    public int            IMAGE_ROTATE_ANGLE = 0 ;
    
    //Data members 
    public Stage           m_stage            = new Stage();
    public ImagePanel      m_imagePanel       = new ImagePanel();
    public BorderPane      m_container        = new BorderPane();
    public HBox            m_headerPane       = new HBox(10);
    public Group           m_imageViewParent  = new Group();
    public ImageView       m_imageView        = new ImageView();
    public myzLabel        m_rotateAngleLabel = new myzLabel();
    public myzLabel        m_rotateValueLabel = new myzLabel();
    public myzIntegerField m_rotateAngleField = new myzIntegerField();
    public myzIntegerField m_rotateValueField = new myzIntegerField()
    {
      @Override
      public void onValueChange()
      {
        IMAGE_ROTATE_VALUE  = m_rotateValueField.getValue() ;
      }
    };
    myzButton    m_rotateLeftButton      = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            rotateImageLeft();
            m_rotateAngleField.setValue(IMAGE_ROTATE_ANGLE);//update field value
            m_imageView.setRotate(IMAGE_ROTATE_ANGLE);
        }
    };
    myzButton    m_rotateRightButton      = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            rotateImageRight();
            m_rotateAngleField.setValue(IMAGE_ROTATE_ANGLE);//update field value
            m_imageView.setRotate(IMAGE_ROTATE_ANGLE);
        }
    };
    myzButton    m_cropButton             = new myzButton()
    {
      @Override
      public void buttonPressed()
      {
          imageCrope();
      }
    };

    // save and exit button 
    public myzButton m_saveAndExit            = new myzButton ()
    {
        @Override
        public void buttonPressed ()
        {
            m_imageView.setImage(rotateImage (SwingFXUtils.fromFXImage(m_imageView.getImage(), null) , IMAGE_ROTATE_ANGLE));
            m_imagePanel.insertImage( getImage() ); 
            m_stage.close();
        }
    };
    private void initHeader()
    {
        //Header children
        m_rotateValueField.setMinSize(50, 25);
        m_rotateValueField.setParentPane(m_headerPane);
        m_rotateValueField.setMaxHeight(25);
        m_rotateValueField.setValue(IMAGE_ROTATE_VALUE);
        
        m_rotateAngleField.setMinSize(50, 25);
        m_rotateAngleField.setParentPane(m_headerPane);
        m_rotateAngleField.setValue(IMAGE_ROTATE_ANGLE);
        m_rotateAngleField.setMaxHeight(25);
        m_rotateAngleField.setDisable(true);

        m_rotateAngleLabel.setCaption("rotate.angle");
        m_rotateAngleLabel.setParentPane(m_headerPane);
        m_rotateAngleLabel.setReSizeOnParentSize(true);
        m_rotateAngleLabel.setMaxHeight(25);
        m_rotateAngleLabel.setFont(new Font(14));
                
        m_rotateValueLabel.setCaption("rotate.value");
        m_rotateValueLabel.setParentPane(m_headerPane);
        m_rotateValueLabel.setReSizeOnParentSize(true);
        m_rotateValueLabel.setMaxHeight(25);
        m_rotateValueLabel.setFont(new Font(14));
        
        m_rotateLeftButton.setCaption("rotate.left");
        m_rotateLeftButton.setGraphic(new ImageView("icon\\rotate_left.png"));
        m_rotateLeftButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_rotateLeftButton.setMaxHeight(25);
        m_rotateLeftButton.setParentPane(m_headerPane);
        m_rotateLeftButton.setReSizeOnParentSize(true);
        
        m_rotateRightButton.setCaption("rotate.right");
        m_rotateRightButton.setGraphic(new ImageView("icon\\rotate_right.png"));
        m_rotateRightButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_rotateRightButton.setMaxHeight(25);
        m_rotateRightButton.setParentPane(m_headerPane);
        m_rotateRightButton.setReSizeOnParentSize(true); 
        
        
        m_cropButton.setCaption("crop");
        m_cropButton.setGraphic(new ImageView("icon\\crop.png"));
        m_cropButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_cropButton.setMaxHeight(25);
        m_cropButton.setParentPane(m_headerPane);
        m_cropButton.setReSizeOnParentSize(true); 
        
        
        m_saveAndExit.setCaption("save.and.exit.image.editor");
        m_saveAndExit.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_saveAndExit.setMaxHeight(25);
        m_saveAndExit.setParentPane(m_headerPane);
        m_saveAndExit.setReSizeOnParentSize(true); 
        
        //Header 
        String cssLayout = "-fx-border-color: #f0f0f0; -fx-border-width: 4;\n" ;
        
        m_headerPane.setAlignment(Pos.CENTER);
        m_headerPane.setStyle(cssLayout);
        m_headerPane.getChildren().addAll( m_saveAndExit ,  m_rotateValueLabel , m_rotateValueField , m_rotateAngleLabel , m_rotateAngleField 
                                           , m_rotateLeftButton , m_rotateRightButton , m_cropButton);
    }
    
    public void rotateImageLeft ()
    {
        //To keep angle between 0 & 360 degree
        IMAGE_ROTATE_ANGLE -= IMAGE_ROTATE_VALUE; 
        if (IMAGE_ROTATE_ANGLE >= 360)
            IMAGE_ROTATE_ANGLE = IMAGE_ROTATE_ANGLE % 360;
        else if (IMAGE_ROTATE_ANGLE < 0)
            IMAGE_ROTATE_ANGLE = 360 + IMAGE_ROTATE_ANGLE ;        
        
    }
    
    public void rotateImageRight()
    {
        //To keep angle between 0 & 360 degree
        IMAGE_ROTATE_ANGLE += IMAGE_ROTATE_VALUE ;
        if (IMAGE_ROTATE_ANGLE >= 360)
            IMAGE_ROTATE_ANGLE = IMAGE_ROTATE_ANGLE % 360;
        else if (IMAGE_ROTATE_ANGLE < 0)
            IMAGE_ROTATE_ANGLE = 360 + IMAGE_ROTATE_ANGLE ; 
        
    }
    
    public void closeMe(  Event windoEvent  )
    {
        boolean answer = myzMessage.confirmMessage(m_bundle.getString("close.confirmation") , m_bundle);
        if (answer)
        {
            m_stage.close();
        }
        else
            windoEvent.consume();
    }
      
    public Image getImage()
    {
        return m_imageView.getImage();
    }
    
    public void imageCrope ()
    {
        Rectangle   rectBound    = new Rectangle(0, 0);
        double      widthRatio   = m_imageView.getImage().getWidth() / IMAGE_VIEW_WIDTH  ;
        double      hightRatio   = m_imageView.getImage().getHeight()/ IMAGE_VIEW_HEIGHT  ;
        ContextMenu contextMenu  = new ContextMenu();
        MenuItem    cropMenuItem = new MenuItem("Crop");
        
        contextMenu.getItems().add( cropMenuItem);
        
        EventHandler<MouseEvent> cropEvent = new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {

                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) 
                {
                    if (rectBound.getParent() == null) 
                    {
                        rectBound.setWidth(0.0); 
                        rectBound.setHeight(0.0);
                        rectBound.setLayoutX(event.getX());
                        rectBound.setLayoutY(event.getY());
                        m_imageViewParent.getChildren().add(rectBound);
                    }
                } 
                else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) 
                {
                    contextMenu.show(m_imageViewParent , event.getScreenX(), event.getScreenY());
                }
                else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) 
                {
                    Bounds boundsInScene = m_imageViewParent.localToScene(m_imageViewParent.getBoundsInLocal());
                    double x = boundsInScene.getWidth();
                    double y = boundsInScene.getHeight();
                    if( event.getX() <= x && event.getY() <= y)
                    {
                        rectBound.setWidth(event.getX() - rectBound.getLayoutX());
                        rectBound.setHeight(event.getY() - rectBound.getLayoutY());
                    }
                } 
                else if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseButton.SECONDARY) 
                {
                    if (rectBound.getParent() != null) 
                    {
                        m_imageViewParent.getChildren().remove(rectBound);
                        m_imageViewParent.removeEventHandler(MouseEvent.ANY , this);
                        contextMenu.hide();//should be remove  
                    }
                } 
            }
        };
        
        cropMenuItem.setOnAction( new EventHandler<ActionEvent> () 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                PixelReader   reader   = m_imageView.getImage().getPixelReader();
                WritableImage newImage = new WritableImage(reader, (int) (rectBound.getLayoutX() * widthRatio),
                (int) (rectBound.getLayoutY() * hightRatio ),
                (int) (rectBound.getWidth()   * widthRatio),
                (int) (rectBound.getHeight()  * hightRatio ) );
                m_imageView.setImage(newImage);
                m_imageViewParent.getChildren().remove(rectBound);
                m_imageViewParent.removeEventHandler(MouseEvent.ANY , cropEvent);

            }
        });
        

        rectBound.setStrokeWidth(2);
        rectBound.setStrokeLineCap(StrokeLineCap.ROUND);
        rectBound.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));
        rectBound.setStroke(Color.BLUE);
        m_imageViewParent.addEventHandler(MouseEvent.ANY , cropEvent);
    }

    public  Image rotateImage( BufferedImage image , int angle )
    {
        try
        {
            final double rads = Math.toRadians(angle);
            final double sin = Math.abs(Math.sin(rads));
            final double cos = Math.abs(Math.cos(rads));
            final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
            final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
            final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
            final AffineTransform at = new AffineTransform();
            at.translate(w / 2, h / 2);
            at.rotate(rads,0, 0);
            at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
            final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            rotateOp.filter(image,rotatedImage);
            return SwingFXUtils.toFXImage(rotatedImage , null );
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null ;
    }
    
    private void reverseHeader()
    {
        if ( m_bundle == ARABIC_BUNDLE )
        {
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(m_headerPane.getChildren());
            Collections.reverse(workingCollection);
            m_headerPane.getChildren().setAll(workingCollection);
            m_headerPane.setAlignment(Pos.CENTER_RIGHT);
        }

    }
}
