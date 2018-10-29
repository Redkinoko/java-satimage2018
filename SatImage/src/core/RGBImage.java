/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Red
 */
public class RGBImage extends BufferedImage {
    
    private Dimension pixelDim;
    public Dimension getPixelDim() { return pixelDim; }
    
    public RGBImage(int w, int h, Dimension pDim)
    {
        super(w*pDim.width,h*pDim.height, BufferedImage.TYPE_INT_RGB);
        pixelDim = new Dimension(pDim.width, pDim.height);
    }
    
    public RGBImage(int w, int h, int pW, int pH)
    {
        super(w*pW,h*pH, BufferedImage.TYPE_INT_RGB);
        pixelDim = new Dimension(pW,pH);
    }
    
    public RGBImage(int w, int h)
    {
        super(w,h, BufferedImage.TYPE_INT_RGB);
        pixelDim = new Dimension(1,1);
    }
    
    public RGBImage clone()
    {
        RGBImage tmp = new RGBImage(getWidth(), getHeight());
        tmp.pixelDim.width  = pixelDim.width;
        tmp.pixelDim.height = pixelDim.height;
        
        for(int j=0 ; j<getHeight() ; j++)
        {
            for(int i=0 ; i<getWidth() ; i++)
            {
                tmp.setRGB(i,j, getRGB(i, j));
            }
        }
        return tmp;
    }
    
    public void draw(Color color, int x, int y)
    {
        for(int j=0 ; j<pixelDim.height ; j++)
        {
            for(int i=0 ; i<pixelDim.width ; i++)
            {
                int x_ = (x*pixelDim.width)+i;
                int y_ = (y*pixelDim.height)+j;
                setRGB(x_, y_, color.getRGB());
            }
        }
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
