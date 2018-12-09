/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondancer;



/**
 *
 * @author beatus
 */
public class Goblin extends Monster implements AttackAudio{
    //private = Paths.currentDir.get("").toAbsolutePath().toString();
    //public BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("AssetsForJava/goblin.png")));

    
    public String name = "Goblin";
    
    public Goblin(Player player){
        super("Goblin", 150, 15, player);
        
    }


}
