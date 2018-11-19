/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;

import javafx.scene.image.Image;

/**
 *
 * @author beatus
 */
public class Slime extends Monster implements AttackAudio{
    
    public String image = "@AssetsForJava/slime.gif";
    
    public Slime(){
        super("Slime", 25);
    }

    
}
