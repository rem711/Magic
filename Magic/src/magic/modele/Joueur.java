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
public abstract class Joueur {
    private int id;
    private String nom;
    private int ressources;
    private int pv;

    public Joueur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.ressources = 0;
        this.pv = 10;
    }
    
    
    public int getPv()
    {
        if(this.pv < 0) {
            this.pv = 0;
        }
        return this.pv;
    }
    public void setPv(int s)
    {
        this.pv=s;
    }
    public String getNom()
    {
        return this.nom;
    }
    
    public void setRessources(int r)
    {
        this.ressources=r;
    }
    
     public int getRessources()
    {
        return ressources;
    }

    public abstract boolean getJouer();
    
     //retourne la case selectionnée pour attaquer
    public abstract Case getCaseAttaque(Plateau p);
    
    //retourne la case selectionnée pour invoquer
    public abstract Case getCaseInvocation(Plateau p);
    
    //retourne la case à être défendue selectionnée
    public abstract Case getCaseDefenseArr(Plateau p);
    
    //retourne la carte selectionnée pour défendre + regarder si elle est fatiguée
    public abstract Case getCaseDefenseDep(Plateau p);
    
    public abstract Case getCaseDefenseDep(Plateau p, int jx, int jy);
    
    public abstract Case getCaseDefenseArr(Plateau p, int jx, int jy);
    
    public abstract Case getCaseInvocation(Plateau p, int jx, int jy);
    
    public abstract Case getCaseAttaque(Plateau p, int jx, int jy);
    
    public abstract CoupInvocation getCoupInvocation(Jeu j) throws InterruptedException;
    
    public abstract CoupAttaque getCoupAttaque(Jeu j) throws InterruptedException;
    
    public abstract CoupDefense getCoupDefense(Jeu j) throws InterruptedException;
}
