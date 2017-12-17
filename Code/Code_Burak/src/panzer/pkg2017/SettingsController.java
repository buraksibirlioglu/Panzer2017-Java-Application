/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import panzer.brainClass.FileManager;

/**
 * FXML Controller class
 *
 * @author Ndricim Rrapi
 */
public class SettingsController implements Initializable {
    MediaPlayer mediaPlayer, mediaPlayer1;
    int sound_state = 1;
    int playerColor = 1;
    @FXML private ImageView on_off_img;
    @FXML private ImageView green_yellow_img;
    @FXML private ImageView player_bg;
    @FXML private ImageView sound_bg;
    @FXML private ImageView reset_btn;
    @FXML private ImageView back_btn;
 
    private final String ON_SELECTED               = "images/on_selected.png";
    private final String OFF_SELECTED              = "images/off_selected.png";
    private final String PLAYER_COLOR_SELECTED     = "images/player_color_bg_selected.png";
    private final String PLAYER_COLOR_NOT_SELECTED = "images/player_color_bg_not_selected.png";
    private final String SOUND_SELECTED            = "images/sound_bg_selected.png";
    private final String SOUND_NOT_SELECTED        = "images/sound_bg_not_selected.png";
    private final String GREEN_SELECTED            = "images/green_color_selected.png";
    private final String YELLOW_SELECTED           = "images/yellow_color_selected.png";
    private final String BACK_PRESSED              = "images/back_pressed.png";
    private final String BACK_NOT_PRESSED          = "images/back_not_pressed.png";
    private final String RESET_PRESSED             = "images/reset_pressed.png";
    private final String RESET_NOT_PRESSED         = "images/reset_not_pressed.png";
    private FileManager fileManager;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Media sound = new Media(MainMenuController.class.getResource("sound/pick.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        Media sound2 = new Media(MainMenuController.class.getResource("sound/selected.mp3").toExternalForm());
        mediaPlayer1 = new MediaPlayer(sound2);

        //thisOne.setWritable(true);
        //starts a key pressed event from on btn
        on_off_img.requestFocus();
        on_off_img.setFocusTraversable(true);
        on_off_img.setOnKeyPressed(new EventHandler<KeyEvent>() {
            boolean firstTime = true;
            int position = 0;
            
            @Override
            public void handle(KeyEvent e) {
                if(firstTime){
                    fileManager = new FileManager();
                    firstTime = false;
                }
                //AT FIRST LINE : SOUND SETTING
                if(position == 0){
                    if (e.getCode()== KeyCode.DOWN){  // go to second line
                        sound_bg.setImage(new Image(Panzer2017.class.getResource(SOUND_NOT_SELECTED).toExternalForm()));     
                        player_bg.setImage(new Image(Panzer2017.class.getResource(PLAYER_COLOR_SELECTED).toExternalForm()));   
                        position = 1; // go to second line at green img
                    }
                    else if (e.getCode()== KeyCode.RIGHT || e.getCode()== KeyCode.LEFT){
                        if (sound_state == 1){
                            on_off_img.setImage(new Image(Panzer2017.class.getResource(OFF_SELECTED).toExternalForm())); 
                            sound_state = 0; // sound off
                        }else {
                            on_off_img.setImage(new Image(Panzer2017.class.getResource(ON_SELECTED).toExternalForm())); 
                            sound_state = 1; // sound off
                        }
                    }
                }
                //AT SECOND LINE : PLAYER COLOR
                else if(position == 1){
                    if (e.getCode()== KeyCode.DOWN){
                        player_bg.setImage(new Image(Panzer2017.class.getResource(PLAYER_COLOR_NOT_SELECTED).toExternalForm())); 
                        reset_btn.setImage(new Image(Panzer2017.class.getResource(RESET_PRESSED).toExternalForm())); 
                        position = 2; // go to second line
                    }
                    else if (e.getCode()== KeyCode.LEFT || e.getCode()== KeyCode.RIGHT ){
                        if (playerColor == 1){
                            green_yellow_img.setImage(new Image(Panzer2017.class.getResource(YELLOW_SELECTED).toExternalForm())); 
                            playerColor = 0; // sound on
                        }else if (playerColor == 0){
                            green_yellow_img.setImage(new Image(Panzer2017.class.getResource(GREEN_SELECTED).toExternalForm())); 
                            playerColor = 1; // sound on
                        }
                    }else if (e.getCode()== KeyCode.UP ){
                        sound_bg.setImage(new Image(Panzer2017.class.getResource(SOUND_SELECTED).toExternalForm()));     
                        player_bg.setImage(new Image(Panzer2017.class.getResource(PLAYER_COLOR_NOT_SELECTED).toExternalForm())); 
                        position = 0; // go to second line
                    }
                }
                // AT RESET
                else if(position == 2){
                    if (e.getCode()== KeyCode.RIGHT){ // go to BACK
                        back_btn.setImage(new Image(Panzer2017.class.getResource(BACK_PRESSED).toExternalForm())); 
                        reset_btn.setImage(new Image(Panzer2017.class.getResource(RESET_NOT_PRESSED).toExternalForm())); 
                        position = 3;
                    }else if (e.getCode()== KeyCode.UP){ // go to first line  
                        player_bg.setImage(new Image(Panzer2017.class.getResource(PLAYER_COLOR_SELECTED).toExternalForm())); 
                        position = 1;
                    }else if (e.getCode()== KeyCode.ENTER){
                        playerColor = 1; // by default player is green
                        sound_state = 1; // by default sound is on
                        on_off_img.setImage(new Image(Panzer2017.class.getResource(ON_SELECTED).toExternalForm())); 
                        green_yellow_img.setImage(new Image(Panzer2017.class.getResource(GREEN_SELECTED).toExternalForm())); 
                    }
                }
                // AT EXIT
                else if(position == 3){
                    if (e.getCode()== KeyCode.LEFT){ // go to RESET
                        reset_btn.setImage(new Image(Panzer2017.class.getResource(RESET_PRESSED).toExternalForm())); 
                        back_btn.setImage(new Image(Panzer2017.class.getResource(BACK_NOT_PRESSED).toExternalForm())); 
                        position = 2;
                    }else if (e.getCode()== KeyCode.UP){ // go to PLAYER COLOR  
                        player_bg.setImage(new Image(Panzer2017.class.getResource(PLAYER_COLOR_SELECTED).toExternalForm())); 
                        back_btn.setImage(new Image(Panzer2017.class.getResource(BACK_NOT_PRESSED).toExternalForm())); 
                        position = 1;
                    }else if (e.getCode()== KeyCode.ENTER){
                        fileManager.writeSettings(sound_state, playerColor);
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
                }
            }
        });
    }  
}