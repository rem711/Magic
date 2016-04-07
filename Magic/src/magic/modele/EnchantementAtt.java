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
public class EnchantementAtt extends DecorateurCarte implements Cloneable{

    public EnchantementAtt(Carte c) {
        this.carte = c;
    }
    
    @Override
    public String getNom() {
        return carte.getNom() + " attaquante";
    }

    @Override
    public int getCoup() {
        return carte.getCoup();
    }

    @Override
    public int getAtt() {
        return carte.getAtt() + 2;
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
        return carte.estRapide();
    }

    @Override
    public boolean getBonus() {
        return carte.getBonus();
    }

    @Override
    public boolean estEnchante() {
        carte.setEnchante(true);
        this.setEnchante(true);
        return true;
    }

    @Override
    public int getTypeEnchantement() {
        carte.setTypeEnchantement(1);
        this.setTypeEnchantement(1);
        return 1;
    }
    
}
