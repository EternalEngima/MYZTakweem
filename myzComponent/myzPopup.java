/*
 * This class present the Popup component witch help to show hint or help for user
 */
package myzComponent;

import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

/**
 *
 * @author Montazar Hamoud
 */
public class myzPopup extends Popup
{
    
    public myzPopup(String label , double x , double y)
    {
        
        getContent().add(m_textArea);
        setAutoHide(true);
        setX(x);
        setY(y);
        m_textArea.setMinWidth(50); 
        m_textArea.setMinHeight(50);
        m_textArea.setStyle("-fx-background-color: #FFFF80;");
        m_textArea.setText(label);
        m_textArea.setWrapText(true);
        m_textArea.setEditable(false);
    }
    //Data member
    TextArea  m_textArea = new TextArea();
    ImageView m_image = new ImageView();

    
    
}
