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
public class Creature extends Carte implements Cloneable{

    public Creature(String nom, int cout, int att, int def, int fat) {
        this.setNom(nom);
        this.setCout(cout);
        this.setAtt(att);
        this.setDef(def);
        this.setFat(false);
        this.setVol(false);
        this.setRapide(false);
        this.setBonus(false);
        this.setEnchante(false);
        this.setTypeEnchantement(0);
    }
    
}
