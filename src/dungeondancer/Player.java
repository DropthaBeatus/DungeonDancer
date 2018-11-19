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
public class Player {
    
    private int health;
    private String name;
    public int damage = 10;
    
    public Player(){
        health = 3;
        name = "Player";
    }
    
    public Player(String name){
        health = 3;
        this.name = name;
    }
    
    public void loseHP(){
        health--;
    }
    
    public String getName(){
        return name;
    }
    
    public String getHealth(){
        return String.valueOf(health);
    }
    
    
}
