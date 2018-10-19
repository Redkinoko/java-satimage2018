/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satimage;

import java.awt.image.BufferedImage;
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
        Tools tools = Tools.get();
        DocumentReader documentReader = DocumentReader.get();
        //--------------------------
        //testCreationImage();
        //--------------------------
        String dataDir = tools.getAbsolutePath() + "\\src\\data\\";
        CNFDocument doc = new CNFDocument(dataDir, "test");
        documentReader.read(doc);
        doc.print();
        
        //---------------------------
        showImage(doc.getRGBImage());
    }
    
    public static RGBImage testRGBImage()
    {
        RGBImage img = new RGBImage(3, 3);
        img.setBlueAt(1, 1);
        img.setBlueAt(1, 1);
        return img;
    }
    
    public static void showImage(RGBImage img)
    {
        Frame frame = new Frame();
        BufferedImage bI = img.getBufferedImage(16,32);
        frame.add(new ImagePanel(bI, 10, 10));
        frame.setSize(bI.getWidth(), bI.getHeight());
        frame.setVisible(true);
    }
}
