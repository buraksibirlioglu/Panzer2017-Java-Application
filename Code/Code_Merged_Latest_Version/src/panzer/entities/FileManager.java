/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class FileManager {
    static ArrayList<String> allData;
    
    public FileManager(){
    }
  
    public void writeSettings(int sound, int playerColor){   
        FileWriter writer;
        allData = getAllData();
        allData.set(0, sound +"_"+playerColor);
        //writer.print("");
        try {
            writer = new FileWriter("saved_data.txt", false);
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
        int[] temp = { Integer.parseInt(getAllData().get(0).substring(0, 1)), Integer.parseInt(getAllData().get(0).substring(2)) };
        return temp;//first line contains settings information
    }
    
    public ArrayList<String> getHighScores(){
        ArrayList<String> temp = getAllData(); 
        ArrayList<String> temp2 = new ArrayList<>();    
        
        temp.remove(0); // we need all except settings, line 2-7 represent player name + score
        for (int i = 0; i < temp.size(); i++){
            String fullData = temp.get(i);// last element of the list contains lowest score
            String name_ = fullData.substring(0,fullData.indexOf("_"));
            String points = fullData.replace(name_+"_", ""); // john_123 becoms "123"
            temp2.add(name_+"\t"+points +"\n");
            System.out.println(name_+  "   "+points );
        }        
        return temp2;
    }
    
    // returns the lowest score from the highscores list or 0 if there is no score 
    public int getLowestScore(){
        allData = getAllData();
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
        allData= getAllData();
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
        if(points > getHighestScore()){ // never goes here
            allData = getAllData();
            
            FileWriter writer;
            System.out.println("HIGHEST SCORE REACHED"+ allData.size());
            try {
            writer = new FileWriter("saved_data.txt", false);
            
            String tempOldHigh = allData.get(0);
            ArrayList<String> temp = new ArrayList<String>(allData);
            temp.remove(0);
            allData.clear();
            allData.add(tempOldHigh);
            allData.add(playerName+"_"+points);
            System.out.println("HIGHEST 1"+allData.size() + temp.size());
            for(int i = 0; i < temp.size(); i++){
                allData.add(temp.get(i)); // added to the top
            }
            System.out.println("HIGHEST 2");
            PrintWriter printer = new PrintWriter(writer);
            for (int i = 0; i < allData.size(); i++){
                printer.printf  ("%s"+ "%n", allData.get(i));
            }
            System.out.println("HIGHEST 3");
            printer.close();
          //  writer.close
            return 2;
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        allData= getAllData();
        if(points > getLowestScore() && allData.size() > 6){ //stupid ??
                 System.out.println("first pass");
                 FileWriter writer;
            try {
                    System.out.println("fouth pass");
                    writer = new FileWriter("saved_data.txt", true);
                    PrintWriter printer = new PrintWriter(writer);
                    printer.printf  ("%s"+ "%n",playerName + "_" + points);
                    printer.close();
                    System.out.println("WRITING: " + playerName + "_" + points);
                    writer.close();
                    ArrayList<String> tt = getAllData();
                    for (int i = 0; i < tt.size(); i++){
                        System.out.println("___"+ tt.get(i));
                    }
                   return 2;
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
        try { 
          //  br = new BufferedReader( new InputStreamReader(FileManager.class.getResourceAsStream("saved_data.txt"))); 
        //    System.out.println("../../saved_data.txt"));
           br = new BufferedReader( new InputStreamReader(new FileInputStream("saved_data.txt")));           
        
            String line = br.readLine();         
            while (line != null ) {
                temp.add( line);
                line = br.readLine(); 
            }
            br.close();
        }catch(IOException  ex){
            System.out.println("EROOOOOOOOOOOOOOOOOOOOOOR" + System.getProperty("user.dir"));
            ex.printStackTrace();
        }
        return  temp;      
    }
}
