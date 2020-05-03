package com.myz.calculable;

/**
 * @author Zaid
 */
public class MYZMathTools
{
    
    //Class methods
      //Equation sqrt ( (x2*x2 - x1*x1) + (y2*y2 - y1*y1) )
    public static double calucateDistanceBetweenTwoPoints( int x1 , int y1 , int x2 , int y2 )
    {        
        double tmp1 = ( Math.pow( x2 , 2 ) - Math.pow( x1 , 2 ) );
        double tmp2 = ( Math.pow( y2 , 2 ) - Math.pow( y1 , 2 ) );
        
        double result = Math.sqrt( tmp1 + tmp2 );        
        return result;
    }
    
      //Equation m = y2 - y1 / x2 - x1
    public static double calucateSlope( int x1 , int y1 , int x2 , int y2 )
    {   
        double tmp1   = y2 - y1;
        double tmp2   = x2 - x1;
   
        double result = tmp1 / tmp2;    
        return result;
    }
    
    
    
}
