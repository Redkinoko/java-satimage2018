/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;
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
        randomVariables();
        randomClauses();
    }
    
    public void randomVariables()
    {
        for(int i=1 ; i<=doc.getnVariables() ; i++)
        {
            int v = Tools.get().getRandom(i, doc.getnVariables());
            doc.invertVariables(i, v);
        }
    }
    public void randomClauses()
    {
        for(int i=1 ; i<=doc.getnClauses() ; i++)
        {
            int c = Tools.get().getRandom(i, doc.getnClauses());
            doc.invertClauses(i, c);
        }
    }
    
    public void sort()
    {
        sortByBlueVar();
        //sortByBlueInClauses();
    }
    
    public void sortByBlueInClauses()
    {
        int[] score = new int[doc.getnClauses()];
        for(int i=0 ; i<doc.getnClauses() ; i++)
        {
            for(int j=0 ; j<doc.getClauses().get(i).size() ; j++)
            {
                if(doc.getVarOrder().get(doc.getClauses().get(i).get(j)) == 1)
                {
                    System.out.println(doc.getClauses().get(i).get(j));
                }
            }
        }
        for(int i=0 ; i<doc.getnClauses() ; i++)
        {
            for(int j=0 ; j<doc.getnClauses() ; j++)
            {
                if(score[i] > score[j])
                {
                    int tmp = score[j];
                    score[j] = score[i];
                    score[i] = tmp;
                    
                    doc.invertClauses(i+1, j+1);
                }
            }
        }
        for(int i:score)
        {
            System.out.println(i);
        }
    }
    
    public void sortByBlueVar()//tri de haut en bas décroissant par variable bleu
    {
        //Sélection du plus grand nombre possible de bleu
        for(int i=0 ; i<doc.getnVariables() ; i++)
        {
            int key = getVarOrderKey(i);
            int pos = doc.getCountVar().get(key);
            int neg = doc.getCountVar().get(-key);
            if(neg > pos)
            {
                doc.invertLineSigns(i+1);
            }
        }
        //Réorganisation par nombre de bleu
        sortVariables();
        
        HashMap<Integer, List<Integer>> positives = new HashMap<>();
        for(int i=0 ; i<doc.getnVariables() ; i++)
        {
            int key = getVarOrderKey(i);
            int pos = doc.getCountVar().get(key);
            if(positives.getOrDefault(pos, null) == null)
            {
                List<Integer> list = new ArrayList();
                list.add(i);
                positives.put(pos, list);
            }
            else
            {
                positives.get(pos).add(i);
            }
        }
        for(Integer key:positives.keySet())
        {
            for(Integer i:positives.get(key))
            {
                for(Integer j:positives.get(key))
                {
                    int v1 = getVarOrderKey(i);
                    int v2 = getVarOrderKey(j);
                    if(v1!=v2 && v1>0 && v2>0)
                    {
                        int c1 = doc.getCountVar().get(-v1);
                        int c2 = doc.getCountVar().get(-v2);
                        if(c1>c2)
                        {
                            doc.invertVariables(v1, v2);
                        }
                    }
                }
            }
        }

        //printCountVar();
    }
    
    public void printCountVar()
    {
        for(int i=0 ; i<doc.getnVariables() ; i++)
        {
            int key = getVarOrderKey(i);
            int pos = doc.getCountVar().get(key);
            int neg = doc.getCountVar().get(-key);
            System.out.println(pos+"_"+neg);
        }
    }
    
    public int getVarOrderKey(int v)
    {
        for(int i=0 ; i<doc.getnVariables() ; i++)
        {
            if(doc.getVarOrder().get(i+1) == v)
            {
                return (i+1);
            }
        }
        return 0;
    }
    
    public void sortVariables()
    {
        for(int i=0 ; i<doc.getnVariables() ; i++)
        {
            for(int j=0 ; j<doc.getnVariables() ; j++)
            {
                int v1 = getVarOrderKey(i);
                int v2 = getVarOrderKey(j);
                if(v1!=v2 && v1>0 && v2>0)
                {
                    int c1 = doc.getCountVar().get(v1);
                    int c2 = doc.getCountVar().get(v2);
                    if(c1>c2)
                    {
                        doc.invertVariables(v1, v2);
                    }
                }
            }
        }
    }
}
