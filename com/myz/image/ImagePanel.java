package com.myz.image;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author Montazar
 */

public class ImagePanel 
{
    
    // Class Members 
    private static final double IMAGE_VIEW_WIDTH  = 500 ;
    private static final double IMAGE_VIEW_HEIGHT = 600 ;
    
    // Class Methods 
    public static StackPane getImagePanel ( String imageUrl ) 
    {   
        String blankImageUrl      = "src\\blank.png" ;
        try 
        {
            Image  image              = new Image(new FileInputStream( new File (imageUrl)));
            Image  blankImage         = new Image(new FileInputStream( new File (blankImageUrl)));            
            double width              = blankImage.getWidth()  ;
            double height             = blankImage.getHeight() ;
            javafx.scene.image.ImageView imageView       = new javafx.scene.image.ImageView( image );
            javafx.scene.image.ImageView blankimageView  = new javafx.scene.image.ImageView( blankImage );
            StackPane root            = new StackPane();
            Vector vLinePointes       = new Vector ();
            imageView.setFitWidth(IMAGE_VIEW_WIDTH);
            imageView.setFitHeight(IMAGE_VIEW_HEIGHT);

            EventHandler<MouseEvent> eventHandler = (MouseEvent e ) -> 
            {
              int x = new Double ( ( e.getX()  )  ).intValue();
              int y = new Double ( ( e.getY()  )  ).intValue();
//              System.out.println("(" + x + " , " + y + ")");
              Image         tmp         = blankimageView.getImage();
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
            };
            blankimageView.addEventHandler(MouseEvent.MOUSE_CLICKED , eventHandler );
            //it's to make the transparent image writable 
            blankimageView.setPickOnBounds(true);
            root.getChildren().addAll( imageView , blankimageView  );
            return root ;
        }
        catch (Exception ex)
        {
            System.out.println("getImagePanel ( String imageUrl ) : " + ex.getMessage() );
        }
        return null ;
    }    
}
