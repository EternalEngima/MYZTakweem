package myzReport;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.myz.calculable.MYZOperation;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myzComponent.myzButton;
import myzComponent.myzLabel;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * @author yazan
 */
public class TakweemReport 
{
    //Frame Member
    private        Stage   m_primaryStage; 
    private static Stage   m_window;
    private static Scene   m_scene;
    
    VBox         m_vBox              = new VBox(20);
  
    myzLabel     m_lpatientName      = new myzLabel();
    TextField    m_patientName       = new TextField();
    myzButton    m_choosePathButton  = new myzButton()
    {
        @Override
        public void buttonPressed()
        {
            FileChooser  fileChooser  = new FileChooser(); 
            fileChooser.setInitialFileName(m_patientName.getText());
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pdf Files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File         file         = fileChooser.showSaveDialog(m_primaryStage);
            try 
            {
                if (file != null)
                {
                    m_filePath.setText(file.getAbsolutePath());
                }
            }
            catch ( Exception ex)
            {
                ex.printStackTrace();
            }
        }
    };
    TextField    m_filePath          = new TextField();
    myzButton    m_okButton          = new myzButton("save")
    {
        @Override
        public void buttonPressed()
        {
            printReport();
            m_window.close();

        }
    };
    myzButton    m_cancelButton      = new myzButton("cancel")
    {
        @Override
        public void buttonPressed()
        {
            m_window.close();
        }
    };
    
    //Report Member
    Document    m_pdfDocument;
    PdfPTable   m_data;
    Image       m_image;

    //Constructer
    TakweemReport( Stage primaryStage , Image image )
    {
        m_primaryStage = primaryStage;
        m_image        = image;
        initFrame();
    }

 
    //Methods
    private void initFrame()
    {
        m_window = new Stage();
        m_window.initStyle(StageStyle.DECORATED);
        m_window.initModality(Modality.APPLICATION_MODAL);
        m_window.setResizable(false);
          
        HBox hBox = new HBox(5);
        m_lpatientName.setCaption("patient.name");
        m_patientName.setMinSize(100 , 25);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(m_lpatientName , m_patientName);
        m_vBox.getChildren().add(hBox);

        myzLabel     label    = new myzLabel("report.label.save");
        m_vBox.getChildren().add(label);
        
        hBox = new HBox(5);
        m_filePath.setMinSize(350, 25);
        m_choosePathButton.setText("...");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(m_filePath , m_choosePathButton);
        m_vBox.getChildren().add(hBox);

        
        hBox = new HBox(50);
        m_okButton.setMinSize(60, 25);
        m_cancelButton.setMinSize(60, 25);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(m_okButton , m_cancelButton);
        m_vBox.getChildren().add(hBox);
        m_vBox.setAlignment(Pos.CENTER);
        m_scene = new Scene(m_vBox , 400, 170 );
        m_window.setScene(m_scene);
        m_window.getIcons().add(new Image("icon\\save.png"));
        m_window.showAndWait();

    }
    
