/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.pkg2017;

import java.io.File;
import java.io.FileWriter;
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
import panzer.brainClass.FileManager;
/**
 *
 * @author Ndricim Rrapi
 */
public class Panzer2017 extends Application  {
    
    @Override
    public void start(Stage stage) throws Exception {
      
       Parent root= null;
       try {
           root = FXMLLoader.load(Panzer2017.class.getResource("MainMenu.fxml"));
       } catch (IOException ex) {
           ex.printStackTrace();
       }
       Scene nnew=new Scene(root);     
       stage.setScene(nnew);
       stage.show();
      
       

    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
   
}
