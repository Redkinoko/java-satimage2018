/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import main.Tools;

/**
 *
 * @author Red
 */
public class Shuffler
{
    private CNFDocument doc;
    public Shuffler(CNFDocument d)
    {
        doc = d;
    }
    
    public void random()
    {
        randomLines();
        randomColumns();
    }
    
    public void randomLines()
    {
        int l1 = Tools.get().getRandom(1, doc.getnVariables());
        int l2 = Tools.get().getRandom(1, doc.getnVariables());
        while(l1 == l2)
        {
            l1 = Tools.get().getRandom(1, doc.getnVariables());
        }
        doc.invertLines(l1, l2);
        doc.saveCurrentDocument();
    }
    
    public void randomColumns()
    {
        int c1 = Tools.get().getRandom(1, doc.getnClauses());
        int c2 = Tools.get().getRandom(1, doc.getnClauses());
        while(c1 == c2)
        {
            c1 = Tools.get().getRandom(1, doc.getnClauses());
        }
        doc.invertColumns(c1, c2);
        doc.saveCurrentDocument();
    }
    
    public void print()
    {
        int[][] m = doc.getMatrix();
        for(int y=0 ; y<getHeight(m) ; y++)
        {
            for(int x=0 ; x<getWidth(m) ; x++)
            {
                switch (m[x][y]) {
                    case CNFDocument.NEGATIVE:
                        System.out.print("*");
                        break;
                    case CNFDocument.POSITIVE:
                        System.out.print("1");
                        break;
                    default:
                        System.out.print("_");
                        break;
                }
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public int getWidth(int[][] m)
    {
        return m.length;
    }
    
    public int getHeight(int[][] m)
    {
        return m[0].length;
    }
    
    public void sort()
    {
        optimizePositive();
        sortByScore();
        doc.saveCurrentDocument();
        doc.recalculateImage();
    }
    
    public int checkLine(int i, int j, int score)
    {
        doc.invertLines(i, j);
        int s = this.countNeighbour();
        if(s > score)
        {
            return s;
        }
        else
        {
            doc.invertLines(i, j);
        }
        return score;
    }
    
    public int checkColumn(int i, int j, int score)
    {
        doc.invertColumns(i, j);
        int s = this.countNeighbour();
        if(s > score)
        {
            return s;
        }
        else
        {
            doc.invertColumns(i, j);
        }
        return score;
    }
    
    public int checkSigns(int i, int j, int score)
    {
        doc.invertLineSigns(j);
        int s = this.countNeighbour();
        if(s > score)
        {
            return s;
        }
        else
        {
            doc.invertLineSigns(j);
        }
        return score;
    }
    
    public int sortNaif()
    {
        int score = this.countNeighbour();
        for(int i=1 ; i<=doc.getnVariables() ; i++)
        {
            for(int j=1 ; j<=doc.getnVariables() ; j++)
            {
                if((score = this.checkLine(i, j, score)) > score)
                {
                    if((score = this.checkColumn(i, j, score)) > score)
                    {
                        if((score = this.checkSigns(i, j, score)) > score)
                        {
                        }
                    }
                }
                else
                {
                    if((score = this.checkLine(i, j, score)) > score)
                    {
                        if((score = this.checkSigns(i, j, score)) > score)
                        {
                            if((score = this.checkColumn(i, j, score)) > score)
                            {
                            }
                        }
                    }
                    else
                    {
                        if((score = this.checkColumn(i, j, score)) > score)
                        {
                            if((score = this.checkLine(i, j, score)) > score)
                            {
                                if((score = this.checkSigns(i, j, score)) > score)
                                {
                                }
                            }
                        }
                        else
                        {
                            if((score = this.checkColumn(i, j, score)) > score)
                            {
                                if((score = this.checkSigns(i, j, score)) > score)
                                {
                                    if((score = this.checkLine(i, j, score)) > score)
                                    {
                                    }
                                }
                            }
                            else
                            {
                                if((score = this.checkSigns(i, j, score)) > score)
                                {
                                    if((score = this.checkLine(i, j, score)) > score)
                                    {
                                        if((score = this.checkColumn(i, j, score)) > score)
                                        {
                                        }
                                    }
                                }
                                else
                                {
                                    if((score = this.checkSigns(i, j, score)) > score)
                                    {
                                        if((score = this.checkColumn(i, j, score)) > score)
                                        {
                                            if((score = this.checkLine(i, j, score)) > score)
                                            {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //sort();
        return score;
    }
    
    public int countNeighbourAt(int[][] m, int x, int y)
    {
        int n = 0;
        int x1 = x-1; int y1 = y-1;
        for(int i=0 ; i<=2 ; i++)
        {
            for(int j=0 ; j<=2 ; j++)
            {
                if(
                    (x1+j) >= 0 && (x1+j) < getWidth(m) &&
                    (y1+i) >= 0 && (y1+i) < getHeight(m)
                )
                {
                    if(m[(x1+j)][(y1+i)] == CNFDocument.POSITIVE)
                    {
                        n++;
                    }
                    /*else if(m[(x1+j)][(y1+i)] == CNFDocument.NEGATIVE)
                    {
                        n--;
                    }*/
                }
            }
        }
        
        return (n-1);
    }
    
    public int countNeighbour()
    {
        int[][] m = doc.getMatrix();
        int score = 0;
        for(int y=0 ; y<getHeight(m) ; y++)
        {
            for(int x=0 ; x<getWidth(m) ; x++)
            {
                if(m[x][y] == CNFDocument.POSITIVE)
                {
                    score += countNeighbourAt(m, x,y);
                }
            }
        }
        return score;
    }
    
    public void optimizePositive()
    {
        int[][] m = doc.getMatrix();
        int pos = 0;
        int neg = 0;
        for(int y=0 ; y<getHeight(m) ; y++)
        {
            pos = 0;
            neg = 0;
            for(int x=0 ; x<getWidth(m) ; x++)
            {
                switch(m[x][y])
                {
                    case CNFDocument.NEGATIVE:
                        neg++;
                        break;
                    case CNFDocument.POSITIVE:
                        pos++;
                        break;
                }
            }
            if(neg > pos)
            {
                doc.invertLineSigns(y+1);
            }
        }
    }
    
    public void sortByScore()
    {
        LinkedList<Integer> list = getOrderByScore();
        for(int i=0 ; i<list.size() ; i++)
        {
            doc.setClausesOrder(list.get(i), i);
        }
    }
    
    public LinkedList<Integer> getOrder()
    {
        LinkedList<Integer> order = new LinkedList<>();
        for(int i=0 ; i<doc.getnClauses() ; i++)
        {
            order.add(i);
        }
        return order;
    }
    
    public LinkedList<Integer> getOrderByScore()
    {
        int[][] m = doc.getMatrix();
        LinkedList<Integer> order = new LinkedList<>();
        LinkedHashMap<Integer, Integer> h = new LinkedHashMap<>();
        for(int y=0 ; y<getHeight(m) ; y++)
        {
            h.clear();
            for(int x=0 ; x<getWidth(m) ; x++)
            {
                int s = getScore(m, x, y);
                if(s > 0)
                {
                    h.put(x, s);
                }
            }
            h = Tools.sortByValue(h);
            for(Integer k : h.keySet())
            {
                if(!order.contains(k))
                {
                    order.add(k);
                }
            }
        }
        return order;
    }
    
    public int getScore(int[][] m, int x, int debY)
    {
        int n = 0;
        if(x>= 0 && x < m.length && debY>=0 && debY< m[0].length)
        {
            //deplacement par colonnes
            for(int y=debY ; y<m[0].length ; y++)
            {
                if(m[x][y] == CNFDocument.POSITIVE)
                {
                    n++;
                }
                else
                {
                    return n;
                }
            }
        }
        return n;
    }
}
