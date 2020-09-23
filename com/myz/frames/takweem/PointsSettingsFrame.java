/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.frames.takweem;

import com.myz.xml.XmlCategory;
import com.myz.xml.XmlClassification;
import com.myz.xml.XmlPointsPool;
import com.myz.xml.XmlTakweem;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myzComponent.myzComboBox;
import myzComponent.myzTableView;
import java.util.Vector;
import javafx.scene.control.cell.PropertyValueFactory;
import takweem.RunTimeObject;
import com.myz.xml.XmlPoint;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import myzComponent.myzButton;
import myzComponent.myzComboBoxItem;
import myzComponent.myzLabel;

/**
 *
 * @author yazan
 */
public class PointsSettingsFrame 
{
    RunTimeObject m_runTimeObject;
    //Frame Member
    private        Stage   m_primaryStage; 
    private static Stage   m_window;
    private static Scene   m_scene;
    
    // Member 
    VBox         m_container      = new VBox(20);
    
    HBox         m_header         = new HBox(20);
    
    myzComboBox  m_category       = new myzComboBox()
    {
        @Override
        public void selectionChange()
        {
            m_classification.deleteAllItems();
            initClassification();
            refreshTableData();

        }
    };
    myzComboBox  m_classification = new myzComboBox()
    {
        @Override
        public void selectionChange()
        {
            refreshTableData();
        }
    };
    
    myzButton  m_saveButton        = new myzButton("save")
    {
        @Override
        public void buttonPressed()
        {
            if ( m_pointsTable.getItems().size() < 1 )
            {
                myzMessage.myzMessage.noteMessage(takweem.Takweem.getCaption("check.point.table.not.empty"), takweem.Takweem.BUNDLE );
                return;
            }
            saveData();
        }
    };
    
    

    myzLabel     m_pointNameLabel   = new myzLabel("point.name");
    TextField    m_pointNameField   = new TextField();
    myzLabel     m_pointSymbolLabel = new myzLabel("point.symbol");
    TextField    m_pointSymbolField = new TextField();
    myzLabel     m_pointDescLabel   = new myzLabel("point.description");
    TextField    m_pointDescField   = new TextField();
    
    myzButton    m_addPoint         = new myzButton("add")
    {
        @Override
        public void buttonPressed()
        {
            if ( m_tableData == null)
            {
                myzMessage.myzMessage.noteMessage(takweem.Takweem.getCaption("check.point.table.not.empty"), takweem.Takweem.BUNDLE );
                return;
            }
            String pointName   = m_pointNameField.getText();
            String pointSymbol = m_pointSymbolField.getText();
            String pointDesc   = m_pointDescField.getText();
            if (pointName != null      && pointSymbol != null      && pointDesc != null &&
                pointName.length() > 0 && pointSymbol.length() > 0 && pointDesc.length() > 0 )
            {
                XmlPoint newPoint      = new  XmlPoint();
                newPoint.m_name        = pointName;
                newPoint.m_symbol      = pointSymbol;
                newPoint.m_description = pointDesc;
                m_pointsTable.getItems().add(newPoint);
            }
            else 
            {
                myzMessage.myzMessage.noteMessage(takweem.Takweem.getCaption("check.all.field.fill"), takweem.Takweem.BUNDLE );
            }
            
//            Object o = m_pointsTable.getSelectionModel().getModelItem(2);
            
        }
    };
    // Montazar come here TODO
    myzTableView m_pointsTable = new myzTableView()
    {
        @Override
        public void clickedOnRow()
        {
            XmlPoint point =  (XmlPoint) getSelectionModel().getSelectedItem();
            System.out.print("we are click on " + getSelectionModel().getSelectedIndex());
            if ( point != null)
                System.out.print("name : " + point.m_name );

        }
    };
    TableColumn  m_pointName;
    TableColumn  m_pointSymbol;
    TableColumn  m_pointDesc;
    Vector       m_tableData  = null;

    //Constructer
    PointsSettingsFrame( Stage primaryStage , RunTimeObject runTimeObject )
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
          
        
        initScene();
        initCategory();
        
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
    
    public void refreshTableData()
    {
        XmlCategory       category       = (XmlCategory) m_category.getExtraDataValue();
        XmlClassification classification = (XmlClassification) m_classification.getExtraDataValue();
        
        if ( category == null || classification == null || category.getName().length()<= 1 || classification.getName().length()<= 1)
            return;
       
        System.out.println("categoryName : " + category.getName() + " classificationName : " + classification.getName());

        XmlPointsPool     pointPool      = classification.getPointsPool();
        
        m_tableData = new Vector();
        for ( int i = 0 ; i < pointPool.getVPoints().size() ; i++)
        {
            XmlPoint xmlPoint = (XmlPoint) pointPool.getVPoints().elementAt(i);
            XmlPoint framePoint = new XmlPoint(xmlPoint);
            m_tableData.add(framePoint);
        }
//        m_pointsTable.setTableData(pointPool.getVPoints());
        m_pointsTable.setTableData(m_tableData);

        
      

    }
    
