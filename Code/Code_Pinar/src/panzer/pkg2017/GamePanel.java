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
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import panzer.entities.EnemyTank;
import panzer.brainClass.GameEngine;
import panzer.brainClass.GameEngine.HandleKeyPressed;
import panzer.brainClass.GameEngine.HandleKeyReleased;
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
    
    public GamePanel( Group root,Stage p) throws IOException {
        super(root,1000,650,Color.BLACK);  
        startGame(root,p);
    }
    
    private void startGame(Group root, Stage p) throws IOException{
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
        VBox gameBox = new VBox();
      
        GameEngine engine = new GameEngine(); // initialize all default objects      
   
        gameBox.setBorder(new Border(new BorderStroke(Color.RED,  BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2, false, false, false, false))));
        Canvas canvas = new Canvas(1000,650);
        gameBox.getChildren().add(canvas);
        GraphicsContext g = canvas.getGraphicsContext2D();
        engine.initializeLevel1();
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());       
       
        this.setOnKeyPressed(engine.new HandleKeyPressed());
        this.setOnKeyReleased(engine.new HandleKeyReleased());
        engine.timer.setGraphics(g);
        engine.timer.start();
        root.getChildren().add(gameBox);
  
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

