/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.main;

import magic.controleur.*;
import magic.modele.*;
import magic.vue.*;

/**
 *
 * @author rem711
 */
public class RunMagic {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws InterruptedException, CloneNotSupportedException{
        // TODO code application logic here
        Joueur j1 = new JoueurHumain(1, "Audrey");
        //Joueur j2= new JoueurAleatoireFacile(2, "Deep Blue");
        Joueur j2= new JoueurAleatoireDifficile(2, "Deep Blue");
        
        Jeu j= new Jeu(j1, j2);
        
        ControleurJeu controleur = new ControleurJeu(j);
        
        Vue vue = new Vue(controleur);
        j.addObserver(vue);     
        
        j.jouerPartie2();
        
    }
    
}
