package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import core.CNFDocument;
import core.RGBImage;
import core.Shuffler;
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
    
    public static void main(String[] args) 
    {
        //--------------------------
        String pathIMG = getAbsolutePath() + "/src/data/img/";
        String pathCNF = getAbsolutePath() + "/src/data/cnf/";
        //--------------------------
        CNFDocument test = new CNFDocument(pathCNF, "test");
        CNFDocument test2 = new CNFDocument(pathCNF, "test2");
        CNFDocument f0   = new CNFDocument(pathCNF, "f0-rand+easy");
        CNFDocument f1   = new CNFDocument(pathCNF, "f1-rand");
        CNFDocument f2   = new CNFDocument(pathCNF, "f2-peebOr+rand");
        CNFDocument f3   = new CNFDocument(pathCNF, "f3-peebXor_rand");
        //---------------------------
        CNFDocument doc = f1;
        doc.load();
        //doc.print();
        doc.setPixelDimension(32, 32);
        Shuffler sh = new Shuffler(doc);
        //---------------------------
        Frame frame = new Frame(doc, sh);
        run(frame);
        //debug(doc, sh);
    }
    
    public static void debug(CNFDocument doc, Shuffler sh)
    {
        sh.print();
        /*
        sh.sort();sh.print();
        
        sh.sortByNeighbour();sh.print();
        
        sh.sort();sh.print();*/
        //System.out.println("Difficulté : "+sh.countNeighbour());
    }
    
    public static void run(Frame frame)
    {
        frame.setSize(200,400);
        frame.setVisible(true);
    }
    
    public static String getAbsolutePath()
    {
        return new File("").getAbsolutePath();
    }
}
