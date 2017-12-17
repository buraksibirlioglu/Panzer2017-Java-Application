/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import java.io.IOException;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Ndricim Rrapi
 */
public class MainMenuController implements Initializable{

    @FXML private ImageView tankImage;
    @FXML private ImageView CreditsPanel;
    @FXML private ImageView HighScorePanel;
    @FXML private ImageView HelpPanel;
    @FXML private ImageView SettingsPanel;
    @FXML private ImageView GamePanel;

    /* Sounds played when user presses Enter on an option */
    private  final String  SOUND_PICKED_PATH    = "sound/pick.mp3"; 
    private  final String  SOUND_SELECTED_PATH  = "sound/selected.mp3";

    /* IMAGE PATHS USED IN THE BELOW CODE */
    private  final String  IMAGE_SETTINGS_BTN_SELECTED_PATH        = "images/settings_btn.png";
    private  final String  IMAGE_SETTINGS_BTN_NOT_SELECTED_PATH   = "images/settings_btn_1.png";
    private  final String  IMAGE_PLAY_BTN_SELECTED_PATH           = "images/play_btn.png";
    private  final String  IMAGE_PLAY_BTN_NOT_SELECTED_PATH       = "images/play_btn_1.png";
    private  final String  IMAGE_HELP_BTN_SELECTED_PATH           = "images/help_btn.png";
    private  final String  IMAGE_HELP_BTN_NOT_SELECTED_PATH       = "images/help_btn_1.png";
    private  final String  IMAGE_HIGHSCORES_BTN_SELECTED_PATH     = "images/high_score_btn.png";
    private  final String  IMAGE_HIGHSCORES_BTN_NOT_SELECTED_PATH = "images/high_score_btn_1.png";
    private  final String  IMAGE_CREDITS_BTN_SELECTED_PATH        = "images/credits_btn.png";
    private  final String  IMAGE_CREDITS_BTN_NOT_SELECTED_PATH    = "images/credits_btn_1.png";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MediaPlayer mediaPlayer, mediaPlayer1; // used to play sound
        TranslateTransition  transition = new TranslateTransition(); // used to make pointer image move up and down    
        transition.setDuration(Duration.millis(50)); // sets duration of the pointer image movement
        transition.setNode(tankImage); // sets object onto which animation is done to 
        tankImage.requestFocus();
        tankImage.setFocusTraversable(true);

        // accesses mp3 sound files to play
        mediaPlayer  = new MediaPlayer(new Media(MainMenuController.class.getResource(SOUND_PICKED_PATH).toExternalForm()));  
        mediaPlayer1 = new MediaPlayer(new Media(MainMenuController.class.getResource(SOUND_SELECTED_PATH).toExternalForm()));  

        // adds an event filter to the image upon keyboard input
        tankImage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            /** 
             * This constant defines the position at which the tank image is pointing at.
             * pointerPosition == 0 : Pointer points at "Play" image
             * pointerPosition == 1 : Pointer points at "Settings" image
             * pointerPosition == 2 : Pointer points at "Help" image
             * pointerPosition == 3 : Pointer points at "High Scores" image
             * pointerPosition == 4 : Pointer points at "Credits" image 
             */
            private int pointerPosition=0;

