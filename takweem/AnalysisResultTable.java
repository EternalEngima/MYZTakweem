/*
 *
 *
 */
package takweem;


import com.myz.calculable.MYZOperation;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import myzComponent.myzPopup;
import myzComponent.myzTableView;

/**
 *
 * @author Montazar Hamoud
 */
public class AnalysisResultTable  extends myzTableView
{
    public AnalysisResultTable (Stage primaryStage)
    {
        m_operationNameCol         = new TableColumn("Opreation Name");
        m_operationValueCol        = new TableColumn("Result");
        m_operationCorrectValueCol = new TableColumn("Normal Result");
        
        m_operationNameCol.setCellValueFactory(new PropertyValueFactory("m_name"));
        m_operationValueCol.setCellValueFactory(new PropertyValueFactory("m_value"));
        m_operationCorrectValueCol.setCellValueFactory(new PropertyValueFactory("m_correctValue"));
        
        getColumns().setAll( m_operationNameCol , m_operationValueCol , m_operationCorrectValueCol);
        setPrefWidth(800);
        setPrefHeight(150);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        
        setRowFactory( tmp -> 
        {
            TableRow<MYZOperation> row = new TableRow<>();
            row.setOnMousePressed(event -> 
            {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) 
                {
                    MYZOperation rowData = row.getItem();
                    myzPopup popup = new myzPopup(rowData.m_description , event.getScreenX() , event.getScreenY());
                    if (!popup.isShowing()) 
                        popup.show(primaryStage); 
                }
            });
        return row ;
        });
        
        setRowFactory(tv -> new TableRow<MYZOperation>() 
        {


            @Override
            protected void updateItem(MYZOperation item , boolean empty) 
            {
                super.updateItem(item , empty); 
                if (item!=null && Double.valueOf(item.getM_value()) > Double.valueOf(item.getM_correctValue()) ) 
                {
                    
                    setStyle("-fx-background-color:red");
                } 
                else if (item!=null && Double.valueOf(item.getM_value()) < Double.valueOf(item.getM_correctValue())) 
                {
                    setStyle("-fx-background-color:blue");
                }

            }
        });
    }

    //Data member
    TableColumn m_operationNameCol   ;
    TableColumn m_operationValueCol ;
    TableColumn m_operationCorrectValueCol ;

    
}
