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
public class MetalBullet extends Bullet{
    
    public MetalBullet(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, Tank master, int direction, int range, int speed) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, master, direction, range, speed);
        setCustomImg(new Image(Panzer2017.class.getResource("images/bullet.png").toExternalForm(),10,10,false,false));
    }
    
}
