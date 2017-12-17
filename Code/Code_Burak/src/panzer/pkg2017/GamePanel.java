/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level; 
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import panzer.brainClass.GameEngine;
import panzer.brainClass.InputManger;

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
        gameBox.setBorder(new Border(new BorderStroke(Color.RED,  BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2, false, false, false, false))));
        Canvas canvas = new Canvas(1000,650);
        gameBox.getChildren().add(canvas);

        GameEngine engine = new GameEngine(); // initialize all default objects      
        engine.setUsername(promptUserName());
        GraphicsContext g = canvas.getGraphicsContext2D();
        engine.initializeLevel1(false);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());  
        engine.timer.setGraphics(g);
        //engine.timer.start();
        this.setOnKeyPressed(new InputManger(1));
        this.setOnKeyReleased(new InputManger(2));
        root.getChildren().add(gameBox);
    }
    
    private String promptUserName(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("USER NAME PROMPT");
        dialog.setHeaderText("Want to smash some enemies");
        dialog.setContentText("Enter a nickname first:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && result.get().length()!=0){
           return result.get();
        }
         else {
            return "Player" + new Random().nextInt(122222) + 1;
        } 
        
    }
}

