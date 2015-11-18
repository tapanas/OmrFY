import org.apache.pdfbox.exceptions.COSVisitorException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import java.awt.Font;


import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.List;
/**
 * Created by tapanas on 19/10/2015.
 */

//Where    i have added code
//     /*

//      */


public class Pdfclass {




    /*
    Different global variable created
     */

    JPanel pdfPanel = new JPanel();
    JLabel picLabel = new JLabel();
    PDPage page = new PDPage();
    PDDocument doc = new PDDocument();
    BufferedImage bi;
    Graphics2D g;
    int width, height;
    int x = 650;
    int y = 580;

    int x2 = 10;
    int y2 = 600;


    public void setPDFpanel(JPanel importPanel) {
        this.pdfPanel=importPanel;
    }
    public JPanel getPDFpanel() {
        return pdfPanel;
    }

    /*
       Constructor of the PDF class is created
     */





    public void createPDF() {

        String fileName = "EmptyPdf.pdf";


        try {
            //PDDocument doc = new PDDocument();
            doc.addPage(page);
            doc.save(fileName);
            //doc.close();
            System.out.println("your file created in : " + System.getProperty("user.dir"));

        } catch (IOException | COSVisitorException e) {
            System.out.println(e.getMessage());
        }

    }

    public void newPDF(String path) throws IOException {

        File PDF_Path = new File(path);
        PDDocument inputPDF = PDDocument.load(PDF_Path);
        doc = PDDocument.load(PDF_Path);
        List<PDPage> allPages = doc.getDocumentCatalog().getAllPages();
        page = allPages.get(0);
        bi = page.convertToImage();
        pdfPanel.add(picLabel);
        picLabel.setIcon(new ImageIcon(bi));
        g = bi.createGraphics();
        //doc.close();


    }


    public void addAnswers(String amountString){

        int amountInt = Integer.parseInt(amountString);
        g.setColor(Color.BLACK);

        for(int i = 1; i <= amountInt; i++ ){
            g.drawOval(x,y,20,20);
            g.drawImage(bi, null, 0, 0);
            x = x + 75;
        }

        picLabel.setIcon(new ImageIcon(bi));
        x = 650;
        y = y + 75;
    }


    public void addQuestion(String newquestion, int questionNo){

        String qNo = Integer.toString(questionNo);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD,20));


        g.drawString("Question" + qNo + ") "+ newquestion, x2,y2 );



        g.drawImage(bi, null, 0, 0);
        picLabel.setIcon(new ImageIcon(bi));
        y2 = y2 + 75;

    }


    public void openPDF() throws IOException {

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        String filename = file.getAbsolutePath();
        newPDF(filename);

    }

    public void savePDF() throws IOException, COSVisitorException {

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.PDF", new String[]{"pdf"});
        fileChooser.setFileFilter(filter);
        int chooseResult = fileChooser.showSaveDialog(null);

        if (chooseResult == JFileChooser.APPROVE_OPTION) {
            String file = fileChooser.getSelectedFile().getAbsolutePath();
            file = file + ".pdf";
            try {

                doc.save(file);
                doc.close();
                System.out.print(fileChooser.getSelectedFile().getName() + ".pdf is saved!");

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (COSVisitorException e2) {
                e2.printStackTrace();
            }
        }
    }


    public void zoom(int zoomLevel){

        int newImageWidth = bi.getWidth() *  zoomLevel/10;
        int newImageHeight = bi.getHeight() * zoomLevel/10;
        BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bi, 0, 0, newImageWidth , newImageHeight , null);
        g.dispose();
        picLabel.setIcon(new ImageIcon(resizedImage));


    }
}