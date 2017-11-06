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
        this.setCoordinateX(posXtoBe);
        this.setCoordinateY(posYtoBe);
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
        this.setCoordinateX(posXtoBe);
        this.setCoordinateY(posYtoBe);
        getAnimation().play();
    }
    
    public void moveLeft(){
        double posXtoBe = getObjectView().getLayoutX()-5;
        double posYtoBe = getObjectView().getLayoutY();
        this.setCoordinateX(posXtoBe);
        this.setCoordinateY(posYtoBe);
        getAnimation().setToX(posXtoBe);
        getAnimation().setToY(posYtoBe);       
        getObjectView().setLayoutX(posXtoBe);
        getObjectView().setLayoutY(posYtoBe);
        setImg(2); // left img
        getAnimation().setNode(getObjectView());
        getAnimation().play();
        this.setCoordinateX(posXtoBe);
        this.setCoordinateY(posYtoBe);
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
           this.setCoordinateX(posXtoBe);
        this.setCoordinateY(posYtoBe);
    }
    
}
    
    
    
    
    
    
    

