/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;

import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;

/**
 *
 * @author Montazar Hamoud
 */
public class myzMenuButton extends MenuButton implements myzComponent
{
    myzScene  m_scene       = null; 
    String    m_captionKey  = null;
    Pane      m_parentPane  = null;

    
    public myzMenuButton()
    {
        super();
    
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
        String str = takweem.Takweem.BUNDLE.getString(key);
        if ( str != null)
            setText(str);
        else
            setText(key);
    }
        
    @Override
    public void refreshCaption()
    {
        if (m_captionKey !=  null)
        {
            String str = takweem.Takweem.BUNDLE.getString(m_captionKey);
            setText(str);
        }
    }
    
}
