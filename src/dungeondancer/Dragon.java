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
public class Dragon extends Monster implements AttackAudio{
    
    public String image ="@AssetsForJava/dragon.png";
    
    public Dragon(){
        super("Dragon", 250); 
    }
    
}
