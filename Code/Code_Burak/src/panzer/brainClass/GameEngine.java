/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import panzer.entities.Bonus;
import panzer.entities.Brick;
import panzer.entities.Castle;
import panzer.entities.EnemyCastle;
import panzer.entities.PlayerTank;
import panzer.entities.Tank;
import panzer.pkg2017.GamePanel;
import panzer.entities.EnemyTank;
import panzer.entities.GameObject;
import panzer.entities.Map;
import panzer.entities.PlayerCastle;
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
    ArrayList<GameObject> allObjectsList ;
    ArrayList<Castle> castleList;
    Map map;
   // Constructor   
    public GameEngine() throws IOException{
        allObjectsList = new ArrayList<>();
        timer = new MyAnimationTimer();
        map = new Map(100,600);
    }
    
    // populate with objects
    public void initializeLevel1(){
        enemyTankList= createEnemyTankArrayList();
        bonusList = createBonusList();
        playerTank = createPlayerTank();  
        allObjectsList.add(playerTank);
//        for (int i = 0; i < bonusList.size(); i++){
//            allObjectsList.add(bonusList.get(i));
//        }
        for (int i = 0; i < map.getBricks().size(); i++){
            allObjectsList.add(map.getBricks().get(i));
        }
        castleList = createCastles();
        allObjectsList.add(castleList.get(0));
        allObjectsList.add(castleList.get(1));
        for (int i = 0; i < enemyTankList.size(); i++){
            allObjectsList.add(enemyTankList.get(i));
        }
    }
    
    // type = 1 = playerCastle ,
    public ArrayList<Castle> createCastles(){
        ArrayList<Castle> castle;
        ArrayList<Image> castleImage = new ArrayList<>();
        castle=new ArrayList<>();
        castleImage.add(new Image(Panzer2017.class.getResource("images/player_king.png").toExternalForm(),40,60,false,false));
        castleImage.add(new Image(Panzer2017.class.getResource("images/enemy_king.png").toExternalForm(),40,60,false,false));
        PlayerCastle C = new PlayerCastle(true, 0, 250,60, 60, 0,castleImage,50); // player castle
        castle.add(C);
        EnemyCastle  enemy =new EnemyCastle(true, 959, 250,60, 60, 0,castleImage,50); // enemy 
        castle.add(enemy);
        return castle;
    }
    
    public ArrayList<GameObject> getAllObjectsList() {
        return allObjectsList;
    }
    
    public void addAGameObject(GameObject object){
        allObjectsList.add(object);
    }
    
    private ArrayList<Bonus> createBonusList(){
        ArrayList<Bonus> temp = new ArrayList<>();
        temp.add(createBonus(100, 100,1 ));
        temp.add(createBonus(200, 100,2 ));
        temp.add(createBonus(250, 300,3 ));
        temp.add(createBonus(400, 200,4 ));
        return temp;
    }
    
    private Bonus createBonus(double x, double y, int type){
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
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_up.png").toExternalForm(),38,38,false,false));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_down.png").toExternalForm(),38,38,false,false));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_left.png").toExternalForm(),38,38,false,false));
        icon.add(new Image(Panzer2017.class.getResource("images/user_tank_right.png").toExternalForm(),38,38,false,false));
        PlayerTank temp = new PlayerTank(true, 200, 200,38, 38,1, icon,5);
        return temp;
    }
    
    private ArrayList<EnemyTank> createEnemyTankArrayList(){
        ArrayList<EnemyTank> enemies = new ArrayList<>();
        enemies.add(createSingleEnemyTank(200));
        enemies.add(createSingleEnemyTank(400));
        enemies.add(createSingleEnemyTank(300));
        return enemies;
    }
    
    private EnemyTank createSingleEnemyTank(float x){
        ArrayList<Image> icon = new ArrayList<>();
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_up.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_down.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_left.png").toExternalForm()));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_right.png").toExternalForm()));
        EnemyTank temp = new EnemyTank(true, x, 0,60, 50,1000000, icon,1);
        temp.getObjectView().setFocusTraversable(true);             
        return temp;
    }
        
    public PlayerTank getPlayerTank() {
        return playerTank;
    }
    
    public EnemyTank getEnemyTank(int n) {
        return enemyTankList.get(n);
    }
    
    public  class HandleKeyPressed implements EventHandler<KeyEvent>{  
         
        @Override
        public void handle(KeyEvent e){   
          
            switch (e.getCode()) {
               case W :
                  getPlayerTank().moveUp(false);
                  break;
               case S:
                  getPlayerTank().moveDown(false);
                  break;
               case D:
                  getPlayerTank().moveRight(false);
                  break;
               case A:
                  getPlayerTank().moveLeft(false);
                  
            }   
        }
    }
    
    public  class HandleKeyReleased implements EventHandler<KeyEvent>{  

        @Override
        public void handle(KeyEvent e){   
            switch (e.getCode()) {
               case W:
                  getPlayerTank().moveUp(true);
                  break;
               case S:
                  getPlayerTank().moveDown(true);
                  break;
               case D:
                  getPlayerTank().moveRight(true);
                  break;
               case A:
                   getPlayerTank().moveLeft(true);
                  break;
            }
        }
    }
  
    public class MyAnimationTimer extends AnimationTimer{
        Canvas thisCanvas;
    
        final int updateTime = 8; // in ms
        boolean right = false;
        int bonusCount = 100;
        int point;
        int time;
        GraphicsContext gc ;
       
        long oldNanoTime = System.nanoTime();
        
        @Override
        public void handle(long now) {
            //moveEnemy(0 ); moves enemy over the map 
            gc.clearRect(0, 0, 1000, 650);
            drawAllObjectsOnScren(gc,point,time);
handleCollision();
//            System.out.println("X=" + playerTank.getCoordinateX() );
//            System.out.println("Y=" + playerTank.getCoordinateY() );
               if(playerTank.getCoordinateY() <= 555 && playerTank.getCoordinateX() >= 0  && playerTank.getCoordinateY() >=0  && playerTank.getCoordinateX() <=958){
                    playerTank.update(1);// 1 = allows movement down
               }
               else if (playerTank.getCoordinateY()>=556 ) // 2 = limit movement beyond lower boundary y < 556
                   playerTank.update(2);
               
               else if (playerTank.getCoordinateX() < 0 )
                   playerTank.update(3);// 3 = limit movement beyond left boundary x > 0
                   
               else if (playerTank.getCoordinateY() < 0){
                    playerTank.update(4);// 4 = limit movement above upper boundary Y > 0
               }else if (playerTank.getCoordinateX() >958){
                    playerTank.update(5);// 4 = limit movement beyond right boundary x <945
               }
        }
        
        public void updateIt(){
               for(int i = 0; i < getAllObjectsList().size(); i ++){
                 GameObject o =  getAllObjectsList().get(i);
                if (o instanceof Tank){
                  // playerTank.update(true);
                }
            }
        }
        
      
        public void drawAllObjectsOnScren(GraphicsContext g,int point,int time){
           for(int i = 0; i < getAllObjectsList().size() ; i++){
                GameObject o = getAllObjectsList().get(i);
			if(o.isAlive()){
                            g.drawImage(o.getImg(), (int)(o.getCoordinateX()) , (int)(o.getCoordinateY()));                       
                        }
		}
            g.setStroke(Color.RED);
            g.setLineWidth(1);
            g.strokeLine(0, 601, 1000, 601);
            g.drawImage(new Image(Panzer2017.class.getResource("images/player_king.png").toExternalForm(),30,40,false,false), 15, 605);
            g.drawImage(new Image(Panzer2017.class.getResource("images/enemy_king.png").toExternalForm(),30,40,false,false), 955, 605);
            g.drawImage(new Image(Panzer2017.class.getResource("images/health.png").toExternalForm(),250,40,false,false), 60, 605);
            g.drawImage(new Image(Panzer2017.class.getResource("images/health.png").toExternalForm(),250,40,false,false), 690, 605);
            g.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
            g.fillText("Points : "+point , 345, 635);
            g.drawImage(new Image(Panzer2017.class.getResource("images/power_freeze.png").toExternalForm(),40,40,false,false), 500, 605);
            g.fillText(time+" s " , 555, 635);
        }
        
        public void setGraphics(GraphicsContext c){
            gc = c;
        }
                
    }
    
    public void handleCollision(){
        for(int i = 0 ; i < allObjectsList.size() ; i++){
            for(int j = 0 ; j < allObjectsList.size() ; j++){
                if(i == j) continue;
                if(i >= allObjectsList.size()) break;
                    GameObject obj1 = allObjectsList.get(i);
                    GameObject obj2 = allObjectsList.get(j);
                    if(allObjectsList.get(i).collisionCheck(allObjectsList.get(j))){
                       if(obj1 instanceof Tank && obj2 instanceof Brick){
						//if(obj2 instanceof GrassTile) continue;
						obj1.setSpeedX(0.0f);
						obj1.setSpeedY(0.0f);
						

					}           
                                    
                    }
            }
        }
    }
 
        
    }
         
              
        
    

