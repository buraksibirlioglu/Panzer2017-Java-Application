/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 *
 * @author Ndricim Rrapi
 */
public class Bonus extends GameObject{
    
    FadeTransition  fadeTransition ;
   
    public Bonus(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, double _speed, ArrayList<Image> _icon) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon);
        fadeTransition = new FadeTransition (Duration.millis(_speed), this.getObjectView());
    }
    
    public FadeTransition getFadeTransition() {
        return fadeTransition;
    }
    
    public void startBonus(){
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
       // fadeTransition.setCycleCount(5);
        //fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }
    
    public Animation.Status transitionState(){
        return fadeTransition.getStatus();
    }
    
}
