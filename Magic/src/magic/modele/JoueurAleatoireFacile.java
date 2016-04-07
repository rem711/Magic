/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.modele;

import java.util.Scanner;

/**
 *
 * @author rem711
 */
public class JoueurAleatoireFacile extends JoueurAleatoire {

    public JoueurAleatoireFacile(int id, String nom) {
        super(id, nom);
    }
    
    @Override
    //retourne la carte selectionnée pour défendre + regarder si elle est fatiguée
    public Case getCaseDefenseDep(Plateau p)
    {
        int jx= 4; //la carte est sur le terrain
        int jy= Tool.monRandom(0,3);
        //La carte doit être sur le terrain
        while (!p.getCase(jx, jy).contientCarte())
        {
            jy= Tool.monRandom(0,3);
        }
        Case c1= new Case(jx, jy);
        c1.ajouterCarte(p.getCase(jx, jy).getCarte());
        return c1;
    }
    
    //retourne la case à être défendue selectionnée
    public Case getCaseDefenseArr(Plateau p)
    {
        int jx= 3; //la case est sur la ligne de combat
        int jy= Tool.monRandom(0,3);
        //La case doit être sur la ligne de combat, ne pas contenir de carte, et être en face d'une carte adverse
        while (!p.getCase(jx-1, jy).contientCarte() || p.getCase(jx, jy).contientCarte())
        {
            
            jy= Tool.monRandom(0,3);
        }
        Case c1= new Case(jx, jy);
        return c1;
    }
    
    //retourne la case selectionnée pour invoquer
    public Case getCaseInvocation(Plateau p)
    {
        int jx= 5; //la carte doit être dans la main
        int jy= Tool.monRandom(0,3);
        //La case doit contenir une carte et le joueur doit avoir assez de ressources
        while (!p.getCase(jx, jy).contientCarte() || (p.getCase(jx, jy).contientCarte() && p.getCase(jx, jy).getCarte().getCoup()>this.getRessources()))
        {
            jy= Tool.monRandom(0,3);
        }
        Case c1= new Case(jx, jy);
        c1.ajouterCarte(p.getCase(jx, jy).getCarte());
        return c1;
    }
    
    //retourne la case selectionnée pour attaquer
    public Case getCaseAttaque(Plateau p)
    {
        
        int jx= 4; //la carte doit être sur le terrain
        int jy= Tool.monRandom(0,3);
        //La case doit contenir une carte
        while (!p.getCase(jx, jy).contientCarte())
        {
            jy= Tool.monRandom(0,3);
        }
        Case c1= new Case(jx, jy);
        c1.ajouterCarte(p.getCase(jx, jy).getCarte());
        return c1;    
    }
    
      @Override
    public CoupAttaque getCoupAttaque(Jeu j) throws InterruptedException
    {
        if(j.getPlateau().coupAttaquePossible())
        {
            Case c=getCaseAttaque(j.getPlateau());
            CoupAttaque cp=new CoupAttaque(1);
            cp.addCoup(c);
            return cp;
        }
        return null;
    }
    
     @Override
    public CoupDefense getCoupDefense(Jeu j) throws InterruptedException
    {
        if(j.getPlateau().coupDefensePossible())
        {
            Case c= getCaseDefenseDep(j.getPlateau());
            Case c2= getCaseDefenseArr(j.getPlateau());
            CoupDefense cp=new CoupDefense(1);
            cp.addCoup(c, c2);
            return cp;
        }

        return null;
    }
    
     @Override
    public CoupInvocation getCoupInvocation(Jeu j) throws InterruptedException
    {
         if(j.getPlateau().coupInvocationPossible(this))
            {
                Case c=getCaseInvocation(j.getPlateau());
                System.out.println(c);
                CoupInvocation cp=new CoupInvocation(1);
                cp.addCoup(c);
                return cp;
            }
         
         return null;
        
    }
    
}
