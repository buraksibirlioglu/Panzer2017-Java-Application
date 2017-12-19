/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
import panzer.entities.FullHealthBonus;
import panzer.entities.GameObject;
import panzer.entities.IceBullet;
import panzer.entities.Map;
import panzer.entities.GunBullet;
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
    static PlayerTank playerTank;
    private String username;
    public static boolean soundOnOrOff;
    ArrayList<EnemyTank> enemyTankList;   
    ArrayList<Bonus> bonusList;
    ArrayList<Castle> castleList;
    static ArrayList<Bullet> bulletList;
    static ArrayList<GameObject> allObjectsList ; 
    static ArrayList<Brick> bricksList;
    Map map;
    Image bonusImage=null;
    Image imageEnemyCastle, imagePlayerCastle;    
    int points;
    boolean drawBottomBar = true;
    static boolean exit = false;
    static Canvas canvas;    
    static FileManager fileManager;
    boolean win = false;
    public static ObjectDrawer timer;
    private final String ENEMY_COLLIDE_PLAYER = "sound/buzz_effect.mp3";
    private final String BULLET_COLLIDE_BRICK = "sound/destroy_brick.mp3";
    private final String BULLET_COLLIDE_ENEMY = "sound/enemy_shot.mp3";
    private final String BULLET_COLLIDE_PLAYER = "sound/player_shot.mp3";
    private final String GAME_LOST = "sound/game_lost.mp3";
    private final String SOUND_ON_IMG = "images/sound_on.png";
    private final String SOUND_OFF_IMG = "images/sound_off.png";  
    private final String PICK = "sound/coin.mp3";
    private final String BONUS_SOUND = "sound/bonus.mp3";
    private final String ATTACK_SOUND = "sound/attack.mp3";
    private EnemyTank guardianTank;
    private boolean startPlaying;
    private ArrayList<Image> explosion = new ArrayList<>();
    
   // Constructor   
    public GameEngine() throws IOException{      
        fileManager= new FileManager(); //makes sure file exists
        soundOnOrOff = fileManager.getSettings()[0] == 1; // 1 means sound is on, else sound will be false
        level = 1;
        exit = false;
        timer = new ObjectDrawer();
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public static ArrayList<Bullet> getBulletList() {
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
            else {
                map = new Map(100,600,level);
            }
             bricksList = map.getBricks();// keep track of bricks only
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        enemyTankList= createEnemyTankArrayList();
        bonusList = createBonusList();
        playerTank = new PlayerTank(true, 240, 240,38, 38,5);
      
         String adress="";
        for(int i=24; i<=91; i++) {
            adress="images/explosion/explosion00"+i+".png";
            System.out.println(adress);
            explosion.add(new Image(Panzer2017.class.getResource(adress).toExternalForm(),380,380,false,false));
        }
        
        
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
        castle.add(new PlayerCastle(true, 0, 250,40, 60,60));
        castle.add(new EnemyCastle(true, 959, 250,40, 60,60));
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
        temp.add(new EnemyFreezeBonus(true, 300,250,38,38 ));
        return temp;
    }
    
    // assigning initial location of enemy tanks
    private ArrayList<EnemyTank> createEnemyTankArrayList(){
        ArrayList<EnemyTank> enemies = new ArrayList<>();
        System.out.println("size ====="+  map.getEmptySpaces());
        //Adding objects in random places into the map
        if (level == 1){
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
        }else if (level ==2){
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,2));//add fast tank
            enemies.add(createSingleEnemyTank(957,212,4,1,3)); // add guardian strong tank
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
       
        }else if (level == 3){
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,1));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,2));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,2));//add fast tank
            enemies.add(createSingleEnemyTank(957,212,4,1,3)); // add guardian strong tank
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,4));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,4));//type 1 = normal tanks
            enemies.add(createSingleEnemyTank(returnEmptySpot(0)[0],returnEmptySpot(0)[1],1,1,4));//type 1 = normal tanks
        }
        
