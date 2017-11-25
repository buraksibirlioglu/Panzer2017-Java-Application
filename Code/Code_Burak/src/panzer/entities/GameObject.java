/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import javafx.scene.image.Image;
/**
 *
 * @author Ndricim Rrapi
 */
public class GameObject {
    private boolean alive;
    private double coordinateX;  
    private double coordinateY;
    private int    width;
    private int    height;
  private ArrayList<Image> icon = new ArrayList<>();

    
    private Image currImage;
    private boolean movingParent;
    protected double speedX;
    protected double speedY;
  
    public GameObject(boolean _isAlive, double _coordinateX, double  _coordinateY, int width, int height){
        alive        = _isAlive;
        coordinateX  = _coordinateX;
        coordinateY  = _coordinateY;
        this.height  = height;
        this.width   = width;
        movingParent = false;
    }
public void setIcon(ArrayList<Image> icon) {
        this.icon = icon;
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
    
    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }
       
    // set up the movement of the objects, tanks'
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
    } 

    //check the collision between objects
    public boolean collisionCheck(GameObject obj) {
        
        if (this instanceof EnemyTank && obj instanceof PlayerTank){
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()+ obj.getSpeedX()) , (int)(obj.getCoordinateY()+obj.getSpeedY()), obj.getWidth(), obj.getHeight());
            if(rectOne.intersects(rectTwo))
               return true;
        }
        if (this instanceof EnemyTank && obj instanceof EnemyTank){
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()+ obj.getSpeedX()) , (int)(obj.getCoordinateY()+obj.getSpeedY()), obj.getWidth(), obj.getHeight());
            if(rectOne.intersects(rectTwo))
               return true;
        }
         if (this instanceof EnemyTank && obj instanceof Brick){
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()) , (int)(obj.getCoordinateY()) , 40 , 40);
            if(rectOne.intersects(rectTwo))
               return true;
        }        
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
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()) , (int)(obj.getCoordinateY()) , obj.getWidth() , obj.getHeight());
            if(rectOne.intersects(rectTwo))
               return true;
        }
        if (this instanceof Bullet && obj instanceof Tank){
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()) , (int)(obj.getCoordinateY()) , obj.getWidth() , obj.getHeight());
             if(rectOne.intersects(rectTwo))
               return true;
        }
        return false;
    }
       
    public boolean isMovingParent() {
        return movingParent;
    }
         
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public ArrayList<Image> getIcon() {
        return icon;
    }
    
    public void setIconArrayList(ArrayList<Image> img){
        icon= img;
    }
    
    public Image getImg() {
        return  currImage;
    }
    
      public void setImg (int num){
        currImage  = icon.get(num);
    }
      public void setCustomImg (Image img){
          currImage= img;
      }
       
    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }
    
    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }
       
    public void setAlive(boolean alive) {
        this.alive = alive;
    }   
    
    public boolean isAlive() {
        return alive;
    }
       
}