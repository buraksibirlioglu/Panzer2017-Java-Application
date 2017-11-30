/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class Brick extends GameObject{
    private int life;
    private int type;
    public Brick(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height,int type) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);
        if(type==1){
            setCustomImg(new Image(Panzer2017.class.getResource("images/brown_brick.png").toExternalForm(),40,40,false,false));
            life=1;
        }
        else if(type==2){
            setCustomImg(new Image(Panzer2017.class.getResource("images/green_brick.png").toExternalForm(),40,40,false,false));
            life=2;
        }
        else if(type==4){
            setCustomImg(new Image(Panzer2017.class.getResource("images/gray_brick.png").toExternalForm(),40,40,false,false));
            life=999999;
        }
        else if(type==3){
            setCustomImg(new Image(Panzer2017.class.getResource("images/red_brick.png").toExternalForm(),40,40,false,false));
            life=3;
        }
        
    }
    public int getLife()
    {
        return life;
    }
    public void setLife(int life)
    {
        this.life=life;
    }
      
    
}
