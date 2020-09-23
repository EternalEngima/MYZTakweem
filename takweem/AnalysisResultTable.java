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
        m_operationCorrectValueCol = new TableColumn("Normal Result");
        m_operationValueCol        = new TableColumn("Result");
        m_operationErrorRangeCol   = new TableColumn("Error Range");
        
        m_operationNameCol.setCellValueFactory(new PropertyValueFactory("m_name"));
        m_operationCorrectValueCol.setCellValueFactory(new PropertyValueFactory("m_correctValue"));
        m_operationValueCol.setCellValueFactory(new PropertyValueFactory("m_value"));
        m_operationErrorRangeCol.setCellValueFactory(new PropertyValueFactory("m_errorRange"));
        
        getColumns().setAll( m_operationNameCol , m_operationCorrectValueCol , m_operationValueCol , m_operationErrorRangeCol);
        setPrefWidth(800);
        setPrefHeight(150);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);

        
        setRowFactory((Object tv) -> 
        {
            return new TableRow<MYZOperation>() 
            {
                @Override
                protected void updateItem(MYZOperation item , boolean empty) 
                {
                    super.updateItem(item , empty); 
                    
                    //Set on mouse click row show the descreption
                    this.setOnMousePressed(event ->
                    {
                        if (event.getClickCount() == 2 && (! this.isEmpty()) )
                        {
                            MYZOperation rowData = this.getItem();
                            myzPopup popup = new myzPopup(rowData.m_description , event.getScreenX() , event.getScreenY()); 
                            if (!popup.isShowing())
                                popup.show(primaryStage);
                        }
                    });
                    //Change row color depend on value
                    if (item!=null && Double.valueOf(item.getM_value()) > 0.0 && Double.valueOf(item.getM_value()) > Double.valueOf(item.getM_correctValue()) )
                    {
                        setStyle("-fx-text-fill:#FF726F;-fx-font-weight:bolder");
                    }
                    else if (item!=null && Double.valueOf(item.getM_value()) > 0.0 && Double.valueOf(item.getM_value()) < Double.valueOf(item.getM_correctValue()))
                    {
                        setStyle("-fx-text-fill:#ADD8E6;-fx-font-weight:bolder");
                    }
                    
                }
            };
        });
    }

    //Data member
    TableColumn m_operationNameCol   ;
    TableColumn m_operationValueCol ;
    TableColumn m_operationCorrectValueCol ;
    TableColumn m_operationErrorRangeCol ;
    
    @Override
    public boolean canAdd()
    {
        return false;
    }
    
    @Override
    public boolean canDelete()
    {
        return false;
    }
    
}
