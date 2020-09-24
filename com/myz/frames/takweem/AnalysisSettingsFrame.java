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
import com.myz.xml.XmlPoint;
import com.myz.xml.XmlPointsPool;
import com.myz.xml.XmlTakweem;
import java.util.Vector;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

/**
 *
 * @author yazan
 */
public class AnalysisSettingsFrame 
{
    // yazan TODO take clone Vector of every things
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
        @Override
        public void selectionChange()
        {
            refreshOperationTableData();
        }
        
    };
    
    myzButton        m_saveButton     = new myzButton(takweem.Takweem.getCaption("save"))
    {
        @Override
        public void buttonPressed()
        {
            saveData();
        }
    };
    
    myzTableView     m_operationTable = new myzTableView()
    {
        @Override
        public void clickedOnRow()
        {
            //TODO load operation point
            refreshOperationPointTableData();
        }
        @Override
        public boolean canAdd()
        {
            return true;
        }
    };
    TableColumn  m_operation;
    TableColumn  m_operationName;
    TableColumn  m_operationDesc;
    TableColumn  m_operationType;
    TableColumn  m_operationCorrectValue;
    TableColumn  m_operationErrorRange;
    Vector       m_operationTableData;
    
    myzTableView     m_operationPointTable = new myzTableView()
    {
        @Override
        public void clickedOnRow()
        {
            if ( getItems().size() > 0)
            m_moveFromOperation.setDisable(false);  
        }
    };
    TableColumn  m_operationPoint;
    TableColumn  m_operationPointName;

    myzButton    m_moveToOperation = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            XmlPoint newPoint      = (XmlPoint) m_classificationPointTable.getSelectionModel().getSelectedItem();
            int      selectedIndex = m_classificationPointTable.getSelectionModel().getSelectedIndex();
            m_classificationPointTable.getItems().remove(selectedIndex);
            m_operationPointTable.getItems().add(newPoint);
            XmlOperation operation = (XmlOperation) m_operationTable.getSelectionModel().getSelectedItem();
            operation.getVXmlPoint().add(newPoint);
            if ( m_classificationPointTable.getItems().size() == 0 )
            {
                setDisable(true);
            }
            m_moveToOperation.setDisable(!operationPointTableFull((XmlOperation) m_operationTable.getSelectionModel().getSelectedItem()));
      }
    };
    
    myzButton    m_moveFromOperation = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            int selectedIndex =  m_operationPointTable.getSelectionModel().getSelectedIndex();
            m_operationPointTable.getItems().remove(selectedIndex);
            
            XmlOperation operation = (XmlOperation) m_operationTable.getSelectionModel().getSelectedItem();
            operation.getVXmlPoint().remove(selectedIndex);
            
            Vector excludePoint = new Vector(m_operationPointTable.getItems());
            refreshClassificationPointTableData(excludePoint);
            
            if (m_operationPointTable.getItems().size() == 0 )
            {
                setDisable(true);
            }
        }
    };
    TableColumn  m_classificationPoint;
    TableColumn  m_classificationPointName;
    
    myzTableView  m_classificationPointTable = new myzTableView()
    {
        @Override
        public void clickedOnRow()
        {
            if ( getItems().size() > 0)
                m_moveToOperation.setDisable(!operationPointTableFull((XmlOperation) m_operationTable.getSelectionModel().getSelectedItem()));
        }
    };



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
        m_saveButton.setGraphic(new ImageView("icon\\save.png"));
        HBox header = new HBox(20);
        header.getChildren().addAll(m_category , m_classification , m_analysis , m_saveButton);
        initOperationTable();
        initOperationPointTable();
        initClassificationPointTable();
        HBox bottom = new HBox(20);
        m_moveToOperation.setGraphic(new ImageView("icon\\leftArrow.png"));
        m_moveToOperation.setDisable(true);
        m_moveFromOperation.setGraphic(new ImageView("icon\\rightArrow.png"));
        m_moveFromOperation.setDisable(true);

        bottom.getChildren().addAll(m_operationPointTable , m_moveToOperation , m_moveFromOperation , m_classificationPointTable);
        bottom.setAlignment(Pos.CENTER);
        m_container.getChildren().addAll(header , m_operationTable , bottom);
        
        m_scene = new Scene(m_container , 900, 400 );
        m_window.setScene(m_scene);
        m_window.getIcons().add(new Image("icon\\analysis.png"));
        m_window.showAndWait();

    }
    

    
    
    public void initCategory()
    {
        XmlTakweem   takweem        = m_runTimeObject.getRunTimeTakweem();
        Vector       vCategory      = m_runTimeObject.getRunTimeTakweem().getVCategory();
        for ( int i = 0 ; i < vCategory.size() ; i++)
        {
            XmlCategory category = (XmlCategory) vCategory.elementAt(i);
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
            m_analysis.addItems(item);
        }           
    }
    
    
    public void initOperationTable()
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
        m_operationCorrectValue.setCellFactory
        (
            TextFieldTableCell.<XmlOperation, Double> forTableColumn(new StringConverter<Double>() 
            {
                @Override
                public String toString(Double object) 
                {
                    return object != null ? String.valueOf(object.doubleValue()): "";
                }

                @Override
                public Double fromString(String string) 
                {
                    return Double.valueOf(string);
                }
            }
        ));
        
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
        m_operationErrorRange.setCellFactory
        (
            TextFieldTableCell.<XmlOperation, Double> forTableColumn(new StringConverter<Double>() 
            {
                @Override
                public String toString(Double object) 
                {
                    return object != null ? String.valueOf(object.doubleValue()): "";
                }

                @Override
                public Double fromString(String string) 
                {
                    return Double.valueOf(string);
                }
            }
        ));  
    
       
        m_operationName.setCellValueFactory(new PropertyValueFactory<XmlOperation , String>("m_name"));
        m_operationDesc.setCellValueFactory(new PropertyValueFactory<XmlOperation , String>("m_description"));
        m_operationType.setCellValueFactory(new PropertyValueFactory<XmlOperation , String>("m_type"));
        m_operationCorrectValue.setCellValueFactory(new PropertyValueFactory<XmlOperation , Number>("m_correctValue"));
        m_operationErrorRange.setCellValueFactory(new PropertyValueFactory<XmlOperation , Number>("m_errorRange"));
         
        m_operationTable.setEditable(true);
        m_operation.getColumns().addAll(m_operationName  , m_operationType , m_operationCorrectValue , m_operationErrorRange , m_operationDesc);
        m_operationTable.getColumns().add(m_operation);
    }
    
    
    public void initOperationPointTable()
    {
        m_operationPoint     = new TableColumn("Operation - Point");
        m_operationPoint.setResizable(false);
        
        m_operationPointName     = new TableColumn("Point - Name");
        m_operationPointName.setResizable(false);
        m_operationPointName.setMinWidth(300);
        m_operationPointName.setCellFactory(TextFieldTableCell.forTableColumn());

        m_operationPointName.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_name"));
        m_operationPoint.getColumns().addAll(m_operationPointName);
        m_operationPointTable.getColumns().add(m_operationPoint);
    }
    
    public void initClassificationPointTable()
    {
        m_classificationPoint     = new TableColumn("Classification - Point");
        m_classificationPoint.setResizable(false);
        
        m_classificationPointName     = new TableColumn("Point - Name");
        m_classificationPointName.setResizable(false);
        m_classificationPointName.setMinWidth(300);
        m_classificationPointName.setCellFactory(TextFieldTableCell.forTableColumn());

        
        m_classificationPointName.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_name"));
        m_classificationPoint.getColumns().addAll(m_classificationPointName);
        m_classificationPointTable.getColumns().add(m_classificationPoint);
    }
    
    
    public void refreshOperationTableData()
    {
        m_operationTable.getItems().removeAll();
        XmlAnalysis       analysis       = (XmlAnalysis) m_analysis.getExtraDataValue();
        if ( analysis == null || analysis.getName().length()<= 1 )
            return;
       
        Vector vOperation = analysis.getVXmlOperation();
        m_operationTableData = new Vector();
       for ( int i = 0 ; i < vOperation.size() ; i++)
       {
           XmlOperation operation = (XmlOperation)vOperation.elementAt(i);
           XmlOperation tableOperation = new XmlOperation(operation);
           m_operationTableData.add(tableOperation);
       }
        m_operationTable.setTableData(m_operationTableData);
    }
    
    public void refreshOperationPointTableData()
    {

        XmlAnalysis  analysis     = (XmlAnalysis) m_analysis.getExtraDataValue();
        XmlOperation xmlOperation = (XmlOperation) m_operationTable.getSelectionModel().getSelectedItem();
 
        if ( analysis == null || analysis.getName().length()<= 1 || xmlOperation == null || xmlOperation.getM_name().length() < 1)
            return;
        
            Vector points = xmlOperation.getVXmlPoint();
            
            for ( int i = 0; i < points.size() ; i++)
            {
                XmlPoint point = (XmlPoint) points.elementAt(i);
            }
        
        m_operationPointTable.setTableData( xmlOperation.getVXmlPoint());
        refreshClassificationPointTableData(xmlOperation.getVXmlPoint());
    }
    
    // we have to add all point in this classification but without those whome already in operation
    public void refreshClassificationPointTableData(Vector excludeVec)
    {
        XmlClassification classification = (XmlClassification) m_classification.getExtraDataValue();
        XmlPointsPool     classPoinPool  = classification.getPointsPool();
        Vector            data           = new Vector(); 
        for ( int i = 0 ; i < classPoinPool.getVPoints().size() ; i++ )
        {
            boolean  exist     = false;
            XmlPoint poolPoint = (XmlPoint) classPoinPool.getVPoints().elementAt(i);
            for ( int j = 0 ; j < excludeVec.size() ; j++)
            {
                XmlPoint excludePoint = (XmlPoint) excludeVec.elementAt(j);
                if ( poolPoint.getM_name().equals(excludePoint.getM_name()) && poolPoint.getM_symbol().equals(excludePoint.getM_symbol()))
                    exist = true;
            }
            if(! exist )
            {
                data.add(poolPoint);
            }
        }
        m_classificationPointTable.setTableData(data);
    }
    
    public boolean operationPointTableFull(XmlOperation operation)
    {
        if ( operation == null)
        {
            return true;
        }
        int tableCount          = m_operationPointTable.getItems().size();
        int operationPointCount = XmlOperation.getOperationNeededPoint(operation.m_type);
        System.out.println("tableCount : " + tableCount  + " operationPointCount " + operationPointCount);
        if (tableCount < operationPointCount)
            return true;
        return false;
    }
    
    public void saveData()
    {
//         System.out.println("save data .........");
        
//        ObservableList list  = m_operationTable.getItems();
//        for ( int i = 0 ; i < list.size() ; i++ )
//        {
//            XmlOperation operation = (XmlOperation) list.get(i);
//            System.out.println("-" + i + " table - name : " + operation.m_name );
//            for ( int j = 0 ; j < operation.getVXmlPoint().size() ; j++)
//            {
//                XmlPoint point = (XmlPoint) operation.getVXmlPoint().elementAt(j);
//                System.out.println("          -" + j + " pointTable - name : " + point.m_name );
//
//            }
//        }
        
       

//        XmlAnalysis       analysis       = (XmlAnalysis) m_analysis.getExtraDataValue();
//        System.out.println("*************************************************************************");
//
//        for ( int i = 0 ; i < analysis.getVXmlOperation().size() ; i++ )
//        {
//            XmlOperation operation = (XmlOperation) analysis.getVXmlOperation().elementAt(i);
//            System.out.println("-" + i +"basic - name : " + operation.m_name );
//            for ( int j = 0 ; j < operation.getVXmlPoint().size() ; j++)
//            {
//                XmlPoint point = (XmlPoint) operation.getVXmlPoint().elementAt(j);
//                System.out.println("          -" + j + " basicPoint - name : " + point.m_name );
//
//            }
//        }
        XmlAnalysis analysis = (XmlAnalysis) m_analysis.getExtraDataValue();
        ObservableList list  = m_operationTable.getItems();
        Vector data = new Vector();
        list.stream().forEach(data::add);
        analysis.setM_vOperation(data);
        m_runTimeObject.getRunTimeTakweem().saveToFile("src\\Takweem.xml");
        myzMessage.myzMessage.noteMessage(takweem.Takweem.getCaption("save.point.done"), takweem.Takweem.BUNDLE);
        m_window.close();
    }
    
    public static void  callFrame(Stage primaryStage , RunTimeObject runTimeObjects)
    {
        AnalysisSettingsFrame stage = new AnalysisSettingsFrame(primaryStage, runTimeObjects);
    }
    
}
