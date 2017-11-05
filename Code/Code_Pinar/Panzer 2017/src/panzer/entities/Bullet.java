/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javax.swing.Timer;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author ENVY
 */
public class Bullet extends GameObject{
    private int life;
     private ImageView bulletView  ;
    //Bullet this bullet 
  
    
    public Bullet(boolean _isAlive, double _coordinateX, double _coordinateY,int width, int height, double _speed, ArrayList<Image> _icon, int life) {
       super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon);
        this.life = life;
        bulletView = new ImageView();
    }
    
    public void shoot(){
      
       
      
    }
}
