/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;

import java.util.ArrayList;
import javafx.scene.image.Image;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class PlayerCastle extends Castle {
    
    public PlayerCastle(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, life);
        setCustomImg(new Image(Panzer2017.class.getResource("images/player_king.png").toExternalForm(),40,60,false,false));
    }
    
}
