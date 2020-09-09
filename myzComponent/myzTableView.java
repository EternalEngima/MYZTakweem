/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 * @author yazan
 */
public class myzTableView extends TableView
{
    myzScene  m_scene       = null; 
    Pane      m_parentPane  = null;
    public void buttonPressed(){}

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
    public void setTableData(Vector  vData)
    {
        List list = new ArrayList();
        vData.stream().forEach(list::add);
        setItems(FXCollections.observableList(list));
        refresh();
    }
    
 
}
