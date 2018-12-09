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
abstract class Player{
    
    int health;
    public String name;
    public int damage = 10;
    protected int slainMonst = 1;
    public int wealth = 0;

    
    public Player(){
        health = 3;
        name = "Player";
    }
    
    public Player(String name){
        health = 5;
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
    
    public void upSlain(int plunder){
        this.wealth += plunder * slainMonst;
        slainMonst += .1;
    }
    
    public String getWealth(){
        return String.valueOf(wealth);
    }
    public String getDamage(){
        return String.valueOf(damage);
        
    }
   
    
}
