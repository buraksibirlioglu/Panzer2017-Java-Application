/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import panzer.brainClass.GameEngine;
import panzer.pkg2017.MainMenuController;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class Tank extends GameObject{
    private int life;
    //Bullet this bullet 
    Bullet myBullet;
    int tank_speed;
    boolean hasSuperBullet;
    boolean iceBullet;
    private boolean frozenState;
    private int frozenStateDuration;
    public int direction; // 0 up , 1 down , 2 left, 3 right 
    private boolean moving;


    public Tank(boolean _isAlive, float _coordinateX, float _coordinateY,int width, int height, int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);  
        this.life = life;
        tank_speed = 1;
    }      
    
    public void setTank_speed(int tank_speed) {
        this.tank_speed = tank_speed;
    }
    
    public int getTank_speed() {
        return tank_speed;
    }
    
    public void setHasSuperBullet(boolean hasSuperBullet) {
        this.hasSuperBullet = hasSuperBullet;
    }
    
    public boolean hasSuperBullet() {
        return hasSuperBullet;
    }

    public boolean hasIceBullet() {
        return iceBullet;
    }

    public void setHasIceBullet(boolean iceBullet) {
        this.iceBullet = iceBullet;
    }
   
      // sets the state of this enemy tank to frozen/ melted
    public void setFrozenState(boolean  value){
        frozenState = value;
    }
    
    //returns frozen state
    public boolean getFrozenState() {
        return frozenState;
    }
    
    public int getFrozenStateDuration() {
        return frozenStateDuration;
    }

    public void incrementFrozenStateDuration() {
        this.frozenStateDuration = 500000;
    }
    
    public void decrementFrozenStateDuration() {
        this.frozenStateDuration -= 1032 ;
    }
    public Bullet getMyBullet() {
        return myBullet;
    }
     
    public int getLife(){
         return life;
    }
    
    public void setLife(int life){
        this.life= life;
    }
  
    public int getDirection() {
        return direction;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public void setBullet(Bullet bull){
        myBullet = bull;
    }
    
    public void feuer(GameEngine engine){
                MediaPlayer mediaPlayer;
                Media sound = new Media(MainMenuController.class.getResource("sound/shoot.mp3").toExternalForm());
                mediaPlayer = new MediaPlayer(sound);  
                mediaPlayer.play();
    }
    
    // below are motion methods
    public void moveUp(boolean released){
      if(released || frozenState){
            setSpeedY(0);
            //direction = 0;
        }else   if(!frozenState){{
            direction = 0;
            setSpeedY(-1*tank_speed);
            setSpeedX(0);
            setImg(0);// up img
          }
        }    
    }
    
    public void moveDown(boolean released){
        if(released || frozenState) {
            setSpeedY(0);
           // direction = 1;
        }else  if(!frozenState){
            direction = 1;
            setSpeedY(1*tank_speed);
            setSpeedX(0);            
            setImg(1);
            }
    }
    
    public void moveLeft(boolean released){
        if(released || frozenState){
            setSpeedX(0);
           // direction = 2;
        }else if(!frozenState){
                direction = 2;
                setSpeedX(-1*tank_speed);
                setSpeedY(0);
                setImg(2); // left img
            }
    }
    
    public void moveRight(boolean released){        
        if(released || frozenState){
            setSpeedX(0);
           // direction = 3;
        }else if(!frozenState){
            direction = 3;
            setSpeedX(1*tank_speed);
            setSpeedY(0);
            setImg(3); // left img
        }
    }
    
    public void moveInDirection(int dir, boolean moveOrStop){
        if(dir==0)
            moveUp(moveOrStop);
        if(dir==1)
            moveDown(moveOrStop);
        if(dir==2)
            moveLeft(moveOrStop);
        if(dir==3)
            moveRight(moveOrStop);
    }

}