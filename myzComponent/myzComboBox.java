/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

/**
 * @author yazan
 */
public class myzComboBox extends ComboBox
{
    Pane      m_parentPane  = null;


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
//            prefHeightProperty().bind(m_parentPane.heightProperty());
            prefWidthProperty().bind(m_parentPane.widthProperty());
        }
            
    }
}
