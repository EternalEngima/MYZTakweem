/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myzComponent;

import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

/**
 * @author yazan
 */
public class myzScene extends Scene
{
    public static ResourceBundle m_bundle;

    public myzScene(Parent root)
    {
        super(root);
    }

    public myzScene(Parent root, double width, double height) 
    {
        super(root, width, height);
    }

    public myzScene(Parent root, Paint fill) 
    {
        super(root, fill);
    }

    public myzScene(Parent root, double width, double height, Paint fill)
    {
        super(root, width, height, fill);
    }

    public myzScene(Parent root, double width, double height, boolean depthBuffer) 
    {
        super(root, width, height, depthBuffer);
    }

    public myzScene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) 
    {
        super(root, width, height, depthBuffer, antiAliasing);
    }
}
