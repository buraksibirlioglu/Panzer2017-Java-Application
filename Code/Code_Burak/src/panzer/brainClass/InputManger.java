/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panzer.brainClass;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import panzer.pkg2017.Panzer2017;

/**
 *
 * @author Ndricim Rrapi
 */
public class InputManger implements EventHandler<KeyEvent> {
    int pressed_or_released; // 1 = keypressed ; 2 = keyreleased
    
    public InputManger(int pressed_or_released){
        this.pressed_or_released = pressed_or_released;
    }
    
    @Override
    public void handle(KeyEvent e){  
       //handle as if keyPressed event
        if (pressed_or_released == 1){
            switch (e.getCode()) {
                case UP :  
                    if (GameEngine.getPlayerTank().isAlive())
                        GameEngine.getPlayerTank().moveUp(false);
                    break;
                case DOWN:
                    if (GameEngine.getPlayerTank().isAlive())
                        GameEngine.getPlayerTank().moveDown(false);
                    break;
                case RIGHT:
                    if (GameEngine.getPlayerTank().isAlive())
                        GameEngine.getPlayerTank().moveRight(false);
                    break;
                case LEFT:
                    if (GameEngine.getPlayerTank().isAlive())
                        GameEngine.getPlayerTank().moveLeft(false);
                    break;
                case SPACE:
                    if (GameEngine.getPlayerTank().isAlive())
                        GameEngine.getPlayerTank().shootMetal();    
                    break;
                case B:
                    if (GameEngine.getPlayerTank().hasIceBullet())
                        GameEngine.getPlayerTank().shootIce();    
                    break;
            }   
        }else if (pressed_or_released == 2) { // handle as onKeyReleased
             switch (e.getCode()) {
               case UP:
                  GameEngine.getPlayerTank().moveUp(true);
                  break;
               case DOWN:
                  GameEngine.getPlayerTank().moveDown(true);
                  break;
               case RIGHT:
                  GameEngine.getPlayerTank().moveRight(true);
                  break;
               case LEFT:
                   GameEngine.getPlayerTank().moveLeft(true);
                  break;
            case P:
                    GameEngine.timer.stop();
                try {
                    FXMLLoader loader = new FXMLLoader(Panzer2017.class.getResource("PauseMenu.fxml"));
                    Parent root = loader.load();
                    Scene nnew=new Scene(root);
                    Stage pause_stage=new Stage();
                    pause_stage.setTitle("Pause Menu");
                    pause_stage.setScene(nnew);
                    pause_stage.show();
                    
                    pause_stage.setOnHidden(t -> {
                        try {
                            GameEngine.showPauseMenu();
                        } catch (IOException ex) {
                              ex.printStackTrace();
                        }
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                   break;
            }
        }
    }
}