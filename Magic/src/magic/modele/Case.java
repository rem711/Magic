/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.modele;


/**
 *
 * @author rem711
 */
public class Case implements Cloneable {
    private int x;
    private int y;
    private boolean contientCarte;
    private Carte carte;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.contientCarte = false;
    }
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public void setX(int x2)
    {
        this.x=x2;
    }
    
    public void ajouterCarte(Carte c) {
        this.carte = c;
        this.contientCarte = true;
    }
    
    public void retirerCarte() {
        this.carte = null;
        this.contientCarte = false;
    }
    public boolean contientCarte()
    {
        return this.contientCarte;
    }
    public Carte getCarte()
    {
        return this.carte;
    }
    
    public Case clone() {
        Case o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
                o = (Case) super.clone();
        } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        if (contientCarte()) 
        {
            o.carte = this.carte.clone();
        }
        // on renvoie le clone
        return o;
    }
}
