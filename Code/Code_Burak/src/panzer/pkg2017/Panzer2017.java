/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPaneBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 *
 * @author Ndricim Rrapi
 */
public class Panzer2017 extends Application implements  EventHandler<KeyEvent>  {
    ImageView tankImage,playImage, settingsImage,helpImage,highscoreImage,creditsImage;
    TranslateTransition  transition = new TranslateTransition();
    private int pointerPosition = 0;
    MediaPlayer mediaPlayer;
    Scene scene ;
    protected static GamePanel gamePanel;
    
    @Override
    public void start(Stage stage) throws Exception {
        initializeImageComponents();
      
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        ((AnchorPane) root).setFocusTraversable(true);
        tankImage        =  (ImageView) ((AnchorPane) root).getChildren().get(1);
        playImage        =  (ImageView) ((AnchorPane) root).getChildren().get(2);
        settingsImage    =  (ImageView) ((AnchorPane) root).getChildren().get(3);
        helpImage        =  (ImageView) ((AnchorPane) root).getChildren().get(4);
        highscoreImage   =  (ImageView) ((AnchorPane) root).getChildren().get(5);
        creditsImage     =  (ImageView) ((AnchorPane) root).getChildren().get(6);
        
        scene = new Scene(root);

                		//Initialize pane that will be used for specific scene!
      
       // scene.setOnKeyPressed(this);   
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
       
        stage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
    public void initializeImageComponents(){  
        Media sound = new Media(Panzer2017.class.getResource("sound/pick.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);       
        transition.setDuration(Duration.millis(100));       

    }
     @Override
    public void handle(KeyEvent e){
         
        System.out.print(pointerPosition);
        if(pointerPosition==4 && e.getCode()== KeyCode.ENTER){
            Parent home_page_parent = null;
            try {
                home_page_parent = FXMLLoader.load(getClass().getResource("Credits.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(Panzer2017.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) scene.getWindow();
            app_stage.hide();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        }
        if (e.getCode()== KeyCode.DOWN){         
            if (pointerPosition == 0){
            transition.setToX(0);
            transition.setToY(50);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition++;
            settingsImage.setImage(new Image(Panzer2017.class.getResource("images/settings_btn.png").toExternalForm()));
            playImage.setImage(new Image(Panzer2017.class.getResource("images/play_btn_1.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            }
            else if (pointerPosition == 1){
            transition.setToX(0);
            transition.setToY(100);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition++;
            helpImage.setImage(new Image(Panzer2017.class.getResource("images/help_btn.png").toExternalForm()));
            settingsImage.setImage(new Image(Panzer2017.class.getResource("images/settings_btn_1.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            }
            else if (pointerPosition == 2){
            transition.setToX(0);
            transition.setToY(150);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition++;
            highscoreImage.setImage(new Image(Panzer2017.class.getResource("images/high_score_btn.png").toExternalForm()));
            helpImage.setImage(new Image(Panzer2017.class.getResource("images/help_btn_1.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            }
            else if (pointerPosition == 3){
            transition.setToX(0);
            transition.setToY(200);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition++;
            highscoreImage.setImage(new Image(Panzer2017.class.getResource("images/high_score_btn_1.png").toExternalForm()));
            creditsImage.setImage(new Image(Panzer2017.class.getResource("images/credits_btn.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            }
        }
        else  if (e.getCode()== KeyCode.UP){
            if (pointerPosition == 1){
            transition.setToX(0);
            transition.setToY(0);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition--;
            settingsImage.setImage(new Image(Panzer2017.class.getResource("images/settings_btn_1.png").toExternalForm()));
            playImage.setImage(new Image(Panzer2017.class.getResource("images/play_btn.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            }
            else if (pointerPosition == 2){
            transition.setToX(0);
            transition.setToY(50);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition--;
            helpImage.setImage(new Image(Panzer2017.class.getResource("images/help_btn_1.png").toExternalForm()));
            settingsImage.setImage(new Image(Panzer2017.class.getResource("images/settings_btn.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            
            }
            else if (pointerPosition == 3){
            transition.setToX(0);
            transition.setToY(100);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition--;
            highscoreImage.setImage(new Image(Panzer2017.class.getResource("images/high_score_btn_1.png").toExternalForm()));
            helpImage.setImage(new Image(Panzer2017.class.getResource("images/help_btn.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            }
            else if (pointerPosition == 4){
            transition.setToX(0);
            transition.setToY(150);
            transition.setNode(tankImage);
            transition.play();
            pointerPosition--;
            highscoreImage.setImage(new Image(Panzer2017.class.getResource("images/high_score_btn.png").toExternalForm()));
            creditsImage.setImage(new Image(Panzer2017.class.getResource("images/credits_btn_1.png").toExternalForm()));
            mediaPlayer.stop();
            mediaPlayer.play();
            }
        }
     }
    

}
