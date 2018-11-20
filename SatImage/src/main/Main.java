package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import core.CNFDocument;
import core.RGBImage;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.System.exit;
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
        //--------------------------
        String pathIMG = getAbsolutePath() + "/src/data/img/";
        String pathCNF = getAbsolutePath() + "/src/data/cnf/";
        //--------------------------
        CNFDocument test = new CNFDocument(pathCNF, "test");
        CNFDocument f0   = new CNFDocument(pathCNF, "f0-rand+easy");
        CNFDocument f1   = new CNFDocument(pathCNF, "f1-rand");
        CNFDocument f2   = new CNFDocument(pathCNF, "f2-peebOr+rand");
        CNFDocument f3   = new CNFDocument(pathCNF, "f3-peebXor_rand");
        //---------------------------
        CNFDocument doc = f1;
        doc.load();
        doc.print();
        doc.setPixelDimension(16, 32);
        //---------------------------
        showImage(doc);
        /*
        doc.invertVariables(1, 2);
        showImage(doc);
        
        doc.invertClauses(0, 1);
        showImage(doc);
        
        doc.invertVariables(1, 2);
        showImage(doc);
        */
        doc.shuffle();
        doc.print();
        showImage(doc);
        //img.writeImage(dirImg, "TEST2");
        //----------------------------
        frame.setVisible(true);
        //exit(0);
    }
    
    public static String getAbsolutePath()
    {
        return new File("").getAbsolutePath();
    }
    
    public static void showImage(CNFDocument doc)
    {
        if(doc != null)
        {
            RGBImage img = doc.getImage();
            if(img != null)
            {
                showImage(img.clone());
            }
        }
    }
    
    public static void showImage(BufferedImage img)
    {
        if(img != null)
        {
            frame.add(new ImagePanel(img, 10, 10));
            frame.setSize(img.getWidth(), img.getHeight()*2);
        }
    }
}
