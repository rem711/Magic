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
public abstract class DecorateurCarte extends Carte {
    protected Carte carte;
    
    public abstract String getNom();
    public abstract int getCoup();
    public abstract int getAtt();    
    public abstract int getDef();    
    public abstract boolean getFat();    
    public abstract boolean estVolant();
    public abstract boolean estRapide();
    public abstract boolean getBonus();
    public abstract boolean estEnchante();
    public abstract int getTypeEnchantement();
    
    public  void setAtt(int a) {
        this.carte.setAtt(a);
    }
    
    public void setDef(int d) {
        this.carte.setDef(d);
    }
    
    public void setFat(boolean f) {
        this.carte.setFat(f);
    }
}
