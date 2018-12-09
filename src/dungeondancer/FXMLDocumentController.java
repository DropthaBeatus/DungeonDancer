/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.animation.Animation.INDEFINITE;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author beatus
 */
public class FXMLDocumentController implements Initializable{
    private Stage stage;
    public Scene combatScene; 
    public Scene mainScene;
    public StartFXMLController startController;
    
    @FXML
    private Label health;
    @FXML
    private Label playerDamage;
    @FXML
    private Label playerGold;
    @FXML
    private Label monstHP;
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
    @FXML
    private Label playerName;
    

    private Media media;
    private MediaPlayer mediaPlayer;
    private final Double updateInterval = 0.05;
    private double leftPos= 0.0;
    private double midPos = 0.0;
    private double rightPos = 0.0;
    private Monster monster;
    public Player player;
    public ArrayList<String> highscoresName = new ArrayList<>();
    public ArrayList<Integer> highscoresNum = new ArrayList<>();
    
     Timeline timelineBeep = new Timeline(new KeyFrame(Duration.millis(100), (ActionEvent actionEvent) ->{
           heartMonitor();
        }));
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
   
    }
    

    public void start(Stage stage){
        this.stage = stage;
        System.out.print(player.name);
        initializeGame();
    }
    
    @FXML
    public void initializeGame(){
        playerName.setText(player.name);
        monster = returnRandomMonster();
        health.setText(player.getHealth());
        monsterName.setText(monster.name);
        monstHP.setText(monster.getHealth());
        playerGold.setText(player.getWealth());
        playerDamage.setText(player.getDamage());
        leftPos = leftRec.getLayoutY();
        midPos = midRec.getLayoutY();
        rightPos = rightRec.getLayoutY();
        timelineBeep.setCycleCount(INDEFINITE);
        timelineBeep.play();
    }
    
    public Monster returnRandomMonster(){
        int randomNum = new Random().nextInt();
        randomNum = Math.abs(randomNum % 3);
        switch(randomNum){
            case 0 : 
                 return new Goblin(player);
            case 1 : 
                 return new Dragon(player);
            case 2: 
                return new Slime(player);
            default :
                return new Goblin(player);
                
        }
    }
    
    private void openMedia(File file) {

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
           mediaPlayer.setAudioSpectrumNumBands(50);
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
        monster.start(30, padPane);
    }
    
    private void handleEndOfMedia() {
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        //timeSlider.setValue(0);
    }
    
    private void handleUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        health.setText(player.getHealth());
        Duration ct = mediaPlayer.getCurrentTime();
        double ms = ct.toMillis();
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
                monster.delObject(monster.ellipsesLeft, i);
                if("DEAD".equals(monster.damage(player.damage))){
                    //monster.ellipsesRight.remove(i);
                    
                    initMonster();
                }
                monstHP.setText(monster.getHealth());
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
                monster.delObject(monster.ellipsesMid, i);
                if("DEAD".equals(monster.damage(player.damage))){
 
                    initMonster();
                }
                monstHP.setText(monster.getHealth());
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
                 monster.delObject(monster.ellipsesRight, i);
                if("DEAD".equals(monster.damage(player.damage))){

                    initMonster();
                }
                monstHP.setText(monster.getHealth());
            }
        }
        
    }
    
    private Boolean compareValues(double recLoc, double ellipseLoc){
        double check = recLoc - ellipseLoc;
        if(check < -155 && check > -240){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void initMonster(){
        monster.end();
        player.upSlain(monster.generateWealth());
        playerGold.setText(player.getWealth());
        monster = returnRandomMonster();
        monstHP.setText(monster.getHealth());
        monsterName.setText(monster.getName()); 
        monster.start(30, padPane); 
    }

    @FXML
    private void handleOpenShop(Event event){
       endGame();
    }
    
    private void endGame(){
        if(mediaPlayer != null){
            monster.end();
            handleEndOfMedia();
        }
        checkScoreBoard(player.name, Integer.valueOf(player.getWealth()));
        stage.setScene(mainScene);
        startController.highscoresNum = highscoresNum;
        startController.highscoresName = highscoresName;
    }
    

    public void heartMonitor(){
        int x = Integer.valueOf(player.getHealth());
        if( x < 1){
            endGame();
        }

    }   

    public void checkScoreBoard(String name, Integer playerScore){
        for(int i = 0 ; i <= highscoresNum.size()-1; i++){
            String pastName = "";
            int pastScore = -1;
            if(playerScore > highscoresNum.get(i)){
                pastScore = highscoresNum.get(i);
                pastName = highscoresName.get(i);
                highscoresNum.set(i, playerScore);
                highscoresName.set(i, name);
                playerScore = pastScore;
                name = pastName;
                System.out.println("Write to file called in fsml doc controller");
               
               // i = -1;
            }
  
        }
         writeToFile();
    }
    

    public void writeToFile(){
        FileOutputStream outputStream = null;
        
        String currentDir = Paths.get("").toAbsolutePath().toString();//
        String test = currentDir + "/src/dungeondancer/highscores.txt";
        String allScores = "";
        for(int i = 0; i < highscoresNum.size(); i++){
                allScores += highscoresName.get(i) + " " + highscoresNum.get(i) + "\n";
            }
        System.out.println(allScores);
            FileWriter fileWriter = null;
           try {
            
            outputStream = new FileOutputStream(test);
            byte[] strToBytes = allScores.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    

    
}
