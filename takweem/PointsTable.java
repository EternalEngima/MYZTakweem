/*
 *this class represent the points table of the chosen Analysis 
 *
 */
package takweem;

import com.myz.calculable.MYZPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import myzComponent.myzPopup;
import myzComponent.myzTableView;

/**
 *
 * @author Montazar hamoud
 */
public class PointsTable extends myzTableView
{
    
    public PointsTable(Stage primaryStage)
    {        
        m_pointNameCol   = new TableColumn("Point Name");
        m_pointSymbolCol = new TableColumn("Symbol");
        
        //it's used to get the value from this member from the passed object 
        m_pointNameCol.setCellValueFactory(new PropertyValueFactory("m_name"));
        m_pointSymbolCol.setCellValueFactory(new PropertyValueFactory("m_symbol"));
        

        getColumns().setAll( m_pointNameCol , m_pointSymbolCol);
        setPrefWidth(210);
        setPrefHeight(230);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        
        
        setRowFactory( tmp -> 
        {
            
            TableRow<MYZPoint> row = new TableRow<>();
            row.setOnMousePressed(event -> 
            {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) 
                {
                    MYZPoint rowData = row.getItem();
                    myzPopup popup = new myzPopup(rowData.m_description , event.getScreenX() , event.getScreenY());
                    if (!popup.isShowing()) 
                        popup.show(primaryStage); 
                }
            });
        return row ;
        });
        
        setRowFactory(tv -> new TableRow<MYZPoint>() 
        {


            @Override
            protected void updateItem(MYZPoint item , boolean empty) 
            {
                super.updateItem(item , empty); 
                if (item!=null && item.getState() == MYZPoint.SELECTED) 
                {
                    getStyleClass().add("-fx-background-color:#cfc");
                } 
                else if (item!=null && item.getState() == MYZPoint.SELECT_NOW) 
                {
                    getStyleClass().add("-fx-background-color:#222");
                }
            }
        });
    }
    
    //Data members
    TableColumn m_pointNameCol   ;
    TableColumn m_pointSymbolCol ;
        

    
}
