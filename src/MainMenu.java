/**
 * Created by Khalid on 16-Oct-15.
 */
import org.apache.pdfbox.exceptions.COSVisitorException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;


import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.List;

//Where /*
//          */ i have added code ~Panayiotis

public class MainMenu {

    JFrame frame = new JFrame();
    JPanel pane = new JPanel();
    int questionNo = 1;
    int answerNo = 1;


    /*
    Create an object of the pdf class to help with code
     */
    Pdfclass pdfHelp = new Pdfclass();



    public MainMenu() {

        frame.setTitle("OMR Questionnaire Builder");
        pane.setLayout(new BorderLayout());
        frame.add(pane);


// ---------- JMenu Creation ----------
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);


        JMenuItem mntmNew = new JMenuItem("New");
        mntmNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                // Replace benchbook with default template
                try {                                                  // ACTION HANDLER CALLING newPDF() Function
                      /*
                        Calling Pdf class methods
                       */
                    pdfHelp.newPDF("Template_7.5.pdf");
                    frame.revalidate();
                } catch (IOException ex) {
                    System.out.print("Error !");
                }
            }
        });
        mnFile.add(mntmNew);

//--------------Adding JMenuItems-----------------//
        JMenuItem mntmOpen = new JMenuItem("Open");
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {                                                    // ACTION HANDLER CALLING openPDF() Function
                    pdfHelp.openPDF();                                          // no argument, function gets path from file chooser
                } catch (IOException ex) {
                    System.out.print("Error !");
                }
            }
        });
        mnFile.add(mntmOpen);

        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                     /*
                    Calling Pdf class methods
                    */
                    pdfHelp.savePDF();
                } catch (IOException ex) {
                    System.out.print("Error !");
                } catch (COSVisitorException ex) {
                    System.out.print("Error !");
                }
            }
        });
        mnFile.add(mntmSave);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mnFile.add(mntmExit);



        // ---------- Compound Border Creation ----------
        Border compound, raisedbevel, loweredbevel;
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();

// ---------- PDF PANEL and JLABEL to hold PDF Creation ----------

        /*
            Pdf Help though the getPDFpanel method helps us to retrieve the panel from Pdf class
         */
        pane.add(pdfHelp.getPDFpanel(), BorderLayout.CENTER);
        pdfHelp.getPDFpanel().setPreferredSize(new Dimension(1200 , 1700 ));
        pdfHelp.getPDFpanel().setAlignmentX(Component.CENTER_ALIGNMENT);
        pdfHelp.getPDFpanel().setAlignmentY(Component.CENTER_ALIGNMENT);
        compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        pdfHelp.getPDFpanel().setBorder(compound);


        JScrollPane scrollPane = new JScrollPane(pdfHelp.getPDFpanel());    // Creates the Scroll
        pane.add(scrollPane, BorderLayout.CENTER);

        // ---------- END Panel Creation ----------//

        // ------Create Bottom Panel--------------//
        JPanel endPanel = new JPanel();
        endPanel.setLayout(new FlowLayout());

        JButton addQuestion, addAnswers, newPage;

        addQuestion = new JButton("Add Question");
        addQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {



                    String question = JOptionPane.showInputDialog((Component) null, "Please enter question Number " + questionNo);
                    pdfHelp.addQuestion(question, questionNo);
                    questionNo++;
                    //frame.revalidate();


            }
        });

        endPanel.add(addQuestion);

        addAnswers = new JButton(" Add Answers");
        addAnswers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String amount = JOptionPane.showInputDialog((Component) null, "Please input the amount of answers for question " + answerNo );
                pdfHelp.addAnswers(amount);
                answerNo ++;
                //frame.revalidate();
            }
        });
        endPanel.add(addAnswers);


        newPage = new JButton("New Page");
        endPanel.add(newPage);

        pane.add(endPanel, BorderLayout.SOUTH);

        JTextField text = new JTextField(20);
        JSlider slider = new JSlider(0, 10,0);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                text.setText(String.valueOf(slider.getValue()));
                /*
                Calling Pdf class methods
                 */

                pdfHelp.zoom(slider.getValue());
                frame.repaint();

            }
        });

        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = text.getText();
                slider.setValue(0);
                if (!typed.matches("\\d+") || typed.length() > 3) {
                    return;
                }
                int value = Integer.parseInt(typed);
                slider.setValue(value);
            }
        });

        endPanel.add(slider);
        endPanel.add(text);

// ---------- Button Panel Creation ----------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300, 300));

        JButton button1, button2, button3;

        button1 = new JButton("button1");
        buttonPanel.add(button1);

        button2 = new JButton(" button2");
        buttonPanel.add(button2);

        button3 = new JButton("button3");
        buttonPanel.add(button3);

        button3 = new JButton("button4");
        buttonPanel.add(button3);

        pane.add(buttonPanel, BorderLayout.WEST);

// ---------- Tool Panel Creation ----------
        JPanel toolPanel = new JPanel();
        toolPanel.setPreferredSize(new Dimension(300, 300));

        JButton tool1, tool2, tool3;

        tool1 = new JButton("tool1");
        toolPanel.add(tool1);

        tool2 = new JButton(" tool2");
        toolPanel.add(tool2);

        tool3 = new JButton("tool3");
        toolPanel.add(tool3);

        pane.add(toolPanel, BorderLayout.EAST);


// ---------- Frame Plumbing ----------
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | frame.MAXIMIZED_BOTH);
        //frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {

       //mainmenu deleted
        

    }

}



