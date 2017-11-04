/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import panzer.entities.Tank;
import java.io.IOException;
import java.util.ArrayList;
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
import panzer.brainClass.GameEngine;
import panzer.entities.Brick;
import panzer.entities.Castle;
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
        super(root,1000,600,Color.BLACK);  
        startGame(root);
    }
    
    private void startGame(Group root) throws IOException{
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
        ArrayList<Brick> B= engine.getMap().getBricks();
        ArrayList<Castle> C= engine.getMap().getCastles();
        Canvas canvas = new Canvas(800,640);
        root.getChildren().add(canvas);
        root.getChildren().add(engine.getPlayerTank().getObjectView());// add player tank to the map with all its functionality
        for(int i=0; i<B.size(); i++)
        {
            root.getChildren().add(B.get(i).getObjectView());
        }
        for(int j=0; j<2; j++)
        {
            root.getChildren().add(C.get(j).getObjectView());
        }
        root.getChildren().add(back);// add back button 
       // root.getChildren().add(bullet); // to be added later
      // root.getChildren().addMapObjects();
      //create a circle with effect
      
    }
      
    }

