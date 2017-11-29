/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author Ndricim Rrapi
 */
public class IceBullet extends Bullet {
    
    public IceBullet(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, Tank master, int direction, int range) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, master, direction, range);
    }
    
}
