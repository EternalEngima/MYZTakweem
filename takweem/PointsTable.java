/*
 *this class represent the points table of the chosen Analysis 
 *
 */
package takweem;

import com.myz.calculable.MYZPoint;
import com.myz.image.Point;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
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
        setPrefWidth(250);
        setPrefHeight(300);
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
                else if ((event.getClickCount() == 1 && (! row.isEmpty()) ))
                {
                    Takweem.m_helperImage.removePreviousePoint();
                    
                    MYZPoint rowData = row.getItem();
                    int x = rowData.getHelperX();
                    int y = rowData.getHelperY();
                    
                    Point point = new Point(x , y);
                    point.draw( Takweem.m_helperImage.getBlankImageView() , Color.RED);
                    Takweem.m_helperImage.setPreviousePoint(x, y);
                }
            });
        return row ;
        });
        

    }
    
    //Data members
    TableColumn m_pointNameCol   ;
    TableColumn m_pointSymbolCol ;
        

    
}
