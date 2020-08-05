/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myz.frames.takweem;

import java.util.Vector;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myzComponent.myzComboBox;
import myzComponent.myzLabel;
import myzComponent.myzScene;

/**
 *
 * @author Zaid
 */
public class TakweemAnalysisFrame
{
    //Driver
    public static void main( String[] args )
    {
        callFrame( new Stage() );
    }
    
    //Constructor
    TakweemAnalysisFrame( Stage parentFrame )
    {
        m_parentFrame = parentFrame;
        initFrame();
    }
    
    //Class Methods
    public static void callFrame( Stage parentFrame )
    {
        new TakweemAnalysisFrame( parentFrame );
        return;
    }
    
    //Members
    private myzScene m_scene;
    private Stage    m_frame;
    private Stage    m_parentFrame;
    
    TextField   m_name        = new TextField();
    myzComboBox m_type        = new myzComboBox();
    TextArea    m_description = new TextArea();
    
    //Methods
    public boolean initFrame()
    {
        //Container
        VBox container = new VBox( 15 );
        container.setAlignment( Pos.TOP_LEFT );
        
        //Name
        HBox     bName = new HBox( 10 );
        myzLabel lName = new myzLabel( "zaid" );
        m_name.setMinSize( 140 , 25 );
        m_name.setStyle(  "-fx-translate-x : 10px;");
        //bName.setAlignment(  Pos.CENTER_RIGHT );
        bName.getChildren().addAll( lName , m_name );
        container.getChildren().add( bName );
        
        //Type
        HBox     bType = new HBox( 10 );
        myzLabel lType = new myzLabel( "smer" );
        m_type.setMinSize( 160 , 20 );
        //bType.setAlignment( Pos.CENTER );
        bType.getChildren().addAll( lType , m_type );
        container.getChildren().add( bType );
        
        //Description
        HBox     bDescription = new HBox( 10 );
        myzLabel lDescription = new myzLabel( "description" );
        m_description.setMinSize( 600 , 60 );
        bDescription.getChildren().addAll( lDescription , m_description );
        container.getChildren().add( bDescription );
        
        m_scene = new myzScene( container , 800 , 350 );
        
        m_frame = new Stage();
        m_frame.initStyle( StageStyle.DECORATED );
        m_frame.initModality( Modality.APPLICATION_MODAL );
        m_frame.setResizable( false );
        m_frame.setScene( m_scene );
        m_frame.showAndWait();
        
        return true;
    }
}
