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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        DocumentReader documentReader = DocumentReader.get();
        //--------------------------
        String dirImg = getAbsolutePath() + "\\src\\data\\img\\";
        String dirCnf = getAbsolutePath() + "\\src\\data\\cnf\\";
        //--------------------------
        CNFDocument doc = new CNFDocument(dirCnf, "test");
        documentReader.read(doc);
        doc.print();
        RGBImage img = doc.getRGBImage(16,32);
        //---------------------------
        showImage(img);
        //img.writeImage(dirImg, "TEST");
    }
    
    public static String getAbsolutePath()
    {
        return new File("").getAbsolutePath();
    }
    
    public static void showImage(BufferedImage img)
    {
        Frame frame = new Frame();
        frame.add(new ImagePanel(img, 10, 10));
        frame.setSize(img.getWidth(), img.getHeight()*2);
        frame.setVisible(true);
    }
}
