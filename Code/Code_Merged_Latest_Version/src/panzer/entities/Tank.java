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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
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

   
     
    private int direction; // 0 up , 1 down , 2 left, 3 right 
    private boolean moving;

    public boolean isMoving() {
        return moving;
    }
    public Tank(boolean _isAlive, float _coordinateX, float _coordinateY,int width, int height, double _speed, ArrayList<Image> _icon, int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon);  
        this.life = life;
    }
      
     public Bullet getMyBullet() {
        return myBullet;
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
        if(myBullet != null) return;
                ArrayList<Image> iconList = new ArrayList<>();
                iconList.add(new Image(Panzer2017.class.getResource("images/bullet.png").toExternalForm(),10,10,false,false));
		Bullet bullet = new Bullet(true, getCoordinateX(), getCoordinateY(), 10,10, 10, iconList, this, direction, 500);
		engine.getAllObjectsList().add(bullet);
		engine.getBulletList().add(bullet);
		myBullet = bullet;
                 MediaPlayer mediaPlayer;
                    Media sound = new Media(MainMenuController.class.getResource("sound/shoot.mp3").toExternalForm());
                    mediaPlayer = new MediaPlayer(sound);  
                    mediaPlayer.play();
    }
    
    // below are motion methods
    public void moveUp(boolean released){
      if(released){
            setSpeedY(0);
            //direction = 0;
        }else{
            direction = 0;
            setSpeedY(-5);
            setSpeedX(0);
            setImg(0);// up img
        }    
    }
    
    public void moveDown(boolean released){
        if(released){
            setSpeedY(0);
           // direction = 1;
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
           // direction = 2;
        }else{
            direction = 2;
            setSpeedX(-5);
            setSpeedY(0);
            setImg(2); // left img
        }
    }
    
    public void moveRight(boolean released){
        if(released){
            setSpeedX(0);
           // direction = 3;
        }else{
            direction = 3;
            setSpeedX(5);
            setSpeedY(0);
            setImg(3); // left img
        }
    }
    
}
    
    
    
    
    
    
    

