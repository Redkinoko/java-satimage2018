/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import main.Tools;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Red
 */
public class CNFDocument extends Document {
    
    public final static String EXT          = ".cnf";
    public final static String DESC_COMMENT = "c";
    public final static String DESC_PROBLEM = "p";
    public final static String DESC_TYPE    = "cnf";
    
    private int nVariables;
    private int nClauses;
  
    private Color positiveColor;
    private Color negativeColor;
    private Color backgroundColor;
    /**
     * Une ligne par clauses
     */
    private List<List<Integer>> clauses;
    /**
     * key   = index de la clause dans la liste des clauses
     * value = colonne d'affichage
     */
    private TreeMap<Integer, Integer> clauseOrder;
    /**
     * key   = valeur (-1,1,...) de la variable lue 
     * value = ligne d'affichage
     */
    private TreeMap<Integer, Integer> varOrder;
    
    /**
     * key = valeur (-1,1,...) de la variable lue
     * value = nombre de clauses po
     */
    private HashMap<Integer, Integer> countVar;
    
    private Dimension pixelDim;
    private RGBImage image;
    
    /**
     * Un CNFDocument est un document qui a l'extension .cnf
     * C'est une structure de donnée normé
     * @param path le chemin du fichier
     * @param name le nom du fichier
     */
    public CNFDocument(String path, String name)
    {
        super(path,name,EXT);
        clauses         = null;
        clauseOrder     = null;
        varOrder        = null;
        nVariables     = 0;
        nClauses       = 0;
        positiveColor   = null;
        negativeColor   = null;
        backgroundColor = null;
        image           = null;
        pixelDim       = null;
    }
    
