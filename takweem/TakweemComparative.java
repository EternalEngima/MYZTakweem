/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package takweem;

import com.myz.files.MYZFile;
import com.myz.image.ImageEditorStage;
import com.myz.image.ImagePanel;
import javafx.geometry.Pos;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 *
 * @author Montazar Hamoud
 */
public class TakweemComparative extends BorderPane
{

    public TakweemComparative (Stage stage)
    {
        m_oldAnalysisTable = new AnalysisResultTable(stage);
        m_newAnalysisTable = new AnalysisResultTable(stage);
        
        m_imagePanelBox.getChildren().addAll(m_oldImagePanel , m_newImagePanel);
        m_imagePanelBox.setAlignment(Pos.CENTER);
        
        m_analysisTableBox.getChildren().addAll(m_oldAnalysisTable , m_newAnalysisTable);
        m_analysisTableBox.setAlignment(Pos.CENTER);
        
        
        setCenter(m_imagePanelBox);
        setBottom(m_analysisTableBox);
        
        m_oldImagePanel.setParentPane(m_imagePanelBox);
        m_newImagePanel.setParentPane(m_imagePanelBox);
        
        
    }
    //Data member
    HBox                m_imagePanelBox    = new HBox(5);
    HBox                m_analysisTableBox = new HBox(5);
    RunTimeObject       m_oldObject        ;
    RunTimeObject       m_newObject        ;
    ImagePanel          m_oldImagePanel    = new ImagePanel()
    {
        @Override
        public void mouseDragDropped(final DragEvent e)
        {
            final Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasFiles()) 
            {
                if(db.getFiles().get(0).getName().endsWith(".MYZ"))
                {
                    MYZFile  file = new MYZFile();
                    m_oldObject   = file.read(db.getFiles().get(0) , 0);
                    ((HBox) getParentPane()).getChildren().remove(0);
                    ((HBox) getParentPane()).getChildren().add( 0 , m_oldObject.getImagePanel() );
                    m_oldObject.calculateOperationsAndShow(m_oldAnalysisTable);
                }
                else
                {
                    ImageEditorStage imageEditorStage = new ImageEditorStage(db.getFiles().get(0) , this);
                }
                success = true;
            }
            e.setDropCompleted(success);
            e.consume();
        }
    };
    ImagePanel          m_newImagePanel    = new ImagePanel()
    {
        @Override
        public void mouseDragDropped(final DragEvent e)
        {
            final Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasFiles()) 
            {
                if(db.getFiles().get(0).getName().endsWith(".MYZ"))
                {
                    MYZFile  file = new MYZFile();
                    m_newObject   = file.read(db.getFiles().get(0) ,0);
                    ((HBox) getParentPane()).getChildren().remove(1);
                    ((HBox) getParentPane()).getChildren().add( 1 , m_newObject.getImagePanel() );
                    m_newObject.calculateOperationsAndShow(m_newAnalysisTable);
                }
                else
                {
                    ImageEditorStage imageEditorStage = new ImageEditorStage(db.getFiles().get(0) , this);
                }
                success = true;
            }
            e.setDropCompleted(success);
            e.consume();
        }
    };
    
    AnalysisResultTable m_oldAnalysisTable = null ;
    AnalysisResultTable m_newAnalysisTable = null ;
    

    
    
    
    
    
}
