/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import panzer.brainClass.GameEngine;
import panzer.entities.FileManager;

/**
 * FXML Controller class
 *
 * @author Ndricim Rrapi
 */
public class PauseMenu implements Initializable {

    @FXML private ImageView son_off_btn;
    @FXML private ImageView continue_btn;
    @FXML private ImageView exit_btn;
    private String CONTINUE_PRESSED      = "images/pause_text_pressed.png";
    private String CONTINUE_NOT_PRESSED  = "images/pause_text_not_pressed.png";    
    private String SOUND_NOT_PRESSED_ON  = "images/sound_on_not_pressed.png";
    private String SOUND_PRESSED_ON      = "images/sound_on_pressed.png";
    private String SOUND_NOT_PRESSED_OFF = "images/sound_off_not_pressed.png";
    private String SOUND_PRESSED_OFF     = "images/sound_off_pressed.png";     
    private String EXIT_NOT_PRESSED      = "images/exit_not_pressed.png";
    private String EXIT_PRESSED          = "images/exit_pressed.png";
    private GameEngine thisEngine;
    boolean on_or_off ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        continue_btn.requestFocus();
        continue_btn.setFocusTraversable(true);
        
        continue_btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            int position = 0;  
            boolean firstTime = true;
            Media sound = new Media(MainMenuController.class.getResource("sound/pick.mp3").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound); 
            Media sound2 = new Media(MainMenuController.class.getResource("sound/selected.mp3").toExternalForm());
          
            @Override
                public void handle(KeyEvent e) {  
                    if(firstTime) { on_or_off = thisEngine.soundOnOrOff; firstTime = false;}
                    if (position == 0){
                        if (e.getCode()== KeyCode.DOWN){
                            continue_btn.setImage(new Image(Panzer2017.class.getResource(CONTINUE_NOT_PRESSED).toExternalForm()));   
                            if(on_or_off){
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_PRESSED_ON).toExternalForm()));   
                            }else{
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_PRESSED_OFF).toExternalForm()));   
                            }
                            position++;
                            mediaPlayer.stop();
                            mediaPlayer.play();
                        }else if (e.getCode()== KeyCode.ENTER){
                            thisEngine.timer.start();
                            Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            app_stage.hide();
                            mediaPlayer = new MediaPlayer(sound2);  
                            mediaPlayer.play();
                        }
                         
                    }else if (position ==1 ){
                        if (e.getCode()== KeyCode.DOWN){
                            exit_btn.setImage(new Image(Panzer2017.class.getResource(EXIT_PRESSED).toExternalForm()));   
                            if(on_or_off){
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_NOT_PRESSED_ON).toExternalForm()));   
                            }else{
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_NOT_PRESSED_OFF).toExternalForm()));   
                            }
                            position++;
                        }
                        else if (e.getCode()== KeyCode.UP){
                            continue_btn.setImage(new Image(Panzer2017.class.getResource(CONTINUE_PRESSED).toExternalForm()));   
                            if(on_or_off){
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_NOT_PRESSED_ON).toExternalForm()));   
                            }else{
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_NOT_PRESSED_OFF).toExternalForm()));   
                            }
                            position--;
                        }else if (e.getCode()== KeyCode.LEFT || e.getCode()== KeyCode.RIGHT){ 
                            on_or_off = !on_or_off; // negate value to true/false
                            thisEngine.soundOnOrOff = on_or_off;
                            if(on_or_off){
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_PRESSED_ON).toExternalForm()));   
                            }else{
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_PRESSED_OFF).toExternalForm()));   
                            }                            
                        }
                        mediaPlayer.stop();
                        mediaPlayer.play();
                    }else if (position ==2 ){
                        if (e.getCode()== KeyCode.DOWN){
                            continue_btn.setImage(new Image(Panzer2017.class.getResource(CONTINUE_PRESSED).toExternalForm()));   
                            exit_btn.setImage(new Image(Panzer2017.class.getResource(EXIT_NOT_PRESSED).toExternalForm()));   
                            position = 0; // set to continue  
                            mediaPlayer.stop();
                            mediaPlayer.play();
                        }else if (e.getCode()== KeyCode.UP){
                            exit_btn.setImage(new Image(Panzer2017.class.getResource(EXIT_NOT_PRESSED).toExternalForm()));   
                             if(on_or_off){
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_PRESSED_ON).toExternalForm()));   
                            }else{
                                son_off_btn.setImage(new Image(Panzer2017.class.getResource(SOUND_PRESSED_OFF).toExternalForm()));   
                            }
                            position--;
                            mediaPlayer.stop();
                            mediaPlayer.play();
                        }else if (e.getCode()== KeyCode.ENTER){
                            Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            thisEngine.setExit(true);
                            app_stage.hide();
                            mediaPlayer = new MediaPlayer(sound2);  
                            mediaPlayer.play();
                        }
                    }
                }
        });
    }    
  

    public void setEngine(GameEngine engine){
        thisEngine = engine;        
    }
}