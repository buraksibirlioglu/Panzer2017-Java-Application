/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;


import java.util.ArrayList;
import javafx.scene.image.Image;
import panzer.entities.PlayerTank;
import panzer.entities.Tank;
import panzer.pkg2017.Panzer2017;

/**
 **  @author
 **/
public class EnemyTank extends Tank {
    
    private boolean frozenState;
    private ArrayList<Image> life_enemy = new ArrayList<>();  
    private int bulletType;
    
    public EnemyTank(boolean _isAlive, float _coordinateX, float _coordinateY, int width, int height, int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, life);
        setEnemyIcons();
        setIconArrayList(life_enemy);
        setCustomImg(life_enemy.get(0));
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
    
    // sets the state of this enemy tank to frozen/ melted
    public void setTankFrozenState(boolean  value){
        frozenState = value;
    }
    
    //returns frozen state
    public boolean isFrozenState() {
        return frozenState;
    }
     private void setEnemyIcons(){
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_up.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_down.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_left.png").toExternalForm(),38,38,false,false));
        life_enemy.add(new Image(Panzer2017.class.getResource("images/enemy1_right.png").toExternalForm(),38,38,false,false));
      }
    
}