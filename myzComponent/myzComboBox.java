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
    Pane                     m_parentPane  = null;

    public myzComboBox()
    {
        valueProperty().addListener((obs, oldVal, newVal)-> {   if (oldVal != newVal) this.selectionChange();  });    
        
    }
    
    public void selectionChange(){ }

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
            prefWidthProperty().bind(m_parentPane.widthProperty());
        }     
    }
    
    public void addItems(myzComboBoxItem ... item )
    {
        getItems().addAll(item);
    }
    
    public int getIntValue()
    {
        myzComboBoxItem item = (myzComboBoxItem)getValue();
        if (item != null)
        {
            return item.getkey();
        }
        return -1;
    }
    
    public String getStringValue()
    {
        myzComboBoxItem item = (myzComboBoxItem)getValue();
        if (item != null)
        {
            return item.getValue();
        }
        return "";
    }
    
    public Object getExtraDataValue()
    {
        myzComboBoxItem item = (myzComboBoxItem)getValue();
        if (item != null)
        {
            return item.getExtraData();
        }
        return null;
    }
    
    public myzComboBoxItem getItemValue()
    {
        myzComboBoxItem item = (myzComboBoxItem)getValue();
        if (item != null)
        {
            return item;
        }
        return null;
    }
    
    public void deleteAllItems()
    {
        getItems().clear();
    }

    
}