    @Override
    public void init()
    {
        super.init();
        clauses         = new ArrayList<>();
        clauseOrder     = new TreeMap<>();
        varOrder        = new TreeMap<>();
        countVar        = new HashMap<>();
        positiveColor   = Color.BLUE;
        negativeColor   = Color.RED;
        backgroundColor = Color.BLACK;
        pixelDim        = new Dimension(1,1);
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
            nVariables = Integer.parseInt(line[2]);
            nClauses   = Integer.parseInt(line[3]);
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
            int key, order;
            for(int i=0 ; i<length ; i++)
            {
                key = Integer.parseInt(line[i]);
                order = Math.abs(key)-1;
                varOrder.put(key, order);
                c.add(key);
                if(countVar.containsKey(key))
                {
                    int v = countVar.get(key);
                    countVar.put(key, v+1);
                }
                else
                {
                    countVar.put(key, 1);
                }
            }
            clauseOrder.put(clauseOrder.size(), clauseOrder.size());
            clauses.add(c);
        }
    }
    
    private Color getColor(int value)
    {
        return (value>0)?positiveColor:negativeColor;
    }
    
    private void draw(int v, int x, int y)
    {
        image.draw(getColor(v), x,y);
    }
    
    private void clean(int x, int y)
    {
        image.draw(backgroundColor, x,y);
    }
    
    /**
     * Permet de dessiner la clause référencé dans clauses à l'index i
     * @param i l'index de la clause à dessiner
     */
    private void drawClause(int i)
    {
        int v;
        for(int j=0 ; j<clauses.get(i).size() ; j++)
        {
            v  = clauses.get(i).get(j);
            draw(v, clauseOrder.get(i), varOrder.get(v));
        }
    }
    
    /**
     * Permet d'effacer la clause i dans l'image de référence
     * @param i la clause à effacer
     */
    private void cleanClause(int i)
    {
        int v;
        for(int j=0 ; j<clauses.get(i).size() ; j++)
        {
            v  = clauses.get(i).get(j);
            clean(clauseOrder.get(i), varOrder.get(v));
        } 
    }
    
    public void setPixelDimension(int pWidth, int pHeight)
    {
        if(pixelDim != null && pWidth > 0 && pHeight > 0)
        {
            pixelDim.width  = pWidth;
            pixelDim.height = pHeight;
        }
    }
    /**
     * Permet de dessiner l'image entiere représentant la clause
     *
    */
    private RGBImage createImage()
    {
        if(clauses != null && clauses.size() > 0)
        {
            image = new RGBImage(nClauses, nVariables, pixelDim);
            for(int i=0 ; i<clauses.size() ; i++)
            {
                drawClause(i);
            }
        }
        else
        {
            System.out.println("Error drawing image : 0 clauses");
        }
        return image;
    }
    
    /**
     * L'image représentant la clause est dessiné et si elle n'existe pas
     * Sinon l'image en mémoire est retournée
     * @return dernière image en mémoire générée
     */
    public RGBImage getImage()
    {
        if(image == null)
        {
            createImage();
        }
        if(image != null && !image.getPixelDim().equals(pixelDim))
        {
            createImage();
        }
        return image;
    }
    
    /**
     * Si une image représentative existe
     * les clauses à echanger sont effacé puis inversé puis enfin redessiné
     * @param c1 la clause à echanger avec c2
     * @param c2 la clause à echanger avec c1
     */
    public void invertClauses(int c1, int c2)
    {
        c1--;
        c2--;
        if(clauseOrder != null && c1 != c2 && c1>=0 && c2 >=0 && clauseOrder.containsKey(c1) && clauseOrder.containsKey(c2))
        {
            if(image != null)
            {
                cleanClause(c1);
                cleanClause(c2);
            }
            
            Integer tmp = clauseOrder.get(c1);
            clauseOrder.put(c1, clauseOrder.get(c2));
            clauseOrder.put(c2, tmp);
            
            if(image != null)
            {
                drawClause(c1);
                drawClause(c2);
            }
        }
    }
    
    private void cleanVariable(int v1)
    {
        int v, absV;
        for(int i=0 ; i<clauses.size() ; i++)
        {
            for(int j=0 ; j<clauses.get(i).size() ; j++)
            {
                v  = clauses.get(i).get(j);
                absV = Math.abs(v);
                if(v1 == absV)
                {
                    clean(clauseOrder.get(i), varOrder.get(v));
                }
            }
        }
    }
    
    private void cleanVariables(int v1, int v2)
    {
        int v, absV;
        for(int i=0 ; i<clauses.size() ; i++)
        {
            for(int j=0 ; j<clauses.get(i).size() ; j++)
            {
                v  = clauses.get(i).get(j);
                absV = Math.abs(v);
                if(v1 == absV || v2 == absV)
                {
                    clean(clauseOrder.get(i), varOrder.get(v));
                }
            }
        }
    }
    
    private void drawVariable(int v1)
    {
        int v, absV;
        for(int i=0 ; i<clauses.size() ; i++)
        {
            for(int j=0 ; j<clauses.get(i).size() ; j++)
            {
                v  = clauses.get(i).get(j);
                absV = Math.abs(v);
                if(v1 == absV)
                {
                    draw(v, clauseOrder.get(i), varOrder.get(v));
                }
            }
        }
    }
    
    private void drawVariables(int v1, int v2)
    {
        int v, absV;
        for(int i=0 ; i<clauses.size() ; i++)
        {
            for(int j=0 ; j<clauses.get(i).size() ; j++)
            {
                v  = clauses.get(i).get(j);
                absV = Math.abs(v);
                if(v1 == absV || v2 == absV)
                {
                    draw(v, clauseOrder.get(i), varOrder.get(v));
                }
            }
        }
    }
    
    public void invertVariables(int v1, int v2)
    {
        if(varOrder != null && v1 != v2 && varOrder.containsKey(v1) && varOrder.containsKey(v2))
        {
            if(image != null)
            {
                cleanVariables(v1,v2);
            }
            int tmp = varOrder.get(v1);
            varOrder.put(v1, varOrder.get(v2));
            varOrder.put(-v1, varOrder.get(v2));
            varOrder.put(v2, tmp);
            varOrder.put(-v2, tmp);
            if(image != null)
            {
                drawVariables(v1,v2);
            }
        }
    }
    
    public void invertLineSigns(int line)
    {
        if(varOrder != null && line>0 && line <= this.getClauses().size())
        {
            if(image != null)
            {
                cleanVariable(line);
            }
            int v, vAbs;
            for(int i=0 ; i<clauses.size() ; i++)
            {
                for(int j=0 ; j<clauses.get(i).size() ; j++)
                {
                    v = getClauses().get(i).get(j);
                    vAbs = Math.abs(v);
                    if(vAbs == line)
                    {
                        getClauses().get(i).set(j, -v);
                    }
                }
            }
            Integer pos = this.getCountVar().get(line);
            this.getCountVar().put(line, this.getCountVar().get(-line));
            this.getCountVar().put(-line, pos);
            if(image != null)
            {
                drawVariable(line);
            }
        }
    }
    
    public void printClauseOrder()
    {
        Set<Integer> keys = clauseOrder.keySet();
        for(Integer key: keys)
        {
            System.out.println("[" + ((key<0)?key:" "+key) + "] = " + clauseOrder.get(key));
        }
    }
    
    public void printVarOrder()
    {
        Set<Integer> keys = varOrder.keySet();
        for(Integer key: keys)
        {
            System.out.println("[" + ((key<0)?key:(" "+key)) + "] = " + varOrder.get(key));
        }
    }
    
    @Override
    public void print()
    {
            System.out.println("VARS\t: "    + nVariables);
            System.out.println("CLAUSES\t: " + nClauses);
            if(!(clauseOrder == null || clauses == null))
            {
                System.out.println("ENTRIES\t: " + clauseOrder.size());
                for(int i=0 ; i<clauses.size() ; i++)
                {
                    for(int j=0 ; j<clauses.get(i).size() ; j++)
                    {
                        System.out.print(clauses.get(i).get(j) + "|");
                    }
                    System.out.println();
                }
                System.out.println();
            }
    }

    public int getnVariables() {
        return nVariables;
    }

    public void setnVariables(int nVariables) {
        this.nVariables = nVariables;
    }

    public int getnClauses() {
        return nClauses;
    }

    public void setnClauses(int nClauses) {
        this.nClauses = nClauses;
    }

    public Color getPositiveColor() {
        return positiveColor;
    }

    public void setPositiveColor(Color positiveColor) {
        this.positiveColor = positiveColor;
    }

    public Color getNegativeColor() {
        return negativeColor;
    }

    public void setNegativeColor(Color negativeColor) {
        this.negativeColor = negativeColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<List<Integer>> getClauses() {
        return clauses;
    }

    public void setClauses(List<List<Integer>> clauses) {
        this.clauses = clauses;
    }

    public TreeMap<Integer, Integer> getClauseOrder() {
        return clauseOrder;
    }

    public void setClauseOrder(TreeMap<Integer, Integer> clauseOrder) {
        this.clauseOrder = clauseOrder;
    }

    public TreeMap<Integer, Integer> getVarOrder() {
        return varOrder;
    }

    public void setVarOrder(TreeMap<Integer, Integer> varOrder) {
        this.varOrder = varOrder;
    }

    public Dimension getPixelDim() {
        return pixelDim;
    }

    public void setPixelDim(Dimension pixelDim) {
        this.pixelDim = pixelDim;
    }

    public HashMap<Integer, Integer> getCountVar() {
        return countVar;
    }

    public void setCountVar(HashMap<Integer, Integer> countVar) {
        this.countVar = countVar;
    }
    
    
}
