/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Ndricim Rrapi
 */
public class Tank extends GameObject{
    private int life;
    //Bullet this bullet 
      
    private int direction; // 0 up , 1 down , 2 left, 3 right 
    private boolean moving;

    public boolean isMoving() {
        return moving;
    }
    public Tank(boolean _isAlive, float _coordinateX, float _coordinateY,int width, int height, double _speed, ArrayList<Image> _icon, int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon);  
        this.life = life;
    }
      
    public int getDirection() {
        return direction;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public void moveUp(boolean released){
      if(released){
            setSpeedY(0);
            direction = 0;
         //  setMovingNew(false);
        }else{
         //  setMovingNew(true);
            direction = 0;
            setSpeedY(-5);
            setSpeedX(0);
            setImg(0);// up img
        }    
    }
    
    public void moveDown(boolean released){
        if(released){
          setSpeedY(0);
            direction = 1;
      //     setSpeedX(0);
     //  setMovingNew(false);
      }else{
             setMovingNew(true);
        direction = 1;
        setSpeedY(5);
        setSpeedX(0);            
        setImg(1);
        }
    }
    
    public void moveLeft(boolean released){
        if(released){
          setSpeedX(0);
          direction = 2;
         //  setMovingNew(false);
        }else{
           //  setMovingNew(true);
            direction = 2;
            setSpeedX(-5);
            setSpeedY(0);
            setImg(2); // left img
        }
    }
    
    public void moveRight(boolean released){
        if(released){
            setSpeedX(0);
            direction = 3;
          //   setMovingNew(false);
      }else{
         //   setMovingNew(true);
              direction = 3;
          setSpeedX(5);
            setSpeedY(0);
               setImg(3); // left img
        }
    }
    
}
    
    
    
    
    
    
    

