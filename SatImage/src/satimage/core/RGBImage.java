/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satimage.core;

import satimage.Tools;
import java.awt.image.BufferedImage;

/**
 *
 * @author Red
 */
public class RGBImage {
    
    private int[][] r;
    public int getR(int x, int y) { return r[x][y]; }
    
    private int[][] g;
    public int getG(int x, int y) { return g[x][y]; }
    
    private int[][] b;
    public int getB(int x, int y) { return b[x][y]; }
    
    private int width;
    private int height;
    
    public RGBImage()
    {
        r = null;
        g = null;
        b = null;
        width  = 0;
        height = 0;
    }
    
    public RGBImage(int w, int h)
    {
        width  = w;
        height = h;
        r = new int[width][height];
        g = new int[width][height];
        b = new int[width][height];
        init();
    }
    
    /**
     * complétion par des zeros le tableau donné
     * @param a le tableau à remplir
     */
    private void init()
    {
        for(int j=0 ; j<height ; j++)
        {
            for(int i=0 ; i<width ; i++)
            {
                this.setWhiteAt(i, j);
            }
        }
    }
    
    /**
     * Permet d'appliquer une couleur par le code palette(r,g,b) à un pixel donné
     * @param x la ligne du pixel
     * @param y la colonne du pixel
     * @param r la valeur pour la couleur rouge
     * @param g la valeur pour la couleur verte
     * @param b la valeur pour la couleur bleu
     */
    public void setColor(int x, int y, int r, int g, int b)
    {
        this.r[x][y] = r;
        this.g[x][y] = g; 
        this.b[x][y] = b;
    }
    
    /**
     * Permet d'appliquer la couleur rouge à un pixel donné
     * @param x
     * @param y 
     */
    public void setRedAt(int x, int y)
    {
        setColor(x,y, 255,0,0);
    }
    
    /**
     * Permet d'appliquer la couleur bleu à un pixel donné
     * @param x
     * @param y 
     */
    public void setBlueAt(int x, int y)
    {
        setColor(x,y, 0,0,255);
    }
    
    /**
     * Permet d'appliquer la couleur verte à un pixel donné
     * @param x
     * @param y 
     */
    public void setGreenAt(int x, int y)
    {
        setColor(x,y, 0,255,0);
    }
    
    /**
     * Permet d'appliquer la couleur noire à un pixel donné
     * @param x
     * @param y 
     */
    public void setBlackAt(int x, int y)
    {
        setColor(x,y, 0,0,0);
    }
    
    /**
     * Permet d'appliquer la couleur blanche à un pixel donné
     * @param x
     * @param y 
     */
    public void setWhiteAt(int x, int y)
    {
        setColor(x,y, 255,255,255);
    }
    
    //--------------------
    //CODE AUTO
    //--------------------

    public int[][] getR() {
        return r;
    }

    public int[][] getG() {
        return g;
    }

    public int[][] getB() {
        return b;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getBufferedImage()
    {
        Tools tools = Tools.get();
        BufferedImage img = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        for(int y=0 ; y<height ; y++)
        {
            for(int x=0 ; x<width ; x++)
            {
                img.setRGB(x, y, tools.getPixelValue(r[x][y], g[x][y], b[x][y]));
            }
        }
        return img;
    }
    
    /**
     * Permet d'obtenir une image d'après les 3 composantes
     * @param pWidth  la largeur d'un pixel
     * @param pHeight la hauteur d'un pixel
     * @return 
     */
    public BufferedImage getBufferedImage(int pWidth, int pHeight)
    {
        Tools tools = Tools.get();
        int tWidth  = width*pWidth;
        int tHeight = height*pHeight;
        BufferedImage img = new BufferedImage(tWidth,tHeight, BufferedImage.TYPE_INT_RGB);
        for(int y=0 ; y<tHeight ; y++)
        {
            for(int x=0 ; x<tWidth ; x++)
            {
                int i = x/pWidth;
                int j = y/pHeight;
                img.setRGB(x, y, tools.getPixelValue(r[i][j], g[i][j], b[i][j]));
            }
        }
        return img;
    }
    
}