    public static void  callFrame(Stage primaryStage , Image image)
    {
        TakweemReport stage = new TakweemReport(primaryStage , image);
    }
    
    
    public void printReport()
    {
        try
        {
            m_pdfDocument  = new Document( PageSize.A4,10f,10f,20f,10f );
            FileOutputStream stream = new FileOutputStream(getFileFromPath());
            PdfWriter.getInstance(m_pdfDocument, stream);
            m_pdfDocument.open();
            addPatientName();  
            if (m_image != null)
            {
                addImage();
            } 
            
            ObservableList list  = takweem.Takweem.m_calculateTable.getItems();
            if ( list.size() > 0 )
            {
                m_data = new PdfPTable(takweem.Takweem.m_calculateTable.getColumns().size());
                m_data.setWidthPercentage(100);
                float [] width = new float[3];
                width[0] = 6;
                width[1] = 3;
                width[2] = 3;
                m_data.setWidths(width);
                PdfPCell cell = null;
                for ( int i = 0 ; i < list.size() ; i ++)
                {
                    if ( i == 0 )// to add Header
                    {
                        cell = new PdfPCell();
                        cell.setPhrase(new Phrase("Operation Name"));
                        cell.setRunDirection(getRunDirection());
                        cell.setBorder(PdfPCell.BOX);
                        cell.setBorderWidth(.1f);
                        cell.setPadding(3f);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                        cell.setBackgroundColor(Color.GRAY);
                        m_data.addCell(cell);

                        cell = new PdfPCell();
                        cell.setPhrase(new Phrase("Value"));
                        cell.setRunDirection(getRunDirection());
                        cell.setBorder(PdfPCell.BOX);
                        cell.setBorderWidth(.1f);
                        cell.setPadding(3f);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                        cell.setBackgroundColor(Color.GRAY);
                        m_data.addCell(cell);

                        cell = new PdfPCell();
                        cell.setPhrase(new Phrase("Correct Value"));
                        cell.setRunDirection(getRunDirection());
                        cell.setBorder(PdfPCell.BOX);
                        cell.setBorderWidth(.1f);
                        cell.setPadding(3f);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                        cell.setBackgroundColor(Color.GRAY);
                        m_data.addCell(cell);
                    }
                    MYZOperation operation = (MYZOperation) list.get(i);
                    
                    cell = new PdfPCell();
                    cell.setPhrase(new Phrase(operation.getM_name()));
                    cell.setRunDirection(getRunDirection());
                    cell.setBorder(PdfPCell.BOX);
                    cell.setBorderWidth(.1f);
                    cell.setPadding(3f);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(Color.white);
                    m_data.addCell(cell);
                    
                    cell = new PdfPCell();
                    cell.setPhrase(new Phrase(operation.getM_value()));
                    cell.setRunDirection(getRunDirection());
                    cell.setBorder(PdfPCell.BOX);
                    cell.setBorderWidth(.1f);
                    cell.setPadding(3f);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(Color.white);
                    m_data.addCell(cell);
                    
                    cell = new PdfPCell();
                    cell.setPhrase(new Phrase(operation.getM_correctValue()));
                    cell.setRunDirection(getRunDirection());
                    cell.setBorder(PdfPCell.BOX);
                    cell.setBorderWidth(.1f);
                    cell.setPadding(3f);
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(Color.white);
                    m_data.addCell(cell);
                }
//                m_data.setWidths({5,5,5});
            }
            
            m_pdfDocument.add(m_data);
            
            m_pdfDocument.close();
        }
        catch(Exception e)
        {
            
        }
    }
    
    public void addPatientName()
    {
        //print Patient information
        PdfPTable InfoTable = new PdfPTable(4);
        InfoTable.setTotalWidth(1);
        InfoTable.setWidthPercentage(100);
        InfoTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(takweem.Takweem.BUNDLE.getString("patient.name")));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
        cell.setVerticalAlignment( PdfPCell.ALIGN_RIGHT  );
        InfoTable.addCell(cell);
        
        cell = new PdfPCell();
        cell.setPhrase(new Phrase(m_patientName.getText()));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
        cell.setVerticalAlignment( PdfPCell.ALIGN_LEFT  );
        InfoTable.addCell(cell);    

        // to print the print Date
        cell = new PdfPCell();
        cell.setPhrase(new Phrase(takweem.Takweem.BUNDLE.getString("date")));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
        cell.setVerticalAlignment( PdfPCell.ALIGN_RIGHT  );
        InfoTable.addCell(cell);
        
        Date       date     = new Date(System.currentTimeMillis());
        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        cell                = new PdfPCell();
        cell.setPhrase(new Phrase(formater.format(date)));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment( PdfPCell.ALIGN_CENTER );
        cell.setVerticalAlignment( PdfPCell.ALIGN_CENTER  );
        InfoTable.addCell(cell);
         
        try 
        {
            m_pdfDocument.add(InfoTable);
        } catch (DocumentException ex) {
            Logger.getLogger(TakweemReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addImage()
    {
        PdfPTable imageTable = new PdfPTable(1);
        imageTable.setTotalWidth(1);
        imageTable.setWidthPercentage(90);
        imageTable.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        BufferedImage bImage = SwingFXUtils.fromFXImage(m_image, null); 
        try
        {
            imageTable.addCell(  com.lowagie.text.Image.getInstance(bImage.getScaledInstance(200, 200, 16), Color.yellow));
            m_pdfDocument.add(imageTable);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
   
    
    public File getFileFromPath()
    {
        String pathToSave = m_filePath.getText();
        File   file;
        if ( pathToSave == null)
        {
            return null;
        }
        
        file       = new File(pathToSave);
        if (!file.exists())
        {
            try 
            {
                file.createNewFile();
            }
            catch (IOException ex) 
            {
            Logger.getLogger(TakweemReport.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        return file;
 
    }
    

    private int getRunDirection()
    {
        if ( takweem.Takweem.BUNDLE == takweem.Takweem.ARABIC_BUNDLE)
            return PdfWriter.RUN_DIRECTION_RTL;
        else 
            return PdfWriter.RUN_DIRECTION_LTR;

    }    
    

}
