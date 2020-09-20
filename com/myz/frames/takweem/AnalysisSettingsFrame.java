/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.frames.takweem;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author yazan
 */
public class AnalysisSettingsFrame 
{
      //Frame Member
    private        Stage   m_primaryStage; 
    private static Stage   m_window;
    private static Scene   m_scene;
    
    // Member 
//    myzTable m_
    
    

    //Constructer
    AnalysisSettingsFrame( Stage primaryStage )
    {
        m_primaryStage = primaryStage;
        initFrame();
    }

 
    //Methods
    private void initFrame()
    {
        m_window = new Stage();
        m_window.initStyle(StageStyle.DECORATED);
        m_window.initModality(Modality.APPLICATION_MODAL);
        m_window.setResizable(false);
          
     
        m_scene = new Scene(null , 400, 170 );
        m_window.setScene(m_scene);
        m_window.getIcons().add(new Image("icon\\save.png"));
        m_window.showAndWait();

    }
    
    public static void  callFrame(Stage primaryStage)
    {
        AnalysisSettingsFrame stage = new AnalysisSettingsFrame(primaryStage);
    }
    
}
