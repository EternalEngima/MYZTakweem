package com.myz.image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Montazar
 */
public class Line 
{
    private Point  m_startPoint ;
    private Point  m_endPoint ;
    private double m_lineSlope ;
    private double m_yIntercept ;
    private int    m_minX ;
    private int    m_maxX ;
    private int    m_minY ;
    private int    m_maxY ;
    
    //Class Members
    public static final int CM = 0 ;
    //Constructor
    public Line ( Point startPoint , Point endPoint )
    {
        setStartPoint( endPoint ) ;
        setEndPoint( startPoint );
        setSlope( calculateSlope() );
        setYIntercept( calculateYIntercept() );
        initLineRanges();
    }
    

    //Methods
    
    //Equation m = y2 - y1 / x2 - x1
    private double calculateSlope ()
    {
        double tmp1 = ( getEndPoint().getY() - getStartPoint().getY() ) ;
        double tmp2 = ( getEndPoint().getX() - getStartPoint().getX() ) ;
        return  tmp1  / tmp2  ;
    }
    
    //Equation y = mx + b ---> here we calculate b
    private double calculateYIntercept ()
    {
        double x        = getStartPoint().getX() ;
        double y        = getStartPoint().getY() ;
        double slope    = getLineSlope() ;
        return y - ( slope * x ) ;
    }
    
    //Equation sqrt ( (x2*x2 - x1*x1) + (y2*y2 - y1*y1) )
    public double calculateDistance ()
    {
        double x1   = getStartPoint().getX();
        double x2   = getEndPoint().getX() ;
        double y1   = getStartPoint().getY();
        double y2   = getEndPoint().getY() ;
        
        double tmp1 = (Math.pow(x2 , 2) - Math.pow(x1 , 2) ) ;
        double tmp2 = (Math.pow(y2 , 2) - Math.pow(y1 , 2) );
        
        return Math.sqrt( tmp1 + tmp2 );
        
    }
    //calculate y depend on x and line Equation
    public  int getY (int x)
    {
        return (int) ( (getLineSlope() * x) + getYIntercept() ) ;
    }
    //calculate x depend on y and line Equation
    public int getX (int y)
    {
        return (int) ( ( y - getYIntercept() ) / getLineSlope() ) ;
    }
    // set the range of starting and ending this line when draw 
    private void initLineRanges ()
    {
        setMaxX( getMax( (int) getStartPoint().getX() , (int)  getEndPoint().getX() ) );
        setMinX( getMin( (int) getStartPoint().getX() , (int) getEndPoint().getX() ) );
        setMaxY( getMax( (int) getStartPoint().getY() , (int) getEndPoint().getY() ) );
        setMinY( getMin( (int) getStartPoint().getY() , (int)  getEndPoint().getY() ) );
    }
    private int getMin ( int number1 , int number2)
    {
        if (number1 > number2)
            return number2 ;
        else
            return number1 ;
    }
    private int getMax ( int number1 , int number2)
    {
        if (number1 > number2)
            return number1 ;
        else
            return number2 ;
    }
    // Check to draw 
    public boolean isInLine ( int x , int y )
    {
        if ( getMinX() <= x && x <= getMaxX() && getMinY() <= y && y <= getMaxY())
        {
            // in these case the slope is undefined because can not divide on zero ( x2 - x1 = 0 ) 
            if ( getLineSlope() == Double.NEGATIVE_INFINITY || getLineSlope() == Double.POSITIVE_INFINITY )
                return true ;
            
            return ((getY(x) == y || getX(y) == x) );
        }

        return false;        
    }

    public void draw (ImageView image)
    {
        Image  tmp                = image.getImage();
        double width              = tmp.getWidth()  ;
        double height             = tmp.getHeight() ;
        PixelReader   pixelReader = tmp.getPixelReader() ;
        WritableImage wImage      = new WritableImage( (int) width , (int) height);
        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();           
        Color color;
        for( int x = 0 ; x < width ; x++ )
        {
            for( int y = 0 ; y < height ; y++ )
            {
                //Retrieving the color of the pixel of the loaded image
                color = pixelReader.getColor( x , y );
                if ( isInLine( x , y ) )
                  writer.setColor( x , y , Color.AQUA);                
                else
                  writer.setColor( x , y , color);
            } 
        }
        image.setImage(wImage);
    }
    public void unDraw(ImageView image)
    {
        Image  tmp                = image.getImage();
        double width              = tmp.getWidth()  ;
        double height             = tmp.getHeight() ;
        PixelReader   pixelReader = tmp.getPixelReader() ;
        WritableImage wImage      = new WritableImage( (int) width , (int) height);
        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();           
        Color color;
        for( int x = 0 ; x < width ; x++ )
        {
            for( int y = 0 ; y < height ; y++ )
            {
                //Retrieving the color of the pixel of the loaded image
                color = pixelReader.getColor( x , y );
                if ( isInLine( x , y ) )
                  writer.setColor( x , y , Color.TRANSPARENT);                
                else
                  writer.setColor( x , y , color);
            } 
        }
        image.setImage(wImage);
    }
    // Setter Methods
    private void setStartPoint ( Point startPoint )
    {
        m_startPoint = startPoint ;
    }
    private void setEndPoint ( Point endPoint )
    {
        m_endPoint = endPoint ;
    }
    private void setSlope ( double slope )
    {
        m_lineSlope = slope ;
    }
    private void setYIntercept ( double yIntercept)
    {
        m_yIntercept = yIntercept ;
    }
    private void setMinX (int minX)
    {
        m_minX = minX ;
    }
    private void setMinY (int minY)
    {
        m_minY = minY ;
    }
    private void setMaxX (int maxX)
    {
        m_maxX = maxX ;
    }
    private void setMaxY (int maxY)
    {
        m_maxY = maxY ;
    }
    // Getter Methods 
    public Point getStartPoint ()
    {
        return m_startPoint ;
    }
    public Point getEndPoint ()
    {
        return m_endPoint ;
    }
    public double getLineSlope ()
    {
        return m_lineSlope ;
    }
    public double getYIntercept ()
    {
        return m_yIntercept ;
    }
    public int getMaxX ()
    {
        return m_maxX ;
    }
    public int getMaxY ()
    {
        return m_maxY ;
    }
    public int getMinX ()
    {
        return m_minX ;
    }
    public int getMinY ()
    {
        return m_minY ;
    }
    
}
