/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.entities;


/**
 *
 * @author Ndricim Rrapi
 */
public class Castle extends GameObject{
    
    private int life;
    
    public Castle(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height,int life) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);
        this.life=life;
       
    }
    
    public int getLife() {
        return life;
    }
    
    public void setLife(int life) {
        this.life=life;
    }
   
}
