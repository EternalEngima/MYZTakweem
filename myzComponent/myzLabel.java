/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author yazan
 */
public class myzLabel extends Label
{
    myzScene  m_scene       = null; 
    String    m_captionKey  = null;
    Pane      m_parentPane  = null;
    public void buttonPressed(){}
    
    public myzLabel()
    {
        super();
    }
    public myzLabel(String text)
    {
        super();
        setCaption(text);
    }
    
    
    public void setParentPane(Pane pane)
    {
        m_parentPane = pane;
    }
    
    public Pane getParentPane()
    {
        return m_parentPane;
    }
    
    public void setReSizeOnParentSize(boolean b)
    {
        if (b && m_parentPane != null)
        {
            prefHeightProperty().bind(m_parentPane.heightProperty());
            prefWidthProperty().bind(m_parentPane.widthProperty());
        }
            
    }
    
    public void setCaption(String key)
    {
        m_captionKey = key;
        String str = takweem.Takweem.m_bundle.getString(key);
        if ( str != null)
            setText(str);
        else
            setText(key);
    }
    
    public void refreshCaption()
    {
        if (m_captionKey !=  null)
        {
            String str = takweem.Takweem.m_bundle.getString(m_captionKey);
            setText(str);
        }
    }
}
