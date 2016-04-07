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
public abstract class Carte {
    private String nom;
    private int cout;
    private int att;
    private int def;
    private boolean fat;
    private boolean rapide;
    private boolean vol;
    private boolean bonus; // permet de déterminer carte classique et autres
    private boolean enchante;
    // 0: aucun - 1: arrivée en jeu - 2: arrivée nouvelle créature
    private int typeEnchantement;

    /*public Carte(String nom, int cout, int att, int def, int bonus) {
        this.nom = nom;
        this.cout = cout;
        this.att = att;
        this.def = def;
        this.fat = false;
        this.bonus=bonus;
    }*/
    
    public Carte getCarte()
    {
        return this;
    }
    public String getNom()
    {
        return this.nom;
    }
    
    public void setNom(String s) {
        this.nom = s;
    }
    
    public int getCoup()
    {
        return this.cout;
    }
    
    public void setCout(int c) {
        this.cout = c;
    }
    
    public int getAtt()
    {
        return this.att;
    }
    
    public void setAtt(int a) {
        this.att = a;
    }
    
    public int getDef()
    {
        return this.def;
    }
    
    public void setDef(int d) {
        this.def = d;
    }
    
    public boolean getFat()
    {
        return this.fat;
    }
    
     public void setFat(boolean b)
    {
        this.fat=b;
    }
    
     public boolean estVolant() {
         return this.vol;
     }
     
     public void setVol(boolean b) {
         this.vol = b;
     }
     
     public boolean estRapide() {
         return this.rapide;
     }
     
     public void setRapide(boolean b) {
         this.rapide = b;
     }
     
     public boolean getBonus() {
         return this.bonus;
     }
     
     public void setBonus(boolean b) {
         this.bonus = b;
     }
     
     public boolean estEnchante() {
         return this.enchante;
     }
     
     public void setEnchante(boolean b) {
         this.enchante = b;
     } 
     
     public int getTypeEnchantement() {
         return this.typeEnchantement;
     }
     
     public void setTypeEnchantement(int t) {
         this.typeEnchantement = t;
     }
     
     public Carte clone() {
        Carte o = null;
        try {
                // On récupère l'instance à renvoyer par l'appel de la 
                // méthode super.clone()
            o = (Carte) super.clone();
        } catch(CloneNotSupportedException cnse) {
                // Ne devrait jamais arriver car nous implémentons 
                // l'interface Cloneable
                cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }
}
