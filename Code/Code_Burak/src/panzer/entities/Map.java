/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.image.Image;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Burak
 */
public class Map {
    private int mapWidth;
    private int mapHeight;
    private int[][] brickMap;
    private ArrayList<Brick> bricks;
    private ArrayList<Castle> castle;
    private Castle enCastle;
    private Castle plCastle;
    
    public Map(int width, int height) throws IOException
    {
        int rowNo,colNo;
        rowNo=height/60;
        colNo=17;
        brickMap=new int[rowNo][colNo];
        System.out.println(rowNo+" "+colNo);
        createMap(rowNo,colNo);
    }  
    public void createMap(int row,int col) throws FileNotFoundException, IOException
    {
    String file = "src/panzer/pkg2017/map.txt";
        System.out.println(row+" "+col);
        ArrayList<Image> brickImage = new ArrayList<>();
        bricks=new ArrayList<>();
        brickImage.add(new Image(Panzer2017.class.getResource("images/brown_brick.png").toExternalForm()));
        String line;
        Scanner scn = new Scanner(new BufferedReader(new FileReader(file)));
        for(int i = 0; i < row; i++){
            line = scn.nextLine(); 
            String[] numbers = line.split(" "); 
            for(int j = 0; j < col; j++){
                brickMap[i][j] = Integer.parseInt(numbers[j]); 
                if( brickMap[i][j] != 0 )
                {
                    Brick B = new Brick(true, 60*j, 60*i,60, 60, 0,brickImage);
                    bricks.add(B);
                }
            }
        }
        ArrayList<Image> castleImage = new ArrayList<>();
        castle=new ArrayList<>();
        castleImage.add(new Image(Panzer2017.class.getResource("images/player_king.png").toExternalForm()));
        castleImage.add(new Image(Panzer2017.class.getResource("images/enemy_king.png").toExternalForm()));
        Castle C = new Castle(true, 0, 300,60, 60, 0,castleImage,50,1);
        castle.add(C);
        C=new Castle(true, 960, 300,60, 60, 0,castleImage,50,2);
        castle.add(C);
        
        
    }
    public ArrayList<Brick> getBricks()
    {
        return bricks;
    }
    public ArrayList<Castle> getCastles()
    {
        return castle;
    }
}
