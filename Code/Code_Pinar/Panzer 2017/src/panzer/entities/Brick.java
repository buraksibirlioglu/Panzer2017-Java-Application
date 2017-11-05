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
public class Brick extends GameObject{
    
    public Brick(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, int _speed, ArrayList<Image> _icon) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon);
    }
    
}
