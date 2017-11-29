/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ndricim Rrapi
 */
public class Map {
    private int mapWidth;
    private int mapHeight;
    private int[][] brickMap;
    private ArrayList<Brick> bricks;
    private ArrayList<Castle> castle;

    
    public Map(int width, int height,int level) throws IOException {
        int rowNo,colNo;
        rowNo=15;
        colNo=25;
        brickMap=new int[rowNo][colNo];
        System.out.println(rowNo+" "+colNo);
        createMap(rowNo,colNo,level);
    }  
    
    public void createMap(int row,int col,int level) throws FileNotFoundException, IOException{
        bricks=new ArrayList<>();
        String line;
        String mapNo="map"+level+".txt";
        System.out.println(mapNo);
        BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(mapNo)));
        Scanner  scn = new Scanner(txtReader);
        for(int i = 0; i < row; i++){
            line = scn.nextLine(); 
            String[] numbers = line.split(" "); 
            for(int j = 0; j < col; j++){
                brickMap[i][j] = Integer.parseInt(numbers[j]); 
                if( brickMap[i][j] != 0 ) {
                    Brick B = new Brick(true, 40*j, 40*i,40, 40,brickMap[i][j]);
                    bricks.add(B);
                }
            }
        }
    }
    
    public ArrayList<Brick> getBricks(){
        return bricks;
    }

}
