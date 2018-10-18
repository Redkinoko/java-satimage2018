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
public class Document {
    
    protected String ext;
    protected String path;
    protected String name;
    protected List<String> lines;
    
    public Document(String path, String name, String ext)
    {
        this.path = path;
        this.name = name;
        this.ext  = ext;
        this.lines = new ArrayList();
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
        System.out.println("DATA\t: ");
        for(int i=0 ; i<lines.size() ; i++)
        {
            System.out.println(lines.get(i));
        }
    }
    
}
