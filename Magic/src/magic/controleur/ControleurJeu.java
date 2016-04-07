/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.controleur;

import magic.modele.*;

/**
 *
 * @author rem711
 */
public class ControleurJeu {
    
    private Jeu jeu;
    private int xCarte;
    private int yCarte;
    
    public ControleurJeu(Jeu j) {
        this.jeu = j;
        this.xCarte = -1;
        this.yCarte = -1;
    }
    
    public void clicCarte(int x, int y) {
        if(jeu.monTour()) {
            if((xCarte == -1) && yCarte == -1) {
                xCarte = x;
                yCarte = y;
            }
            else {
                jeu.setMouvement(xCarte, yCarte, x, y);
                xCarte = -1;
                yCarte = -1;
            }
        }
    }
    
    public void phaseSuivante() {
        if(jeu.monTour()) {
            jeu.setNext();
            xCarte = -1;
            yCarte = -1;
        }
    }
    
    
}
