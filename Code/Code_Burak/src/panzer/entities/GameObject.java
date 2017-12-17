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
        if (this instanceof Bonus && obj instanceof PlayerTank){
            Rectangle rectOne = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
            Rectangle rectTwo = new Rectangle((int)(obj.getCoordinateX()) , (int)(obj.getCoordinateY()) , obj.getWidth() , obj.getHeight());
            if(rectOne.intersects(rectTwo))
               return true;
        }
        return false;
    }
       
    
    /**
     * This method predicts whether the enemy is heading towards a closed area
     * Since EnemyTank's usually decide a new random direction/position when hitting
     * another object they might end up merging with the object(EnemyTank or Brick)
     * which could by coincidence be exactly at the place of the new random 
     * position. This would cause a major glitch in the enemy behavior.In order 
     * to avoid that this method makes a lookahead predicate and tells warns the
     * GameEngine that this EnemyTank cannot move towards a random new position.
     * Instead, this method will generate a new correct direction for this tank to move
     * 
     * @param paramObj : Could be a Brick or an EnemyTank
     * 
     * @return 
     * case : {0 = up, 1 = down, 2 = left, 3 = right} If one of these 
     * are returned they define that EnemyTank has gone to a dead end and needs to 
     * move in this new direction in order to get out of there
     * 
     * case : {-1} : In this case, EnemyTank has a clear path on 4 sides, so the nor-
     * mal random function can be applied
     * 
     * case : {4,5,6,7}: In this case the EnemyTank is surrounded on 4 sides. Thus 
     * the tank should shoot and then move to get out of that difficult position
     * The return being > 3 signifies just that. Furthermore, subtracting 4 from
     * any of the returned values will give the direction in which the Tank
     * needs to move after it has shot the brick
     * 
     * case: {8,9,10,11}: In this case the EnemyTank is about to collide with
     * another EnemyTank. The environment receiving values in this interval
     * will know what is happening. Subtracting 8 from these values will give
     * the new direction in which the EnemyTank should move
     */
    public int collisionPredicate(GameObject paramObj){
        // initialize variables to keep the position of the tanks 
        double thisPosX = this.getCoordinateX()+ this.getSpeedX();
        double thisPosY = this.getCoordinateY()+ this.getSpeedY();
        double paramX   = paramObj.getCoordinateX() + paramObj.getSpeedX();
        double paramY   = paramObj.getCoordinateY() + paramObj.getSpeedY();
        
        //scenario 1: There is some object on my (this) left, that I should know about
        if (thisPosX - paramX <= 40){
            
        }
        //scenario 2: There is some object on my (this) right, that I should know about
        else if(thisPosX-paramX >= -40){
            
        }
        //scenario 3: There is some object on my (this) right, that I should know about
        Rectangle thisObjRect  = new Rectangle((int)(coordinateX + speedX) , (int)(coordinateY + speedY), this.getWidth(), this.getHeight());
        Rectangle paramObjRect = new Rectangle((int)(paramObj.getCoordinateX()+ paramObj.getSpeedX()) , (int)(paramObj.getCoordinateY()+paramObj.getSpeedY()), paramObj.getWidth(), paramObj.getHeight());
           return 1;
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