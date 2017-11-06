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
public class PlayerCastle extends Castle {
    
    public PlayerCastle(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, double _speed, ArrayList<Image> _icon, int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon, life);
           setImg(0);
    }
    
}
