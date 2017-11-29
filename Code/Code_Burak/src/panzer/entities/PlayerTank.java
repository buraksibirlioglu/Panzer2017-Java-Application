/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.scene.image.Image;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class PlayerTank extends Tank {
     private ArrayList<Image> life_5 = new ArrayList<>();  
    private ArrayList<Image> life_4 = new ArrayList<>();  
    private ArrayList<Image> life_3 = new ArrayList<>();
    private ArrayList<Image> life_2 = new ArrayList<>();
    private ArrayList<Image> life_1 = new ArrayList<>();
    
    public PlayerTank(boolean _isAlive, float _coordinateX, float _coordinateY, int width, int height,int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, life,1);
        setIcons();
        setIconArrayList(get5LifeIconImages());
         setCustomImg(life_5.get(0));
    }
    
    private void setIcons(){
        life_5.add(new Image(Panzer2017.class.getResource("images/user_tank_up.png").toExternalForm(),38,38,false,false));
        life_5.add(new Image(Panzer2017.class.getResource("images/user_tank_down.png").toExternalForm(),38,38,false,false));
        life_5.add(new Image(Panzer2017.class.getResource("images/user_tank_left.png").toExternalForm(),38,38,false,false));
        life_5.add(new Image(Panzer2017.class.getResource("images/user_tank_right.png").toExternalForm(),38,38,false,false));
               
        life_4.add(new Image(Panzer2017.class.getResource("images/user_tank_up_4life.png").toExternalForm(),38,38,false,false));
        life_4.add(new Image(Panzer2017.class.getResource("images/user_tank_down_4life.png").toExternalForm(),38,38,false,false));
        life_4.add(new Image(Panzer2017.class.getResource("images/user_tank_left_4life.png").toExternalForm(),38,38,false,false));
        life_4.add(new Image(Panzer2017.class.getResource("images/user_tank_right_4life.png").toExternalForm(),38,38,false,false));
       
        life_3.add(new Image(Panzer2017.class.getResource("images/user_tank_up_3life.png").toExternalForm(),38,38,false,false));
        life_3.add(new Image(Panzer2017.class.getResource("images/user_tank_down_3life.png").toExternalForm(),38,38,false,false));
        life_3.add(new Image(Panzer2017.class.getResource("images/user_tank_left_3life.png").toExternalForm(),38,38,false,false));
        life_3.add(new Image(Panzer2017.class.getResource("images/user_tank_right_3life.png").toExternalForm(),38,38,false,false));
       
        life_2.add(new Image(Panzer2017.class.getResource("images/user_tank_up_2life.png").toExternalForm(),38,38,false,false));
        life_2.add(new Image(Panzer2017.class.getResource("images/user_tank_down_2life.png").toExternalForm(),38,38,false,false));
        life_2.add(new Image(Panzer2017.class.getResource("images/user_tank_left_2life.png").toExternalForm(),38,38,false,false));
        life_2.add(new Image(Panzer2017.class.getResource("images/user_tank_right_2life.png").toExternalForm(),38,38,false,false));
       
        life_1.add(new Image(Panzer2017.class.getResource("images/user_tank_up_1life.png").toExternalForm(),38,38,false,false));
        life_1.add(new Image(Panzer2017.class.getResource("images/user_tank_down_1life.png").toExternalForm(),38,38,false,false));
        life_1.add(new Image(Panzer2017.class.getResource("images/user_tank_left_1life.png").toExternalForm(),38,38,false,false));
        life_1.add(new Image(Panzer2017.class.getResource("images/user_tank_right_1life.png").toExternalForm(),38,38,false,false));
    }
    
    public ArrayList<Image> get5LifeIconImages() {
        return life_5;
    }
         
    public ArrayList<Image> get4LifeIconImages() {
        return life_4;
    }

    public ArrayList<Image> get3LifeIconImages() {
        return life_3;
    }

    public ArrayList<Image> get2LifeIconImages() {
        return life_2;
    }

    public ArrayList<Image> get1LifeIconImages() {
        return life_1;
    }
}
