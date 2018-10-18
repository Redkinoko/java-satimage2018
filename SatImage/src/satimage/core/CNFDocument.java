/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satimage.core;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Red
 */
public class CNFDocument extends Document {
    
    public final static String EXT          = ".cnf";
    public final static String DESC_COMMENT = "c";
    public final static String DESC_PROBLEM = "p";
    public final static String DESC_TYPE    = "cnf";
    
    private int nbVariables;
    private int nbClauses;
    private List<List<Integer>> clauses;
    
    /**
     * Un CNFDocument est un document qui a l'extension .cnf
     * C'est une structure de donnée normé
     * @param path le chemin du fichier
     * @param name le nom du fichier
     */
    public CNFDocument(String path, String name)
    {
        super(path,name,EXT);
        clauses     = new ArrayList();
        nbVariables = 0;
        nbClauses   = 0;
    }
    
    /**
     * On enregistre la ligne lu et on la converti en clauses
     * @param line la line lu courante
     */
    @Override
    public void add(String line)
    {
        super.add(line);
        parseLine(line);
    }
    
    /**
     * Une ligne du fichier commence par :
     * soit _ représentatif du caratère 'espace'
     * c : est un commentaire (c, ...)
     * p_cnf : est une description (p_cnf_nombreDeVariables_nombreDeClauses)
     * Une ligne qui commence par un nombre et termine par 0 :
     * 1_-2_0 : est une clause pour les couples (1 = a, 2 = b, -1 = -a, -2 = -b)
     * donne { a OR -b }
     * @param line la ligne courante a découper en clauses
     */
    private void parseLine(String line)
    {
        String[] split = line.split(" ");
        switch(split[0])
        {
            case DESC_COMMENT:
                break;
            case DESC_PROBLEM:
                addDescription(split);
                break;
            default:
                addClauses(split);
        }
    }
    
    /**
     * Permet de transcrire la chaine en description de clauses
     * @param line la line à traiter
     */
    private void addDescription(String[] line)
    {
        if(line[0].equals(DESC_PROBLEM) && line[1].equals(DESC_TYPE))
        {
            nbVariables = Integer.parseInt(line[2]);
            nbClauses   = Integer.parseInt(line[3]);
        }
    }
    
    /**
     * Permet de transcrire la chaine en clauses
     * @param line a line à traiter
     */
    private void addClauses(String[] line)
    {
        int length = line.length-1;
        if(line[length].equals("0"))
        {
            List<Integer> c = new ArrayList();
            for(int i=0 ; i<length ; i++)
            {
                c.add(Integer.parseInt(line[i]));
            }
            clauses.add(c);
        }
    }
    
    public RGBImage getRGBImage()
    {
        RGBImage img = new RGBImage(nbClauses, nbVariables);
        for(int i=0 ; i<clauses.size() ; i++)
        {
            for(int j=0 ; j<clauses.get(i).size() ; j++)
            {
                int v  = clauses.get(i).get(j);
                int vabs = Math.abs(v)-1;
                if(v < 0)//Si négatif en rouge
                {
                    img.setRedAt(i, vabs);
                }
                else//Si positif en bleu
                {
                    img.setBlueAt(i, vabs);
                }

            }
            System.out.println();
        }
        System.out.println();
        return img;
    }
    
    @Override
    public void print()
    {
        System.out.println("VARS\t: "    + nbVariables);
        System.out.println("CLAUSES\t: " + nbClauses);
        System.out.println("ENTRIES\t: " + clauses.size());
        for(int i=0 ; i<clauses.size() ; i++)
        {
            for(int j=0 ; j<clauses.get(i).size() ; j++)
            {
                System.out.print(clauses.get(i).get(j) + "|");
            }
            System.out.println();
        }
        System.out.println();
        super.print();
    }
    
}
