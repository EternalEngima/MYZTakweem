/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.frames.takweem;

import com.myz.xml.XmlAnalysis;
import com.myz.xml.XmlCategory;
import com.myz.xml.XmlClassification;
import com.myz.xml.XmlOperation;
import com.myz.xml.XmlTakweem;
import java.util.Vector;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myzComponent.myzButton;
import myzComponent.myzComboBox;
import myzComponent.myzComboBoxItem;
import myzComponent.myzTableView;
import takweem.RunTimeObject;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author yazan
 */
public class AnalysisSettingsFrame 
{
      //Frame Member
    private  Stage   m_primaryStage; 
    private  Stage   m_window;
    private  Scene   m_scene;
    VBox             m_container      = new VBox(20);
    RunTimeObject    m_runTimeObject;
    // Member 
    myzComboBox      m_category       = new myzComboBox()
    {
        @Override
        public void selectionChange()
        {
            m_classification.deleteAllItems();
            initClassification();
//            refreshAnalysisCombo();

        }
    };
    myzComboBox      m_classification = new myzComboBox()
    {
        @Override
        public void selectionChange()
        {
            initAnalysisCombo();
        }
    };
    myzComboBox      m_analysis       = new myzComboBox()
    {
        public void selectionChanged()
        {
            
        }
        
    };
    
    myzTableView     m_operationTable = new myzTableView()
    {
        public void clickedOnRow()
        {
            //TODO load operation point
        }
    };
    TableColumn  m_operation;
    TableColumn  m_operationName;
    TableColumn  m_operationDesc;
    TableColumn  m_operationType;
    TableColumn  m_operationCorrectValue;
    TableColumn  m_operationErrorRange;
   



    //Constructer
    AnalysisSettingsFrame( Stage primaryStage  , RunTimeObject runTimeObject)
    {
        m_primaryStage  = primaryStage;
        m_runTimeObject = runTimeObject;
        initFrame();
    }

 
    //Methods
    private void initFrame()
    {
        m_window = new Stage();
        m_window.initStyle(StageStyle.DECORATED);
        m_window.initModality(Modality.APPLICATION_MODAL);
        m_window.setResizable(false);
        
        m_category.setPromptText(takweem.Takweem.getCaption("category"));
        m_category.setMinWidth(100);
        initCategory();
        m_classification.setPromptText(takweem.Takweem.getCaption("classification"));
        m_classification.setMinWidth(300);
        m_analysis.setPromptText(takweem.Takweem.getCaption("analysis"));
        m_analysis.setMinWidth(200);
        
        HBox header = new HBox(20);
        header.getChildren().addAll(m_category , m_classification , m_analysis);
        initTable();
        m_container.getChildren().addAll(header , m_operationTable);
        
        m_scene = new Scene(m_container , 900, 400 );
        m_window.setScene(m_scene);
        m_window.getIcons().add(new Image("icon\\pointSettingsFrame.png"));
        m_window.showAndWait();

    }
    

    
    
    public void initCategory()
    {
        XmlTakweem   takweem        = m_runTimeObject.getRunTimeTakweem();
        Vector       vCategory      = m_runTimeObject.getRunTimeTakweem().getVCategory();
        for ( int i = 0 ; i < vCategory.size() ; i++)
        {
            XmlCategory category = (XmlCategory) vCategory.elementAt(i);
            System.out.println(category.getName());
            myzComboBoxItem item = new myzComboBoxItem(category.getName(), 0, category);
            m_category.addItems(item);
        }   
    }
    
    public void initClassification()
    {
        XmlCategory category        = (XmlCategory) m_category.getExtraDataValue();
        Vector      vClassification = category.gteVClassification();
        for ( int i = 0 ; i < vClassification.size() ; i++)
        {
            XmlClassification classification = (XmlClassification) vClassification.elementAt(i);
            myzComboBoxItem item = new myzComboBoxItem(classification.getName(), 0, classification);
            System.out.println(classification.getName());
            m_classification.addItems(item);
        }           
    }
    
    public void initAnalysisCombo()
    {
        XmlClassification classification        = (XmlClassification) m_classification.getExtraDataValue();
        Vector            vAnalysis             = classification.getVAnalysis();
        for ( int i = 0 ; i < vAnalysis.size() ; i++)
        {
            XmlAnalysis analysis = (XmlAnalysis) vAnalysis.elementAt(i);
            myzComboBoxItem item = new myzComboBoxItem(analysis.getName(), 0, analysis);
            System.out.println(classification.getName());
            m_analysis.addItems(item);
        }           
    }
    
    
    public void initTable()
    {
        m_operation     = new TableColumn("OPERATION");
        m_operation.setResizable(false);
        m_operation.setEditable(true);
        
        m_operationName     = new TableColumn("Name");
        m_operationName.setResizable(false);
        m_operationName.setEditable(true);
        m_operationName.setMinWidth(120);
        m_operationName.setCellFactory(TextFieldTableCell.forTableColumn());
        m_operationName.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlOperation) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_name = (String) t.getNewValue();
            }
        });
        
        m_operationDesc     = new TableColumn("Description");
        m_operationDesc.setResizable(false);
        m_operationDesc.setMinWidth(355);
        m_operationDesc.setEditable(true);
        m_operationDesc.setCellFactory(TextFieldTableCell.forTableColumn());
        m_operationDesc.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlOperation) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_description = (String) t.getNewValue();
            }
        });
        m_operationType     = new TableColumn("Type");
        m_operationType.setResizable(false);
        m_operationType.setMinWidth(230);
        m_operationType.setEditable(true);
        m_operationType.setCellFactory(TextFieldTableCell.forTableColumn());
        m_operationType.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlOperation) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_type = (String) t.getNewValue();
            }
        });
        
        
        m_operationCorrectValue     = new TableColumn("Correct Value");
        m_operationCorrectValue.setResizable(false);
        m_operationCorrectValue.setMinWidth(100);
        m_operationCorrectValue.setEditable(true);
        m_operationCorrectValue.setCellFactory(TextFieldTableCell.forTableColumn());
        m_operationCorrectValue.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlOperation) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_correctValue = (Double) t.getNewValue();
            }
        });
        
        m_operationErrorRange     = new TableColumn("Error Range");
        m_operationErrorRange.setResizable(false);
        m_operationErrorRange.setMinWidth(100);
        m_operationErrorRange.setEditable(true);
        m_operationErrorRange.setCellFactory(TextFieldTableCell.forTableColumn());
        m_operationErrorRange.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlOperation) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_errorRange = (Double) t.getNewValue();
            }
        });
           
    
       
        m_operationName.setCellValueFactory(new PropertyValueFactory<XmlOperation , String>("m_name"));
        m_operationDesc.setCellValueFactory(new PropertyValueFactory<XmlOperation , String>("m_description"));
        m_operationType.setCellValueFactory(new PropertyValueFactory<XmlOperation , String>("m_type"));
        m_operationCorrectValue.setCellValueFactory(new PropertyValueFactory<XmlOperation , Double>("m_correctValue"));
        m_operationErrorRange.setCellValueFactory(new PropertyValueFactory<XmlOperation , Double>("m_errorRange"));

        m_operationTable.setEditable(true);
        m_operation.getColumns().addAll(m_operationName  , m_operationType , m_operationCorrectValue , m_operationErrorRange , m_operationDesc);
        m_operationTable.getColumns().add(m_operation);
    }
    
    public static void  callFrame(Stage primaryStage , RunTimeObject runTimeObjects)
    {
        AnalysisSettingsFrame stage = new AnalysisSettingsFrame(primaryStage, runTimeObjects);
    }
    
}
