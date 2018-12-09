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
public class Rogue extends Player{
    
    public Rogue(String name){
        super(name);
        super.health = 10;
        super.damage = 22;
    }
    
    public void upSlain(){
        super.upSlain(damage);
        if((super.slainMonst % 10) == 0){
            super.health++;
        }
    }   
}
