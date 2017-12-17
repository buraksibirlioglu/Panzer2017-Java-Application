/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import javafx.scene.image.Image;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class Bullet extends GameObject{
    private int direction;
    private double range; 
  
    Tank master;
    
    public Bullet(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, Tank master, int direction, int range, int speed) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);
        this.direction = direction;
        this.range = range;
        this.master = master;
  
      
        if(direction == 0){ // tank looking upwards
            setCoordinateX(_coordinateX+14);
            setCoordinateY(_coordinateY);
            speedX = 0;
            speedY = -1*speed;
        }else if(direction == 1){ // tank looking down
            setCoordinateX(_coordinateX+14);
            setCoordinateY(_coordinateY+34);
            speedY = 1*speed;
            speedX = 0;
           
        }else if(direction == 2){// tank looking left
            setCoordinateX(_coordinateX);
            setCoordinateY(_coordinateY+14);
            speedX = -1*speed;
            speedY = 0;
        }else if(direction == 3){// tank looking right
            setCoordinateX(_coordinateX+34);
            setCoordinateY(_coordinateY+14);
            speedX = 1*speed;
            speedY = 0;
        }
        
    }
        
        public Tank getBulletOwner(){
            return master;
        }
        
        public void decrementBulletRange(){
            range -= 15;
        }
        
        public double getRange() {
            return range;
        }
        
        public void setRange(double range) {
            this.range = range;
        }
}