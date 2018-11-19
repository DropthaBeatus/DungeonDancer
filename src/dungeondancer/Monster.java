/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import static java.lang.Integer.min;
import java.util.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


/**
 *
 * @author beatus
 */
public class Monster implements AttackAudio {
    
    private int health;
    private String name;
    
    
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
    
    private float[] pastMag = {0,0,0};
    
    public String image = "@AssetsForJava/goblin.png";
    
    public Monster(){
        
        health = 100;
        name = "Monster";
    }
    
    public Monster(String name, int health){
        this.health = health;
        this.name = name;
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

        bandWidth = width / numBands;
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
        addElipses(ellipsesLeft, pastMag[0], magnitudes[0]);
        addElipses(ellipsesMid, pastMag[1], magnitudes[1]);
        addElipses(ellipsesRight, pastMag[2], magnitudes[2]);
        moveElipses(ellipsesLeft);
        moveElipses(ellipsesMid);
        moveElipses(ellipsesRight);
        
        //System.out.println(startHue - (magnitudes[0] * -6.0));
        
        pastMag[0] = magnitudes[0];
        pastMag[1] = magnitudes[1];
        pastMag[2] = magnitudes[2];
    }
    
    private void addElipses(ArrayList<Ellipse> ellipses, float pastMag, float currMag){
        if( CompareMag(pastMag, currMag) == true){
           
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidth / 2 + bandWidth * 0);
            ellipse.setCenterY(0);
            ellipse.setRadiusX(minEllipseRadius);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue - (currMag * -20.0), 1.0, 1.0, 1.0));
            double hue = startHue - (currMag * -6.0);
            padPane.getChildren().add(ellipse);

            ellipses.add(ellipse);
        }
    }
    
    public Boolean CompareMag(float pastMag, float CurrMag){
        double total = pastMag - CurrMag;
        if(total > 6){
            return true;
        }
        else if(total < -6){
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
                ellipses.remove(i);
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
    
    
}
