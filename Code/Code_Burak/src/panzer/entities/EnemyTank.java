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
    private int enemyType;
  
    public EnemyTank(boolean _isAlive, float _coordinateX, float _coordinateY, int width, int height, int life, int speed_of_tank, int enemyType) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, life);
        setEnemyIcons(enemyType);
        setIconArrayList(life_enemy);
        setCustomImg(life_enemy.get(0));
        setTank_speed(speed_of_tank);
        this.enemyType = enemyType;
        if(enemyType==4)
           iceBullet = true ;
    }
    
    // detects if the player's castle is near to this enemy tank
    public boolean castleIsNear(){
        return false; // TO BE IMPLEMENTED
    }
    
    public int getEnemyType() {
        return enemyType;
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
    
    private void setEnemyIcons(int enemyType){
        if(enemyType == 1){
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_normal_up.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_normal_down.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_normal_left.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_normal_right.png").toExternalForm(),38,38,false,false));
        } else if(enemyType == 2){
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_fast_up.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_fast_down.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_fast_left.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_fast_right.png").toExternalForm(),38,38,false,false));
        }else if(enemyType == 3){
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_powerful_up.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_powerful_down.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_powerful_left.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_powerful_right.png").toExternalForm(),38,38,false,false));
        }else if(enemyType == 4){
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_ice_up.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_ice_down.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_ice_left.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy_ice_right.png").toExternalForm(),38,38,false,false));
        }
    }
    
    public ArrayList<Image> getEnemyImages() {
        return life_enemy;
    }
      
    public void shootMetalOrIce(){
        if (iceBullet){
            shootEnemyIce();
        }else {
            shootEnemyMetal();
        }
    }
    
    public void shootEnemyMetal(){
        if(myBullet != null) return;
        Bullet bullet = new MetalBullet(true, getCoordinateX(), getCoordinateY(), 10,10, this, direction, 500, 5);
        GameEngine.getAllObjectsList().add(bullet);
        GameEngine.getBulletList().add(bullet);
        myBullet = bullet;
        feuer();
    }
    
    public void shootEnemyIce(){
        if(myBullet != null) return;
        Bullet bullet= null;
        if(hasIceBullet()){
            bullet = new IceBullet(true, getCoordinateX(), getCoordinateY(), 10,10, this, direction, 500, 5);
            GameEngine.getAllObjectsList().add(bullet);
            GameEngine.getBulletList().add(bullet);
            myBullet = bullet;
            feuer(); // boom
        }
    }
}