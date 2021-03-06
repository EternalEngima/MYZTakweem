package takweem;
import com.myz.calculable.MYZPoint;
import com.myz.calculable.MYZRuler;
import com.myz.files.MYZFile;
import com.myz.frames.takweem.PointsSettingsFrame;
import com.myz.frames.takweem.AnalysisSettingsFrame;
import com.myz.image.ImageEditorStage;
import static com.myz.image.ImagePanel.PAINT_MODE_RULER;
import com.myz.image.ImagePanelHelper;
import com.myz.image.Line;
import com.myz.image.Point;
import com.myz.xml.XmlAnalysis;
import com.myz.xml.XmlCategory;
import com.myz.xml.XmlClassification;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import myzComponent.myzButton;
import myzComponent.myzComboBox;
import myzComponent.myzComboBoxItem;
import myzComponent.myzComponent;
import myzComponent.myzLabel;
import myzComponent.myzMagnifier;
import myzComponent.myzMenuButton;
import myzComponent.myzScene;
import myzMessage.myzMessage;
import myzReport.TakweemReport;

/**
 * @author yazan
 */
public class Takweem extends Application 
{
    
    //Class members
    public static final ResourceBundle   ARABIC_BUNDLE   =  ResourceBundle.getBundle("captions",new Locale("ar", "sy"));
    public static final ResourceBundle   ENGLISH_BUNDLE  =  ResourceBundle.getBundle("captions",new Locale("en", "en"));
    public static final ResourceBundle   FRENCH_BUNDLE   =  ResourceBundle.getBundle("captions",new Locale("fr", "fr"));

    public static       ResourceBundle   BUNDLE         = ResourceBundle.getBundle("captions",new Locale("en", "en"));
    public static       RunTimeObject    RUNTIME_OBJECT = new RunTimeObject();
    public static       myzScene         TAKWEEM_SCENE ;
   
    //Data members
    BorderPane          m_container = new BorderPane();
    public Stage        m_primaryStage ;
    
    

    
    //Magnifier
    public myzMagnifier m_magnifier      = new myzMagnifier();
   
    //Header component 
    VBox           m_header                = new VBox(5);
    MenuBar        m_sittingsBar           = new MenuBar();
    Menu           m_sittingsMenu          = new Menu(getCaption("menubar.settings"));
    Menu           m_langMenu              = new Menu(getCaption("application.lang"));
    MenuItem       m_pointMenu             = new MenuItem(getCaption("points"));
    MenuItem       m_analysisMenu          = new MenuItem(getCaption("analysis"));

    HBox           m_headerPane            = new HBox(5);
    myzMenuButton  m_categoryMenuButton    = new myzMenuButton();
    myzButton      m_saveAnalysisButton    = new myzButton()
    {        
        @Override
        public void buttonPressed()
        {
            MYZFile          save        = new MYZFile();
            FileChooser      fileChooser = new FileChooser();
            ExtensionFilter  extFilter   = new FileChooser.ExtensionFilter( "Analysis File (*.MYZ)", "*." + MYZFile.FILE_EXTENSION );
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setSelectedExtensionFilter(extFilter);
            File file = fileChooser.showSaveDialog(m_primaryStage);
            
            if(file != null)
                save.write(file);
        }
    };

    myzButton      m_saveImageButton       = new myzButton()
    {        
        @Override
        public void buttonPressed()
        {
            FileChooser      fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files (*.PNG)", "*.png"));
            File file = fileChooser.showSaveDialog(m_primaryStage);
            
            if(file != null)
                RUNTIME_OBJECT.getImagePanel().saveImage(file );
        }
    };

