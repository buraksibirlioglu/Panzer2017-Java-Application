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
 * @author Burak
 */
public class Castle extends GameObject {
    private int life;
    private int type;
    public Castle(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, double _speed, ArrayList<Image> _icon,int life,int type) {
        super(_isAlive, _coordinateX, _coordinateY, width, height, _speed, _icon);
        this.life=life;
        this.type=type;
        if(type==1)
            setImg(0);
        if(type==2)
            setImg(1);
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
