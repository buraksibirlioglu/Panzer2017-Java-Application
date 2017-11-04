/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import panzer.entities.Map;
import panzer.entities.Tank;
import panzer.pkg2017.GamePanel;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class GameEngine {
    Tank playerTank;
    ArrayList<EnemyTank> enemyTankList;
    Map map; 
  
    
    public GameEngine() throws IOException{
        playerTank = createPlayerTank();    
        enemyTankList= createEnemyTankArrayList();
        map=new Map(800,640);
    }
    
    private Tank createPlayerTank(){
        ArrayList<Image> icon = new ArrayList<>();
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_up.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_down.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_left.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_right.png").toExternalForm()));
        Tank temp = new Tank(true, 0, 0,60, 50,1, icon,5);
           //myTank = T.getObjectView();
        temp.getObjectView().setFocusTraversable(true);             
        temp.getObjectView().addEventFilter(KeyEvent.KEY_PRESSED, new TankButtonHandler(temp) );
        return temp;
    }
    
    private ArrayList<EnemyTank> createEnemyTankArrayList(){
        ArrayList<EnemyTank> enemies = new ArrayList<>();
        enemies.add(createSingleEnemyTank(0, 0, 0, 0));
        enemies.add(createSingleEnemyTank(0, 1, 0, 0));
        enemies.add(createSingleEnemyTank(0, 0, 2, 0));
        return enemies;
    }
    public Map getMap()
    {
        return map;
    }
    
    private EnemyTank createSingleEnemyTank(int param1, int param2, int speed3, int etc){
        return null;
    }
    
      public Tank getPlayerTank() {
        return playerTank;
    }
    
    class TankButtonHandler implements EventHandler<KeyEvent>{  
        boolean up,left,down,right;
        ImageView tankView ;
        Tank thisTank;
        
        public TankButtonHandler(Tank t) {
            thisTank = t;
            tankView = t.getObjectView();
        }         
         
        @Override
        public void handle(KeyEvent e){            
             switch (e.getCode()) {
                 case W:
                     if(tankView.getLayoutY()>=0){
                         thisTank.moveUp();
                     }        break;
                 case S:
                     if(tankView.getLayoutY()<=270){
                         thisTank.moveDown();
                     }    break;
                 case D:
                     if(tankView.getLayoutX()<=470 ){
                         thisTank.moveRight();
                     }    break;
                 case A:
                     if(tankView.getLayoutX()>=0 ){
                         thisTank.moveLeft();
                     }     break;
                 default:
                     break;
            }
        }
    }
    
}