            /**
              This method is overrided in order to implement the the motion 
              * graphics at the main menu. By making use of this KeyEvent 
              * handler uses may use their keyboard to move through options
            */
            @Override
            public void handle(KeyEvent e){
                if(pointerPosition == 4 && e.getCode() == KeyCode.ENTER){
                    showPanel("Credits.fxml",  e );
                } if( pointerPosition == 3 && e.getCode()== KeyCode.ENTER){
                    showPanel("HighScorePanel.fxml",  e );
                } if(pointerPosition == 2 && e.getCode()== KeyCode.ENTER){
                    showPanel("Help.fxml",  e );
                } if(pointerPosition == 1 && e.getCode()== KeyCode.ENTER){
                     showPanel("Settings.fxml",  e );
                } if(pointerPosition==0 && e.getCode()== KeyCode.ENTER){
                    mediaPlayer1.play();
                    Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    app_stage.hide();
                    try {
                        // Calls the GamePanel when pressing enter on play
                        app_stage.setScene(new GamePanel(new Group(), app_stage));
                    } catch (IOException ex) {
                        Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    app_stage.show();                
                }

                if (e.getCode()== KeyCode.DOWN){         
                    if (pointerPosition == 0){
                        System.out.println("hi");
                        switchImageAndMove(GamePanel,SettingsPanel, IMAGE_PLAY_BTN_NOT_SELECTED_PATH, IMAGE_SETTINGS_BTN_SELECTED_PATH, 50, false);
                    }
                    else if (pointerPosition == 1){
                        switchImageAndMove(SettingsPanel,HelpPanel, IMAGE_SETTINGS_BTN_NOT_SELECTED_PATH, IMAGE_HELP_BTN_SELECTED_PATH, 100, false);
                    }
                    else if (pointerPosition == 2){
                        switchImageAndMove(HelpPanel,HighScorePanel, IMAGE_HELP_BTN_NOT_SELECTED_PATH, IMAGE_HIGHSCORES_BTN_SELECTED_PATH, 150, false);
                    }
                    else if (pointerPosition == 3){
                        switchImageAndMove(HighScorePanel,CreditsPanel, IMAGE_HIGHSCORES_BTN_NOT_SELECTED_PATH, IMAGE_CREDITS_BTN_SELECTED_PATH, 200, false);
                    }
                }
                else if (e.getCode()== KeyCode.UP){
                    if (pointerPosition == 1){
                        switchImageAndMove(SettingsPanel,GamePanel, IMAGE_SETTINGS_BTN_NOT_SELECTED_PATH, IMAGE_PLAY_BTN_SELECTED_PATH, 0, true);
                    }
                    else if (pointerPosition == 2){
                        switchImageAndMove(HelpPanel,SettingsPanel, IMAGE_HELP_BTN_NOT_SELECTED_PATH, IMAGE_SETTINGS_BTN_SELECTED_PATH, 50, true);
                    }
                    else if (pointerPosition == 3){
                        switchImageAndMove(HighScorePanel,HelpPanel, IMAGE_HIGHSCORES_BTN_NOT_SELECTED_PATH, IMAGE_HELP_BTN_SELECTED_PATH, 100, true);
                    }
                    else if (pointerPosition == 4){ 
                        switchImageAndMove(CreditsPanel,HighScorePanel, IMAGE_CREDITS_BTN_NOT_SELECTED_PATH, IMAGE_HIGHSCORES_BTN_SELECTED_PATH, 150, true);
                    }
                }
            }
            /**
             * Used to switch to the preferred panel. Gets the current Stage
             * from @param e and uses the @param panelName to define which
             * stage is going to be opened next
             * 
             * @param panelName : defines the panel which is going to be opened 
             * upon pressing ENTER
             * 
             * @param e : defines the KeyEvent. Needed to get the current stage 
             * so that we can switch from this Stage to the next panels Stage
             */
            private void showPanel(String panelName, KeyEvent e ){
                mediaPlayer1.play();
                Parent home_page_parent = null;
                try {
                    home_page_parent = FXMLLoader.load(getClass().getResource(panelName));
                } catch (IOException ex) {
                    Logger.getLogger(Panzer2017.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene home_page_scene = new Scene(home_page_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            }

            /**
             * This method will be used to implement switching image backgrounds
             * It takes two images as parameters targetDeselect and targetSelect
             * and then switches their backgrounds accordingly 
             * @param targetDeselect : defines the image which is deselected. 
             * A deselected image is nothing but an image with less opacity just
             * to indicate to the user which option was deselected
             * @param targetSelect : defines the image which is selected. A
             * selected image is nothing but an image with 100% opacity just to
             * indicate to the user which option was selected
             * @param deselectedImagePath : defines the path of image to which
             * targetDeselect will be switched to
             * @param selectedImagePath : defines the path of image to which 
             * targetSelect will be switched to
             * @param newPosition : defines the new Y-coordinate to which the 
             * pointer image tankImage will go to 
             * @param UP : if true method is used for KeyEvent.UP occasion, else
             * it means we are moving down; used to define whether
             * pointerPosition needs to be decremented or not
             */
            private void switchImageAndMove(ImageView targetDeselect,ImageView targetSelect, String deselectedImagePath, String selectedImagePath, int newPosition, boolean UP){
                transition.setToY(newPosition);
                transition.play();
                if (UP) pointerPosition--; // moving up so decrease pointer value
                else if (!UP) pointerPosition++; // else moving down so increment pointer value
                Image img = new Image(Panzer2017.class.getResource(selectedImagePath).toExternalForm());
                Image img2 = new Image(Panzer2017.class.getResource(deselectedImagePath).toExternalForm());
                targetSelect.setImage(img);
                targetDeselect.setImage(img2);                    
                mediaPlayer.stop();
                mediaPlayer.play(); // plays the sound 
            }            
        });
    }   
}
