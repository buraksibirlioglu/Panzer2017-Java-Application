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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.Timer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Ndricim Rrapi
 */
public class MainMenuController implements Initializable{
        

@FXML 
private AnchorPane anchor;
@FXML 
private ImageView tankImage;
@FXML private ImageView creditsImage;
@FXML private ImageView highscoreImage;
@FXML private ImageView helpImage;
@FXML private ImageView settingsImage;
@FXML private ImageView playImage;

private int pointerPosition=0;
MediaPlayer mediaPlayer;
  TranslateTransition  transition = new TranslateTransition();
 
    /**
     * Initializes the controller class.
     */

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    transition.setDuration(Duration.millis(50)); 
    tankImage.requestFocus();
    tankImage.setFocusTraversable(true);
 
    Media sound = new Media(MainMenuController.class.getResource("sound/pick.mp3").toExternalForm());
    mediaPlayer = new MediaPlayer(sound);  
    tankImage.addEventFilter(KeyEvent.KEY_PRESSED, new thisEvent() );
 
    }    

   
    class thisEvent implements EventHandler<KeyEvent>{
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
                 Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            }
             if(pointerPosition==2 && e.getCode()== KeyCode.ENTER){
                Parent home_page_parent = null;
                try {
                    home_page_parent = FXMLLoader.load(getClass().getResource("Help.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(Panzer2017.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene home_page_scene = new Scene(home_page_parent);
                 Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            }
              if(pointerPosition==1 && e.getCode()== KeyCode.ENTER){
                Parent home_page_parent = null;
                try {
                    home_page_parent = FXMLLoader.load(getClass().getResource("Settings.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(Panzer2017.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene home_page_scene = new Scene(home_page_parent);
                 Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            }
              
                if(pointerPosition==0 && e.getCode()== KeyCode.ENTER){
               Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        app_stage.hide();
                try {
                    app_stage.setScene(new GamePanel(new Group(), app_stage));
                } catch (IOException ex) {
                    Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
   
}
