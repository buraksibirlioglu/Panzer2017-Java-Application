/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import panzer.brainClass.GameEngine;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class Tank extends GameObject{
    private int life;
    //Bullet this bullet 
      protected Bullet currentBullet;
    
    public Tank(boolean _isAlive, double _coordinateX, double _coordinateY,int width, int height, double _speed, ArrayList<Image> _icon, int life) {
       super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon);
        this.life = life;
    }
      
    public void moveUp(){
        double posXtoBe = getObjectView().getLayoutX();
        double posYtoBe = getObjectView().getLayoutY()-5;
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe);
        setImg(0);// up img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
    
    public void moveDown(){
        double posXtoBe = getObjectView().getLayoutX();
        double posYtoBe = getObjectView().getLayoutY()+5;
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe);
        setImg(1);// down img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
    
    public void moveLeft(){
        double posXtoBe = getObjectView().getLayoutX()-5;
        double posYtoBe = getObjectView().getLayoutY();
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe);
           setImg(2); // left img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
    
    public void moveRight(){
        double posXtoBe = getObjectView().getLayoutX()+5;
        double posYtoBe = getObjectView().getLayoutY();
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe); 
        setImg(3);//right img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
    public void fire() {
		if(currentBullet != null) return;
               
                ArrayList<Image> icon = new ArrayList<>();
                icon.add(new Image(Panzer2017.class.getResource("images/bullet.png").toExternalForm()));
		Bullet bullet = new Bullet(true, 60, 60,70, 70,1, icon,5);
                bullet.getObjectView().setFocusTraversable(true);  
                currentBullet = bullet;
                
                Animation anim;
                anim = getAnimation();
                getAnimation(). setNode(getObjectView());
                anim.play();
               
      
	}
    public void jumpRight(){
        double posXtoBe = getObjectView().getLayoutX()+30;
        double posYtoBe = getObjectView().getLayoutY();
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe); 
        setImg(3);//right img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
    public void jumpLeft(){
        double posXtoBe = getObjectView().getLayoutX()-30;
        double posYtoBe = getObjectView().getLayoutY();
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe); 
        setImg(2);//right img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
    public void jumpUp(){
        double posXtoBe = getObjectView().getLayoutX();
        double posYtoBe = getObjectView().getLayoutY()-30;
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe); 
        setImg(0);//right img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
    public void jumpDown(){
        double posXtoBe = getObjectView().getLayoutX();
        double posYtoBe = getObjectView().getLayoutY()+30;
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe);
        setImg(1);// down img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
    }
}
    
    
    
    
    
    
    

