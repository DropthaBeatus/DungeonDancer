/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import java.awt.Toolkit;
import static java.lang.Integer.min;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javax.swing.ImageIcon;

/**
 *
 * @author beatus
 */
public class Goblin extends Monster implements AttackAudio{
    
    public String image = "@AssetsForJava/goblin.png";


    private final Double startHue = 260.0;
    
    
    public Goblin(){
        super("Goblin", 50);
        
    }


}
