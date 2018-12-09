/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import static java.lang.Integer.min;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


/**
 *
 * @author beatus
 */
public class Monster implements AttackAudio {
    
    private int health;
    public String name;
    private int gold;
    
    private Player player;
    
    private AnchorPane padPane;
    private Double width = 0.0;
    private Double height = 0.0;
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private final Double bandHeightPercentage = 1.3;
    
    
    //change to fireballs
    public ArrayList<Ellipse> ellipsesLeft = new ArrayList<>();
    public ArrayList<Ellipse>ellipsesMid = new ArrayList<>();
    public ArrayList<Ellipse>ellipsesRight = new ArrayList<>();
    
    private final Double minEllipseRadius = 20.0;  // 10.0

    private final Double startHue = 260.0;
    
    private float[] pastMag = {90,70,60};
    
    //public String name = "Monster";

    public Monster(String name, int health, int gold, Player player){
        this.health = health;
        this.name = name;
        this.gold = gold;
        this.player = player;
    }
    
    public String damage(int damage){
        health -= damage;
        if(checkStatus()){
        return String.valueOf(health);
        }
        else{
            return "DEAD";
        }
    }
    
    public Boolean checkStatus(){
        if(health > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public String getName(){
        return name;
    }
    
    public String getHealth(){
        return String.valueOf(health);
    }
    
    

    @Override
    public void start(int numBands, AnchorPane vizPane) {
        //end();
 
        this.padPane = vizPane;
        height = vizPane.getHeight();
        width = vizPane.getWidth();

        bandWidth = width / 3;
        bandHeight = height * bandHeightPercentage;
        
  
    }

    @Override
    public void end() {
        
        if (ellipsesLeft != null) {
             for (Ellipse ellipse : ellipsesLeft) {
                 padPane.getChildren().remove(ellipse);
             }
            ellipsesLeft = null;
        }
                    
        if (ellipsesRight != null) {
             for (Ellipse ellipse : ellipsesRight) {
                 padPane.getChildren().remove(ellipse);
             }
            ellipsesRight = null;
        }
                    
        if (ellipsesMid != null) {
             for (Ellipse ellipse : ellipsesMid) {
                 padPane.getChildren().remove(ellipse);
             }
            ellipsesMid = null;
        }

    }

    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if(checkIfNull()){
            return;
        }
        
        //need to balance this part better
        addElipsesL(ellipsesLeft, pastMag[0], magnitudes[5], 0);
        addElipsesM(ellipsesMid, pastMag[1], magnitudes[7], 1);
        addElipsesR(ellipsesRight, pastMag[2], magnitudes[9], 2);
        moveElipses(ellipsesLeft);
        moveElipses(ellipsesMid);
        moveElipses(ellipsesRight);
        
        //System.out.println(startHue - (magnitudes[0] * -6.0));
        
        pastMag[0] = magnitudes[5];
        pastMag[1] = magnitudes[7];
        pastMag[2] = magnitudes[9];
    }
    
    private void addElipsesL(ArrayList<Ellipse> ellipses, float pastMag, float currMag, int distance){
        if( compareMagL(pastMag, currMag) == true){
           //need to specify if right or left here 
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidth / 2 + bandWidth * distance);
            ellipse.setCenterY(0);
            ellipse.setRadiusX(minEllipseRadius);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue - (currMag * -20.0), 1.0, 1.0, 1.0));
            double hue = startHue - (currMag * -6.0);
            ellipse.toBack();
            padPane.getChildren().add(ellipse);

            ellipses.add(ellipse);
        }
    }

    public Boolean compareMagL(float pastMag, float CurrMag){
        double total = pastMag / CurrMag;
        if(total > 1.20){
            return true;
        }
        else if(total < .80 ){
            return true;
        }
        else{
            return false;
        }
    }
        private void addElipsesM(ArrayList<Ellipse> ellipses, float pastMag, float currMag, int distance){
        if( compareMagM(pastMag, currMag) == true){
           //need to specify if right or left here 
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidth / 2 + bandWidth * distance);
            ellipse.setCenterY(0);
            ellipse.setRadiusX(minEllipseRadius);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue - (currMag * -20.0), 1.0, 1.0, 1.0));
            double hue = startHue - (currMag * -6.0);
            ellipse.toBack();
            padPane.getChildren().add(ellipse);

            ellipses.add(ellipse);
        }
    }

    public Boolean compareMagM(float pastMag, float CurrMag){
        double total = pastMag / CurrMag;
        if(total > 1.15){
            return true;
        }
        else if(total < .85 ){
            return true;
        }
        else{
            return false;
        }
    }
        private void addElipsesR(ArrayList<Ellipse> ellipses, float pastMag, float currMag, int distance){
        if( compareMagR(pastMag, currMag) == true){
           //need to specify if right or left here 
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidth / 2 + bandWidth * distance);
            ellipse.setCenterY(0);
            ellipse.setRadiusX(minEllipseRadius);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue - (currMag * -20.0), 1.0, 1.0, 1.0));
            double hue = startHue - (currMag * -6.0);
            ellipse.toBack();
            padPane.getChildren().add(ellipse);

            ellipses.add(ellipse);
        }
    }

    public Boolean compareMagR(float pastMag, float CurrMag){
        double total = pastMag / CurrMag;
        if(total > 1.13){
            return true;
        }
        else if(total < .87 ){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void moveElipses(ArrayList<Ellipse> ellipses){
        for (int i = 0; i < ellipses.size(); i++){
            double pos = ellipses.get(i).getCenterY();
            ellipses.get(i).setCenterY(pos + 2);
            //need to find a way to remove from the padPane
            //maybe get ref of memory with adjacent array list and remove from there????
            if(pos - minEllipseRadius > height){
                //Object del = ellipses.get(i);
               // del = null;
                //ellipses.remove(i);
                delObject(ellipses, i);
                hurtPlayer();
            }
        }
        
    }
    
    public Boolean checkIfNull(){
        if(ellipsesLeft == null && ellipsesRight == null && ellipsesMid == null){
            return true;
        }
        else{
            return false;
        }
    }
    //make this private
    public void delObject(ArrayList<Ellipse> ellipses, int i){
    //tried to reference object to delete but this is not working!!!!!!!!!!
        padPane.getChildren().remove(ellipses.get(i));
        ellipses.remove(i);
    }
    
    public void delObject(double posY, ArrayList<Ellipse> ellipses){
        
        for(int i = 0; i < ellipses.size(); i++){
            if( posY > ellipses.get(i).getCenterY()+25 || posY < ellipses.get(i).getCenterY()-25){
                     System.out.println("Position of Y ------->" + posY);
                     System.out.println("Position of ellipses ------->" + ellipses.get(i).getCenterY());
                    
            }
            else{
                padPane.getChildren().remove(ellipses.get(i));
            }
        }
    }
    
    
    public int generateWealth(){
        return new Random().nextInt(gold);
    }
    
    private void hurtPlayer(){
        player.loseHP();
    }
    
}
