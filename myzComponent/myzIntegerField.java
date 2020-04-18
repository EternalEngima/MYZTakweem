/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * @author yazan
 */
public class myzIntegerField extends TextField
{
    //Member
    Pane      m_parentPane  = null;

    //Constructer
    public myzIntegerField()
    {
        this.textProperty().addListener(new myzIntegerField_actionAdapter(this));
     
    }
    
    
    //Method
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
        
 
}

class myzIntegerField_actionAdapter implements ChangeListener
{
    //Member
    private myzIntegerField m_integerField;
    
    //Constructer
    myzIntegerField_actionAdapter (myzIntegerField integerField)
    {
        m_integerField = integerField;
    }


    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) 
    {
        String newVal  = (String) newValue; 
        String oldVal  = (String) oldValue; 

        if (!newVal.matches("\\d*")) 
        {
            m_integerField.setText(newVal.replaceAll("[^\\d]", ""));
        }   
    }
    
    
}

