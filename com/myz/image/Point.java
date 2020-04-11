package com.myz.image;

/**
 * @author Montazar
 */

public class Point 
{
    //Constructor
    public Point ( double x , double y )
    {
        m_x = x ;
        m_y = y ;
    }
    
    //Members
    double m_x , m_y ;
    

    // Setter Methods
    void setX ( double x )
    {
        m_x = x ;
    }
    void setY ( double y )
    {
        m_y = y ;
    }
    
    // Getter Methods
    public double getX ()
    {
        return m_x ;
    }    
    public double getY ()
    {
        return m_y ;
    }
}
