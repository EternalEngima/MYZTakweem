package takweem;
import com.myz.image.ImageEditorStage;
import com.myz.image.ImagePanel;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import myzComponent.myzButton;
import myzComponent.myzComboBox;
import myzComponent.myzComponent;
import myzComponent.myzLabel;
import myzComponent.myzMagnifier;
import myzComponent.myzScene;
import myzComponent.myzTableView;
import myzMessage.myzMessage;
import myzReport.TakweemReport;

/**
 * @author yazan
 */
public class Takweem extends Application 
{
    
    public static final ResourceBundle   ARABIC_BUNDLE   =  ResourceBundle.getBundle("captions",new Locale("ar", "sy"));
    public static final ResourceBundle   ENGLISH_BUNDLE  =  ResourceBundle.getBundle("captions",new Locale("en", "en"));
    public static final ResourceBundle   FRENCH_BUNDLE   =  ResourceBundle.getBundle("captions",new Locale("fr", "fr"));

    public static       ResourceBundle   m_bundle        = ResourceBundle.getBundle("captions",new Locale("en", "en"));
    BorderPane          m_container                      = new BorderPane();

    public Stage m_primaryStage ;
    
    //ImagePanel
    public ImagePanel      m_imagePanel       = new ImagePanel();//add by montazar 


    //Magnifier
    public myzMagnifier m_magnifier      = new myzMagnifier();//add by montazar
    
    // header component 
    VBox         m_header                = new VBox(10);
    MenuBar      m_sittingsBar           = new MenuBar();
    Menu         m_sittingsMenu          = new Menu(getCaption("menubar.settings"));
    Menu         m_langMenu              = new Menu(getCaption("application.lang"));
    
    HBox         m_headerPane            = new HBox(20);
    myzButton    m_saveImageButton             = new myzButton()
    {
        
        @Override
        public void buttonPressed()
        {
            m_imagePanel.saveImage();
        }
    };


