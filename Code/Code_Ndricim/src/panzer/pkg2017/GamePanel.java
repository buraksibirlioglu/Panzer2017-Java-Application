/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import panzer.entities.Tank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import panzer.brainClass.EnemyTank;
import panzer.brainClass.GameEngine;
import panzer.entities.Bonus;
import panzer.entities.PlayerTank;
import panzer.entities.Tank;

/**
 *
 * @author Ndricim Rrapi
 */
public class GamePanel extends Scene{
      //main timeline
    private Timeline timeline;
    private AnimationTimer timer;
     String another;
    
    public GamePanel( Group root,Stage p) {
        super(root,1000,600,Color.BLACK);  
        startGame(root);
    }
    
    private void startGame(Group root){
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               Parent home_page_parent = null;
            try {
                home_page_parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(Panzer2017.class.getName()).log(Level.SEVERE, null, ex);
            }
                Scene home_page_scene = new Scene(home_page_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            }
        });
        
        GameEngine engine = new GameEngine(); // initialize all default objects             
        EnemyTank e1 = engine.getEnemyTank(0);
        EnemyTank e2 = engine.getEnemyTank(1);
        EnemyTank e3 = engine.getEnemyTank(2);
        PlayerTank p = engine.getPlayerTank();
        ArrayList<Bonus> bonuses  = engine.getBonusList();
        
        Canvas canvas = new Canvas(800,640);
        root.getChildren().add(canvas);
        root.getChildren().add(p.getObjectView());// add player tank to the map with all its functionality
        root.getChildren().add(back);// add back button 
        root.getChildren().add(e1.getObjectView()); // to be added later
        root.getChildren().add(e2.getObjectView()); // to be added later
        root.getChildren().add(e3.getObjectView()); // to be added later
        for (int i = 0; i < bonuses.size(); i ++){
            root.getChildren().add(bonuses.get(i).getObjectView());
           bonuses.get(i).getObjectView().setVisible(false);
        }
        engine.timer.start();
        
      animateBonuses(bonuses);
       // engine.moveTheBadTank(e1, p);
      // root.getChildren().addMapObjects();
      //create a circle with effect
      
    }
    
    public void animateBonuses(ArrayList<Bonus> bonus){
        generateBonuses(4000, bonus.get(0));
        generateBonuses(1200, bonus.get(1));
        generateBonuses(12000, bonus.get(2));
        generateBonuses(20000, bonus.get(3));
         new Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                
                generateBonuses(4000, bonus.get(0));
        generateBonuses(1200, bonus.get(1));
        generateBonuses(12000, bonus.get(2));
        generateBonuses(20000, bonus.get(3)); 
            }
        }, 
        20000 
        );
    }
   
    public void generateBonuses(int delay, Bonus b){
        new Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                b.getObjectView().setVisible(true);
                
            }
        }, 
        delay 
        );
        
        new Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                b.startBonus();
                
            }
        }, 
        delay+1000 
        );
        
        
      }
    }

