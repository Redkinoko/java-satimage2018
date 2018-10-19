/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satimage;

import java.awt.image.BufferedImage;
import java.io.File;
import satimage.core.*;
import view.Frame;
import view.ImagePanel;

/**
 *
 * @author Red
 */
public class Main {

    private static Frame frame = new Frame();
    public static void main(String[] args) 
    {
        DocumentReader documentReader = DocumentReader.get();
        //--------------------------
        String pathIMG = getAbsolutePath() + "\\src\\data\\img\\";
        String pathCNF = getAbsolutePath() + "\\src\\data\\cnf\\";
        //--------------------------
        CNFDocument doc = new CNFDocument(pathCNF, "test");
        documentReader.read(doc);
        doc.print();
        doc.drawImage(16, 32);
        //---------------------------
        showImage(doc.getImage().clone());

        doc.invertVariables(1, 2);
        showImage(doc.getImage().clone());
        
        doc.invertClauses(0, 1);
        showImage(doc.getImage().clone());
        
        doc.invertVariables(1, 2);
        showImage(doc.getImage().clone());
        //img.writeImage(dirImg, "TEST2");
    }
    
    public static String getAbsolutePath()
    {
        return new File("").getAbsolutePath();
    }
    
    public static void showImage(BufferedImage img)
    {
        frame.add(new ImagePanel(img, 10, 10));
        frame.setSize(img.getWidth(), img.getHeight()*2);
        frame.setVisible(true);
    }
}
