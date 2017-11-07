/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.awt.Rectangle;
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
    private Image currImage;
    private boolean movingParent;
    private boolean movingNew;


 
    protected double speedX;

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }
    protected double speedY;

    public GameObject(boolean _isAlive, double _coordinateX, double  _coordinateY, int width, int height, double _speed, ArrayList<Image> _icon){
        alive = _isAlive;   
        speed = _speed;
        icon  = _icon;       
        objectView = new ImageView(_icon.get(0));
        currImage = _icon.get(0);
        coordinateX = _coordinateX;
        coordinateY = _coordinateY;
        this.height = height;
        this.width = width;
        movingParent= false;
        animation = new TranslateTransition(Duration.millis(speed));
    }

    public void setMovingParent(boolean movingParent) {
        this.movingParent = movingParent;
    }
    
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }
    
     public void update(int endOfMapX){
         if(endOfMapX ==2){             
            coordinateX  -= speedX*2;
            coordinateY  = 555;// -= speedY*2;
         }
         else if (endOfMapX ==1){
            coordinateX  += speedX;
            coordinateY  += speedY;
         }
         else if (endOfMapX == 3){
               coordinateX  = 0;
         }
          else if (endOfMapX == 4){
               coordinateY  = 0;
         } else if (endOfMapX == 5){
               coordinateX  = 958;
         }else if (endOfMapX == 6){
             coordinateY += speedY;
         }
       // movingParent = movingNew;
    } 

    public boolean isMovingParent() {
        return movingParent;
    }
     
    public void setMovingNew(boolean movingNew) {
        this.movingNew = movingNew;
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
    
    public Image getImg() {
        return currImage;
    }
    public void setImg (int num){
        currImage  = icon.get(num);
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
    
    public boolean collisionCheck(GameObject obj) {
        if (this instanceof PlayerTank && obj instanceof Brick){
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()) , (int)(obj.getCoordinateY()) , 40 , 40);
            if(rectOne.intersects(rectTwo))
               return true;
        }
        if (this instanceof Bullet && obj instanceof Brick){
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()) , (int)(obj.getCoordinateY()) , 40 , 40);
            if(rectOne.intersects(rectTwo))
               return true;
        } if (this instanceof Bullet && obj instanceof Castle){
           // System.out.println("hit");
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()) , (int)(obj.getCoordinateY()) , obj.getWidth() , obj.getHeight());
            if(rectOne.intersects(rectTwo))
               return true;
        }
        return false;
    }
   
    
}
