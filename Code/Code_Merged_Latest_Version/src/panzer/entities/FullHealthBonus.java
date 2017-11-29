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
public class FullHealthBonus extends Bonus{
    
    public FullHealthBonus(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);
          this.duration =1000000; // decrementing this value, when 0 bonus is deleted ! 
      setCustomImg( new Image(Panzer2017.class.getResource("images/power_life.png").toExternalForm(),38,38,false,false));
   
    }
    
}
