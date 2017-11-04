/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Ndricim Rrapi
 */
public class GameObject {
    private boolean alive;
    private double coordinateX;  
    private double coordinateY;
    private double    speed;
    private int width;
    private int height;
    private ArrayList<Image> icon = new ArrayList<>();
    private ImageView objectView  ;
    private TranslateTransition animation;

  
   
    public GameObject(boolean _isAlive, double _coordinateX, double  _coordinateY, int width, int height, double _speed, ArrayList<Image> _icon){
        alive = _isAlive;   
        speed = _speed;
        icon = _icon;
        objectView = new ImageView();
        setImg(0);
        coordinateX = _coordinateX;
        coordinateY = _coordinateY;
        this.height = height;
        this.width = width;
        objectView.setFitHeight(height);
        objectView.setFitWidth(width);         
        objectView.setLayoutX(_coordinateX);
        objectView.setLayoutY(_coordinateY);
        animation = new TranslateTransition(Duration.millis(speed));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public TranslateTransition getAnimation() {
        return animation;
    }
    
    public ImageView getObjectView() {
        return objectView;
    }
         
    public boolean isAlive() {
        return alive;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public double getSpeed() {
        return speed;
    }

    public ArrayList<Image> getIcon() {
        return icon;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setImg (int num){
        objectView.setImage(icon.get(num));
    }
    
    
        
}