    public void initScene()
    {
        m_category.setPromptText(takweem.Takweem.getCaption("category"));
        m_category.setMinWidth(100);
        m_classification.setPromptText(takweem.Takweem.getCaption("classification"));
        m_classification.setMinWidth(300);
        m_saveButton.setGraphic(new ImageView("icon\\save.png"));
        
        HBox pointNameSymbolBox = new HBox(20);
        pointNameSymbolBox.getChildren().addAll(m_pointNameLabel , m_pointNameField , m_pointSymbolLabel , m_pointSymbolField);
        
        m_pointDescField.setMinWidth(250);
        HBox pointDescBox = new HBox(20);
//        m_addPoint.setGraphic(new ImageView(""));
        pointDescBox.getChildren().addAll(m_pointDescLabel ,m_pointDescField ,m_addPoint);
        
        VBox subHeader   = new VBox(20);
        
        subHeader.getChildren().addAll(pointNameSymbolBox ,pointDescBox );
        
        initTable();
        m_header.getChildren().addAll(m_category , m_classification , m_saveButton);
        m_container.getChildren().addAll(m_header ,subHeader, m_pointsTable );
    }
    
    public void initTable()
    {
         
        m_scene = new Scene(m_container , 600, 400 );
        m_window.setScene(m_scene);
        m_window.getIcons().add(new Image("icon\\save.png"));
        
        m_pointName     = new TableColumn("Name");
        m_pointName.setResizable(false);
        m_pointName.setEditable(true);
        m_pointName.setMinWidth(200);
        m_pointName.setCellFactory(TextFieldTableCell.forTableColumn());
        m_pointName.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlPoint) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_name = (String) t.getNewValue();
            }
        });
        m_pointSymbol   = new TableColumn("Symbol");
        m_pointSymbol.setResizable(false);
        m_pointSymbol.setEditable(true);
        m_pointSymbol.setCellFactory(TextFieldTableCell.forTableColumn());
        m_pointSymbol.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlPoint) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_symbol = (String) t.getNewValue();
            }
        });
        m_pointDesc     = new TableColumn("Description");
        m_pointDesc.setResizable(false);
        m_pointDesc.setMinWidth(350);
        m_pointDesc.setEditable(true);
        m_pointDesc.setCellFactory(TextFieldTableCell.forTableColumn());
        m_pointDesc.setOnEditCommit(new EventHandler<CellEditEvent>() 
        {             
            @Override
            public void handle(CellEditEvent t)
            {             
                ((XmlPoint) t.getTableView().getItems().get(
                t.getTablePosition().getRow())).m_description = (String) t.getNewValue();
            }
        });
        m_pointName.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_name"));
        m_pointSymbol.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_symbol"));
        m_pointDesc.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_description"));
        m_pointsTable.setEditable(true);
        
        m_pointsTable.getColumns().addAll(m_pointName , m_pointSymbol , m_pointDesc);
    }
    
    public void saveData()
    {
//        System.out.println("save data .........");
        
        ObservableList list  = m_pointsTable.getItems();
//        for ( int i = 0 ; i < list.size() ; i++ )
//        {
//            XmlPoint xmlPoint = (XmlPoint) list.get(i);
//            
//            System.out.println("-" + i + "- name : " + xmlPoint.m_name + ", symbol : " + xmlPoint.m_symbol + " , desc : " + xmlPoint.m_description);
//        }
        
        XmlCategory       category       = (XmlCategory) m_category.getExtraDataValue();
        XmlClassification classification = (XmlClassification) m_classification.getExtraDataValue();

        XmlPointsPool     pointPool      = classification.getPointsPool();
//        System.out.println("*************************************************************************");
//
//        for ( int i = 0 ; i < pointPool.getVPoints().size() ; i++ )
//        {
//            XmlPoint xmlPoint = (XmlPoint) pointPool.getVPoints().elementAt(i);
//            
//            System.out.println("-" + i + i + "- name : " + xmlPoint.m_name + ", symbol : " + xmlPoint.m_symbol + " , desc : " + xmlPoint.m_description);
//        }

        Vector data = new Vector();
        list.stream().forEach(data::add);
        pointPool.setVPoints(data);
        m_runTimeObject.getRunTimeTakweem().saveToFile(null);
        myzMessage.myzMessage.noteMessage(takweem.Takweem.getCaption("save.point.done"), takweem.Takweem.BUNDLE);
        m_window.close();
                
        
    }
    
    public static void  callFrame(Stage primaryStage , RunTimeObject runTimeObjects)
    {
        PointsSettingsFrame stage = new PointsSettingsFrame(primaryStage , runTimeObjects);
    }
}
