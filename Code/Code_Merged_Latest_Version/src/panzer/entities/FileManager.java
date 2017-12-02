/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ndricim Rrapi
 */
public class FileManager {
    private FileWriter writer;
    static ArrayList<String> allData;
    
    public FileManager(){
       allData = getAllData();
    }
    
    
    
    public void writeSettings(int sound, int playerColor){      
        allData.set(0, sound +"_"+playerColor);
        //writer.print("");
        try {
            writer = new FileWriter("src/panzer/pkg2017/files/saved_data.txt", false);
            writer.write(allData.get(0));
            writer.append(System.getProperty("line.separator"));  
            for(int i = 1; i < allData.size() && allData.get(i) != null; i++){
                writer.write(allData.get(i));
                if(i != allData.size())
                    writer.append(System.getProperty("line.separator"));     
            }
            writer.close();
        } catch (IOException  ex) {
            System.err.println("ERROR FILE NOT FOUND ");
        }
       
    }
    
    // returns settings as a array of integers size 2, int[0] -> soundOnOff, int[1] -> playerColor
    public int [] getSettings(){        
        int[] temp = { Integer.parseInt(getAllData().get(0).substring(0, 1)), Integer.parseInt(getAllData().get(0).substring(1)) };
        return temp;//first line contains settings information
    }
    
    public ArrayList<String> getHighScores(){
        ArrayList<String> temp = allData;       
        temp.remove(0); // we need all except settings, line 2-7 represent player name + score
        return temp;
    }
    
    // returns the lowest score from the highscores list or 0 if there is no score 
    public int getLowestScore(){
        System.out.println("oooooooo" + allData.size());
        if (allData.size()==1){
            return 0;
        }
        String fullData = allData.get(allData.size()-1);// last element of the list contains lowest score
        String name_ = fullData.substring(0,fullData.indexOf("_")+1);
        String elem = fullData.replace(name_, ""); // john_123 becoms "123"
        System.out.println("kontroll"+Integer.valueOf(elem));
        return (elem != null ) ? Integer.valueOf(elem) : 0; // playername though must not contain _
    }
    
     // returns the lowest score from the highscores list or 0 if there is no score 
    public int getHighestScore(){
        if(allData.size()==1)
            return 0;
        String fullData = allData.get(1);// last element of the list contains lowest score
        String name_ = fullData.substring(0,fullData.indexOf("_")+1);
        String elem = fullData.replace(name_, ""); // john_123 becoms "123"
        return (elem != null ) ? Integer.valueOf(elem) : 0; // playername though must not contain _
    }
    
    // returns 0 if points < all scores in highscore list, 1 if points > highest score, 2 if points < highest score && points > lowest score
    public int writeScore(String playerName, int points){
        System.out.println("print points "+ points);
        if(points > getLowestScore()){
                 System.out.println("first pass");
            try {
                    System.out.println("sec pass");
                if(points > getHighestScore()){
                        System.out.println("third pass");
                    writer = new FileWriter("src/panzer/pkg2017/files/saved_data.txt", false);
                    writer.write(allData.get(0));//first line
                    writer.append(System.getProperty("line.separator"));
                    writer.write(playerName + "_" + points); // writen as new highest score
                       writer.append(System.getProperty("line.separator"));
                    for(int i = 1; i < allData.size();i++){
                        writer.write(allData.get(i));
                        if(!(i == allData.size()-1))
                            writer.append(System.getProperty("line.separator"));
                    }
                    writer.close();
                    return 2;
                }
                System.out.println("fouth pass");
                writer = new FileWriter("src/panzer/pkg2017/files/saved_data.txt", true);
                writer.append(System.getProperty("line.separator"));
                writer.write   ( playerName + "_" + points); // player managed to make it in the top 5
                writer.close();
            } catch (IOException  ex) {
                System.err.println("ERROR FILE NOT FOUND ");
            }
            
        }
      
      
        return 0;
    }
    
    // returns an arraylist of elements , each element represnt a row, first row = settings {"01"}, other rows = highscore = {john_12540}
    private ArrayList<String> getAllData(){
        ArrayList<String> temp = new ArrayList<>();
        BufferedReader br = null;
        System.out.println("my size = " +temp.size());
        try {
            br = new BufferedReader(new FileReader("src/panzer/pkg2017/files/saved_data.txt"));
            String line = br.readLine();
            System.out.println("line= "+ line);           
            while (line != null ) {
                System.out.println("done here" + line);
                temp.add( line);
                line = br.readLine();
            }
            br.close();
        }catch(IOException  ex){
            ex.printStackTrace();
        }
        return  temp;      
    }
}
