/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import java.awt.Button;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 *
 * @author beatus
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label health;
    
    @FXML
    private ImageView monsterView;
    
    @FXML
    private Label monsterName;
    @FXML
    private AnchorPane padPane;
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Rectangle leftRec;
    @FXML
     private Rectangle midRec;
    @FXML
     private Rectangle rightRec;
    
    
    private Media media;
    private MediaPlayer mediaPlayer;
    private final Double updateInterval = 0.05;
    private double leftPos= 0.0;
    private double midPos = 0.0;
    private double rightPos = 0.0;
    private Monster monster;
    public Player player;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player = new Player();
        monster = returnRandomMonster();
        health.setText(player.getHealth());
        File file = new File(monster.image);
        //Image image = new Image(file.toURI().toString());
        monsterName.setText(monster.getName());
        //monsterView.setImage(image);
        
        leftPos = leftRec.getLayoutY();
        midPos = midRec.getLayoutY();
        rightPos = rightRec.getLayoutY();
    }

    public Monster returnRandomMonster(){
        int randomNum = new Random().nextInt();
        randomNum = randomNum % 3;
        System.out.println(randomNum);
        switch(randomNum){
            case 0 : 
                 return new Goblin();
            case 1 : 
                 return new Dragon();
            case 2: 
                return new Slime();
            default :
                return new Goblin();
                
        }
    }
    
    private void openMedia(File file) {
        //filePathText.setText("");
        //errorText.setText("");
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
            System.out.println("mediaPlayer is disposed");
        }
        
        try {
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnReady(() -> {
                handleReady();
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                handleEndOfMedia();
            });
           mediaPlayer.setAudioSpectrumNumBands(3);
           mediaPlayer.setAudioSpectrumInterval(updateInterval);
           mediaPlayer.setAudioSpectrumListener((double timestamp, double duration, float[] magnitudes, float[] phases) -> {
                handleUpdate(timestamp, duration, magnitudes, phases);
            });
            mediaPlayer.setAutoPlay(true);
            
        } catch (Exception ex) {
            System.out.println("Error Running into playing media: " + ex);
        }
    }
        
            
    private void handleReady() {
        //Duration duration = mediaPlayer.getTotalDuration();
       // lengthText.setText(duration.toString());
        //Duration ct = mediaPlayer.getCurrentTime();
       // currentText.setText(ct.toString());
        monster.start(3, padPane);
        //timeSlider.setMin(0);
        //timeSlider.setMax(duration.toMillis());
    }
    
    private void handleEndOfMedia() {
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        //timeSlider.setValue(0);
    }
    
    private void handleUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        Duration ct = mediaPlayer.getCurrentTime();
        double ms = ct.toMillis();
        //currentText.setText(String.format("%.1f ms", ms));
        //timeSlider.setValue(ms);
        monster.update(timestamp, duration, magnitudes, phases);
    }
    
    @FXML
    private void handleOpen(Event event) {
        Stage primaryStage = (Stage)padPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            openMedia(file);
        }
    }
    
    @FXML
    private void handleLeft(Event event) {
        if(monster.ellipsesLeft == null){
            return;
        }
        System.out.println(leftPos);
        for (int i = 0; i < monster.ellipsesLeft.size(); i++){
            System.out.println(monster.ellipsesLeft.get(i).getCenterY());
            if(compareValues(leftPos, monster.ellipsesLeft.get(i).getCenterY())){
                if("DEAD".equals(monster.damage(player.damage))){

                    initMonster();
                }
            }
        }
        
    }
    
    @FXML
    private void handleMid(Event event) {
        if(monster.ellipsesMid == null){
            return;
        }
        for (int i = 0; i < monster.ellipsesMid.size(); i++){
            if(compareValues(midPos, monster.ellipsesMid.get(i).getCenterY())){
                if("DEAD".equals(monster.damage(player.damage))){

                    initMonster();
                }
            }
        }
    }
    
    @FXML
    private void handleRight(Event event) {
        if(monster.ellipsesRight == null){
            return;
        }
        for (int i = 0; i < monster.ellipsesRight.size(); i++){
            if(compareValues(rightPos, monster.ellipsesRight.get(i).getCenterY())){
                if("DEAD".equals(monster.damage(player.damage))){
                    
                    initMonster();
                }
            }
        }
        
    }
    
    private Boolean compareValues(double recLoc, double ellipseLoc){
        double check = recLoc - ellipseLoc;
        if(check < 20 && check > -20){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void initMonster(){
        monster.end();
        monster = returnRandomMonster();
        monsterName.setText(monster.getName()); 
        
    }

    
    
}
