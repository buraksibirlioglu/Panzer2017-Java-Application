/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javax.swing.Timer;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class Bonus extends GameObject{
    
    FadeTransition  fadeTransition ;
    long duration ;
    boolean brute_destroy;

    public Bonus(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);
          // fadeTransition = new FadeTransition (Duration.millis(_speed), this.getObjectView());
    }
    
    public void setBrute_destroy(boolean brute_destroy) {
        this.brute_destroy = brute_destroy;
    }

    public boolean isBrute_destroy() {
        return brute_destroy;
    }
   
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }
    
    // skip this for now
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