    myzButton    m_addPhotoButton          = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            FileChooser      fileChooser      = new FileChooser(); 
            File             file             = fileChooser.showOpenDialog(m_primaryStage);
            ImageEditorStage imageEditorStage = null ;
            try 
            {
                //Modified by montazar
                if(file != null)
                    imageEditorStage = new ImageEditorStage(file , RUNTIME_OBJECT.getImagePanel());            
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }     
        }
    }; 

    myzButton    m_printResultButton       = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            //add by montazar 
            Image selectedImage = RUNTIME_OBJECT.getImagePanel().getSelectedImage();
            TakweemReport.callFrame(m_primaryStage , selectedImage);

        }
    };
    myzButton    m_rulerButton         = new myzButton()
    {
      @Override
      public void buttonPressed()
      { 
        TAKWEEM_SCENE.setCursor(Cursor.CROSSHAIR);
        RUNTIME_OBJECT.getRuler().setUnitType(MYZRuler.CM); //TODO
        
        Point startPoint = RUNTIME_OBJECT.getRuler().getStartPoint();
        Point endPoint   = RUNTIME_OBJECT.getRuler().getEndPoint();
        //Remove the previouse line and size 
        if(startPoint != null)
            startPoint.erase(RUNTIME_OBJECT.getImagePanel().getBlankImageView());
        if(endPoint != null)
            endPoint.erase(RUNTIME_OBJECT.getImagePanel().getBlankImageView());
        if(startPoint != null && endPoint != null)
        {
            Line line = new Line(startPoint, endPoint);
            line.erase(RUNTIME_OBJECT.getImagePanel().getBlankImageView());
        }
        RUNTIME_OBJECT.getRuler().setStartPoint(null);
        RUNTIME_OBJECT.getRuler().setEndPoint(null);      
        RUNTIME_OBJECT.getImagePanel().setCurrentPaintMode(PAINT_MODE_RULER);

      }
    };
    myzButton CompareTwoAnalysisButton = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            
            Stage stage = new Stage();
            Scene scene = new Scene( new TakweemComparative(stage), 900, 700);
            stage.setTitle("Takweem Comparative");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent widowEvent) 
                {    
                    
                }
            });   
        }
    };
    //Modified by montazar 
    //this combobox should be static to set selection item when load MYZ file on drag and drop event at imagePanel class
    public static myzComboBox  m_anatomyCombo = new myzComboBox()
    {
        @Override
        public void selectionChange()
        {
            if(getItemValue() == null){return;}

            String analysisName = getItemValue().getValue();                                    
            //On change analysis we should remove the previouse analysis
            if(RUNTIME_OBJECT.getRunTimeAnalysis() != null)
            {
                RUNTIME_OBJECT.erasePreviouseAnalysis();
            }
            //Init the choosen analysis
            RUNTIME_OBJECT.setRunTimeAnalysisByName(analysisName);
            m_calculateTable.setTableData(RUNTIME_OBJECT.getRunTimeAnalysis().getVOperation());
            
            //if the vMYZPoint is empty that's mean all the points have been sets 
            if(RUNTIME_OBJECT.getRunTimePointsPool().getVMYZPoint().isEmpty())
            {
                if(RUNTIME_OBJECT.getImagePanel().getBlankImageView() != null)
                    RUNTIME_OBJECT.calculateOperationsAndShow();
            } 
        }
    };
    myzLabel     m_anatomyLabel          = new myzLabel();

    myzButton    m_modifyAnatomy           = new myzButton();
    myzButton    m_deleteAnatomy           = new myzButton();

    //Center Component 
    Label        m_fixedFooterLabel        = new Label();
    
    //Left Component
    VBox         m_leftSidebar            = new VBox(10);
    VBox         m_rightSidebar           = new VBox(10);
    Pane         m_pointsTablePan         = new Pane();
    
    public static PointsTable      m_pointsTable = null ;
    public static ImagePanelHelper m_helperImage = new ImagePanelHelper();
    
    myzButton    m_undoButton            = new myzButton()//TODO PointsTable hilighte the current row 
    {
        @Override
        public void buttonPressed()
        {
            /*
            * 1.undo in runtime object will remove the last point's value and remove it from image  
            * 2.return to the previous row in table points to navigate on this point (its current point you have to put it)
            */
            RUNTIME_OBJECT.Undo();
        }
    };
    
    // Bottom Component
    VBox         m_footer                  = new VBox();
    Pane         m_TablePan                = new Pane();
    public static AnalysisResultTable m_calculateTable ;

    
    @Override
    public void start(Stage primaryStage)
    {
        m_primaryStage = primaryStage;
        //we can not write init() method cuz there is some class have and it is think we override it
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
        initRightSidebar();
        initBottom();
        
        RUNTIME_OBJECT.getImagePanel().setParentPane(m_container);//it's used when loading file and replace the image panel with onther one
        
        TAKWEEM_SCENE = new myzScene(m_container, 900, 700);
        
        //Modified by montazar 
        m_container.setTop(m_header);
        m_container.setLeft(m_leftSidebar);
        m_container.setRight(m_rightSidebar);
        m_container.setBottom(m_footer);
        m_container.setCenter(RUNTIME_OBJECT.getImagePanel() ); 
        
        m_primaryStage.setTitle("Takweem");
        m_primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/programIcon.png")));
        m_primaryStage.setScene(TAKWEEM_SCENE);

        m_primaryStage.show();
        m_primaryStage.setMaximized(true);
        
    }
    
    public void initHeader()
    {   // Header-------------------------------------------------------------------------------------------------------------
        initAnalysisComponents();
        CheckMenuItem  eng  = new  CheckMenuItem ("English");
        eng.setSelected(true);// set the default language to english
        CheckMenuItem  arab = new CheckMenuItem ("\u0627\u0644\u0639\u0631\u0628\u064a\u0629");
        CheckMenuItem  fran = new CheckMenuItem ("French");
        EventHandler<ActionEvent> laneEvent = new EventHandler<ActionEvent>()
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
                ResourceBundle old  = BUNDLE;
                if ( eventItem.getText().equals("English"))
                    BUNDLE = ENGLISH_BUNDLE;
                if ( eventItem.getText().equals("\u0627\u0644\u0639\u0631\u0628\u064a\u0629"))
                    BUNDLE = ARABIC_BUNDLE;
                if ( eventItem.getText().equals("French"))
                    BUNDLE = FRENCH_BUNDLE;
                refreshComponent(old);
            } 
        };
        eng.setOnAction(laneEvent);
        arab.setOnAction(laneEvent);
        fran.setOnAction(laneEvent);

        m_langMenu.getItems().addAll(eng , arab , fran);
        
        EventHandler<ActionEvent> pointsMenuEvent = new EventHandler<ActionEvent>()
        { 
            @Override
            public void handle(ActionEvent e) 
            { 
                PointsSettingsFrame.callFrame(m_primaryStage , RUNTIME_OBJECT );
            } 
        };
        
        m_pointMenu.setOnAction(pointsMenuEvent);
        
        EventHandler<ActionEvent> analysisMenuEvent = new EventHandler<ActionEvent>()
        { 
            @Override
            public void handle(ActionEvent e) 
            { 
                AnalysisSettingsFrame.callFrame(m_primaryStage , RUNTIME_OBJECT );
            } 
        };
        
        m_analysisMenu.setOnAction(analysisMenuEvent);
        
        m_sittingsMenu.getItems().addAll(m_langMenu , m_pointMenu, m_analysisMenu);
        
        m_sittingsBar.getMenus().addAll(m_sittingsMenu );

        m_saveAnalysisButton.setCaption("save.analysis");
        m_saveAnalysisButton.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/save.png") ) ) );
        m_saveAnalysisButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_saveAnalysisButton.setMaxHeight(25);
        m_saveAnalysisButton.setParentPane(m_headerPane);
        m_saveAnalysisButton.setReSizeOnParentSize(true);
        
        m_saveImageButton.setCaption("save.image");
        m_saveImageButton.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/save.png") ) ) );
        m_saveImageButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_saveImageButton.setMaxHeight(25);
        m_saveImageButton.setParentPane(m_headerPane);
        m_saveImageButton.setReSizeOnParentSize(true);
        
        m_addPhotoButton.setCaption("button.add.photo");
        m_addPhotoButton.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/addphoto.png") ) ) );
        m_addPhotoButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_addPhotoButton.setMaxHeight(25);
        m_addPhotoButton.setParentPane(m_headerPane);
        m_addPhotoButton.setReSizeOnParentSize(true);

        m_printResultButton.setCaption("button.prin.result");
        m_printResultButton.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/print.png") ) ) );
        m_printResultButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_printResultButton.setParentPane(m_headerPane);
        m_printResultButton.setReSizeOnParentSize(true);
        
        m_anatomyLabel.setCaption("anatomy.choose.label");
        m_anatomyLabel.setParentPane(m_headerPane);
        m_anatomyLabel.setReSizeOnParentSize(true);
        m_anatomyLabel.setFont(new Font(14));
        
        m_anatomyCombo.setMinSize(200, 25);
        m_anatomyCombo.setParentPane(m_headerPane);
        m_anatomyCombo.setReSizeOnParentSize(true);
        m_anatomyCombo.setPromptText("Please select anatomy");
 
        m_categoryMenuButton.setMinWidth(100);
        m_categoryMenuButton.setCaption("category");
        m_categoryMenuButton.setParentPane(m_headerPane);
        m_categoryMenuButton.setReSizeOnParentSize(true);
        
        m_rulerButton.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/ruler.png") ) ) );
        m_rulerButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_rulerButton.setParentPane(m_headerPane);
        m_rulerButton.setReSizeOnParentSize(true);
        
        CompareTwoAnalysisButton.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/compare.jpg") ) ) );
        CompareTwoAnalysisButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        CompareTwoAnalysisButton.setParentPane(m_headerPane);
        CompareTwoAnalysisButton.setReSizeOnParentSize(true);
        
        m_headerPane.setAlignment(Pos.CENTER);
        m_headerPane.getChildren().addAll(m_categoryMenuButton , m_anatomyLabel 
                                          , m_anatomyCombo       , m_addPhotoButton  
                                          , m_saveAnalysisButton , m_saveImageButton   
                                          , m_printResultButton , m_rulerButton , CompareTwoAnalysisButton);

        m_header.getChildren().addAll( m_sittingsBar , m_headerPane );
    }
    
    public void initLeftSidebar()
    {
        m_pointsTable    = new PointsTable(m_primaryStage);
        String cssLayout = "-fx-border-color: black;\n" +
                           "-fx-border-width: 2;\n" ;
        
        m_magnifier.startRunning();//start magnifier thread
        
        m_pointsTablePan.getChildren().add(m_pointsTable);
        m_pointsTable.setParentPane(m_pointsTablePan);

        m_undoButton.setStyle("-fx-border-color: #00b7ff; -fx-border-width: 1px;-fx-background-color:#ffffff;");
        m_undoButton.setParentPane(m_leftSidebar);
        m_undoButton.setReSizeOnParentSize(true);
        m_undoButton.setMaxSize(75,40);
        m_undoButton.setCaption("analysis.undo");
        
        m_helperImage.setStyle("-fx-border-color:black;");
        
        m_leftSidebar.getChildren().addAll( m_pointsTablePan , m_undoButton , m_helperImage);
        m_leftSidebar.setAlignment(Pos.TOP_CENTER);
        m_leftSidebar.setStyle(cssLayout);
    }

    public void initRightSidebar()
    {
        String cssLayout = "-fx-border-color: black;\n" +
                           "-fx-border-width: 2;\n" ;
                
        m_rightSidebar.getChildren().addAll( m_magnifier );
        m_rightSidebar.setAlignment(Pos.TOP_RIGHT);
        m_rightSidebar.setStyle(cssLayout);
    }
    
    
    public void initBottom()
    {       
        m_fixedFooterLabel.setText(getCaption("window.footer.title"));   
        m_fixedFooterLabel.setFont(new Font(15));
        m_calculateTable = new AnalysisResultTable(m_primaryStage);
        
        m_calculateTable.setMinHeight(150);
        m_calculateTable.setCenterShape(true);
        m_calculateTable.setParentPane(m_TablePan);
        m_calculateTable.setReSizeOnParentSize(true);
        m_TablePan.getChildren().add(m_calculateTable);
        
        m_footer.getChildren().addAll(m_TablePan , m_fixedFooterLabel);
    }
    
    public void initAnalysisComponents()
    {
        Vector<XmlCategory> xmlCategory                     = RUNTIME_OBJECT.getRunTimeTakweem().getVCategory();
        EventHandler<ActionEvent> chooseClassificationEvent = new EventHandler<ActionEvent>()
        {
            //TODO remove the check box when chose other classification 
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
                
                int    index              = 0 ;
                String categoryName       = eventItem.getParentMenu().getText();
                String classificationName = eventItem.getText();
                
                RUNTIME_OBJECT.setRunTimeCategoryByName(categoryName);
                RUNTIME_OBJECT.setRunTimeClassifiactionByName(classificationName);
                m_anatomyCombo.deleteAllItems();
                
                for(XmlAnalysis analysis : RUNTIME_OBJECT.getRunTimeClassification().getVAnalysis())
                {
                    m_anatomyCombo.addItems(new myzComboBoxItem(analysis.getName() , index) ) ;
                    index++;
                }
                
                //Get points vector from points pool and set it to point table data
                RUNTIME_OBJECT.initRunTimePointsPool();
                m_pointsTable.setTableData(RUNTIME_OBJECT.getRunTimePointsPool().getVMYZPoint() ) ;
                //Load helper Image
                String imageHelperPath = RUNTIME_OBJECT.getRunTimePointsPool().getHelperImagePath();
                if(imageHelperPath != null && !"".equals(imageHelperPath))
                {
                    //Show first helper point on helper image 
                    MYZPoint myzPoint = RUNTIME_OBJECT.getRunTimePointsPool().getVMYZPoint().get(0);
                    m_helperImage.setImageView(imageHelperPath);
                    Takweem.m_helperImage.paintHelperPoint(myzPoint.getHelperX() , myzPoint.getHelperY());
                }
            } 
        };
        
        for(XmlCategory category :xmlCategory)
        {
            Menu categoryMenu = new Menu(category.getName());
            //temp should fetch the path from xml 
            categoryMenu.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/" + category.getName() + ".jpg") ) ));
            
            for(XmlClassification classification :category.gteVClassification())
            {
                CheckMenuItem classificationItem = new CheckMenuItem(classification.getName());
                categoryMenu.getItems().add(classificationItem);
                classificationItem.setOnAction(chooseClassificationEvent);
                classificationItem.setGraphic(new ImageView(new Image (getClass().getResourceAsStream("/icon/classification.png") ) ) );
            }
            
            m_categoryMenuButton.getItems().add(categoryMenu);
            
        }
    }
    
    public void refreshComponent(ResourceBundle oldBundle)
    {
        refreshCaption();
        refreshAlignment(oldBundle);
    }
    
    public void refreshAlignment(ResourceBundle oldBundle)
    {
        if ((oldBundle == ENGLISH_BUNDLE || oldBundle == FRENCH_BUNDLE) && BUNDLE == ARABIC_BUNDLE)
        {
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(m_headerPane.getChildren());
            Collections.reverse(workingCollection);
            m_headerPane.getChildren().setAll(workingCollection);
            m_headerPane.setAlignment(Pos.CENTER_RIGHT);
            return;
        }
        if ((oldBundle == ENGLISH_BUNDLE || oldBundle == FRENCH_BUNDLE) && (BUNDLE == ENGLISH_BUNDLE || BUNDLE == FRENCH_BUNDLE) )
        {
            return;
        }
        if (oldBundle == ARABIC_BUNDLE && (BUNDLE == ENGLISH_BUNDLE || BUNDLE == FRENCH_BUNDLE))
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
        m_pointMenu.setText(getCaption("points"));
        m_analysisMenu.setText(getCaption("analysis"));
        //footer
        m_fixedFooterLabel.setText(getCaption("window.footer.title"));
    }
    
    public static String getCaption(String key)
    {
        String str = "";
        try 
        {
            str = BUNDLE.getString(key);
        }
        catch(Exception ex)
        {
            System.out.println("Bundle can not find key " + key);
            ex.printStackTrace();
                                    
        }
        finally
        {
            if ( str == null || str.length() < 1)
                str = key;
        }
        return str;
      
    }
    
    
    public void closeMe(Stage primaryStage , Event windoEvent )
    {
        boolean answer = myzMessage.confirmMessage(BUNDLE.getString("exit.confirmation") , BUNDLE);
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
