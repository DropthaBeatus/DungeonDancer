/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author beatus
 */
public class StartFXMLController implements Initializable{

    @FXML
    private TextField textField;
    @FXML
    private TextArea hsc;
    @FXML
    private Label Warning;
    private Stage stage;
    public Scene mainScene;
    public Scene combatScene;
    public int playerGold;
    public String playerName;
    public FXMLDocumentController documentController;
    public ArrayList<String> highscoresName = new ArrayList<>();
    public ArrayList<Integer> highscoresNum = new ArrayList<>();
    public String currentDir;
    public String test;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readScores();
        Warning.setText("");
    }
    

    @FXML
    private void handleWizard(Event event) {
        String name = textField.getText();
        if(checkName(name)==false){
            Warning.setText("Name must not contain Spaces or be Null!");
        }
        else{
            Player player = new Wizard(name);
            changeScene(player);
        }

    }
    
    @FXML
    private void handleKnight(Event event) {
        String name = textField.getText();
        if(checkName(name)==false){
            Warning.setText("Name must not contain Spaces or be Null!");
        }
        else{
            Player player = new Knight(name);
            changeScene(player);
        }
        
    }
    
    @FXML    
    private void handleThief(Event event) {
        
        String name = textField.getText();
        if(checkName(name)==false){
           Warning.setText("Name must not contain Spaces or be Null!");
        }
        else{
            Player player = new Rogue(name);
            changeScene(player);
        }
        
    }
    
    private Boolean checkName(String name){
        if(name.equals("")){
            return false;
        }
        else if(name.contains(" ")){
            return false;
        }
        else{
        return true;
        }
        
    }
    
    private void changeScene(Player player){
         if (combatScene == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                Parent combat2Root = loader.load(); 
                documentController = loader.getController();
               // documentController.mainScene = mainScene;
                documentController.startController = this;
                combatScene = new Scene(combat2Root);
               
                
            } catch (IOException ex) {
                Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        stage.setScene(combatScene);
        documentController.highscoresName = highscoresName;
        documentController.highscoresNum = highscoresNum;
        documentController.player = player;
        documentController.mainScene = mainScene;
        documentController.start(stage);
        
    }
    
    public void start(Stage stage) {
        System.out.println("Page 1 has started");
        this.stage = stage; 
        mainScene = stage.getScene();
    }
    /*
    @FXML
    public void checkScoreBoard(String name, Integer playerScore){
        for(int i = 0 ; i <= highscoresNum.size()-1; i++){
            String pastName = "";
            int pastScore = -1;
            if(playerScore > highscoresNum.get(i)){
                i = highscoresNum.size();
                pastScore = highscoresNum.get(i);
                pastName = highscoresName.get(i);
                highscoresNum.set(i, playerScore);
                highscoresName.set(i, name);
                playerScore = pastScore;
                name = pastName;
                System.out.println("Write to file called. Size of highscores is " + highscoresNum.size());
            }
  
        }
         writeToFile();
    }
   */ 

    public void writeToFile(){
        System.out.println("Highscores does not exsist creating a new file");
        
        hsc.setText("");
        FileOutputStream outputStream = null;
        
        currentDir = Paths.get("").toAbsolutePath().toString();//
        test = currentDir + "/src/dungeondancer/highscores.txt";
        String allScores = "";
        for(int i = 0; i < 10; i++){
                allScores += "Nobody 0\n";
            }
         FileWriter fileWriter = null;
           try {
            
            outputStream = new FileOutputStream(test);
            byte[] strToBytes = allScores.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
            readScores();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outputStream.close();
                hsc.setText("");
            } catch (IOException ex) {
                Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }    
    }

    public void readScores(){
        System.out.println("Read scores being called!");
        String temp[];
        hsc.setText("");
    BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("highscores.txt")));
        try {
            if(!br.ready()){
                writeToFile();
            }   } catch (IOException ex) {
            Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            String s="";
             highscoresName.clear();
            highscoresNum.clear();
        try {
            int i = 0;
            while((s=br.readLine())!=null && s.length()!=0) {
            hsc.appendText(s + "\n");
            temp = s.split(" ", 2);
            highscoresName.add(temp[0]);
            highscoresNum.add(Integer.valueOf(temp[1]));
            }
            br.close();  
        } catch (IOException ex) {
            Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

}