    myzButton    m_addPhotoButton        = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            FileChooser  fileChooser   = new FileChooser(); 
            File         file          = fileChooser.showOpenDialog(m_primaryStage);   
            try 
            {
                //Modified by montazar
                new ImageEditorStage(file , m_imagePanel);            
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }     
        }
    };
    
    myzButton    m_printResultButton     = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            //add by montazar 
            Image selectedImage = m_imagePanel.getSelectedImage();
           TakweemReport.callFrame(m_primaryStage , selectedImage);

        }
    };
    
    myzLabel     m_anatomyLabel          = new myzLabel();
    myzComboBox  m_anatomyCombo          = new myzComboBox();
    myzButton    m_modifyAnatomy         = new myzButton();
    myzButton    m_deleteAnatomy         = new myzButton();

    


    //center Component 
    Label        m_fixedFooterLabel = new Label();
    //Left 
    VBox         m_leftSidebar      = new VBox(10);

    Pane         m_TablePan         = new Pane();
    myzTableView m_calculateTable   = new myzTableView();

    @Override
    public void start(Stage primaryStage)
    {
        m_primaryStage = primaryStage;
        // we can not write init() method cuz there is some class have and it is think we override it
        initFrame();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent widowEvent) 
            {    
                closeMe(m_primaryStage , widowEvent );
            }
        });
        
    } 
    
    public void initFrame()// wtf is going on if we mdelete this parameterc  
    {
    
        initHeader();
        initLeftSidebar();
        myzScene scene = new myzScene(m_container, 900, 700);
        m_container.setTop(m_header);
        //Modified by montazar 
        m_container.setCenter(m_imagePanel); 
        m_container.setLeft(m_leftSidebar);

        m_primaryStage.setTitle("Takweem");
        m_primaryStage.getIcons().add(new Image("icon\\programIcon.png"));
        m_primaryStage.setScene(scene);
//        primaryStage.setMinHeight(700);
//        primaryStage.setMinWidth(700);
//        primaryStage.setResizable(false);
//        primaryStage.setOnCloseRequest(e -> {e.consume();closeMe(primaryStage);} );
        m_primaryStage.show();
        m_primaryStage.setMaximized(true);
        
    }
    
    public void initHeader()
    {
            
        // Header-------------------------------------------------------------------------------------------------------------
        
        CheckMenuItem  eng  = new  CheckMenuItem ("English");
        eng.setSelected(true);// set the default language to english
        CheckMenuItem  arab = new CheckMenuItem ("\u0627\u0644\u0639\u0631\u0628\u064a\u0629");
        CheckMenuItem  fran = new CheckMenuItem ("French");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>()
        { 
            @Override
            public void handle(ActionEvent e) 
            { 
                ObservableList<MenuItem> items     = ((CheckMenuItem)e.getSource()).getParentMenu().getItems();
                CheckMenuItem            eventItem = (CheckMenuItem)e.getSource();
                for ( int i = 0 ; i < items.size() ; i++)
                {
                    CheckMenuItem item = (CheckMenuItem) items.get(i);
                    if (!item.getText().equals(eventItem.getText()))
                    {
                        item.setSelected(false);
                    }
                    
                }  
                eventItem.setSelected(true);
                ResourceBundle old  = m_bundle;
                if ( eventItem.getText().equals("English"))
                    m_bundle = ENGLISH_BUNDLE;
                if ( eventItem.getText().equals("\u0627\u0644\u0639\u0631\u0628\u064a\u0629"))
                    m_bundle = ARABIC_BUNDLE;
                if ( eventItem.getText().equals("French"))
                    m_bundle = FRENCH_BUNDLE;
                refreshComponent(old);
            } 
        };
        eng.setOnAction(event);
        arab.setOnAction(event);
        fran.setOnAction(event);

        m_langMenu.getItems().addAll(eng , arab , fran);
        m_sittingsMenu.getItems().add(m_langMenu);
        
        m_sittingsBar.getMenus().add(m_sittingsMenu);
        
        m_saveImageButton.setCaption("save.image");
        m_saveImageButton.setGraphic(new ImageView("icon\\save.png"));
        m_saveImageButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_saveImageButton.setMaxHeight(25);
        m_saveImageButton.setParentPane(m_headerPane);
        m_saveImageButton.setReSizeOnParentSize(true);
        
        m_addPhotoButton.setCaption("button.add.photo");
        m_addPhotoButton.setGraphic(new ImageView("icon\\addphoto.png"));
        m_addPhotoButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_addPhotoButton.setMaxHeight(25);
        m_addPhotoButton.setParentPane(m_headerPane);
        m_addPhotoButton.setReSizeOnParentSize(true);

        m_printResultButton.setCaption("button.prin.result");
        m_printResultButton.setGraphic(new ImageView("icon\\print.png"));
        m_printResultButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_printResultButton.setParentPane(m_headerPane);
        m_printResultButton.setReSizeOnParentSize(true);
        
        m_anatomyLabel.setCaption("anatomy.choose.label");
        m_anatomyLabel.setParentPane(m_headerPane);
        m_anatomyLabel.setReSizeOnParentSize(true);
        m_anatomyLabel.setFont(new Font(14));
        
        m_anatomyCombo.setMinSize(250, 25);
        m_anatomyCombo.setParentPane(m_headerPane);
        m_anatomyCombo.setReSizeOnParentSize(true);
        
        m_modifyAnatomy.setCaption("anatomy.modify");
        m_modifyAnatomy.setGraphic(new ImageView("icon\\modify.png"));
        m_modifyAnatomy.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_modifyAnatomy.setDisable(true);
        m_modifyAnatomy.setMaxHeight(25);
        m_modifyAnatomy.setParentPane(m_headerPane);
        m_modifyAnatomy.setReSizeOnParentSize(true);
         
        m_deleteAnatomy.setCaption("anatomy.delete");
        m_deleteAnatomy.setGraphic(new ImageView("icon\\delete.png"));
        m_deleteAnatomy.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
//        m_deleteAnatomy.setOnAction(e -> {e.consume();closeMe(primaryStage);} );
        m_deleteAnatomy.setDisable(true);
        m_deleteAnatomy.setMaxHeight(25);
        m_deleteAnatomy.setParentPane(m_headerPane);
        m_deleteAnatomy.setReSizeOnParentSize(true);
 
        m_headerPane.getChildren().addAll(m_saveImageButton , m_addPhotoButton , m_printResultButton 
                                          ,m_anatomyLabel ,m_anatomyCombo , m_modifyAnatomy ,m_deleteAnatomy );

        m_header.getChildren().addAll( m_sittingsBar , m_headerPane);
//        m_header.setStyle("-fx-border-color : black ;");
    }
    
    public void initLeftSidebar()
    {
        String cssLayout = "-fx-border-color: black;\n" +
                           "-fx-border-width: 2;\n" ;
        
        m_magnifier.startRunning();//start magnifier thread
        
        m_leftSidebar.getChildren().addAll(m_magnifier);
        m_leftSidebar.setAlignment(Pos.TOP_LEFT);
        m_leftSidebar.setStyle(cssLayout);
    }

    
    
