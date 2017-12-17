/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.scene.image.Image;
import panzer.brainClass.GameEngine;
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
    private long my_bonus_duration;
    private boolean hasShieldProtection;
   
    public PlayerTank(boolean _isAlive, float _coordinateX, float _coordinateY, int width, int height,   int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, life);
        setIcons();
        setIconArrayList(get5LifeIconImages());
        setCustomImg(life_5.get(0));
        my_bonus_duration = 0;
        //hasShieldProtection = false;
    }
    
    public void setShieldProtection(boolean hasShieldProtection) {
        this.hasShieldProtection = hasShieldProtection;
    }

    public boolean hasShieldProtection() {
        return hasShieldProtection;
    }
    
    public void decrementMyBonusDuration() {
        this.my_bonus_duration = this.my_bonus_duration-1032;
    }

    public void incrementMyBonusDuration() {
        this.my_bonus_duration = 1000000;
    }
    
    public void incrementLifeBonusDuration(){
         this.my_bonus_duration = 1032;
    }
    
    public long getMyBonusDuration() {
        return my_bonus_duration;
    }
    
    public void shootMetal(){
        if(myBullet != null) return;
        if(!getFrozenState()){
            int speed = 0, range = 0;       
            if(hasSuperBullet){ speed = 10; range = 700;}
            if(!hasSuperBullet){ speed = 5; range = 500;}    
            Bullet bullet = new MetalBullet(true, getCoordinateX(), getCoordinateY(), 10,10, this, direction, range, speed);
            GameEngine.getAllObjectsList().add(bullet);
            GameEngine.getBulletList().add(bullet);
            myBullet = bullet;
            feuer();
        }
    }
    public void shootIce(){
        if(myBullet != null) return;
        if(!getFrozenState()){
            Bullet bullet= null;
            if(hasIceBullet()){
                bullet = new IceBullet(true, getCoordinateX(), getCoordinateY(), 10,10, this, direction, 500, 5);
                GameEngine.getAllObjectsList().add(bullet);
                GameEngine.getBulletList().add(bullet);
                myBullet = bullet;
                feuer(); // boom
            }
        }
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
    
    public ArrayList<Image> getCurrentPlayerIcon(){
        if(getLife() == 5){
            return life_5;
        }else if (getLife() == 4){
            return  life_4;
        }else if (getLife() == 3){
            return  life_3;
        }else if (getLife() == 2){
            return life_2;
        }else {
            return life_1;
        }
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
