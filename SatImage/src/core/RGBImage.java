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
    
    private Dimension base;
    private int[][] matrix;
    private Dimension pixelDim;
    public Dimension getPixelDim() { return pixelDim; }
    
    public RGBImage(int w, int h, Dimension pDim)
    {
        super(w*pDim.width, h*pDim.height, BufferedImage.TYPE_INT_RGB);
        matrix   = new int[w][h];
        base     = new Dimension(w,h);
        pixelDim = new Dimension(pDim.width, pDim.height);
    }
    
    public RGBImage(Dimension b, int pW, int pH)
    {
        super(((int)b.getWidth())*pW, ((int)b.getHeight())*pH, BufferedImage.TYPE_INT_RGB);
        int w = (int)b.getWidth();
        int h = (int)b.getHeight();
        matrix   = new int[w][h];
        base     = new Dimension(w,h);
        pixelDim = new Dimension(pW,pH);
    }
    
    public RGBImage clone()
    {
        return clone(base, this.pixelDim.width, this.pixelDim.height);
    }
    
    public RGBImage clone(int pW, int pH)
    {
        return clone(base, pW,pH);
    }
    
    public RGBImage clone(Dimension b, int pW, int pH)
    {
        RGBImage tmp = new RGBImage(b, pW, pH);
        for(int y=0 ; y<base.getHeight() ; y++)
        {
            for(int x=0 ; x<base.getWidth() ; x++)
            {
                for(int j=0 ; j<pH ; j++)
                {
                    for(int i=0 ; i<pW ; i++)
                    {
                        int x_ = (x*pW)+i;
                        int y_ = (y*pH)+j;
                        tmp.setRGB(x_, y_, matrix[x][y]);
                        tmp.matrix[x][y] = matrix[x][y];
                    }
                }
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
                matrix[x][y] = color.getRGB();
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
    
    public boolean equals(RGBImage img)
    {
        if(getWidth() != img.getWidth() || getHeight() != img.getHeight())
        {
            return false;
        }
        for(int i=0 ; i<getWidth() ; i++)
        {
            for(int j=0 ; j<getHeight() ; j++)
            {
                if(this.getRGB(i, j) != img.getRGB(i, j))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getRedChannel(int i, int j)
    {
        return (getRGB(i,j) >> 16) & 0xFF;
    }
    
    public int getGreenChannel(int i, int j)
    {
        return (getRGB(i,j) >> 8) & 0xFF;
    }
    
    public int getBlueChannel(int i, int j)
    {
        return (getRGB(i,j) >> 0) & 0xFF;
    }
    
    public RGBImage substract(RGBImage img)
    {
        RGBImage this_ = this.clone();
        for(int i=0 ; i<getWidth() ; i++)
        {
            for(int j=0 ; j<getHeight() ; j++)
            {
                int p = this_.getRGB(i, j);
                int r = (p >> 16) & 0xFF;
                int g = (p >> 8)  & 0xFF;
                int b = (p >> 0)  & 0xFF;
                
            }
        }
        return this_;
    }
}
