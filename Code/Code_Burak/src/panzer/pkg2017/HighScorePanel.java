/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import panzer.brainClass.FileManager;
import panzer.entities.Score;

/**
 * FXML Controller class
 *
 * @author Ndricim Rrapi
 */
public class HighScorePanel implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Label scores_label;
    @FXML private Label name_label;
    @FXML private ImageView back_highscores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        FileManager fileManager = new FileManager();
        String text = "";
        String text2="";
        ArrayList<Score> array = fileManager.getHighScores();
        for (int i = 0; i < array.size();i++){
            //int Score=Integer.parseInt(array.get(i).substring(8));
            //System.out.println("asd"+array.get(i).substring(array.get(i).indexOf(" ")));
            text+= i+1 +". "+array.get(i).getName() +"\n";
            text2 += array.get(i).getScore()+"\n";
        }
        name_label.setText(text);
        scores_label.setText(text2);
        back_highscores.requestFocus();
        back_highscores.setFocusTraversable(true);
        back_highscores.setOnKeyPressed(new EventHandler<KeyEvent>() {
            int position = 0;  
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode()== KeyCode.ENTER){
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
        });
    }    
  
    
}
