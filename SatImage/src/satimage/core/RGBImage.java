/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satimage.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author Red
 */
public class RGBImage extends BufferedImage {
    
    private Dimension pixelSize;
    private Hashtable<Point, Color> values;
    
    public RGBImage(int w, int h)
    {
        super(w,h, BufferedImage.TYPE_INT_RGB);
        pixelSize = new Dimension(1,1);
        values = new Hashtable<Point, Color>();
    }
    
    public RGBImage(int w, int h, int pW, int pH)
    {
        super(w*pW,h*pH, BufferedImage.TYPE_INT_RGB);
        pixelSize = new Dimension(pW,pH);
        values = new Hashtable<Point, Color>();
    }
    
    public void addColor(int x, int y, Color color)
    {
        values.put(new Point(x,y), color);
    }
    
    public void addRedAt(int x, int y)
    {
        addColor(x, y, Color.RED);
    }
    
    public void addBlueAt(int x, int y)
    {
        addColor(x, y, Color.BLUE);
    }
    
    public void addWhiteAt(int x, int y)
    {
        addColor(x, y, Color.WHITE);
    }
    
    public void addBlackAt(int x, int y)
    {
        addColor(x, y, Color.BLACK);
    }
    
    
    public void build()
    {
        if ((pixelSize.width>0) && (pixelSize.height>0))
        {
            //COLORATION DES RECTANGLES
            //Pour chaque clause on dessine chaque rectange de pixels
            Set<Point> keys = values.keySet();
            for(Point key : keys)
            {
                drawRectangle(key.x*pixelSize.width, key.y*pixelSize.height, pixelSize.width, pixelSize.height, values.get(key));
            }
        }
    }
    
    private void drawRectangle(int x, int y, int w, int h, Color color)
    {
        for(int j=0 ; j<h ; j++)
        {
            for(int i=0 ; i<w ; i++)
            {
                int x_ = x+i;
                int y_ = y+j;
                setRGB(x_, y_, color.getRGB());
            }
        }
    }
    
    /**
     * Permet d'obtenir une image avec les pixels zoomés
     * @param pWidth  la largeur d'un pixel
     * @param pHeight la hauteur d'un pixel
     * @return 
     */
    public RGBImage getImageZoomed(int pWidth, int pHeight)
    {
        RGBImage img = new RGBImage(getWidth()*pWidth, getHeight()*pHeight, pWidth, pHeight);
        img.values = new Hashtable(values);
        img.build();
        return img;
    }
    
    /**
     * Permet de sauvegarder une image en .png
     * @param root l'endroit où la sauver
     * @param name le nom de l'image
     */
    public void writeImage(String root, String name)
    {
        try
        {
            File file = new File(root + name + ".png");
            ImageIO.write(this, "png", file);
        } 
        catch (IOException e) 
        {
            System.out.println("Erreur : " + e );
        }
    }
}
