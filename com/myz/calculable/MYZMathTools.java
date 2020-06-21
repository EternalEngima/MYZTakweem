package com.myz.calculable;

import com.myz.image.Point;

/**
 * @author Zaid
 */
public class MYZMathTools
{
    //Driver
    public static void main( String[] args )
    {        
        Point  point1, point2, point3, point4, pResult;
        double dResult, dResult1;
        
        point1 = new Point( 2 , 2 );
        point2 = new Point( 1 , 1 );
        
        point3 = new Point( 2 , 2 );
        point4 = new Point( 2 , 4 );
            
        dResult  = calculateDistanceBetweenTwoPoints( point1 , point2 );
        dResult1 = calculateAngelBetweenTwoLines( point1 , point2 , point3 , point4 );        
    }
    
    //Statics
    public static double NO_ANGEL = 180;//return value of calculateAngelBetweenTwoLines(..) if the two line were paralle
    
    //Class methods
    public static Point calculateProjectedPointOnLine( Point l1 , Point l2 , Point p )
    {
        Point projected = null;
        
        //calculate the slope and YIntercept of the line
        double slope      = calculateSlope( l1.getX() , l1.getY() , l2.getX() , l2.getY() );
        if( slope == Double.NEGATIVE_INFINITY || slope == Double.POSITIVE_INFINITY )
        {
            projected = new Point( l1.getX() , p.getY() );
            return projected;
        }
        if( slope == 0 )
        {
            projected = new Point( p.getX() , l1.getY() );
            return projected;
        }
        double yIntercept = calculateYIntercept( slope , l1 );
        
        //calculate the inverse slope and it's YIntercept
        double iSlope      = -( 1 / slope );
        double iYIntercept = calculateYIntercept( iSlope , p );
        
        double x = ( iYIntercept - yIntercept ) / ( slope - iSlope );
        double y = slope * x + yIntercept;
        
        projected = new Point( x , y );
        return projected;
    }
    
    //Overload
    public static double calculateYIntercept( double slope , Point point )
    {
        double result = 0;
        
        result = calculateYIntercept( slope , point.getX() , point.getY() );
        
        return result;
    }
    
    //Overload
    public static double calculateAngelBetweenTwoLines( Point firstPointL1 , Point secondPointL1 , Point firstPointL2 , Point secondPointL2 )
    {
        double result = 0;
        
        result = calculateAngelBetweenTwoLines( firstPointL1.getX() , firstPointL1.getY() , secondPointL1.getX() , secondPointL1.getY() 
                                              , firstPointL2.getX() , firstPointL2.getY() , secondPointL2.getX() , secondPointL2.getY() );        
        
        return result;
    }
    //Overload
    public static double calculateDistanceBetweenTwoPoints( Point firstPoint , Point secondPoint )
    {
        return calculateDistanceBetweenTwoPoints( firstPoint.getX(), firstPoint.getY() , secondPoint.getX() , secondPoint.getY() );
    }        
    
      //Equation = tan^-1( abs( ( m2 - m1 ) / 1 + m2 * m1 ) )
      // or if one of the slobe is undifiend = 90 - tan^-1( the defiend slobe )
    public static double calculateAngelBetweenTwoLines( double x1 , double y1 , double x2 , double y2 , double x3 , double y3 , double x4 , double y4 )
    {
        double result = 0;
        
        double m1 = calculateSlope( x1 , y1 , x2 , y2 );
        double m2 = calculateSlope( x3 , y3 , x4 , y4 );
        
        //Checking if the two line are parallel
        if( ( m1 == Double.NEGATIVE_INFINITY || m1 == Double.POSITIVE_INFINITY ) && ( m2 == Double.NEGATIVE_INFINITY || m2 == Double.POSITIVE_INFINITY ) )
            return NO_ANGEL;
        if( m1 == 0 && m2 == 0 )
            return NO_ANGEL;
        if( m1 == m2 )
            return NO_ANGEL;
        
        if( m1 == Double.NEGATIVE_INFINITY || m1 == Double.POSITIVE_INFINITY )
        {
            double temp = Math.atan( m2 );
            result = 90 - temp;
        }
        else if( m2 == Double.NEGATIVE_INFINITY || m2 == Double.POSITIVE_INFINITY )
        {
            double temp = Math.atan( m1 );
            temp = Math.toDegrees( temp );
            result = 90 - temp;
        }
        else
        {
            double temp1 = m2 - m1;
            double temp2 = m2 * m1 + 1;

            result = temp1 / temp2;
            result = Math.abs( result );
            result = Math.atan( result );    
            result = Math.toDegrees( result );
            int x;
        }
        
        return result;
    }
    
      //Equation sqrt ( ( x2 - x1 )^2 + ( y2 - y1 )^2 )
    public static double calculateDistanceBetweenTwoPoints( double x1 , double y1 , double x2 , double y2 )
    {        
        double tmp1 = Math.pow( x2 - x1 , 2 );
        double tmp2 = Math.pow( y2 - y1 , 2 );
        
        double result = Math.sqrt( tmp1 + tmp2 );        
        return result;
    }
      //Equation y = mx + b ---> b = y - ( m x )
    public static double calculateYIntercept( double slope , double x , double y )    
    {
        double result = 0;
        
        result = y - ( slope * x );
        
        return result;
    }
    
      //Equation m = y2 - y1 / x2 - x1
    public static double calculateSlope( double x1 , double y1 , double x2 , double y2 )
    {   
        double tmp1   = y2 - y1;
        double tmp2   = x2 - x1;
   
        double result = tmp1 / tmp2;    
        return result;
    }

}