//    public void initBottom()
//    {
//                
//        
//        m_fixedFooterLabel.setText(getCaption("window.footer.title"));   
//        m_fixedFooterLabel.setFont(new Font(15));
////        m_calculateTable.setMinWidth(900);
//        m_calculateTable.setMinHeight(100);
//        m_calculateTable.setCenterShape(true);
//        m_calculateTable.setParentPane(m_TablePan);
//        m_calculateTable.setReSizeOnParentSize(true);
//        m_TablePan.getChildren().add(m_calculateTable);
//        
//        m_footer.getChildren().addAll(m_TablePan , m_fixedFooterLabel);
//        m_footer.setAlignment(Pos.CENTER);
//    }
    
    public void refreshComponent(ResourceBundle oldBundle)
    {
        refreshCaption();
        refreshAlignment(oldBundle);
    }
    

    
    public void refreshAlignment(ResourceBundle oldBundle)
    {
        if ((oldBundle == ENGLISH_BUNDLE || oldBundle == FRENCH_BUNDLE) && m_bundle == ARABIC_BUNDLE)
        {
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(m_headerPane.getChildren());
            Collections.reverse(workingCollection);
            m_headerPane.getChildren().setAll(workingCollection);
            m_headerPane.setAlignment(Pos.CENTER_RIGHT);
            return;
        }
        if ((oldBundle == ENGLISH_BUNDLE || oldBundle == FRENCH_BUNDLE) && (m_bundle == ENGLISH_BUNDLE || m_bundle == FRENCH_BUNDLE) )
        {
            return;
        }
        if (oldBundle == ARABIC_BUNDLE && (m_bundle == ENGLISH_BUNDLE || m_bundle == FRENCH_BUNDLE))
        {
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(m_headerPane.getChildren());
            Collections.reverse(workingCollection);
            m_headerPane.getChildren().setAll(workingCollection);
            m_headerPane.setAlignment(Pos.CENTER_LEFT);

        }

    }
    
       
    public void refreshCaption()
    {
        try
        {
            Field []     buttons   = Takweem.class.getDeclaredFields();
            myzComponent component = null ;
            for( Field field : buttons)
            {
                Class className = field.getType() ;
                if ( Class.forName("myzComponent.myzComponent").isAssignableFrom(className) )
                {
                    component = (myzComponent)field.get(this);
                    component.refreshCaption();
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        // header 
        m_sittingsMenu.setText(getCaption("menubar.settings"));
        m_langMenu.setText(getCaption("application.lang"));
        //footer
        m_fixedFooterLabel.setText(getCaption("window.footer.title"));
    }
    
    public static String getCaption(String key)
    {
        
        return m_bundle.getString(key);
    }
    
    
    public void closeMe(Stage primaryStage , Event windoEvent )
    {
        boolean answer = myzMessage.confirmMessage(m_bundle.getString("exit.confirmation") , m_bundle);
        if (answer)
        {
            primaryStage.close();
            m_magnifier.stopRunning();
        }
        else
            windoEvent.consume();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
