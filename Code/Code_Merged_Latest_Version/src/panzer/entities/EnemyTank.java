/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;


import java.util.ArrayList;
import javafx.scene.image.Image;
import panzer.brainClass.GameEngine;
import panzer.entities.PlayerTank;
import panzer.entities.Tank;
import panzer.pkg2017.Panzer2017;

/**
 **  @author
 **/
public class EnemyTank extends Tank {
    
 
    private ArrayList<Image> life_enemy = new ArrayList<>();  
    
    public EnemyTank(boolean _isAlive, float _coordinateX, float _coordinateY, int width, int height, int life, int speed_of_tank, boolean ice_shooter) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, life);
        setEnemyIcons();
        setIconArrayList(life_enemy);
        setCustomImg(life_enemy.get(0));
        setTank_speed(speed_of_tank);
        iceBullet = ice_shooter ;
    }
    
    // detects if the player's castle is near to this enemy tank
    public boolean castleIsNear(){
        return false;
    }
    
    // detects if the player tank is near to this enemy tank
    public boolean playerIsNear(PlayerTank t){
      
        if (t.getCoordinateX() + t.getWidth() - this.getCoordinateX()+this.getWidth() < 10 ){
            return true;
        }
        else if (t.getCoordinateY() + t.getHeight() - this.getCoordinateY() + this.getHeight() < 10){
            return true;
        }
        return false;   
    }
    
 
    
     private void setEnemyIcons(){
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_up.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_down.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_left.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_right.png").toExternalForm(),38,38,false,false));
    }
    
    public ArrayList<Image> getEnemyImages() {
        return life_enemy;
    }
      
    public void shootMetalOrIce(GameEngine engine){
        if (iceBullet){
            shootEnemyIce(engine);
        }else {
            shootEnemyMetal(engine);
        }
    }
    
    public void shootEnemyMetal(GameEngine engine){
        if(myBullet != null) return;
        Bullet bullet = new MetalBullet(true, getCoordinateX(), getCoordinateY(), 10,10, this, direction, 500, 5);
        engine.getAllObjectsList().add(bullet);
        engine.getBulletList().add(bullet);
        myBullet = bullet;
        feuer(engine);
    }
    
    public void shootEnemyIce(GameEngine engine){
        if(myBullet != null) return;
        Bullet bullet= null;
        if(hasIceBullet()){
            bullet = new IceBullet(true, getCoordinateX(), getCoordinateY(), 10,10, this, direction, 500, 5);
            engine.getAllObjectsList().add(bullet);
            engine.getBulletList().add(bullet);
            myBullet = bullet;
            feuer(engine); // boom
        }
    }
}