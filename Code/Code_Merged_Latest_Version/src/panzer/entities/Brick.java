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
    public Brick(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);
        setCustomImg(new Image(Panzer2017.class.getResource("images/brown_brick.png").toExternalForm(),40,40,false,false));
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
