/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * @author yazan
 */
public class myzButton extends Button
{
    myzScene  m_scene       = null; 
    String    m_captionKey  = null;
    Pane      m_parentPane  = null;
    
    public myzButton()
    {
        super();
        setOnMouseClicked(new myzButton_actionAdapter(this));
    }
    public myzButton(String text)
    {
        setCaption(text);
        setOnMouseClicked(new myzButton_actionAdapter(this));
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
    
    // override this method on the button you created
    public  void buttonPressed(){}
  
    
}


class myzButton_actionAdapter implements EventHandler
{
    private myzButton m_button;
    myzButton_actionAdapter (myzButton button)
    {
        m_button = button;
    }

    @Override
    public void handle(Event event)
    {
        m_button.buttonPressed();
    }
    
    
}
