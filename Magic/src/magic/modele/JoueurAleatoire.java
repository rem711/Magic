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
public abstract class  JoueurAleatoire extends Joueur {

    public JoueurAleatoire(int id, String nom) {
        super(id, nom);
    }
    
       @Override
    
    public boolean getJouer()
    {
        int r=Tool.monRandom(0,5);
        //if (r==0) return false;
        return true;
    }
    
     //retourne la case selectionnée pour attaquer
    public abstract Case getCaseAttaque(Plateau p);
    
    //retourne la case selectionnée pour invoquer
    public Case getCaseInvocation(Plateau p)
    {
        Case c1= new Case(0, 0);
        return c1;
    }
    
    //retourne la case à être défendue selectionnée
    public  Case getCaseDefenseArr(Plateau p)
    {
        Case c1= new Case(0, 0);
        return c1;
    }
    
    //retourne la carte selectionnée pour défendre + regarder si elle est fatiguée
    public  Case getCaseDefenseDep(Plateau p)
    {
        Case c1= new Case(0, 0);
        return c1;
    }
    
    @Override
    public Case getCaseDefenseDep(Plateau p, int jx, int jy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Case getCaseDefenseArr(Plateau p, int jx, int jy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Case getCaseInvocation(Plateau p, int jx, int jy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Case getCaseAttaque(Plateau p, int jx, int jy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public CoupInvocation getCoupInvocation(Jeu j) throws InterruptedException
    {
        CoupInvocation c=new CoupInvocation(1);
        return c;
    }
}
