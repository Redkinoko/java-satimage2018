/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import core.interfaces.ICoreSchema;

/**
 *
 * @author Red
 */
public class Document {
    
    protected String ext;
    protected String path;
    protected String name;
    protected List<String> lines;
    
    public Document(String path, String name, String ext)
    {
        this.path  = path;
        this.name  = name;
        this.ext   = ext;
        this.lines = null;
    }
    
    public void init()
    {
        this.lines = new ArrayList<String>();
    }
    
    public void add(String line)
    {
        lines.add(line);
    }
    
    public String getFullName()
    {
        return path + name + ext;
    }
    
    public void print()
    {
        System.out.println("PATH\t: " + getFullName());
        if(lines != null)
        {
            System.out.println("DATA\t: ");
            for(int i=0 ; i<lines.size() ; i++)
            {
                System.out.println(lines.get(i));
            }
        }
    }
    
    public boolean fileExists()
    {
        File f = new File(getFullName());
        return f.exists();
    }
    
    /**
     * Permet de lire un document
     */
    public void load()
    {
        init();
        String line = null;
        String fileName = getFullName();
        if(fileExists())
        {
            try
            {
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((line = bufferedReader.readLine()) != null)
                {
                    add(line);
                }   

                bufferedReader.close();         
            }
            catch(FileNotFoundException ex)
            {
                System.out.println("Unable to open file '" + fileName + "' : " + ex.toString());                
            }
            catch(IOException ex)
            {
                System.out.println("Error reading file '" + fileName + "' : " + ex.toString());
            }
        }
        else
        {
            System.out.println("Error reading file '" + fileName + "' : File doesn't exist");
        }
    }
    
}
