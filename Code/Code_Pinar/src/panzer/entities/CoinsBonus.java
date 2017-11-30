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
public class CoinsBonus extends Bonus{
    
    private int bonusPoints;
    private int coinType;
 
    public CoinsBonus(boolean _isAlive, double _coordinateX, double _coordinateY, int width, int height, int coinType) {
        super(_isAlive, _coordinateX, _coordinateY, width, height);
        this.duration =800000; // decrementing this value, when 0 bonus is deleted ! 
        this.coinType = coinType;
        if(coinType == 1){ // normal tank
            setCustomImg( new Image(Panzer2017.class.getResource("images/coin_type_1.png").toExternalForm(),38,38,false,false));
            bonusPoints = 100;
        }
        if(coinType == 2){ //fast tank
            setCustomImg( new Image(Panzer2017.class.getResource("images/coin_type_2.png").toExternalForm(),38,38,false,false));
            bonusPoints = 300;
        }
        if(coinType == 3){ // strong tank
            setCustomImg( new Image(Panzer2017.class.getResource("images/coin_type_3.png").toExternalForm(),38,38,false,false));
            bonusPoints = 800;
        }
        if(coinType == 4){//ice tank
            setCustomImg( new Image(Panzer2017.class.getResource("images/coin_type_4.png").toExternalForm(),38,38,false,false));
            bonusPoints = 500;
        }
    }
    
    public int getBonusPoints() {
        return bonusPoints;
    }

    public int getCoinType() {
        return coinType;
    }
    
}
