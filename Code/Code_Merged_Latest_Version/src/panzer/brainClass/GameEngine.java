/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import panzer.entities.Bonus;
import panzer.entities.Brick;
import panzer.entities.Bullet;
import panzer.entities.Castle;
import panzer.entities.EnemyCastle;
import panzer.entities.PlayerTank;
import panzer.entities.Tank;
import panzer.pkg2017.GamePanel;
import panzer.entities.EnemyTank;
import panzer.entities.GameObject;
import panzer.entities.Map;
import panzer.entities.PlayerCastle;
import panzer.pkg2017.MainMenuController;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class GameEngine {
    PlayerTank playerTank;
    EnemyTank enemyTank;
    ArrayList<EnemyTank> enemyTankList;
    public MyAnimationTimer timer;
    ArrayList<Bonus> bonusList;
    ArrayList<GameObject> allObjectsList ;
    ArrayList<Castle> castleList;
    Map map;
    Image imageEnemy;
    ArrayList<Bullet> bulletList;
    int points;
    boolean drawBottomBar = true;
  
            
   // Constructor   
    public GameEngine() throws IOException{
        allObjectsList = new ArrayList<>();
        timer = new MyAnimationTimer();
        map = new Map(100,600);
        
    }
    
    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }
    
    // populate with objects
    public void initializeLevel1(){
        enemyTankList= createEnemyTankArrayList();
        bonusList = createBonusList();
        playerTank = createPlayerTank();  
        allObjectsList.add(playerTank);
        bulletList = new ArrayList<>();
//        for (int i = 0; i < bonusList.size(); i++){
//            allObjectsList.add(bonusList.get(i));
//        }
        for (int i = 0; i < map.getBricks().size(); i++){
            allObjectsList.add(map.getBricks().get(i));
        }
//        for (int i = 0; i < getBulletList().size(); i++){
//            allObjectsList.add(getBulletList().get(i));
//        }
        setImageEnemy(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy1.png").toExternalForm(),250,40,false,false));
        castleList = createCastles();
        allObjectsList.add(castleList.get(0));
        allObjectsList.add(castleList.get(1));
     //  enemyTank= createSingleEnemyTank(400);
        // allObjectsList.add(enemyTank);
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
        PlayerCastle C = new PlayerCastle(true, 0, 250,60, 60, 0,castleImage,60); // player castle
        castle.add(C);
        EnemyCastle  enemy =new EnemyCastle(true, 959, 250,60, 60, 0,castleImage,60); // enemy 
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
        enemies.add(createSingleEnemyTank(820,250));
        enemies.add(createSingleEnemyTank(800,350));
        enemies.add(createSingleEnemyTank(850,400));
        enemies.add(createSingleEnemyTank(800,150));
        enemies.add(createSingleEnemyTank(810,50));
        enemies.add(createSingleEnemyTank(850,450));
        return enemies;
    }
    
    private EnemyTank createSingleEnemyTank(float x, float y){
        ArrayList<Image> icon = new ArrayList<>();
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_up.png").toExternalForm(),38,38,false,false));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_down.png").toExternalForm(),38,38,false,false));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_left.png").toExternalForm(),38,38,false,false));
        icon.add(new Image(Panzer2017.class.getResource("images/enemy1_right.png").toExternalForm(),38,38,false,false));
        EnemyTank temp = new EnemyTank(true, x, y,38, 38,5, icon,21);
        temp.getObjectView().setFocusTraversable(true);             
        return temp;
    }
        
    public PlayerTank getPlayerTank() {
        return playerTank;
    }
    
    public EnemyTank getEnemyTank(int n) {
        return enemyTankList.get(n);
    }
    
    public void setImageEnemy(Image img){
        imageEnemy = img;
    }
    
    public  class HandleKeyPressed implements EventHandler<KeyEvent>{  
         
        @Override
        public void handle(KeyEvent e){   
          
            switch (e.getCode()) {
               case UP :  
                getPlayerTank().moveUp(false);
                  break;
               case DOWN:
                    getPlayerTank().moveDown(false);
                  break;
               case RIGHT:
                  getPlayerTank().moveRight(false);
                  break;
               case LEFT:
                  getPlayerTank().moveLeft(false);
                  break;
               case SPACE:{
                    getPlayerTank().feuer(GameEngine.this);                   
               }
                   break;
                  
            }   
        }
    }
    
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
            }
        }
    }
     
    public class MyAnimationTimer extends AnimationTimer{
        Canvas thisCanvas;
        final int updateTime = 8; // in ms
        boolean right = false;
        int bonusCount = 100;
        GraphicsContext gc ;
        int time;
        long oldNanoTime = System.nanoTime();  
        long oldNanoTime1 = System.nanoTime(); 
        long previosTime = 0;
        long previosTime1 = 0;
        @Override
        public void handle(long now) {
            //moveEnemy(0 ); moves enemy over the map 
            gc.clearRect(0, 0, 1000, 600);
         
            gc.clearRect(390, 0, 104, 650); 
            drawAllObjectsOnScren(gc,points,time,drawBottomBar);          
            
             previosTime1 += System.nanoTime() - oldNanoTime;
            oldNanoTime1 = System.nanoTime(); // update old nano time
            if(previosTime1 / 1000000.0 > 8){
                previosTime1 = 0;
             //   System.out.println("time");
                handleCollision(gc); 
                  drawAllObjectsOnScren(gc,points,time,drawBottomBar);                         
            }
            
          //enemyTank.update(1);
            drawBottomBar = false;
            keepPlayerTankWithinBounds();
              for (int i = 0; i < enemyTankList.size();i++)
                  keepEnemyTankWithinBounds(enemyTankList.get(i));
            
            
            previosTime += System.nanoTime() - oldNanoTime;
            oldNanoTime = System.nanoTime(); // update old nano time
            if(previosTime / 1000000.0 > 2000){           
                System.out.println("time");
                for (int i = 0; i < enemyTankList.size();i++) {  
                    if(!playerOnSight(enemyTankList.get(i)))
                        changeRoute(enemyTankList.get(i));//  
                }
                previosTime = 0;
              
                //random enemy bullet shot 
                int random = getRandom();
                if(random >=0 && random <= 500)
                   enemyTankList.get(0).feuer(GameEngine.this);
                if(random >500 && random <= 1000)
                   enemyTankList.get(1).feuer(GameEngine.this);
                if(random >1000 && random <= 1500)
                   enemyTankList.get(2).feuer(GameEngine.this);
            }
                        
            setBulletMotion(getPlayerTank());
            
            for (int i = 0; i < enemyTankList.size();i++){
                setBulletMotion(enemyTankList.get(i));
               
                shootIfOnSight( enemyTankList.get(i));
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
       
        public void drawAllObjectsOnScren(GraphicsContext g,int point,int time, boolean drawKings){
           for(int i = 0; i < getAllObjectsList().size() ; i++){
                GameObject o = getAllObjectsList().get(i);
			if(o.isAlive()){
                            g.drawImage(o.getImg(), (int)(o.getCoordinateX()) , (int)(o.getCoordinateY()));                       
                        }
		}
       
            g.setFill(Color.WHITE);
            g.setFont(Font.font("Verdana", FontWeight.LIGHT, 25));
            g.fillText("Points :   " , 345, 635);
            g.fillText("Points : "+point , 345, 635);
             g.setStroke(Color.RED);
            g.setLineWidth(1);
            g.strokeLine(0, 601, 1000, 601);
           if(drawKings){
          
            g.drawImage(new Image(Panzer2017.class.getResource("images/player_king.png").toExternalForm(),30,40,false,false), 15, 605);
            g.drawImage(new Image(Panzer2017.class.getResource("images/enemy_king.png").toExternalForm(),30,40,false,false), 955, 605);
           
               System.out.println("do it agaaaaain");
            g.drawImage(new Image(Panzer2017.class.getResource("images/health_bar_king1.png").toExternalForm(),250,40,false,false), 60, 605);
            g.drawImage(imageEnemy, 690, 605);
           
           
            g.drawImage(new Image(Panzer2017.class.getResource("images/power_freeze.png").toExternalForm(),40,40,false,false), 500, 605);
            g.fillText(time+" s " , 555, 635);
           }
        }
        
        public void setGraphics(GraphicsContext c){
            gc = c;
        }                
    }
    
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
    
    public void keepEnemyTankWithinBounds(EnemyTank enemyTank){
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
       
    public int getRandom(){
        Random rand = new Random();
        return rand.nextInt(1500) + 1;
    }
    
    // changes enemy tank's route when evoked
    public void changeRoute(EnemyTank enemyTank){
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
                enemyTank.feuer(GameEngine.this);
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
                        if( obj1.getCoordinateX()-obj2.getCoordinateX() >=40){
                           obj1.setCoordinateX(obj1.getCoordinateX()+1);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() <=-38){
                           obj1.setCoordinateY(obj1.getCoordinateY()-1);
                        }
                        if( obj1.getCoordinateY()-obj2.getCoordinateY() >=40){
                           obj1.setCoordinateY(obj1.getCoordinateY()+1);
                        }
                        changeRoute((EnemyTank)obj1);
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
                         MediaPlayer mediaPlayer;
                        Media sound = new Media(MainMenuController.class.getResource("sound/buzz_effect.mp3").toExternalForm());
                        mediaPlayer = new MediaPlayer(sound);  
                        mediaPlayer.play();
                        changeRoute((EnemyTank)obj1);
                        System.out.println("enemy touched");
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
                        changeRoute((EnemyTank)obj1);
                        changeRoute((EnemyTank)obj2);
                        System.out.println("enemy touched");
                    }
                    if(obj1 instanceof Bullet && obj2 instanceof Brick){
                        //if(obj2 instanceof GrassTile) continue;
                        obj2.setAlive(false);
                        obj1.setAlive(false);
                         Bullet b = (Bullet)obj1;
                         b.setSpeedX(0);
                         b.setSpeedY(0);
                        System.out.println("shotttt");
                        allObjectsList.remove(j);
                        MediaPlayer mediaPlayer;
                        Media sound = new Media(MainMenuController.class.getResource("sound/destroy_brick.mp3").toExternalForm());
                        mediaPlayer = new MediaPlayer(sound);  
                        mediaPlayer.play();
                        points++;
                    }
                    if(obj1 instanceof Bullet && obj2 instanceof EnemyTank){                   
                         Bullet b = (Bullet)obj1;
                        if(b.getBulletOwner()==getPlayerTank()){
                            obj2.setAlive(false);
                            obj1.setAlive(false);
                            b.setSpeedX(0);
                            b.setSpeedY(0);
                            System.out.println("shotttt Enemy");                       
                            EnemyTank t = (EnemyTank)obj2;
                            t.setSpeedX(0);
                            t.setSpeedY(0);
                            allObjectsList.remove(j);
                            MediaPlayer mediaPlayer;
                            Media sound = new Media(MainMenuController.class.getResource("sound/enemy_shot.mp3").toExternalForm());
                            mediaPlayer = new MediaPlayer(sound);  
                            mediaPlayer.play();
                            points+=50;
                        }
                    }
                     if(obj1 instanceof Bullet && obj2 instanceof PlayerTank){                   
                        Bullet b = (Bullet)obj1;
                        if (b.getBulletOwner()!=getPlayerTank()){
                        obj1.setAlive(false);
                        b.setSpeedX(0);
                        b.setSpeedY(0);
                        allObjectsList.remove(i);
                        System.out.println("shotttt Enemy");                       
                        PlayerTank t = (PlayerTank)obj2;
                        t.setLife(t.getLife()-1);
                         System.err.println("Shot by enemy");
                        MediaPlayer mediaPlayer;
                        Media sound = new Media(MainMenuController.class.getResource("sound/player_shot.mp3").toExternalForm());
                        mediaPlayer = new MediaPlayer(sound);  
                        if(t.getLife()==4){
                            t.setIcon(t.get4LifeIconImages());
                            mediaPlayer.play();
                        } 
                        if(t.getLife()==3){
                            t.setIcon(t.get3LifeIconImages());
                            mediaPlayer.play();
                        }                          
                        if(t.getLife()==2){
                            t.setIcon(t.get2LifeIconImages());
                            mediaPlayer.play();
                        }if(t.getLife()==1){
                            t.setIcon(t.get1LifeIconImages());
                            mediaPlayer.play();
                        } 
                        if(t.getLife()==0){
                           allObjectsList.remove(j);
                           t.setAlive(false);
                            System.err.println("Shot by enemy and DIED!!");
                            Media sound2 = new Media(MainMenuController.class.getResource("sound/game_lost.mp3").toExternalForm());
                            mediaPlayer = new MediaPlayer(sound2);  
                            mediaPlayer.play();
                         }
                       
                    
                        }
                    }
                     if(obj1 instanceof Bullet && obj2 instanceof Castle){
                        //if(obj2 instanceof GrassTile) continue;
                        if (obj1.isAlive()){
                            EnemyCastle temp = (EnemyCastle)obj2;
                            System.out.println("life=" + temp.getLife());
                            temp.setLife(temp.getLife()-10);
                            obj1.setAlive(false);
                            Bullet b = (Bullet)obj1;
                            b.setSpeedX(0);
                            b.setSpeedY(0);
                            System.out.println("done---------");
                            drawBottomBar = true;
                            points += 40;
                            if (temp.getLife() == 50)
                                setImageEnemy(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy2.png").toExternalForm(),250,40,false,false));
                            else if (temp.getLife() == 40)
                                setImageEnemy(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy3.png").toExternalForm(),250,40,false,false));
                            else if (temp.getLife() == 30)
                                 setImageEnemy(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy4.png").toExternalForm(),250,40,false,false));
                            else if (temp.getLife() == 20)
                                setImageEnemy(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy5.png").toExternalForm(),250,40,false,false));
                            else if (temp.getLife() == 10)
                                setImageEnemy(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy6.png").toExternalForm(),250,40,false,false));
                            else if (temp.getLife() == 0){
                                setImageEnemy(new Image(Panzer2017.class.getResource("images/health_bar_king_enemy_empty.png").toExternalForm(),250,40,false,false));
                                temp.setAlive(false);
                                allObjectsList.remove(j);
                                c.clearRect(0, 0, 1000, 600);
                                timer.stop();
                                showDialog( "PANZER 2017", "Congratulations!!", "YOU WON THE GAME!" );                                    
                            }                            
                        }
                    }  
                }
            }
        }
    }
    
    //If playerTank is near, the shooterTank will then detect and start shooting 
    public boolean playerOnSight(EnemyTank shooterTank){
        double ePosX = shooterTank.getCoordinateX();
        double ePosY = shooterTank.getCoordinateY();
        double pPosX = getPlayerTank().getCoordinateX();
        double pPosY = getPlayerTank().getCoordinateY();
        if(getPlayerTank().isAlive() && shooterTank.isAlive()){
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
        return false;  
        
    }
    
    // if player tank is near , move towards him and shoot
     public void shootIfOnSight(EnemyTank shooterTank){
        double ePosX = shooterTank.getCoordinateX();
        double ePosY = shooterTank.getCoordinateY();
        double pPosX = getPlayerTank().getCoordinateX();
        double pPosY = getPlayerTank().getCoordinateY();
        if(getPlayerTank().isAlive() && shooterTank.isAlive()){
            if( (ePosX-pPosX) <=100 && (ePosX-pPosX) > 0 && Math.abs(ePosY-pPosY) <=20 ){              
                shooterTank.feuer(this);
                if((ePosX-pPosX) > 0 && (ePosX-pPosX) < 45  )
                      shooterTank.moveInDirection(2,true); // set him to stop
                else
                      shooterTank.moveInDirection(2,false); // set him to move left toward player  
            }
            if( (ePosX-pPosX) >=-100 && (ePosX-pPosX) < 0  && Math.abs(ePosY-pPosY) <= 20){              
                shooterTank.feuer(this);
                if((ePosX-pPosX) < 0  && (ePosX-pPosX) > -45)
                    shooterTank.moveInDirection(3,true); // set him to stop
                else
                    shooterTank.moveInDirection(3,false); // set him to move right toward player  
            }

            if( (ePosY-pPosY) >=-100 && (ePosY-pPosY) < 0 && Math.abs(ePosX-pPosX)<=20){
                shooterTank.feuer(this);
                if((ePosY-pPosY) < 0 && (ePosY-pPosY) > -45  )  
                    shooterTank.moveInDirection(1,true); // set him to stop
                else
                    shooterTank.moveInDirection(1,false); // set him to move DOWN toward player                       
            }
            if( (ePosY-pPosY) <=100 && (ePosY-pPosY) > 0 && Math.abs(ePosX-pPosX)<=20){
                shooterTank.feuer(this);
                if((ePosY-pPosY) >0 && (ePosY-pPosY) < 45 ){
                   shooterTank.moveInDirection(0,true); // set him to stop                   
                }
                else
                    shooterTank.moveInDirection(0,false); // set him to move DOWN toward player  
            }
        }             
    }
     
     // assist the bullet animation logic. 
    public void decerementBulletRange() {
        for(int i = 0 ; i < bulletList.size() ; i++){
            bulletList.get(i).decrementBulletRange();
            if(bulletList.get(i).getRange() <= 0){
                allObjectsList.remove(bulletList.get(i));
                bulletList.get(i).getBulletOwner().setBullet(null);
                bulletList.remove(bulletList.get(i));
            }
        }
    }
        
    private void showDialog( String title, String header, String content ){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
       // ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(icon);
         alert.setOnHidden(evt -> Platform.exit());
        alert.show();
    }  

}
