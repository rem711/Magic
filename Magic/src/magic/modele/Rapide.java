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
public class Rapide extends DecorateurCarte implements Cloneable{
    
    public Rapide(Carte c) {
        this.carte = c;
    }

    @Override
    public String getNom() {
        return carte.getNom() + " rapide";
    }

    @Override
    public int getCoup() {
        return carte.getCoup();
    }

    @Override
    public int getAtt() {
        return carte.getAtt();
    }

    @Override
    public int getDef() {
        return carte.getDef();
    }

    @Override
    public boolean getFat() {
        return carte.getFat();
    }

    @Override
    public boolean estVolant() {
        return carte.estVolant();
    }

    @Override
    public boolean estRapide() {
        carte.setRapide(true);
        this.setRapide(true);
        return true; 
    }

    @Override
    public boolean getBonus() {
        carte.setBonus(true);
        this.setBonus(true);
        return true;
    }

    @Override
    public boolean estEnchante() {
        return carte.estEnchante();
    }

    @Override
    public int getTypeEnchantement() {
        return carte.getTypeEnchantement();
    }
}
