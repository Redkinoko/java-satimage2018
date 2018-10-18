/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satimage.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


/**
 *
 * @author Red
 */
public class DocumentReader {

    
    private DocumentReader(){}
    private static DocumentReader documentReader;
    public static DocumentReader get() { if(documentReader == null){ documentReader = new DocumentReader(); } return documentReader; }

    /**
     * Permet de lire un document
     * @param d le document à lire
     */
    public void read(Document d)
    {
        String line = null;
        String fileName = d.getFullName();
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null)
            {
                d.add(line);
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
    
}