//        enemies.add(createSingleEnemyTank(200,0,1,1,2));
//        enemies.add(createSingleEnemyTank(350,0,1,1,1));
//        enemies.add(createSingleEnemyTank(450,250,1,1,4));
//        enemies.add(createSingleEnemyTank(800,10,1,1,1));
//        enemies.add(createSingleEnemyTank(800,500,1,1,1));
       
       return enemies;
    }
    
    public float[] returnEmptySpot(int i){
        float x = map.getEmptySpaces().get(i).get(0);
        float y = map.getEmptySpaces().get(i).get(1);
        map.getEmptySpaces().remove(i);
         return new float[]{x,y};
    }
    private EnemyTank createSingleEnemyTank(float x, float y, int lifepoints , int speed_of_tank,int enemyType){
        EnemyTank temp = new EnemyTank(true, x, y,38, 38,lifepoints,speed_of_tank,enemyType);   
        if(enemyType == 3){
            guardianTank = temp; // save the guardian tank
            
        }
        return temp;
    }
    // returns all of the objects currently on the game
  
    public static ArrayList<GameObject> getAllObjectsList() {
        return allObjectsList;
    }
       
    public ArrayList<Bonus> getBonusList() {
        return bonusList;
    }
            
    public static PlayerTank getPlayerTank() {
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
   
    // called to decide what happens on pause menu button press
    public static void showPauseMenu() throws IOException {
        System.out.println(exit);
        if(exit==false){
            timer.start();
        }else{
        canvas.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Panzer2017.class.getResource("MainMenu.fxml"));
        Scene nnew=new Scene(root);                    
        Stage pause_stage=new Stage();
        pause_stage.setScene(nnew);
        pause_stage.show();}
    } 
     
    // The timer which runs the code on a certain framerate to offer smooth gameplay 
    public class ObjectDrawer extends AnimationTimer{
        Canvas thisCanvas;
        GraphicsContext gc ;
        int time;
        long oldNanoTime = System.nanoTime();  
        long oldNanoTime1 = System.nanoTime(); 
        long enemyNanoTime = 0;   
        long previosTime1 = 0;
        long previosTimeBonus = 0;
        long timeDifferenceForEmergency = 0;
        long oldTimeForEmergency = System.nanoTime(); 
        int first = 0;
        int ImageNo=0;
        
        @Override
        public void handle(long now) {
            //moveEnemy(0 ); moves enemy over the map 
       
            gc.clearRect(0, 0, 1000, 600);// clear field map
            gc.clearRect(345, 600, 300, 100);
            gc.clearRect(390, 0, 104, 650); 
            gc.clearRect(600,600,100,300);
            keepEnemyTankCloseToCastle(gc);
           // gc.drawImage(new Image(Panzer2017.class.getResource("images/ext.jpg").toExternalForm()),guardianTank.getCoordinateX(), guardianTank.getCoordinateY(), 38, 250);
              //Rectangle enemyLookUp = new Rectangle((int)(enemyX) , (int)(enemyY-250) , 38 , 250);
            drawAllObjectsOnScren(gc,points,time,drawBottomBar);
            
            previosTime1 += System.nanoTime() - oldNanoTime;
            oldNanoTime1 = System.nanoTime(); // update old nano time
            if( previosTime1 / 1000000.0 > 8 ){
                previosTime1 = 0;
                handleCollision(gc); 
                drawAllObjectsOnScren(gc,points,time,drawBottomBar);
                retreatEnemiesToBase();
                // make a check, to increase bullet range for all 
                // enemy tanks on emergency mode because with the normal bullet
                // range bullets are shot every 8 milliseconds which is way to
                // fast and abnormal
                if(first==0){
                        for(int i = 0;i < enemyTankList.size(); i ++){
                           enemyTankList.get(i).setMyBulletRange(1500);
                        }
                        first = 1;
                }
            }
          //enemyTank.update(1);
            drawBottomBar = false;
            keepPlayerTankWithinBounds();
              for (int i = 0; i < enemyTankList.size();i++){
                  keepEnemyTankWithinBounds(enemyTankList.get(i));
                  if(!enemyTankList.get(i).isAlive()){
                        enemyTankList.get(i).setSpeedX(0);
                        enemyTankList.get(i).setSpeedY(0);
                        gc.drawImage(explosion.get(ImageNo), enemyTankList.get(i).getCoordinateX()-65, enemyTankList.get(i).getCoordinateY()-65,150,150);
                        if(ImageNo>66){                           
                            enemyTankList.remove(i);
//                            for(int k = 0; k < allObjectsList.size(); k++){
//                                if(allObjectsList.get(i) instanceof  EnemyTank){
//                                    if(allObjectsList.get(i).isAlive()){
//                                        allObjectsList.remove(i);
//                                    }
//                                }
//                            }
                            ImageNo=0;
                        }
                        ImageNo++;
                  }
                  if(!playerTank.isAlive())
                  {
                      playerTank.setSpeedX(0);
                      playerTank.setSpeedY(0);
                      gc.drawImage(explosion.get(ImageNo), playerTank.getCoordinateX()-65, playerTank.getCoordinateY()-65,150,150);
                      if(ImageNo>66){                           
                            allObjectsList.remove(0);
                            ImageNo=0;
                            timer.stop();
                        }
                        ImageNo++;
                  }
                  
              }
            
            enemyNanoTime      += System.nanoTime() - oldNanoTime;
            previosTimeBonus += System.nanoTime() - oldNanoTime;
            oldNanoTime = System.nanoTime(); // update old nano time
            if(enemyNanoTime / 1000000.0 > 2000){ 
                for (int i = 0; i < enemyTankList.size();i++) {  
                    int random = getRandom();
                    if(!playerOnSight(enemyTankList.get(i))){
                        if(random>= 0  && random <= 900){
                            ArrayList<Integer> temp =  returnProhibitedDirections(enemyTankList.get(i));
                            if(temp.size() >= 1 || temp.size() <= 3 ){
                                System.out.println("GOING FOR INSEDE");
                                findARoute(enemyTankList.get(i),temp);//  
                            }
                        }
                    }
                }
                enemyNanoTime = 0;
              
                //random enemy bullet shot 
                int random = getRandom();
                int eIndex = random/100; // tanks shoot here, what if they are null
                if(eIndex < enemyTankList.size()){
                    if(enemyTankList.get(eIndex).getEnemyType()!=3){
                        if(enemyTankList.get(eIndex) != null){
                            System.err.println("NOOOOOOOOOOO");
                            if(random >=0 && random <= 500)
                               enemyTankList.get(eIndex).shootMetalOrIce();
                            if(random >500 && random <= 1000)
                               enemyTankList.get(eIndex).shootMetalOrIce();
                            if(random >1000 && random <= 1500)
                               enemyTankList.get(eIndex).shootMetalOrIce();
                        }
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
              if(enemyTankList.get(i).getEnemyType()!=3)  shootIfOnSight( enemyTankList.get(i));
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
                        if (thisBonus.getDuration() ==0 ){
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
            g.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            g.fillText( username, 40, 28);
            g.setStroke(Color.RED);
            g.setLineWidth(1);
            g.strokeLine(0, 601, 1000, 601);
            
            if(soundOnOrOff)
                g.drawImage(new Image(Panzer2017.class.getResource(SOUND_ON_IMG).toExternalForm(),30,30,false,false), 5, 5);     
            if(!soundOnOrOff)
                g.drawImage(new Image(Panzer2017.class.getResource(SOUND_OFF_IMG).toExternalForm(),30,30,false,false), 5, 5);
           
            if(getPlayerTank().getMyBonusDuration() > 0){
                g.drawImage(bonusImage, 550, 605);
            
              g.setFill(Color.YELLOW);
              g.setFont(Font.font("Arial", FontWeight.BOLD, 25));
              g.fillText(getPlayerTank().getMyBonusDuration()/10000 +"", 605, 635);
            }if(getPlayerTank().getMyBonusDuration() == 0){
                bonusImage=null;
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
        if(!enemyTank.getFrozenState() ){
            ArrayList<Integer> dirs = returnProhibitedDirections(enemyTank);
            if(dirs.size() < 3 && dirs.size() >=0){
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
            else if(dirs.size()==3){
                enemyTank.moveInDirection(getReverseDirection(enemyTank.getDirection()), true);
            }
        }
    }
       
    // generate a random number
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(1500) + 1;
    }
    
    // changes enemy tank's route when evoked - randomly decide a new path
    public void findARoute(EnemyTank enemyTank, ArrayList<Integer> pDir ){
        System.err.println("blablala");
        if(!enemyTank.getFrozenState() && enemyTank.getEnemyType() !=3){
            if( pDir.size() == 0 ){ // keep on moving random
                Random rand = new Random();
                int  n = rand.nextInt(1500) + 1;
                int direction = enemyTank.getDirection();
                if(n >= 0 && n <= 200){
                    enemyTank.moveInDirection(getReverseDirection(direction), false);
                   // if(getReverseDirection(direction)==1)
                        enemyTank.shootMetalOrIce();
                   // System.out.println("FIRST IF new dir = " + getReverseDirection(direction) );
                }
                else if(n > 200  && n <= 400){
                    int chooseRandomDirection;
                    if(direction == 0) chooseRandomDirection = 1;
                    else if (direction == 1)  chooseRandomDirection = 3;
                    else if (direction == 2)  chooseRandomDirection = 0;
                    else  chooseRandomDirection = 2;
                    enemyTank.moveInDirection(chooseRandomDirection, false);
                    enemyTank.shootMetalOrIce();
                   // System.out.println("MOVING IN  new DIRECTION == " + chooseRandomDirection );
                }
                else if (n > 400 && n <= 1500){
                     //System.out.println("THIRD IF " + direction );
                       int chooseRandomDirection;
                    if(direction == 0) chooseRandomDirection = 2;
                    else if (direction == 1)  chooseRandomDirection = 3;
                    else if (direction == 2)  chooseRandomDirection = 1;
                    else  chooseRandomDirection = 0;
                       enemyTank.moveInDirection(chooseRandomDirection, false);
                      // System.out.println("MOVING IN  new DIRECTION == " + chooseRandomDirection );
                         enemyTank.shootMetalOrIce();
                }
            }else if (pDir.size() > 0 ){ // find a new smart non-prohibitet path
                if(pDir.size() == 3){ //blocked
                  //  System.out.println("BLOCKED BY 3");
                    boolean allSidesWithBricks = true;
                    int directionWithBrick = -1;
                    for(int i = 0; i < pDir.size(); i++){
                        if(pDir.get(i) < 4){
                            allSidesWithBricks = false; // at least one side is an EnemyTank
                        }//else it's all surrounded by Bricks
                        else {
                            directionWithBrick = pDir.get(i); // keep at least one brick
                        }
                    }
                    if (allSidesWithBricks){ // just shoot somewhere
                        enemyTank.moveInDirection(pDir.get(0)-4, false); // move towards direction in index 0
                        enemyTank.shootMetalOrIce(); // shoot forward to clear the way
                        enemyTank.setSpeedX(0);
                        enemyTank.setSpeedY(0);
                       
                    }else{ // blocked but one enemy in one side
                        if(directionWithBrick == -1){ // surrounded with all enemies. wait until they're gone
                            enemyTank.moveInDirection(0, true); //stop the tank. No way out. or shoot forwards decide later
                        }else { // at least one brick so turn to brick and shoot and move
                            enemyTank.moveInDirection(directionWithBrick, false); // move towards direction in index 0
                            enemyTank.shootMetalOrIce(); // shoot forward to clear the way
                        }
                    }
                }else if (pDir.size() == 2){
                    int dontGo1 = pDir.get(0);
                    int dontGo2 = pDir.get(1);
                    int tankDir = enemyTank.getDirection();
                    int newDirection=0;
                    int sum = dontGo2+dontGo1+tankDir;
                    switch (sum) {
                        case 3:  newDirection = 3; break;
                        case 4:  newDirection = 2; break;
                        case 5:  newDirection = 1; break;
                        case 6:  newDirection = 0; break;
                        default: break;
                    }
                    enemyTank.moveInDirection(newDirection, false); // move towards newDirection
                    if(newDirection==1)
                           enemyTank.shootMetalOrIce();
                }else if (pDir.size() == 1){
                    int dontGo1 = pDir.get(0);
                    if(dontGo1 >3) dontGo1 = dontGo1 - 4;
                    int tankDir = enemyTank.getDirection();
                    int sum = dontGo1+tankDir;
                    int newDirection = 0;
                    System.out.println("sum = "+ sum);
                    if(sum == 1) newDirection = 3; // 0 , 1
                    else if (sum == 2) newDirection = 1; // 0,2
                    else if (sum == 4) newDirection = 2; // 1,3
                    else if (sum == 5) newDirection = 0; // 2,3
                    else if (sum == 3 && (dontGo1 != 0 || tankDir != 0)) newDirection = 0; // 1,2
                    else newDirection = 1; // 0,3
                    System.out.println("new direction = " + newDirection);
                    enemyTank.moveInDirection(newDirection, false); // move towards newDirection
                    enemyTank.shootMetalOrIce();
                }
            }
        }
    }
    
    //decided what happens when object collides. Check if collision between objcets
    //using a nested loop. Check's insance types and decides what to do in either case
    public void handleCollision(GraphicsContext c){
        for(int i = 0 ; i < allObjectsList.size() ; i++){
            for(int j = 0 ; j < allObjectsList.size() ; j++){
                if(i == j) continue;
                if(i >= allObjectsList.size()) break;
                GameObject obj1 = allObjectsList.get(i);
                GameObject obj2 = allObjectsList.get(j);
                if(allObjectsList.get(i).collisionCheck(allObjectsList.get(j))){
                    //Player tank collides with Brick, Stop it and move it backword
                    if(obj1 instanceof PlayerTank && obj2 instanceof Brick){
                        PlayerTank thatTank = (PlayerTank)obj1;
                        obj1.setSpeedX(0.0f);
                        obj1.setSpeedY(0.0f);
                        if (thatTank.getDirection() == 0)
                            obj1.setCoordinateY(thatTank.getCoordinateY() + 1);
                        else if (thatTank.getDirection() == 1 ){
                            obj1.setCoordinateY(thatTank.getCoordinateY() - 1);
                        }else if (thatTank.getDirection() == 2 ){
                            obj1.setCoordinateX(thatTank.getCoordinateX() + 1);
                        }else if (thatTank.getDirection() == 3 ){
                            obj1.setCoordinateX(thatTank.getCoordinateX()-1);
                        }
                    }  
                   // EnemyTank collidees with Brick: Enenmy Tank changes route
                   if(obj1 instanceof EnemyTank && obj2 instanceof Brick){
                        EnemyTank thisTank = (EnemyTank) obj1;
                        ArrayList<Integer> temp = returnProhibitedDirections(obj1);
                        if(temp.size()==0){ 
                            if( obj1.getCoordinateX()-obj2.getCoordinateX() <=- 38){
                                obj1.setCoordinateX(obj1.getCoordinateX()-1);
                            }
                            else if( obj1.getCoordinateX()-obj2.getCoordinateX() >= 38){
                                obj1.setCoordinateX(obj1.getCoordinateX()+1);
                                // System.out.println("HEREEE");
                                //obj1.setSpeedX(0);
                            }
                            else if( obj1.getCoordinateY()-obj2.getCoordinateY() <= -38){
                               obj1.setCoordinateY(obj1.getCoordinateY()-1);
                            }
                            else  if( obj1.getCoordinateY()-obj2.getCoordinateY() >=38){
                                obj1.setCoordinateY(obj1.getCoordinateY()+1);
                                  thisTank.shootMetalOrIce();
                            }
                            thisTank.moveInDirection(getReverseDirection(thisTank.getDirection()),false);
                            thisTank.shootMetalOrIce();
                        }
                         else  findARoute((EnemyTank)obj1,temp);
                        thisTank.shootMetalOrIce();

                    }if(obj1 instanceof Tank && obj2 instanceof Castle){
                        if( obj1.getCoordinateX()-obj2.getCoordinateX() >= 40 && (obj2 instanceof PlayerCastle)){
                            obj1.setCoordinateX(obj1.getCoordinateX()+1);
                            obj1.setSpeedX(0);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() <=-38){
                           obj1.setCoordinateY(obj1.getCoordinateY()-1);
                              obj1.setSpeedY(0);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() >=38){
                            obj1.setCoordinateY(obj1.getCoordinateY()+1);
                              obj1.setSpeedY(0);
                        }if( obj1.getCoordinateX()-obj2.getCoordinateX() <=-38 && obj2 instanceof EnemyCastle){
                            obj1.setCoordinateX(obj1.getCoordinateX()-1);
                             obj1.setSpeedX(0);
                        }
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
                        findARoute((EnemyTank)obj1,returnProhibitedDirections(obj1));
                    }
                    if(obj1 instanceof EnemyTank && obj2 instanceof EnemyTank){  
                        ArrayList<Integer> prohibitionObj1= returnProhibitedDirections(obj1);
                        ArrayList<Integer> prohibitionObj2= returnProhibitedDirections(obj2);
                        if(prohibitionObj1.size()==0 ){
                            if( obj1.getCoordinateX()-obj2.getCoordinateX() >= 38){
                               obj1.setCoordinateX(obj1.getCoordinateX()+1);
                                if(prohibitionObj2.size()==0 ){
                                   obj2.setCoordinateX(obj2.getCoordinateX()-1);
                                   ((EnemyTank)obj2).moveInDirection(getReverseDirection(((EnemyTank)obj2).getDirection()),false);
                                }else {
                                    obj2.setSpeedX(0);
                                    findARoute((EnemyTank)obj2, prohibitionObj2); 
                                }                        
                            }else  if( obj1.getCoordinateX()-obj2.getCoordinateX() <=-38){
                                obj1.setCoordinateX(obj1.getCoordinateX()-1);
                                if(prohibitionObj2.size()==0 ){
                                    obj2.setCoordinateX(obj2.getCoordinateX()+1);
                                 ((EnemyTank)obj2).moveInDirection(getReverseDirection(((EnemyTank)obj2).getDirection()),false);
                                }else {
                                    obj2.setSpeedX(0);
                                    findARoute((EnemyTank)obj2, prohibitionObj2); 
                                }
                            }
                            else if( obj1.getCoordinateY()-obj2.getCoordinateY() <=-38){
                               obj1.setCoordinateY(obj1.getCoordinateY()-1);
                                if(prohibitionObj2.size()==0 ){
                                     obj2.setCoordinateY(obj2.getCoordinateY()+1);
                                      ((EnemyTank)obj2).moveInDirection(getReverseDirection(((EnemyTank)obj2).getDirection()),false);
                                }else{
                                    obj2.setSpeedY(0);
                                    findARoute((EnemyTank)obj2, prohibitionObj2); 
                                }
                            }
                            else if( obj1.getCoordinateY()-obj2.getCoordinateY() >=38){
                                obj1.setCoordinateY(obj1.getCoordinateY()+1);
                                if(prohibitionObj2.size()==0 ){
                                    obj2.setCoordinateY(obj2.getCoordinateY()-1);
                                     ((EnemyTank)obj2).moveInDirection(getReverseDirection(((EnemyTank)obj2).getDirection()),false);
                                }else{
                                obj2.setSpeedY(0);
                                    findARoute((EnemyTank)obj2, prohibitionObj2); 
                                }
                            }
                        
                            ((EnemyTank)obj1).moveInDirection(getReverseDirection(((EnemyTank)obj1).getDirection()), false);    
                            ((EnemyTank)obj1).shootMetalOrIce();
                         
                        } else{
                            findARoute((EnemyTank)obj1, prohibitionObj1);
                         //   findARoute((EnemyTank)obj2, prohibitionObj2); 
                        }
                       
                    }
                    if(obj1 instanceof Bullet && obj2 instanceof Brick){
                        Brick a=(Brick)obj2;
                        a.setLife(a.getLife()-1);
                         
                        if(a.getLife()==0){
                            System.out.println(a.getLife());
                            allObjectsList.remove(j);
                            obj2.setAlive(false);
                        }
                        obj1.setAlive(false);
                        Bullet b = (Bullet)obj1;
                        b.setSpeedX(0);
                        b.setSpeedY(0);
                        if(a.getLife() < 4){
                            playSound(BULLET_COLLIDE_BRICK);
                            if(b.getBulletOwner()== getPlayerTank())
                                points++;
                            //remove the brick from the brick list
                            for(int m = 0; m < bricksList.size();m++){
                                if(!bricksList.get(m).isAlive()){
                                    bricksList.remove(m);
                                }
                            }
                        }
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
                            }else if (b instanceof GunBullet){
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
                                        t.setSpeedX(0);
                                        t.setSpeedY(0);
                                       
                                        allObjectsList.get(j).setAlive(false);
                                        allObjectsList.remove(j);
                                        for(int m = 0; m<enemyTankList.size();m++){
                                            if(!enemyTankList.get(m).isAlive()){
                                              //  enemyTankList.remove(m);
                                            }
                                        }
                                        // generate coin
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
                                        System.out.println("----------->"+ false);
                                        showDialog( false, level);      
                                    } 
                                }
                            }
                        }                        
                    }
                     //Bullet hits EnemyCastle. Life points decremented for- 
                     //EnemyCastle and all tanks retreat to save the castle
                     if(obj1 instanceof Bullet && obj2 instanceof EnemyCastle){
                        if (obj1.isAlive()){
                            if(((Bullet)obj1).getBulletOwner()==getPlayerTank()){
                                // retreat all enemies to their base 
                                for(int m = 0; m < enemyTankList.size(); m ++){
                                    enemyTankList.get(m).setRetreatState(true);
                                }
                                // delete bulellet and decrement life points accordingly
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
                        if (obj1.isAlive() && ((Bullet)obj1).getBulletOwner() != getPlayerTank()){
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
                                 playSound(BONUS_SOUND);
                                thisBonus.setDuration(-1);
                                getPlayerTank().setTank_speed(3);
                                getPlayerTank().incrementMyBonusDuration();
                                thisBonus.setBrute_destroy(true);
                                 bonusImage=new Image(Panzer2017.class.getResource("images/power_speed.png").toExternalForm(),40,40,false,false);
                                drawBottomBar = true; // draw it once
                            }else if(thisBonus instanceof ProtectionBonus){ // finished
                                 playSound(BONUS_SOUND);
                                thisBonus.setDuration(-1);
                                thisBonus.setBrute_destroy(true);
                                getPlayerTank().setShieldProtection(true);
                                bonusImage=new Image(Panzer2017.class.getResource("images/power_shield.png").toExternalForm(),40,40,false,false);
                                getPlayerTank().incrementMyBonusDuration();
                                drawBottomBar = true; // draw it once
                            }else if(thisBonus instanceof EnemyFreezeBonus){
                                 playSound(BONUS_SOUND);
                                thisBonus.setDuration(-1);
                                getPlayerTank().incrementMyBonusDuration();
                                thisBonus.setBrute_destroy(true);
                                getPlayerTank().setHasIceBullet(true);
                                bonusImage=new Image(Panzer2017.class.getResource("images/power_freeze.png").toExternalForm(),40,40,false,false);
                          }else if(thisBonus instanceof FullHealthBonus){ // finished
                                thisBonus.setDuration(-1);                                
                                thisBonus.setBrute_destroy(true);
                                getPlayerTank().setLife(5);
                                castleList.get(0).setLife(60);
                                setHealthBarPlayerCastle(new Image(Panzer2017.class.getResource("images/health_bar_king1.png").toExternalForm(),250,40,false,false));
                                getPlayerTank().setIconArrayList(getPlayerTank().get5LifeIconImages());
                                drawBottomBar= true;
                                 playSound(BONUS_SOUND);
                            }else if (thisBonus instanceof FastBulletBonus){//finished
                                playSound(BONUS_SOUND);
                                thisBonus.setDuration(-1);
                                getPlayerTank().incrementMyBonusDuration();
                                thisBonus.setBrute_destroy(true);
                                 bonusImage=new Image(Panzer2017.class.getResource("images/power_fast_bullets.png").toExternalForm(),40,40,false,false);
                                 getPlayerTank().setHasSuperBullet(true);                                
                            }else if (thisBonus instanceof  CoinsBonus){
                                thisBonus.setDuration(-1);
                                thisBonus.setBrute_destroy(true);
                                playSound(PICK);
                                points += ((CoinsBonus) thisBonus).getBonusPoints();
                            }                            
                        }                    
                    }
                }
            }
        }
    }
    
    /** Return the prohibited directions towards which <br>
     * When player collides, he stops and calls findARoute(). Yet, since that <br>
     * method generates random movement, the next movement might send our <br>
     * EnemyTank to merge it's position with some adjacent object. <br>
     * In such a case it would appear as a constant collision and the objects <br>
     * would glitch. To prevent that, when we collide, we look in other directions<br>
     * and check if we can move there or not. This method will give in which <br>
     * directions we can not move. Knowing that we can call findARoute and <br>
     * set the new direction manually.
     */
    public ArrayList<Integer> returnProhibitedDirections(GameObject tankObject){
        ArrayList<Integer> temp = new ArrayList<>();
        EnemyTank enemy = (EnemyTank)tankObject;
        int direction = enemy.getDirection();
        int changeX = 0, changeY = 0;
        if (direction == 0){changeY = 2;}
        else if (direction == 1){changeY = -3;}
        else if (direction == 2){changeX=2;}
        else if (direction == 3){changeX=-2;}
        for (int i = 0; i < bricksList.size(); i++){            
            Rectangle brick = new Rectangle((int)(bricksList.get(i).getCoordinateX()) , (int)(bricksList.get(i).getCoordinateY()) , 40 , 40);
            Rectangle tankWider = new Rectangle((int)(enemy.getCoordinateX()-1) , (int)(enemy.getCoordinateY()), enemy.getWidth()+4, enemy.getHeight());
            Rectangle tankTaller = new Rectangle((int)(enemy.getCoordinateX()) , (int)(enemy.getCoordinateY()-1), enemy.getWidth(), enemy.getHeight()+4);
            if(brick.intersects(tankWider) || brick.intersects(tankTaller)){
                int temporary = collisionPredicate(((EnemyTank)tankObject),bricksList.get(i));
                if( temporary != -1 ){
                    temp.add(temporary); // saving prohibited positions
                }
            }
        }
        for (int i = 0; i < enemyTankList.size(); i++){
            Rectangle enemy2 = new Rectangle((int)(enemyTankList.get(i).getCoordinateX()) , (int)(enemyTankList.get(i).getCoordinateY()) , 40 , 40);
            Rectangle tankWider = new Rectangle((int)(enemy.getCoordinateX()-1) , (int)(enemy.getCoordinateY()), enemy.getWidth()+4, enemy.getHeight());
            Rectangle tankTaller = new Rectangle((int)(enemy.getCoordinateX()) , (int)(enemy.getCoordinateY()-1), enemy.getWidth(), enemy.getHeight()+4);
            if(enemy2.intersects(tankWider) || enemy2.intersects(tankTaller)){
            int temporary = collisionPredicate(((EnemyTank)tankObject),enemyTankList.get(i));
            if( temporary != -1 ){
                temp.add(temporary); // saving prohibited positions
            }
            }
        }
        return temp;
    }
    
    /**
     *  When the EnemyCastle is getting shot, EnemyTanks retreat to save their king.<br>
     *  They would however neglect this method if they are nearer to the PlayerCastle<br>
     *  In that case they would move towards the PlayerKing to kill him instantly
     */
    public void retreatEnemiesToBase(){
        //if at this corner, the enemyshould move down, to get to their target
        Rectangle topLeftCorner = new Rectangle(0 , 0, 10 , 250); 
        //if at this corner, the enemy should move up, to get to their target
        Rectangle bottomLeftCorner = new Rectangle(0 , 350, 10 , 250);
        //if at this region, enemytank should move left, to approach to their target
        Rectangle middleAttackRegion = new Rectangle(10 , 0, 472 , 600);
        Rectangle middleDefenseRegion = new Rectangle(500 , 0, 360 , 600); // retreat 
        Rectangle topRightCorner = new Rectangle(860 , 0, 130 , 250); //move down
        Rectangle bottomRightCorner = new Rectangle(860 , 350, 130 , 250);//move up
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i = 0; i < enemyTankList.size();i++){
            if(enemyTankList.get(i).getRetreatState() && enemyTankList.get(i).getEnemyType() !=3){ 
                EnemyTank tank= enemyTankList.get(i); // current tank
                Rectangle enemy = new Rectangle((int)enemyTankList.get(i).getCoordinateX() , (int)enemyTankList.get(i).getCoordinateY(), 38 , 38);//move up
                temp = returnProhibitedDirections(tank);
                if(enemy.intersects(topLeftCorner)){
                    if(temp.isEmpty() || temp.size() <=1)  tank.moveInDirection(1, false); //move down
                    else                findARoute(tank, temp);
                    tank.shootMetalOrIce();         // shoot
                }else if (enemy.intersects(bottomLeftCorner)){
                    if(temp.isEmpty()|| temp.size() <=1) tank.moveInDirection(0, false);//move up
                    else                findARoute(tank, temp);
                    tank.shootMetalOrIce();         // shoot
                }else if (enemy.intersects(middleAttackRegion)){
                    if(temp.isEmpty()|| temp.size() <=1) tank.moveInDirection(2, false);// move left
                    else                findARoute(tank, temp);
                    tank.shootMetalOrIce();         // shoot
                }else if (enemy.intersects(middleDefenseRegion)){
                    if(temp.isEmpty()|| temp.size() <=1)  tank.moveInDirection(3, false);// move right
                    else                findARoute(tank, temp);    
                    tank.shootMetalOrIce();         // shoot
                }else if (enemy.intersects(topRightCorner)){
                    if(temp.isEmpty()|| temp.size() <=1)  tank.moveInDirection(1, false);// move down
                    else                findARoute(tank, temp);    
                    tank.shootMetalOrIce();         // shoot
                }else if (enemy.intersects(bottomRightCorner)){
                    if(temp.isEmpty()|| temp.size() <=1) tank.moveInDirection(0, false);// move UP
                    else                findARoute(tank, temp);    
                    tank.shootMetalOrIce();         // shoot
                }
            }
        }
    }
    
    public int getReverseDirection(int currDirection){
        if(currDirection ==0) return 1;
        else if (currDirection ==1) return 0;
        else if (currDirection == 2) return 3;
        else return 2;
    }
    
    /**
     * This method predicts whether the enemy is heading towards a closed area.
     * Since EnemyTank's usually decide a new random direction/position when hitting
     * another object they might end up merging with the object(EnemyTank or Brick)
     * which could by coincidence be exactly at the place of the new random 
     * position. <br>This would cause a major glitch in the enemy behavior.In order 
     * to avoid that this method makes a lookahead predicate and tells warns the
     * GameEngine that this EnemyTank cannot move towards a random new position.
     * Instead, this method will generate a new correct direction for this tank to move
     * 
     * @param enemyTank : The current referred EnemyTank
     * @param adjacentObject : Could be a Brick or an EnemyTank
     * 
     * @return 
     * case : {0 = up, 1 = down, 2 = left, 3 = right} If one of these 
     * are returned they define that EnemyTank has gone to a dead end and needs to 
     * move in this new direction in order to get out of there<br>
     * 
     * case : {-1} : In this case, EnemyTank has a clear path on 4 sides, so the nor-
     * mal random function can be applied<br>
     * 
     * case : {4 = up,5 = down,6=left,7=right}: In this case the EnemyTank might
     * be surrounded by 1 or more objects. Thus the tank should shoot and then
     * move to get out of that difficult position. 
     * The return being > 3 signifies just that. <br>Furthermore, subtracting 4 from
     * any of the returned values will give the direction in which the Tank
     * needs to move after it has shot the brick<br>
     * 
     */
    public int collisionPredicate(EnemyTank enemyTank,GameObject adjacentObject){ // CAN CONSIDER TO ADD DEFAULT OBJECTS TRY LATER
        // initialize variables to keep the position of the tanks 
    
        double thisPosX = enemyTank.getCoordinateX();
        double thisPosY = enemyTank.getCoordinateY();
        double paramX   = adjacentObject.getCoordinateX() ;
        double paramY   = adjacentObject.getCoordinateY() ;
        double xDiff = thisPosX - paramX;
        double yDiff = thisPosY - paramY;
        boolean sth_On_TheRight = xDiff >= -40.0 && xDiff <= -38;
        boolean sth_On_TheLeft  = xDiff <= 42.0 && xDiff >= 40.0;
        boolean sth_On_TheBottom= yDiff >= -40 && yDiff <= -38;
        boolean sth_On_TheTop   = yDiff <= 42 && yDiff >= 40;
        
         //scenario 1:  I collided with something upwards, so check right, left, down 
        if(enemyTank.getDirection() == 0){ 
           if(sth_On_TheRight){ // checks if I have something on my right
                if (adjacentObject instanceof Brick){ // can be a brick
                   //  System.out.println("S1_1111");
                    return 7; // = right+ can shoot right, 7-4 = 3
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 3; // = right  (can't shoot)
                } //38  , 37
            }else if (sth_On_TheLeft){ // checks if I have brick on my left
                if (adjacentObject instanceof  Brick){ // can be another fellow enemy tank
                        return 6; // move left only
                } else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 2; // move left only
                }               
            }else if (sth_On_TheBottom){ // checks if I have something down
                if (adjacentObject instanceof Brick){ // can be a brick
                        return 5; // shoot down, move down
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 1; // move down only
                }
            }
        }
         
        //scenario 2: I collided with something downwards, so check right, left, up 
        else if(enemyTank.getDirection() == 1){
            if(sth_On_TheRight){ // checks if I have something on my right
                if (adjacentObject instanceof Brick){ // can be a brick
                    return 7; // = right+ can shoot right, 7-4 = 3
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 3; // = right  (can't shoot)
                }
            }else if (sth_On_TheLeft){ // checks if I have brick on my left
                if (adjacentObject instanceof  Brick){ // can be another fellow enemy tank
                        return 6; // move left only
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 2; // move left only
                }               
            }else if (sth_On_TheTop){ // checks if I have brick up
                if (adjacentObject instanceof  Brick){ // can be another fellow enemy tank
                        return 4; // move left only
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 0; // move left only
                } 
            }
        }
        
        //scenario 3: I collided with something leftwards, so check right, down, up 
        else if(enemyTank.getDirection() == 2){ //LEFT
            if (sth_On_TheTop){ // checks if I have brick up
                if (adjacentObject instanceof  Brick){ // can be another fellow enemy tank
                    return 4; // move or shoot
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 0; // move  only
                }             
            }else if (sth_On_TheBottom){ // checks if I have something down
                if (adjacentObject instanceof Brick){ // can be a brick
                    return 5; // shoot down, move down
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 1; // move down only
                }
            }else if(sth_On_TheRight){ // checks if I have something on my right
                if (adjacentObject instanceof Brick){ // can be a brick
                    return 7; // = right+ can shoot right, 7-4 = 3
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 3; // = right  (can't shoot)
                }
            }
        }
        //scenario 4: I collided with something rightwards, so check down, left, up 
        else if(enemyTank.getDirection() == 3){
            if (sth_On_TheBottom){ // checks if I have something down
                if (adjacentObject instanceof Brick){ // can be a brick
                    return 5; // shoot down, move down
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 1; // move down only
                }
            }if (sth_On_TheTop){ // checks if I have brick up
                if (adjacentObject instanceof  Brick){ // can be another fellow enemy tank
                        return 4; // move left only
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 0; // move left only
                }               
            }else if (sth_On_TheLeft){ // checks if I have brick on my left
                if (adjacentObject instanceof  Brick){ // can be another fellow enemy tank
                        return 6; // move left only
                }else if (adjacentObject instanceof  EnemyTank){ // can be another fellow enemy tank
                    return 2; // move left only
                }               
            }
        }
         return -1; // no object on three sides at all, keep moving random
    }
    
    //If playerTank is near, the shooterTank will then detect the presence and start shooting : method returns true
    //Also if playerCastle is near the shooterTank will then detect the presence and start shooting
    public boolean playerOnSight(EnemyTank shooterTank){
        double ePosX = shooterTank.getCoordinateX();
        double ePosY = shooterTank.getCoordinateY();
        double pPosX = getPlayerTank().getCoordinateX();
        double pPosY = getPlayerTank().getCoordinateY();
        double castleX = castleList.get(0).getCoordinateX();
        double castleY = castleList.get(0).getCoordinateY();
        if(shooterTank.isAlive()){
            if(!shooterTank.getFrozenState()){
                if(getPlayerTank().isAlive() ){// tank on my left
                    if( (ePosX-pPosX) <=190 && (ePosX-pPosX) > 0 && Math.abs(ePosY-pPosY) <=24 ){    
                        return true;
                    }
                    if( (ePosX-pPosX) >=-190 && (ePosX-pPosX) < 0  && Math.abs(ePosY-pPosY) <= 24){  
                        return true; // tank on my right
                    }
                    if( (ePosY-pPosY) >=-190 && (ePosY-pPosY) < 0 && Math.abs(ePosX-pPosX)<=24){
                        return true;//tank below
                    }
                    if( (ePosY-pPosY) <=190 && (ePosY-pPosY) > 0 && Math.abs(ePosX-pPosX)<=24){
                        return true; // tank upwatds
                    }
                }else if (castleList.get(0).isAlive()){
                    if( (ePosX-castleX) <=190 && (ePosX-castleX) > 0 && Math.abs(ePosY-castleY) <=46 ){    
                        return true;//castle on the left
                    }
                    if( (ePosY-castleY) >=-190 && (ePosY-pPosY) < 0 && Math.abs(ePosX-castleX)<=46){
                        return true;//castle on the below
                    }
                    if( (ePosY-castleY) <=190 && (ePosY-pPosY) > 0 && Math.abs(ePosX-castleX)<=46){
                        return true; // castle on the upwatds
                    }
                }
            }
        }
        return false;  
    }
    
    // if PlayerTank/PlayerCastle is within the shooterTank vision
    // , move towards him and shoot
    public void shootIfOnSight(EnemyTank shooterTank){
        
             
        if(!shooterTank.getFrozenState() && shooterTank.isAlive()){
            int range = 140; // how far can the enemy see
            double ePosX = shooterTank.getCoordinateX();
            double ePosY = shooterTank.getCoordinateY();
            double pPosX = getPlayerTank().getCoordinateX();
            double pPosY = getPlayerTank().getCoordinateY();
            double castleX = castleList.get(0).getCoordinateX();
            double castleY = castleList.get(0).getCoordinateY();
            // create rectangles representing victim objects
            Rectangle victimPlayer = new Rectangle((int)(pPosX) , (int)(pPosY) , 38 , 38); // width = height = 38 pixels
            Rectangle victimCastle = new Rectangle((int)(castleX) , (int)(castleY) , castleList.get(0).getWidth() , castleList.get(0).getHeight());
            Rectangle visionOnMyLeft  = new Rectangle((int)(ePosX-range) , (int)(ePosY) , 38+range , 38);
            Rectangle visionOnMyRight = new Rectangle((int)(ePosX) , (int)(ePosY) , 38+range , 38);
            Rectangle visionOnMyUp = new Rectangle((int)(ePosX) , (int)(ePosY-range) , 38 , 38+range);
            Rectangle visionOnMyDown = new Rectangle((int)(ePosX) , (int)(ePosY) , 38 , 38+range);
            boolean stopChasingTankLeft = (ePosX-pPosX) > 0 && (ePosX-pPosX) < 45 ;
            boolean stopChasingTankRight =(ePosX-pPosX) < 0  && (ePosX-pPosX) > -45;
            boolean stopChasingTankBelow = (ePosY-pPosY) < 0 && (ePosY-pPosY) > -45 ;
            boolean stopChasingTankUpwards = (ePosY-pPosY) >0 && (ePosY-pPosY) < 45;

            // check if enemy tank can see anything within range on the left
            if(visionOnMyLeft.intersects(victimPlayer)){
                if(visionOnMyLeft.getY()-victimPlayer.getY() < 0){
                    Rectangle intersection = visionOnMyLeft.intersection(victimPlayer);
                    if(intersection.getHeight() >= 24 && intersection.getHeight() <=38){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingTankLeft) shooterTank.moveInDirection(2, true); // stop 
                        else                    shooterTank.moveInDirection(2, false); // chase 
                    }
                }else if(visionOnMyLeft.getY()-victimPlayer.getY() >= 0){
                    Rectangle intersection = visionOnMyLeft.intersection(victimPlayer);
                    if(intersection.getHeight() >= 14 && intersection.getHeight() <=38){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingTankLeft) shooterTank.moveInDirection(2, true); // stop 
                        else                    shooterTank.moveInDirection(2, false); // chase 
                    }
                }
            // check if enemy tank can see anything within range on the right
            }else if (visionOnMyRight.intersects(victimPlayer)){
                if(visionOnMyRight.getY()-victimPlayer.getY() < 0){
                    Rectangle intersection = visionOnMyRight.intersection(victimPlayer);
                    if(intersection.getHeight() >= 24 && intersection.getHeight() <=38){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingTankRight) shooterTank.moveInDirection(3, true); // stop 
                        else                    shooterTank.moveInDirection(3, false); // chase 
                    }
                }else if(visionOnMyRight.getY()-victimPlayer.getY() >= 0){
                    Rectangle intersection = visionOnMyRight.intersection(victimPlayer);
                    if(intersection.getHeight() >= 14 && intersection.getHeight() <=38){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingTankRight) shooterTank.moveInDirection(3, true); // stop 
                        else                    shooterTank.moveInDirection(3, false); // chase 
                    }
                }  
            // check if enemy tank can see anything within range above him
            }else if (visionOnMyUp.intersects(victimPlayer)){
                if(visionOnMyUp.getX()-victimPlayer.getX() < 0){
                    Rectangle intersection = visionOnMyUp.intersection(victimPlayer);
                    if(intersection.getWidth() >= 24 && intersection.getWidth() <=38){
                        shooterTank.shootMetalOrIce();
                        System.out.println("shooot up 1");
                        if(stopChasingTankUpwards) shooterTank.moveInDirection(0, true); // stop 
                        else                    shooterTank.moveInDirection(0, false); // chase 
                    }
                }else if(visionOnMyUp.getX()-victimPlayer.getX() >= 0){
                    Rectangle intersection = visionOnMyUp.intersection(victimPlayer);
                    if(intersection.getWidth() >= 14 && intersection.getWidth() <=38){
                        
                        System.out.println("shooot up 2__"+shooterTank.getDirection());
                       
                        if(stopChasingTankUpwards) {
                            shooterTank.moveInDirection(0, true); 
                            System.out.println("shooo22222_"+shooterTank.getDirection());} // stop 
                        else                    shooterTank.moveInDirection(0, false); // chase 
                         shooterTank.shootMetalOrIce();
                    }
                }       
            // check if enemy tank can see anything within range below him
            }else if (visionOnMyDown.intersects(victimPlayer)){
                if(visionOnMyDown.getX()-victimPlayer.getX() < 0){
                    System.out.println("ska mundsi o burr");
                    Rectangle intersection = visionOnMyDown.intersection(victimPlayer);
                    if(intersection.getWidth() >= 24 && intersection.getWidth() <=38){
                     
                        shooterTank.shootMetalOrIce();
                        if(stopChasingTankBelow) shooterTank.moveInDirection(1, true); // stop 
                        else                    shooterTank.moveInDirection(1, false); // chase 
                    }
                }else if(visionOnMyDown.getX()-victimPlayer.getX() >= 0){
                    Rectangle intersection = visionOnMyDown.intersection(victimPlayer);
                    if(intersection.getWidth() >= 14 && intersection.getWidth() <=38){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingTankBelow) shooterTank.moveInDirection(1, true); // stop 
                        else                    shooterTank.moveInDirection(1, false); // chase 
                    }
                }
            }

            // check if EnemyTank can see the player castle
            boolean stopChasingCastleLeft = (ePosX-pPosX) > 0 && (ePosX-pPosX) < 67 ;
            boolean stopChasingCastleBelow = (ePosY-pPosY) < 0 && (ePosY-pPosY) > -67 ;
            boolean stopChasingCastleUpwards = (ePosY-pPosY) >0 && (ePosY-pPosY) < 67;
            // check if enemy tank can see the castle within range on the left
            if(visionOnMyLeft.intersects(victimCastle)){
                if(visionOnMyLeft.getY()-victimCastle.getY() < 0){
                    Rectangle intersection = visionOnMyLeft.intersection(victimCastle);
                    if(intersection.getHeight() >= 24 && intersection.getHeight() <=60){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingCastleLeft) shooterTank.moveInDirection(2, true); // stop 
                        else                    shooterTank.moveInDirection(2, false); // chase 
                    }
                }else if(visionOnMyLeft.getY()-victimCastle.getY() >= 0){
                    Rectangle intersection = visionOnMyLeft.intersection(victimCastle);
                    if(intersection.getHeight() >= 14 && intersection.getHeight() <=60){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingCastleLeft) shooterTank.moveInDirection(2, true); // stop 
                        else                    shooterTank.moveInDirection(2, false); // chase 
                    }
                }
            // checks to see if enemy can see the castle within range above him    
            }else if (visionOnMyUp.intersects(victimCastle)){
                if(visionOnMyUp.getX()-victimCastle.getX() >= 0){
                    Rectangle intersection = visionOnMyUp.intersection(victimCastle);
                    if(intersection.getWidth() >= 14 && intersection.getWidth() <=40){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingCastleUpwards) shooterTank.moveInDirection(0, true); // stop 
                        else                    shooterTank.moveInDirection(0, false); // chase 
                    }
                }
            // check if enemy tank can see anything within range below him
            }else if (visionOnMyDown.intersects(victimCastle)){
                 if(visionOnMyDown.getX()-victimCastle.getX() >= 0){
                    Rectangle intersection = visionOnMyDown.intersection(victimCastle);
                    if(intersection.getWidth() >= 14 && intersection.getWidth() <=40){
                        shooterTank.shootMetalOrIce();
                        if(stopChasingCastleBelow) shooterTank.moveInDirection(1, true); // stop 
                        else                       shooterTank.moveInDirection(1, false); // chase 
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

    /**
     * This method moves the enemy tank accordingly through trackign the player 
     * tank position
     * @param tank : the guardian tank which is going to move
     */
    public void moveGuardian(EnemyTank tank, GraphicsContext gc){
        double playerX = getPlayerTank().getCoordinateX()+1;
        double playerY = getPlayerTank().getCoordinateY()+1;
        double enemyX = tank.getCoordinateX()+1;
        double enemyY = tank.getCoordinateY()+1 ;
        double width= getPlayerTank().getWidth();
        Rectangle player = new Rectangle((int)(playerX) , (int)(playerY) , 38 , 38);
        Rectangle enemyLookDown = new Rectangle((int)(enemyX) , (int)(enemyY) , 38 , 250);
        Rectangle enemyLookUp = new Rectangle((int)(enemyX) , (int)(enemyY-250) , 38 , 250); 
        //System.err.println("iiiiiiiiiiiiii"+enemyLookDown.intersection(player).getWidth());
         // System.err.println("iiiiiiiiiiiiii"+enemyLookUp.intersection(player).getWidth());
        if(enemyLookDown.intersects(player)){
            if(enemyLookDown.intersection(player).getWidth()>25 ){
            tank.moveDown(false);
            shootIfOnSight(tank);
        }
        }else if(enemyLookUp.intersects(player)){
            if(enemyLookUp.intersection(player).getWidth()>25){
            tank.moveUp(false);
            shootIfOnSight(tank);
        }
        }else{
            if(playerX-enemyX < -width){
               // System.err.println("EEEE"+enemyLookDown.intersection(player).getWidth());
                System.out.println("LEFT--"  );
                System.out.println(playerX -enemyX-1);
               
                tank.moveLeft(false);
                shootIfOnSight(tank);
            }
            else if (playerX-enemyX > width ){
                tank.moveRight(false);
              //  System.out.println("RIGHT");
                System.out.println(playerX -enemyX);
            }
        }
    }
 
    /**
     * This method will make sure a tank stays close to his king as a guardian
     * In case playerTank enters the region then enemy will move and try to kill
     * the newcomer
     * @param enemy : points to the enemy tank which is going to act as a guradian
     */
    public void keepEnemyTankCloseToCastle(GraphicsContext gc){
        if(guardianTank != null){
            if(!guardianTank.getFrozenState()){
                double posX = guardianTank.getCoordinateX();
                double posY = guardianTank.getCoordinateY();
                 
               
                Rectangle guardian = new Rectangle((int)(posX) , (int)(posY) , 38 , 38); // width = height = 38 pixels
                // if player tank steps on protectionRegion he is in forbidden land
                // in that case this enemy will move and go kill him
                Rectangle protectionRegion = new Rectangle((int)(750) , (int)(110) , 250 , 320);
                Rectangle player = new Rectangle((int)(getPlayerTank().getCoordinateX()) , (int)(getPlayerTank().getCoordinateY()) , 38 , 38);
                Rectangle regionMoveDown  = new Rectangle(750, 110  , 210 , 102); // if in this region move down.
                Rectangle regionMoveRight = new Rectangle(750, 212  , 210 , 38); // if in this region move down.
                Rectangle regionMoveUp    = new Rectangle(750, 250, 210, 180); // if in this region move up.
                if(player.intersects(protectionRegion) && player.intersection(protectionRegion).getWidth() == 38){
                    if (!startPlaying){ playSound(ATTACK_SOUND); startPlaying=true;}
                    //System.out.println("protection region _________>");
                    if( posX > 752){
                            moveGuardian(guardianTank,gc);
                    }
                }else{
                    startPlaying =false;
                    if (guardian.intersects(regionMoveRight) && guardian.intersection(regionMoveRight).getHeight()==38){
//                          if(posY == 212.0){
                        if((posX != 957.0) && (posY == 212.0)){
                            guardianTank.moveRight(false);
                        }else{
                            guardianTank.moveLeft(false);
                            guardianTank.moveLeft(true);
                            guardianTank.setSpeedX(0);
                        }
                    }else if (guardian.intersects(regionMoveUp) && guardian.intersection(regionMoveUp).getHeight()>0){
                          guardianTank.moveUp(false);
                            // System.out.println("_____________rightOOO"+ posX + "_"+guardianTank.getCoordinateY() + "first="+(posX != 957.0) +"sec="+ (posY != 211.0));
                         
                          if(guardianTank.getCoordinateY() == 213.0){
                             // push it to pixels further
                             
                             // guardianTank.update(6);
                            //  guardianTank.update(6);
                              // System.out.println("_____________rightOOO"+ posX + "_"+guardianTank.getCoordinateY() + "first="+(posX != 957.0) +"sec="+ (posY != 211.0));
                         
                             }
                    }else if (guardian.intersects(regionMoveDown) && guardian.intersection(regionMoveDown).getHeight()>0){
                          guardianTank.moveDown(false);
                    }else{
                        guardianTank.moveRight(false);
                      //    startPlaying=false;
                    }
//                    if((posX != 957.0) && (posY == 211.0)){
//                      //  System.out.println("posX"+posX + " posY="+posY);
//                        guardianTank.moveRight(false);
//                         System.out.println("_____________right_inside");
//                    }else {
//                        guardianTank.moveLeft(false);
//                        guardianTank.moveLeft(true);
//                        guardianTank.setSpeedX(0);
//                       
//                    }
                }
            }
            
        }
    }
   
    // Shows the dialog box on game end points : clear level, win game, lose
    private void showDialog( boolean won, int level){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);     
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Panzer2017.class.getResource("images/gameIcon.png").toExternalForm()));
        String levelClearedContent = "You may proceed to next level";        
        String gameClearedHeader = "ALL LEVELS CLEARED! YOU WON ! :D";
        String gameClearedContent = "You are now a licensed TANK driver!!";        
        String gameLostHeader  = "GAME OVER";
        String gameLostContent = "You failed your master shame on you! ";
        alert.setTitle("Information");
        System.out.println("=====>"+ won);
        fileManager = new FileManager(); // this line makes sure the file exists and creates it if not
        if(!won){
            fileManager.writeScore(username, points);
            showTheBoxInformation( alert, "Restart Game", gameLostHeader, gameLostContent,  false,  level);
        }if (won){
            if(level == 2){
                showTheBoxInformation( alert, "Next Level",  "LEVEL " + 1 + " CLEARED" , levelClearedContent,  won,  level);
            }
            else if (level == 3){
                showTheBoxInformation( alert, "Next Level",  "LEVEL " + 2 + " CLEARED", levelClearedContent,  won,  level);
            }
            else{
                showTheBoxInformation( alert, "Restart Game", gameClearedHeader, gameClearedContent,  won,  level); 
                
                fileManager.writeScore(username, points);
            }
        }
    }  
    
    // Constructs the alert dialog box
    public void showTheBoxInformation(Alert alert, String btn, String header, String content, boolean won, int level){
        alert.setHeaderText(header);
        alert.setContentText(content);
        System.out.println("-->" + header );
        System.out.println("-->" + content);
        
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
  
    public static void playSound(String soundFileName){
        if (soundOnOrOff){
            MediaPlayer mediaPlayer;
            Media sound = new Media(MainMenuController.class.getResource(soundFileName).toExternalForm());
            mediaPlayer = new MediaPlayer(sound);  
            mediaPlayer.play();
        }
    }
    
    public static void setExit( boolean value){
        exit = value;
    }
}