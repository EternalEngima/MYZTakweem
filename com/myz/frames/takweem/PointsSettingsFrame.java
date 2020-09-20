/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.frames.takweem;

import com.myz.calculable.MYZPoint;
import com.myz.calculable.MYZPointsPool;
import com.myz.xml.XmlCategory;
import com.myz.xml.XmlClassification;
import com.myz.xml.XmlPointsPool;
import com.myz.xml.XmlTakweem;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
import javafx.event.EventHandler;
import javafx.scene.control.cell.TextFieldTableCell;
import myzComponent.myzComboBoxItem;

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
        public void selectionChange()
        {
            m_classification.deleteAllItems();
            initClassification();
            refreshTableData();

        }
    };
    myzComboBox  m_classification = new myzComboBox()
    {
        public void selectionChange()
        {
            refreshTableData();
        }
    };
    myzTableView m_pointsTable = new myzTableView();
    
    TableColumn m_pointName;
    TableColumn m_pointSymbol;
    TableColumn m_pointDesc;
    

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
        
        m_scene = new Scene(m_container , 600, 400 );
        m_window.setScene(m_scene);
        m_window.getIcons().add(new Image("icon\\save.png"));
        
        m_pointName     = new TableColumn("Name");
        m_pointName.setResizable(false);
        m_pointName.setEditable(true);
        m_pointSymbol   = new TableColumn("Symbol");
        m_pointSymbol.setResizable(false);
        m_pointDesc     = new TableColumn("Description");
        m_pointDesc.setResizable(false);
        m_pointDesc.setMinWidth(200);


        m_pointName.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_name"));
        m_pointSymbol.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_symbol"));
        m_pointDesc.setCellValueFactory(new PropertyValueFactory<XmlPoint , String>("m_description"));
        
        
        m_pointsTable.setEditable(true);
 
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
 
        
        m_pointsTable.getColumns().addAll(m_pointName , m_pointSymbol , m_pointDesc);
        
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
        m_pointsTable.setTableData(pointPool.getVPoints());
        
        
      

    }
    
    public void initScene()
    {
        m_header.getChildren().addAll(m_category , m_classification);
        m_container.getChildren().addAll(m_header , m_pointsTable );
    }
    
    
    public static void  callFrame(Stage primaryStage , RunTimeObject runTimeObjects)
    {
        PointsSettingsFrame stage = new PointsSettingsFrame(primaryStage , runTimeObjects);
    }
}
