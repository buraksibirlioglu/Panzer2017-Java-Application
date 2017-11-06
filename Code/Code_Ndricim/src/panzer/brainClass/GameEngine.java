/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import panzer.entities.Bonus;
import panzer.entities.PlayerTank;
import panzer.entities.Tank;
import panzer.pkg2017.GamePanel;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class GameEngine {
    PlayerTank playerTank;
    ArrayList<EnemyTank> enemyTankList;
    public MyAnimationTimer timer;
    ArrayList<Bonus> bonusList;

   
    public GameEngine(){
        playerTank = createPlayerTank();    
        enemyTankList= createEnemyTankArrayList();
        timer = new MyAnimationTimer();
        bonusList = createBonusList();
    }
    
    private ArrayList<Bonus> createBonusList(){
        ArrayList<Bonus> temp = new ArrayList<>();
        temp.add(createBonus(100, 100,1 ));
        temp.add(createBonus(200, 100,2 ));
        temp.add(createBonus(250, 300,3 ));
        temp.add(createBonus(400, 200,4 ));
        return temp;
    }
    
    private Bonus createBonus(int x, int y, int type){
        String fileName ="images/"; 
        if ( type == 1){
            fileName += "power_speed.png";
        }else if (type == 2){
             fileName += "power_freeze.png";
        }else if (type == 3){
             fileName += "power_shield.png";
         }else if (type == 4){
             fileName += "power_fast_bullets.png";
        }
        ArrayList<Image> icon = new ArrayList<>();
        icon.add(new Image(Panzer2017.class.getResource(fileName).toExternalForm()));
        return new Bonus(true,x,y,60,60,6000,icon);
    }
     public ArrayList<Bonus> getBonusList() {
        return bonusList;
    }
    
    
    private PlayerTank createPlayerTank(){
        ArrayList<Image> icon = new ArrayList<>();
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_up.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_down.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_left.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_right.png").toExternalForm()));
        PlayerTank temp = new PlayerTank(true, 0, 0,60, 50,1, icon,5);
           //myTank = T.getObjectView();
        temp.getObjectView().setFocusTraversable(true);             
    
        temp.getObjectView().addEventFilter(KeyEvent.KEY_PRESSED, new TankButtonHandler(temp) );
        return temp;
    }
    
    private ArrayList<EnemyTank> createEnemyTankArrayList(){
        ArrayList<EnemyTank> enemies = new ArrayList<>();
        enemies.add(createSingleEnemyTank(200));
        enemies.add(createSingleEnemyTank(400));
       enemies.add(createSingleEnemyTank(300));
        return enemies;
    }
    
    private EnemyTank createSingleEnemyTank(int x){
        ArrayList<Image> icon = new ArrayList<>();
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_up.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_down.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_left.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_right.png").toExternalForm()));
        EnemyTank temp = new EnemyTank(true, x, 0,60, 50,1000000, icon,1);
      
        temp.getObjectView().setFocusTraversable(true);             
        
   //     temp.getObjectView().addEventFilter(KeyEvent.KEY_PRESSED, new TankButtonHandler(temp) );
        return temp;
    }
    
    public void moveTheBadTank(EnemyTank t, PlayerTank p){
        boolean directionUp,directionDown,directionLeft,directionRight;
        
        while(!t.playerIsNear(p)){
            while (t.getObjectView().getLayoutY()>=0){
                 t.moveUp();
            }
            while(t.getObjectView().getLayoutY()<=270){
                t.moveDown();
            }                 
            while(t.getObjectView().getLayoutX()<=470 ){
                t.moveRight();
            }              
            while(t.getObjectView().getLayoutX()>=0 ){
                t.moveLeft();
            }
        }
        System.err.println("Found my enemy muhahahaha");
    }
    
    public PlayerTank getPlayerTank() {
        return playerTank;
    }
    
    public EnemyTank getEnemyTank(int n) {
        return enemyTankList.get(n);
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
    
    public class MyAnimationTimer extends AnimationTimer{
       boolean down = true;
            boolean up = false;
            boolean left = false;
            boolean right = false;
            int bonusCount = 100;
        @Override
        public void handle(long now) {
           moveEnemy(0 );
    //       moveEnemy(1);
           //moveEnemy(2);
           //if (exampleBonus.transitionState=Animation.Status.)
        }
        
        public void moveEnemy(int i ){              
            
                if(down)
                    enemyTankList.get(i).moveDown();
                if(right)
                    enemyTankList.get(i).moveRight();
                if(up){
                     enemyTankList.get(i).moveUp();
                }
                if (left ){
                   enemyTankList.get(i).moveLeft();
                }

                if (!(enemyTankList.get(i).getObjectView().getLayoutY()<=550)){
                    down = false;
                    right=true;
                }
                if (!(enemyTankList.get(i).getObjectView().getLayoutX()<=944 )){
                    right = false;
                    if (!(right && down))
                        up = true;
                }                  

                if (! (enemyTankList.get(i).getObjectView().getLayoutY()>=0)){
                    up = false;
                    left = true;
                }

                 if  (!(enemyTankList.get(i).getObjectView().getLayoutX()>=0 )){                     
                     left = false;
                    if (!(left && right && down && up)){
                        down = true;
                    }
                }
        }
    }
        
    }
         
              
        
    

