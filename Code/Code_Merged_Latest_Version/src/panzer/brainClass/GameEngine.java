/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import panzer.entities.Bonus;
import panzer.entities.Brick;
import panzer.entities.Bullet;
import panzer.entities.Castle;
import panzer.entities.CoinsBonus;
import panzer.entities.EnemyCastle;
import panzer.entities.EnemyFreezeBonus;
import panzer.entities.PlayerTank;
import panzer.entities.Tank;
import panzer.entities.EnemyTank;
import panzer.entities.FastBulletBonus;
import panzer.entities.FileManager;
import panzer.entities.FullHealthBonus;
import panzer.entities.GameObject;
import panzer.entities.IceBullet;
import panzer.entities.Map;
import panzer.entities.MetalBullet;
import panzer.entities.PlayerCastle;
import panzer.entities.ProtectionBonus;
import panzer.entities.SpeedBonus;
import panzer.pkg2017.MainMenuController;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class GameEngine {

    int level;    
    PlayerTank playerTank;
    private String username;
    public boolean soundOnOrOff;
    ArrayList<EnemyTank> enemyTankList;   
    ArrayList<Bonus> bonusList;
    ArrayList<Castle> castleList;
    ArrayList<Bullet> bulletList;
    ArrayList<GameObject> allObjectsList ; 
    Map map;
    Image imageEnemyCastle, imagePlayerCastle;    
    int points;
    boolean drawBottomBar = true;
    static boolean exit = false;
    Canvas canvas;    
    boolean win = false;
    FileManager fileManager;
    public MyAnimationTimer timer;
    private final String ENEMY_COLLIDE_PLAYER = "sound/buzz_effect.mp3";
    private final String BULLET_COLLIDE_BRICK = "sound/destroy_brick.mp3";
    private final String BULLET_COLLIDE_ENEMY = "sound/enemy_shot.mp3";
    private final String BULLET_COLLIDE_PLAYER = "sound/player_shot.mp3";
    private final String GAME_LOST = "sound/game_lost.mp3";
    private final String SOUND_ON_IMG = "images/sound_on.png";
    private final String SOUND_OFF_IMG = "images/sound_off.png";  
   // Constructor   
    public GameEngine() throws IOException{      
        fileManager= new FileManager();   
        soundOnOrOff = fileManager.getSettings()[0] == 1; // 1 means sound is on, else sound will be false
        level = 1;
        exit = false;
        timer = new MyAnimationTimer();
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }
    
    // populate with objects
    public void initializeLevel1(boolean restartLevel){    
       
        allObjectsList = new ArrayList<>();
        try {        
            if(restartLevel){
                allObjectsList = new ArrayList<>();
                map = new Map(100,600,1);
              
            }
            else 
                map = new Map(100,600,level);
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        enemyTankList= createEnemyTankArrayList();
        bonusList = createBonusList();
        playerTank = new PlayerTank(true, 200, 200,38, 38,5);
      
        allObjectsList.add(playerTank);
        bulletList = new ArrayList<>();
        
        for (int i = 0; i < map.getBricks().size(); i++){
            allObjectsList.add(map.getBricks().get(i));
        }        
        setHealthBarEnemyCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy1.png").toExternalForm(),250,40,false,false));
        setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king1.png").toExternalForm(),250,40,false,false));
       
        castleList = createCastles();
        allObjectsList.add(castleList.get(0));
        allObjectsList.add(castleList.get(1));
        for (int i = 0; i < enemyTankList.size(); i++){
            allObjectsList.add(enemyTankList.get(i));
        }
        createBonusList();
        timer.start();
    }
    
    // creates both enemy-friendly castles
    public ArrayList<Castle> createCastles(){
        ArrayList<Castle> castle=new ArrayList<>();
        castle.add(new PlayerCastle(true, 0, 250,60, 60,60));
        castle.add(new EnemyCastle(true, 959, 250,60, 60,60));
        return castle;
    }
    
     // createas a list of bonuses
    private ArrayList<Bonus> createBonusList(){
        ArrayList<Bonus> temp = new ArrayList<>();
        temp.add(new EnemyFreezeBonus(true, 100,100,38,38 ));
        temp.add(new FastBulletBonus(true, 200,500,38,38 ));
        temp.add(new FullHealthBonus(true, 500,200,38,38 ));
       
        temp.add(new FullHealthBonus(true, 500,400,38,38 ));
        temp.add(new FullHealthBonus(true, 500,500,38,38 ));
        temp.add(new ProtectionBonus(true, 250,250,38,38 ));
        temp.add(new SpeedBonus(true, 200,250,38,38 ));
        temp.add(new FastBulletBonus(true, 200,250,38,38 ));
        temp.add(new FastBulletBonus(true, 250,250,38,38 ));
        temp.add(new EnemyFreezeBonus(true, 100,100,38,38 ));
        temp.add(new EnemyFreezeBonus(true, 550,250,38,38 ));
        temp.add(new EnemyFreezeBonus(true, 600,250,38,38 ));
        temp.add(new SpeedBonus(true, 200,250,38,38 ));
        temp.add(new FullHealthBonus(true, 500,500,38,38 ));        
        temp.add(new SpeedBonus(true, 500,350,38,38 ));
        temp.add(new EnemyFreezeBonus(true, 550,300,38,38 ));
        temp.add(new ProtectionBonus(true, 400,250,38,38 ));
        temp.add(new FastBulletBonus(true, 300,250,38,38 ));
        temp.add(new FullHealthBonus(true, 250,250,38,38 ));
        return temp;
    }
    
    // assigning initial location of enemy tanks
    private ArrayList<EnemyTank> createEnemyTankArrayList(){
        ArrayList<EnemyTank> enemies = new ArrayList<>();
        enemies.add(createSingleEnemyTank(820,250,1,1,1));//type 1 = normal tanks
        enemies.add(createSingleEnemyTank(800,350,1,3,2));//type 2 = fast tanks
        enemies.add(createSingleEnemyTank(850,400,5,1,3));//type 3 = life points = 5
        enemies.add(createSingleEnemyTank(800,150,1,1,4));//
        enemies.add(createSingleEnemyTank(810,50, 1,1,4));
        enemies.add(createSingleEnemyTank(850,450,1,1,4));
        return enemies;
    }
    
    private EnemyTank createSingleEnemyTank(float x, float y, int lifepoints , int speed_of_tank,int enemyType){
        EnemyTank temp = new EnemyTank(true, x, y,38, 38,lifepoints,speed_of_tank,enemyType);   
        return temp;
    }
    // returns all of the objects currently on the game
  
    public ArrayList<GameObject> getAllObjectsList() {
        return allObjectsList;
    }
       
    public ArrayList<Bonus> getBonusList() {
        return bonusList;
    }
            
    public PlayerTank getPlayerTank() {
        return playerTank;
    }
    
     // sets the image to the health bar of the enemy king
    public void setHealthBarEnemyCastle(Image img){
        imageEnemyCastle = img;
    }
    
     // sets the image to the health bar of the enemy king
    public void setHealthBarPlayerCastle(Image img){
        imagePlayerCastle = img;
    }
    
    // Custom event handler for keyboard on press
    public  class HandleKeyPressed implements EventHandler<KeyEvent>{  
         
        @Override
        public void handle(KeyEvent e){   
          
            switch (e.getCode()) {               
                case UP :  
                    if (getPlayerTank().isAlive())
                        getPlayerTank().moveUp(false);
                    break;
               case DOWN:
                    if (getPlayerTank().isAlive())
                        getPlayerTank().moveDown(false);
                    break;
               case RIGHT:
                    if (getPlayerTank().isAlive())
                        getPlayerTank().moveRight(false);
                    break;
               case LEFT:
                    if (getPlayerTank().isAlive())
                        getPlayerTank().moveLeft(false);
                    break;
               case SPACE:{
                    if (getPlayerTank().isAlive())
                    getPlayerTank().shootMetal(GameEngine.this);    
                }
                    break;
                case B:{
                if (getPlayerTank().hasIceBullet())
                   getPlayerTank().shootIce(GameEngine.this);    
                }
                    break;
                  
            }   
        }
    }
    
    // Custom event handler for keyboard on release 
    public  class HandleKeyReleased implements EventHandler<KeyEvent>{  

        @Override
        public void handle(KeyEvent e){   
            switch (e.getCode()) {
               case UP:
                  getPlayerTank().moveUp(true);
                  break;
               case DOWN:
                  getPlayerTank().moveDown(true);
                  break;
               case RIGHT:
                  getPlayerTank().moveRight(true);
                  break;
               case LEFT:
                   getPlayerTank().moveLeft(true);
                  break;
           case P:
                    timer.stop();
                try {
                    Parent root = FXMLLoader.load(Panzer2017.class.getResource("PauseMenu.fxml"));
                    Scene nnew=new Scene(root);                    
                    Stage pause_stage=new Stage();
                    pause_stage.setTitle("Pause Menu");
                    pause_stage.setScene(nnew);
                    pause_stage.show();
                    pause_stage.setOnHidden(t -> {
                        try {
                            showPauseMenu();
                        } catch (IOException ex) {
                              ex.printStackTrace();
                        }
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }                
                   break;
            }
        }
    }
  
    public void showPauseMenu() throws IOException {
        System.out.println(exit);
        if(exit==false){
            System.out.println("menu1");
            timer.start();}
        else{
        System.out.println("menu2");
        canvas.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Panzer2017.class.getResource("MainMenu.fxml"));
        Scene nnew=new Scene(root);                    
        Stage pause_stage=new Stage();
        pause_stage.setScene(nnew);
        pause_stage.show();}
    } 
     
    // The timer which runs the code on a certain framerate to offer smooth gameplay 
    public class MyAnimationTimer extends AnimationTimer{
        Canvas thisCanvas;
        GraphicsContext gc ;
        int time;
        long oldNanoTime = System.nanoTime();  
        long oldNanoTime1 = System.nanoTime(); 
        long enemyNanoTime = 0;   
        long previosTime1 = 0;
        long previosTimeBonus = 0;
        
        
        @Override
        public void handle(long now) {
            //moveEnemy(0 ); moves enemy over the map 
            
            gc.clearRect(0, 0, 1000, 600);// clear field map
           
            gc.clearRect(390, 0, 104, 650); 
            gc.clearRect(555,600,100,300);
   
            drawAllObjectsOnScren(gc,points,time,drawBottomBar);
            
            previosTime1 += System.nanoTime() - oldNanoTime;
            oldNanoTime1 = System.nanoTime(); // update old nano time
            if( previosTime1 / 1000000.0 > 8 ){
                previosTime1 = 0;
                handleCollision(gc); 
                drawAllObjectsOnScren(gc,points,time,drawBottomBar);                         
            }
            
          //enemyTank.update(1);
            drawBottomBar = false;
            keepPlayerTankWithinBounds();
              for (int i = 0; i < enemyTankList.size();i++)
                  keepEnemyTankWithinBounds(enemyTankList.get(i));
            
            
            enemyNanoTime      += System.nanoTime() - oldNanoTime;
            previosTimeBonus += System.nanoTime() - oldNanoTime;
            oldNanoTime = System.nanoTime(); // update old nano time
            if(enemyNanoTime / 1000000.0 > 2000){ 
                for (int i = 0; i < enemyTankList.size();i++) {  
                    if(!playerOnSight(enemyTankList.get(i)))
                        findARoute(enemyTankList.get(i));//  
                }
                enemyNanoTime = 0;
              
                //random enemy bullet shot 
                int random = getRandom();
                int eIndex = random/100; // tanks shoot here, what if they are null
                if(eIndex < enemyTankList.size()){
                    if(enemyTankList.get(eIndex) != null){
                        if(random >=0 && random <= 500)
                           enemyTankList.get(eIndex).shootMetalOrIce(GameEngine.this);
                        if(random >500 && random <= 1000)
                           enemyTankList.get(eIndex).shootMetalOrIce(GameEngine.this);
                        if(random >1000 && random <= 1500)
                           enemyTankList.get(eIndex).shootMetalOrIce(GameEngine.this);
                    }
                }
            }
              if(previosTimeBonus / 1000000.0 > 10000){ 
                  System.out.println("created bonus---------");   
                  if (bonusList.size()!=0){
                      allObjectsList.add( bonusList.get( bonusList.size()-1 ));
                  }
                  previosTimeBonus = 0;
              }          
                        
            setBulletMotion(getPlayerTank());
            
            for (int i = 0; i < enemyTankList.size();i++){
                setBulletMotion(enemyTankList.get(i));               
                shootIfOnSight( enemyTankList.get(i));
            }
            checkBonusExpired();
            checkEnemyFrozenDuration();
        }
        
        public void checkBonusExpired(){
            if(getPlayerTank().getMyBonusDuration() > 0){
                getPlayerTank().decrementMyBonusDuration();
                //System.out.println("decrementing");
                if(getPlayerTank().getMyBonusDuration()<=0){
                    if (getPlayerTank().getTank_speed() > 1)
                        getPlayerTank().setTank_speed(1);
                    if(getPlayerTank().hasShieldProtection()){
                        getPlayerTank().setShieldProtection(false);
                    }if(getPlayerTank().hasSuperBullet()){
                        getPlayerTank().setHasSuperBullet(false);
                    }if(getPlayerTank().hasIceBullet()){
                        getPlayerTank().setHasIceBullet(false);
                    }
                }
            }            
        }
        
        public void checkEnemyFrozenDuration(){
            if(getPlayerTank().getFrozenStateDuration()> 0){
                getPlayerTank().decrementFrozenStateDuration();
                if(getPlayerTank().getFrozenStateDuration() <=0){
                    getPlayerTank().setFrozenState(false);
                    getPlayerTank().setIconArrayList(getPlayerTank().getCurrentPlayerIcon());
                    getPlayerTank().setCustomImg(getPlayerTank().getCurrentPlayerIcon().get(getPlayerTank().getDirection()));
                }
            }
            for (int i = 0; i < enemyTankList.size();i++){
                if (enemyTankList.get(i).getFrozenStateDuration() > 0){
                    enemyTankList.get(i).decrementFrozenStateDuration();
                    if (enemyTankList.get(i).getFrozenStateDuration()<=0){
                        enemyTankList.get(i).setFrozenState(false);
                        enemyTankList.get(i).setCustomImg(enemyTankList.get(i).getEnemyImages().get(0));
                        enemyTankList.get(i).setIconArrayList(enemyTankList.get(i).getEnemyImages());
                        System.out.println("defrost ENEMY TANK " + i);
                    }
                }
            }
        }
                
        public void setBulletMotion(Tank enemyOrFriendly){
             if( enemyOrFriendly.getMyBullet() != null){
                decerementBulletRange();
                 if (enemyOrFriendly.getDirection() == 0){
                    if( enemyOrFriendly.getMyBullet() != null){
                         enemyOrFriendly.getMyBullet().setCoordinateY(enemyOrFriendly.getMyBullet().getCoordinateY()+enemyOrFriendly.getMyBullet().getSpeedY());
                    }
                 }else if (enemyOrFriendly.getDirection() == 1){
                    if( enemyOrFriendly.getMyBullet() != null){
                        enemyOrFriendly.getMyBullet().setCoordinateY(enemyOrFriendly.getMyBullet().getCoordinateY()+enemyOrFriendly.getMyBullet().getSpeedY());
                    }
                 }else if (enemyOrFriendly.getDirection() == 2){
                    if( enemyOrFriendly.getMyBullet() != null){
                        enemyOrFriendly.getMyBullet().setCoordinateX(enemyOrFriendly.getMyBullet().getCoordinateX()+enemyOrFriendly.getMyBullet().getSpeedX());
                    }
                 }else if (enemyOrFriendly.getDirection() == 3){
                    if( enemyOrFriendly.getMyBullet() != null){
                         enemyOrFriendly.getMyBullet().setCoordinateX(enemyOrFriendly.getMyBullet().getCoordinateX()+enemyOrFriendly.getMyBullet().getSpeedX());
                    }
                }
            }
        }
       
        public void drawAllObjectsOnScren(GraphicsContext g,int point,int time, boolean drawKings)  {
           for(int i = 0; i < getAllObjectsList().size() ; i++){
                GameObject o = getAllObjectsList().get(i);
			if(o.isAlive()){
                            //System.out.println("hi this is me");
                            g.drawImage(o.getImg(), (int)(o.getCoordinateX()) , (int)(o.getCoordinateY()));                       
                        }                        
                        // regulate bonus timing, fix the duration of the bonus on the map
                        if(o instanceof Bonus){                         
                            Bonus thisBonus = ((Bonus) o);
                            long duration = thisBonus.getDuration() ;
                            if(thisBonus.isBrute_destroy()){
                                deleteBonusFromMap(thisBonus);
                            }
                            else  if (duration > 0 ){    
                                thisBonus.setDuration(duration-1000);
                               // System.out.println(thisBonus.getDuration()+"decrementing duration==+?!!@#!@##!#!##@!#!#!#@!#!#!#");
                                if (thisBonus.getDuration() ==0 ){
                                     // System.out.println(duration+"duration CIMIIIIIII");
                                    deleteBonusFromMap(thisBonus);
                                        g.clearRect( 500, 605,40,40);
                                }
                            }
                        }
		}
            
           
            g.setFill(Color.WHITE);
            g.setFont(Font.font("Verdana", FontWeight.LIGHT, 25));
            g.fillText("Points :   " , 345, 635);
            g.fillText("Points : "+point , 345, 635);
            g.setStroke(Color.RED);
            g.setLineWidth(1);
            g.strokeLine(0, 601, 1000, 601);
            
            if(soundOnOrOff)
                g.drawImage(new Image(Panzer2017.class.getResource(SOUND_ON_IMG).toExternalForm(),30,30,false,false), 5, 5);     
            if(!soundOnOrOff)
                g.drawImage(new Image(Panzer2017.class.getResource(SOUND_OFF_IMG).toExternalForm(),30,30,false,false), 5, 5);
                
            
            
            if(getPlayerTank().getMyBonusDuration() > 0){
                if( getPlayerTank().getTank_speed() > 1 && drawKings ){ // he is moving fast
                    g.drawImage(new Image(Panzer2017.class.getResource("images/power_speed.png").toExternalForm(),40,40,false,false), 500, 605);
                }else if(getPlayerTank().hasShieldProtection() && drawKings){
                     g.drawImage(new Image(Panzer2017.class.getResource("images/power_shield.png").toExternalForm(),40,40,false,false), 500, 605);
            }
                
              g.setFill(Color.YELLOW);
              g.setFont(Font.font("Arial", FontWeight.BOLD, 25));
              g.fillText(getPlayerTank().getMyBonusDuration()/10000 +"", 555, 635);
            }
           if(drawKings){
            g.drawImage(new Image(Panzer2017.class.getResource("images/player_king.png").toExternalForm(),30,40,false,false), 15, 605);
            g.drawImage(new Image(Panzer2017.class.getResource("images/enemy_king.png").toExternalForm(),30,40,false,false), 955, 605);
           
            g.drawImage(imagePlayerCastle, 60, 605);
            g.drawImage(imageEnemyCastle, 690, 605);
           }
            canvas = g.getCanvas();
        }
        
        public void setGraphics(GraphicsContext c){
            gc = c;
        }      
        
        public void deleteBonusFromMap(Bonus bonus){
            bonus.setAlive(false);
            for(int m = 0; m < bonusList.size();m++){
                if ((bonusList.get(m).getDuration()<=0) || bonusList.get(m).isBrute_destroy() ) {
                    bonusList.remove(m);                                        
                }              
            }
        }
    }
    
    // method which stops player tank  to go beyond map bounds
    public void keepPlayerTankWithinBounds(){
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
    
    //method which stops enemy tanks to go beyond map bounds
    public void keepEnemyTankWithinBounds(EnemyTank enemyTank){
        if(!enemyTank.getFrozenState()){
            if(enemyTank.getCoordinateY() <= 555 && enemyTank.getCoordinateX() >= 0  && enemyTank.getCoordinateY() >=0  && enemyTank.getCoordinateX() <=958){
                     enemyTank.update(1);// 1 = allows movement down
            }
            else if (enemyTank.getCoordinateY()>=556 ){ // 2 = limit movement beyond lower boundary y < 556
                enemyTank.update(2);
                 enemyTank.moveUp(false);
            }
            else if (enemyTank.getCoordinateX() < 0 ){
                enemyTank.update(3);// 3 = limit movement beyond left boundary x > 0
                 enemyTank.moveRight(false);
            }
            else if (enemyTank.getCoordinateY() < 0){
               //  changeRoute();
                enemyTank.update(4);// 4 = limit movement above upper boundary Y > 0
                enemyTank.moveDown(false);
            }else if (enemyTank.getCoordinateX() >958){
                enemyTank.update(5);// 4 = limit movement beyond right boundary x <945
                enemyTank.moveLeft(false);
            }
        }
    }
       
    // generate a random number
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(1500) + 1;
    }
    
    // changes enemy tank's route when evoked - randomly decide a 
    public void findARoute(EnemyTank enemyTank){
        if(!enemyTank.getFrozenState()){
            Random rand = new Random();
            int  n = rand.nextInt(1500) + 1;
          //  System.out.println("random = "+n);
            if(n >= 0 && n <= 200){
                if(enemyTank.getDirection()==0){//up
                    enemyTank.moveDown(false);
                    return;
                }else if (enemyTank.getDirection()==1){//down          
                    enemyTank.moveUp(false);
                    return;
                }else if (enemyTank.getDirection()==2){//left          
                    enemyTank.moveRight(false);
                    return;
                }else if (enemyTank.getDirection()==3){//right          
                    enemyTank.moveLeft(false);
                    return;
                }
            }
            else if(n > 200  && n <= 400){
                if(enemyTank.getDirection()==0){//up
                    enemyTank.moveLeft(false);
                    return;
                }else if (enemyTank.getDirection()==1){//down          
                    enemyTank.moveRight(false);              
                    return;
                }else if (enemyTank.getDirection()==2){//left          
                    enemyTank.moveDown(false);
                    return;
                }else if (enemyTank.getDirection()==3){//right          
                    enemyTank.moveDown(false);
                    return;
                } 
            }
            else if(n > 400 && n <= 600){
                if(enemyTank.getDirection()==0){//up
                    enemyTank.moveRight(false);
                    return;
                }else if (enemyTank.getDirection()==1){//down          
                    enemyTank.moveLeft(false);
                    return;
                }else if (enemyTank.getDirection()==2){//left          
                    enemyTank.moveUp(false);
                    return;
                }else if (enemyTank.getDirection()==3){//right          
                    enemyTank.moveUp(false);
                    enemyTank.shootMetalOrIce(GameEngine.this);
                    return;
                } 
            }
            else if(n > 600 && n <= 800){
                if(enemyTank.getDirection()==0){//up
                    enemyTank.moveDown(false);
                    return;
                }else if (enemyTank.getDirection()==1){//down          
                    enemyTank.moveRight(false);
                    return;
                }else if (enemyTank.getDirection()==2){//left          
                    enemyTank.moveUp(false);
                    return;
                }else if (enemyTank.getDirection()==3){//right          
                    enemyTank.moveRight(false);
                    return;
                }   
            }
            else if (n > 800 && n <= 900){
                if(enemyTank.getDirection()==0){//up
                    enemyTank.moveLeft(false);
                    return;
                }else if (enemyTank.getDirection()==1){//down          
                    enemyTank.moveRight(false);
                    return;
                }else if (enemyTank.getDirection()==2){//left          
                    enemyTank.moveDown(false);
                    return;
                }else if (enemyTank.getDirection()==3){//right          
                    enemyTank.moveDown(false);
                    return;
                }   
            }
        }
    }
    
    //decided what happens when object collide
    public void handleCollision(GraphicsContext c){
        for(int i = 0 ; i < allObjectsList.size() ; i++){
            for(int j = 0 ; j < allObjectsList.size() ; j++){
                if(i == j) continue;
                if(i >= allObjectsList.size()) break;
                GameObject obj1 = allObjectsList.get(i);
                GameObject obj2 = allObjectsList.get(j);
                if(allObjectsList.get(i).collisionCheck(allObjectsList.get(j))){
                   if(obj1 instanceof PlayerTank && obj2 instanceof Brick){
                        obj1.setSpeedX(0.0f);
                        obj1.setSpeedY(0.0f);
                    }  
                   if(obj1 instanceof EnemyTank && obj2 instanceof Brick){ 
                        if( obj1.getCoordinateX()-obj2.getCoordinateX() <=-38){
                           obj1.setCoordinateX(obj1.getCoordinateX()-1);
                        }
                        if( obj1.getCoordinateX()-obj2.getCoordinateX() >=38){
                           obj1.setCoordinateX(obj1.getCoordinateX()+1);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() <=-38){
                           obj1.setCoordinateY(obj1.getCoordinateY()-1);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() >=38){
                           obj1.setCoordinateY(obj1.getCoordinateY()+1);
                        }
                        findARoute((EnemyTank)obj1);
                    }
                    if(obj1 instanceof EnemyTank && obj2 instanceof PlayerTank){   
                        if( obj1.getCoordinateX()-obj2.getCoordinateX() >= 38){
                            obj1.setCoordinateX(obj1.getCoordinateX()+5);
                            obj2.setCoordinateX(obj2.getCoordinateX()-5);
                        }                        
                         if( obj1.getCoordinateX()-obj2.getCoordinateX() <=-38){
                            obj1.setCoordinateX(obj1.getCoordinateX()-1);
                            obj2.setCoordinateX(obj2.getCoordinateX()+1);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() <=-38){
                           obj1.setCoordinateY(obj1.getCoordinateY()-1);
                            obj2.setCoordinateY(obj2.getCoordinateY()+1);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() >=38){
                            obj1.setCoordinateY(obj1.getCoordinateY()+1);
                            obj2.setCoordinateY(obj2.getCoordinateY()-1);
                        }
                        playSound(ENEMY_COLLIDE_PLAYER);
                        findARoute((EnemyTank)obj1);
                    }
                    if(obj1 instanceof EnemyTank && obj2 instanceof EnemyTank){   
                        if( obj1.getCoordinateX()-obj2.getCoordinateX() >= 38){
                           obj1.setCoordinateX(obj1.getCoordinateX()+5);
                            obj2.setCoordinateX(obj2.getCoordinateX()-5);
                        }                        
                         if( obj1.getCoordinateX()-obj2.getCoordinateX() <=-38){
                            obj1.setCoordinateX(obj1.getCoordinateX()-5);
                            obj2.setCoordinateX(obj2.getCoordinateX()+5);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() <=-38){
                           obj1.setCoordinateY(obj1.getCoordinateY()-5);
                            obj2.setCoordinateY(obj2.getCoordinateY()+5);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() >=38){
                            obj1.setCoordinateY(obj1.getCoordinateY()+5);
                            obj2.setCoordinateY(obj2.getCoordinateY()-5);
                        }
                        findARoute((EnemyTank)obj1);
                        findARoute((EnemyTank)obj2);
                    }
                    if(obj1 instanceof Bullet && obj2 instanceof Brick){
                        Brick a=(Brick)obj2;
                         a.setLife(a.getLife()-1);
                         
                         if(a.getLife()==0){
                            System.out.println(a.getLife());
                            allObjectsList.remove(j);
                            obj2.setAlive(false);}
                        obj1.setAlive(false);
                        Bullet b = (Bullet)obj1;
                        b.setSpeedX(0);
                        b.setSpeedY(0);
                        playSound(ENEMY_COLLIDE_PLAYER);
                        if(b.getBulletOwner()== getPlayerTank())
                            points++;
                    }
                    if(obj1 instanceof Bullet && obj2 instanceof EnemyTank){                   
                         Bullet b = (Bullet)obj1;
                        if(b.getBulletOwner()==getPlayerTank()){
                            if(b instanceof IceBullet){
                                obj1.setAlive(false);
                                b.setSpeedX(0);
                                b.setSpeedY(0);
                                System.out.println("shotttt Enemy with ICEEE");                       
                                EnemyTank t = (EnemyTank)obj2;
                                t.setFrozenState(true);
                                t.incrementFrozenStateDuration();
                                t.setCustomImg(new Image(Panzer2017.class.getResource("images/enemy1_frozen.png").toExternalForm(),38,38,false,false));
                                playSound(BULLET_COLLIDE_ENEMY);
                            }else if (b instanceof MetalBullet){
                                obj1.setAlive(false);
                                b.setSpeedX(0);
                                b.setSpeedY(0);
                                System.out.println("shotttt Enemy");                       
                                EnemyTank t = (EnemyTank)obj2;
                                allObjectsList.remove(i);
                                if(t.getLife() >=1){                                    
                                    System.out.println("LIFE = "+ t.getLife());
                                    t.setLife(t.getLife()-1);// decrement life
                                  
                                    if (t.getLife() == 0){
                                        double posX = t.getCoordinateX();
                                        double posY = t.getCoordinateY();
                                        int enemyType= t.getEnemyType();
                                        t.setAlive(false);
                                        allObjectsList.get(j).setAlive(false);
                                        allObjectsList.remove(j);
                                        for(int m = 0; m<enemyTankList.size();m++){
                                            if(!enemyTankList.get(m).isAlive()){
                                                enemyTankList.remove(m);
                                            }
                                        }
                                        Bonus coin = new  CoinsBonus(true, posX, posY, 38, 38, enemyType);                                        
                                        allObjectsList.add(coin);
                                        
                                    }
                                }
                                playSound(BULLET_COLLIDE_ENEMY);
                                points+=50;
                            }
                        }
                    }
                     if(obj1 instanceof Bullet && obj2 instanceof PlayerTank){                   
                        Bullet b = (Bullet)obj1;
                        if (b.getBulletOwner()!=getPlayerTank()){
                            if(obj1 instanceof IceBullet){
                                obj1.setAlive(false);
                                b.setSpeedX(0);
                                b.setSpeedY(0);
                                System.out.println("shotttt by ICEEE ENEMY");                       
                              
                                getPlayerTank().setFrozenState(true);
                                getPlayerTank().incrementFrozenStateDuration();
                                getPlayerTank().setCustomImg(new Image(Panzer2017.class.getResource("images/enemy1_frozen.png").toExternalForm(),38,38,false,false));
                            }else{
                                if(!getPlayerTank().hasShieldProtection()){
                                    obj1.setAlive(false);
                                    b.setSpeedX(0);
                                    b.setSpeedY(0);
                                    allObjectsList.remove(i);
                                    System.out.println("shotttt Enemy");                       
                                    PlayerTank t = (PlayerTank)obj2;
                                    t.setLife(t.getLife()-1);
                                    System.err.println("Shot by enemy");
                                    if(t.getLife()==4){
                                        t.setIcon(t.get4LifeIconImages());
                                        t.setCustomImg(t.get4LifeIconImages().get(t.getDirection()));
                                        playSound(BULLET_COLLIDE_PLAYER);
                                    } 
                                    if(t.getLife()==3){
                                        t.setIcon(t.get3LifeIconImages());
                                        t.setCustomImg(t.get3LifeIconImages().get(t.getDirection()));
                                        playSound(BULLET_COLLIDE_PLAYER);
                                    }                          
                                    if(t.getLife()==2){
                                        t.setIcon(t.get2LifeIconImages());
                                        t.setCustomImg(t.get2LifeIconImages().get(t.getDirection()));
                                        playSound(BULLET_COLLIDE_PLAYER);
                                    }if(t.getLife()==1){
                                        t.setIcon(t.get1LifeIconImages());
                                        t.setCustomImg(t.get1LifeIconImages().get(t.getDirection()));
                                        playSound(BULLET_COLLIDE_PLAYER);
                                    } 
                                    if(t.getLife()==0){
                                        allObjectsList.remove(j);
                                        t.setAlive(false);
                                        System.err.println("Shot by enemy and DIED!!");
                                        playSound(GAME_LOST);
                                        timer.stop();
                                        showDialog( false, level);      
                                    } 
                                }
                            }
                        }                        
                    }
                     if(obj1 instanceof Bullet && obj2 instanceof EnemyCastle){
                        if (obj1.isAlive()){
                            if(((Bullet)obj1).getBulletOwner()==getPlayerTank()){
                                EnemyCastle temp = (EnemyCastle)obj2;
                                temp.setLife(temp.getLife()-10);
                                obj1.setAlive(false);
                                Bullet b = (Bullet)obj1;
                                b.setSpeedX(0);
                                b.setSpeedY(0);
                                drawBottomBar = true;
                                points += 40;
                                if (temp.getLife() == 50)
                                    setHealthBarEnemyCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy2.png").toExternalForm(),250,40,false,false));
                                else if (temp.getLife() == 40)
                                    setHealthBarEnemyCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy3.png").toExternalForm(),250,40,false,false));
                                else if (temp.getLife() == 30)
                                     setHealthBarEnemyCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy4.png").toExternalForm(),250,40,false,false));
                                else if (temp.getLife() == 20)
                                    setHealthBarEnemyCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy5.png").toExternalForm(),250,40,false,false));
                                else if (temp.getLife() == 10)
                                    setHealthBarEnemyCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy6.png").toExternalForm(),250,40,false,false));
                                else if (temp.getLife() == 0){
                                    setHealthBarEnemyCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy_empty.png").toExternalForm(),250,40,false,false));
                                    temp.setAlive(false);
                                    allObjectsList.remove(j);
                                    c.clearRect(0, 0, 1000, 600);
                                    timer.stop();
                                    win=true;
                                    level++;
                                    showDialog( win, level ); 
                                }                            
                            }
                        }
                    }if(obj1 instanceof Bullet && obj2 instanceof PlayerCastle){
                        if (obj1.isAlive()){
                            Bullet temp = (Bullet) obj1;
                                PlayerCastle temp2 = ((PlayerCastle)obj2);
                                System.out.println("======"+temp2.getLife());
                                temp2.setLife(temp2.getLife()-10);
                                obj1.setAlive(false);
                                Bullet b = (Bullet)obj1;
                                b.setSpeedX(0);
                                b.setSpeedY(0);
                                drawBottomBar = true; //let the canvas be redrawn
                                if (temp2.getLife() == 50)
                                    setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king2.png").toExternalForm(),250,40,false,false));
                                else if (temp2.getLife() == 40)
                                    setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king3.png").toExternalForm(),250,40,false,false));
                                else if (temp2.getLife() == 30)
                                    setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king4.png").toExternalForm(),250,40,false,false));
                                else if (temp2.getLife() == 20)
                                    setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king5.png").toExternalForm(),250,40,false,false));
                                else if (temp2.getLife() == 10)
                                    setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king6.png").toExternalForm(),250,40,false,false));
                                else if (temp2.getLife() == 0){
                                    setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king_empty.png").toExternalForm(),250,40,false,false));
                                    temp2.setAlive(false);
                                    allObjectsList.remove(j);
                                    c.clearRect(0, 0, 1000, 600);                                   
                                    timer.stop();
                                    showDialog( false, level  );                                    
                                }  
                            //}
                        }
                    }if(obj1 instanceof Bonus && obj2 instanceof PlayerTank){
                        if(obj1.isAlive()){
                            Bonus thisBonus = ((Bonus) obj1);
                            if(thisBonus instanceof SpeedBonus){ // finished
                                thisBonus.setDuration(-1);
                                getPlayerTank().setTank_speed(3);
                                getPlayerTank().incrementMyBonusDuration();
                                thisBonus.setBrute_destroy(true);
                                drawBottomBar = true; // draw it once
                            }else if(thisBonus instanceof ProtectionBonus){ // finished
                                thisBonus.setDuration(-1);
                                thisBonus.setBrute_destroy(true);
                                getPlayerTank().setShieldProtection(true);
                                System.out.println("shield set to = "+ getPlayerTank().hasShieldProtection());
                                getPlayerTank().incrementMyBonusDuration();
                                drawBottomBar = true; // draw it once
                            }else if(thisBonus instanceof EnemyFreezeBonus){
                                thisBonus.setDuration(-1);
                                getPlayerTank().incrementMyBonusDuration();
                                thisBonus.setBrute_destroy(true);
                                getPlayerTank().setHasIceBullet(true);
                            }else if(thisBonus instanceof FullHealthBonus){ // finished
                                thisBonus.setDuration(-1);                                
                                thisBonus.setBrute_destroy(true);
                                getPlayerTank().setLife(5);
                                castleList.get(0).setLife(60);
                                setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king1.png").toExternalForm(),250,40,false,false));
                                getPlayerTank().setIconArrayList(getPlayerTank().get5LifeIconImages());
                                drawBottomBar= true;
                            }else if (thisBonus instanceof FastBulletBonus){//finished
                                thisBonus.setDuration(-1);
                                getPlayerTank().incrementMyBonusDuration();
                                thisBonus.setBrute_destroy(true);
                                getPlayerTank().setHasSuperBullet(true);                                
                            }else if (thisBonus instanceof  CoinsBonus){
                                thisBonus.setDuration(-1);
                                thisBonus.setBrute_destroy(true);
                               
                                points += ((CoinsBonus) thisBonus).getBonusPoints();
                            }                            
                        }                    
                    }
                }
            }
        }
    }
    
    //If playerTank is near, the shooterTank will then detect the presence and start shooting : method returns true
    public boolean playerOnSight(EnemyTank shooterTank){
        double ePosX = shooterTank.getCoordinateX();
        double ePosY = shooterTank.getCoordinateY();
        double pPosX = getPlayerTank().getCoordinateX();
        double pPosY = getPlayerTank().getCoordinateY();
        if(getPlayerTank().isAlive() && shooterTank.isAlive()){
            if(!shooterTank.getFrozenState()){
                if( (ePosX-pPosX) <=100 && (ePosX-pPosX) > 0 && Math.abs(ePosY-pPosY) <=24 ){    
                    return true;
                }
                if( (ePosX-pPosX) >=-100 && (ePosX-pPosX) < 0  && Math.abs(ePosY-pPosY) <= 24){  
                    return true;
                }

                if( (ePosY-pPosY) >=-100 && (ePosY-pPosY) < 0 && Math.abs(ePosX-pPosX)<=24){
                    return true;
                }
                if( (ePosY-pPosY) <=100 && (ePosY-pPosY) > 0 && Math.abs(ePosX-pPosX)<=24){
                    return true;
                }
            }
        }
        return false;  
    }
    
    // if player tank is near , move towards him and shoot
    public void shootIfOnSight(EnemyTank shooterTank){
        double ePosX = shooterTank.getCoordinateX();
        double ePosY = shooterTank.getCoordinateY();
        double pPosX = getPlayerTank().getCoordinateX();
        double pPosY = getPlayerTank().getCoordinateY();
        if(getPlayerTank().isAlive() && shooterTank.isAlive()){
            if(!shooterTank.getFrozenState()){
                if(!getPlayerTank().getFrozenState()){
                    if( (ePosX-pPosX) <=100 && (ePosX-pPosX) > 0 && Math.abs(ePosY-pPosY) <=20 ){              
                        shooterTank.shootMetalOrIce(this);
                        if((ePosX-pPosX) > 0 && (ePosX-pPosX) < 45  )
                              shooterTank.moveInDirection(2,true); // set him to stop
                        else
                              shooterTank.moveInDirection(2,false); // set him to move left toward player  
                    }
                    if( (ePosX-pPosX) >=-100 && (ePosX-pPosX) < 0  && Math.abs(ePosY-pPosY) <= 20){              
                        shooterTank.shootMetalOrIce(this);
                        if((ePosX-pPosX) < 0  && (ePosX-pPosX) > -45)
                            shooterTank.moveInDirection(3,true); // set him to stop
                        else
                            shooterTank.moveInDirection(3,false); // set him to move right toward player  
                    }

                    if( (ePosY-pPosY) >=-100 && (ePosY-pPosY) < 0 && Math.abs(ePosX-pPosX)<=20){
                        shooterTank.shootMetalOrIce(this);
                        if((ePosY-pPosY) < 0 && (ePosY-pPosY) > -45  )  
                            shooterTank.moveInDirection(1,true); // set him to stop
                        else
                            shooterTank.moveInDirection(1,false); // set him to move DOWN toward player                       
                    }
                    if( (ePosY-pPosY) <=100 && (ePosY-pPosY) > 0 && Math.abs(ePosX-pPosX)<=20){
                        shooterTank.shootMetalOrIce(this);
                        if((ePosY-pPosY) >0 && (ePosY-pPosY) < 45 ){
                           shooterTank.moveInDirection(0,true); // set him to stop                   
                        }
                        else
                            shooterTank.moveInDirection(0,false); // set him to move DOWN toward player  
                    }
                }
            }      
        }             
    }
     
     // assist the bullet animation logic. 
    public void decerementBulletRange() {
        for(int i = 0 ; i < bulletList.size() ; i++){
            bulletList.get(i).decrementBulletRange();
            boolean bulletDeletedOnce = false;
            if(!bulletIsInsideMap(bulletList.get(i))){
                allObjectsList.remove(bulletList.get(i));
                bulletList.get(i).getBulletOwner().setBullet(null);
                bulletList.remove(bulletList.get(i));
                bulletDeletedOnce = true; // skips array index out of bounds, or else attempts to delete same bullet twice 
            }
            else if(bulletList.get(i).getRange() <= 0 && !bulletDeletedOnce){
                allObjectsList.remove(bulletList.get(i));
                bulletList.get(i).getBulletOwner().setBullet(null);
                bulletList.remove(bulletList.get(i));
            }
        }
    }
    
    // Regulates bullet passing thru the status bar. If bullet is shot towards the status bar
    // it will be deleted once it touches the status bar
    public boolean bulletIsInsideMap(Bullet bullet){
        if (bullet.getCoordinateY() >= 590 ){ // 2 = limit movement beyond lower boundary y < 556
                return false;
        }
        return true;
    }
        
    // Shows the dialog box on game end points : clear level, win game, lose
    private void showDialog( boolean won, int level){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);        
        String levelClearedContent = "You may proceed to next level";        
        String gameClearedHeader = "ALL LEVELS CLEARED! YOU WON ! :D";
        String gameClearedContent = "You are now a licensed TANK driver!!";        
        String gameLostHeader  = "GAME OVER";
        String gameLostContent = "You failed your master shame on you! ";
        alert.setTitle("Information");
        
        if(!win){
            fileManager.writeScore(username, points);
            showTheBoxInformation( alert, "Restart Game", gameLostHeader, gameLostContent,  false,  level);            
        }if (win){
            if(level == 2){
                showTheBoxInformation( alert, "Next Level",  "LEVEL " + 1 + " CLEARED" , levelClearedContent,  won,  level);
            }
            else if (level == 3){
                showTheBoxInformation( alert, "Next Level",  "LEVEL " + 2 + " CLEARED", levelClearedContent,  won,  level);
            }
            else{
                fileManager.writeScore(username, points);
                showTheBoxInformation( alert, "Restart Game", gameClearedHeader, gameClearedContent,  won,  level);                
            }
        }
    }  
    
    // Constructs the alert dialog box
    public void showTheBoxInformation(Alert alert, String btn, String header, String content, boolean won, int level){
        alert.setHeaderText(header);
        alert.setContentText(content);

        if(btn.equalsIgnoreCase("Next Level")){                  
            alert.setOnHidden(evt -> setAlertOnClickAction(false,false)  ); // start next level);                    
        }
        if(btn.equalsIgnoreCase("Restart Game")){
             alert.setOnHidden(evt -> setAlertOnClickAction(false, true));
        }
        alert.show();
    }
    
    //Decides action that need to be taken when OK is clicked on the Alert Dialog Box
    public void setAlertOnClickAction(boolean  b, boolean exit){
          if(!exit){
            allObjectsList= new ArrayList<>();
            this.initializeLevel1(b);
            timer.start();
          }
          else{
                canvas.getScene().getWindow().hide();
                Parent root= null;
                try {
                    root = FXMLLoader.load(Panzer2017.class.getResource("MainMenu.fxml"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Scene nnew=new Scene(root);                    
                Stage newStage = new Stage();
                newStage.setScene(nnew);
                newStage.show();
          }
    }
  
    public void playSound(String soundFileName){
        if (soundOnOrOff){
            MediaPlayer mediaPlayer;
            Media sound = new Media(MainMenuController.class.getResource(soundFileName).toExternalForm());
            mediaPlayer = new MediaPlayer(sound);  
            mediaPlayer.play();
        }
    }
    
    public void on_continue_pressed(ActionEvent e){
       Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
       app_stage.hide();
     
   }

    public void on_exit_pressed(ActionEvent e){       
       Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
       exit=true;
       app_stage.hide();
       System.out.println("presed exit");
   }
}