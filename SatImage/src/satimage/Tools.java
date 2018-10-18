/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satimage;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import satimage.core.RGBImage;

/**
 *
 * @author Red
 */
public class Tools {
    
    private Tools(){}
    private static Tools tools;
    public static Tools get() { if(tools == null){ tools = new Tools(); } return tools; }
    /**
     * Affiche le tableau donné
     * @param a le tableau
     * @param s la taille du tableau
     */
    public void printA(int[] a, int s)
    {
        System.out.print("|");
        for(int i=0 ; i<s ; i++)
        {
            System.out.print(a[i]+"|");
        }
        System.out.println();
    }
    
    /**
     * Affiche le tableau donné
     * @param a le tableau 2d
     * @param w la largeur du tableau
     * @param h la hauteur du tableau
     */
    public void printA(int[][] a, int w, int h)
    {
        for(int j=0 ; j<h ; j++)
        {
            System.out.print("|");
            for(int i=0 ; i<w ; i++)
            {
                System.out.print(a[i][j]+"|");
            }
            System.out.println();
        }
    }
    
    /**
     * Affiche les composentes d'une RGBImage
     * @param img l'image à afficher
     */
    public void printA(RGBImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        System.out.println("R:");
        printA(img.getR(), w,h);
        System.out.println("G:");
        printA(img.getG(), w,h);
        System.out.println("B:");
        printA(img.getB(), w,h);
    }
    
    /**
     * Permet de sauvegarder une image
     * @param bImg l'image à sauver
     * @param root l'endroit où la sauver
     * @param name le nom de l'image
     * @param ext  l'extension de l'image
     */
    public void writeImage(BufferedImage bImg, String root, String name, String ext)
    {
        try
        {
            File file = new File(root + name + "." + ext);
            ImageIO.write(bImg, ext, file);
        } 
        catch (IOException e) 
        {
            System.out.println("Erreur : " + e );
        }
    }
    
    /**
     * Permet de Lire une image
     * @param path emplacement complet de l'image
     * @return image de type BufferedImage
     */
    public BufferedImage loadImage(String path)
    {
        try
        {
            File file = new File(path);
            return ImageIO.read(file);
        }
        catch(IOException e)
        {
        }
        return null;
    }

    public final static int R = 0;
    public final static int G = 1;
    public final static int B = 2;
    /**
     * Permet de récupérer un tableau contenant les composantes d'une R,G,B
     * @param img l'image à exploiter
     * @param x
     * @param y
     * @return un tableau {R,G,B}
     */
    public int[] getPixelToArrayARGB(BufferedImage img, int x, int y)
    {
        int p = img.getRGB(x, y);
        return new int[]{ (p>>16)&0xff, (p>>8)&0xff, p&0xff };
    }
    
    /**
     * Permet de récupérer la valeur sur un entier des 3 composantes R,G,B
     * @param r valeur pour la couleur rouge
     * @param g valeur pour la couleur vert
     * @param b valeur pour la couleur bleu
     * @return 
     */
    public int getPixelValue(int r, int g, int b)
    {
        return ((r<<16) | (g<<8) | b);
    }
    
    /**
     * Permet de redimensionner une image donnée
     * @param originalImage L'image a redimensionner
     * @param scaledWidth   la largeur de l'image voulue
     * @param scaledHeight  la hauteur de l'image voulue
     * @return 
     */
    public BufferedImage getImgResized(BufferedImage originalImage, int scaledWidth, int scaledHeight)
    {
        BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        return resizedImage;
    }
    
    public void print(String s)
    {
        System.out.println(s);
    }
    
    public String getAbsolutePath()
    {
        return new File("").getAbsolutePath();
    }
}
