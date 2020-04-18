package com.myz.image;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage; 
import static com.myz.image.ImagePanel.getImagePanel;
 
public class ImageEditor extends Application
{
    //Driver
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
     
    @Override
    public void start( Stage stage ) throws FileNotFoundException 
    {           
        StackPane root = getImagePanel( "src\\cephalometric.jpg" ) ;
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(800 , 800);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       
    }

}

