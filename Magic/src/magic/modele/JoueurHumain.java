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
public class JoueurHumain extends Joueur {

    public JoueurHumain(int id, String nom) {
        super(id, nom);
    }
    
    @Override 
    public boolean getJouer()
    {
        System.out.println("Voulez vous jouer? 1:oui");
        Scanner scf = new Scanner(System.in);
        int f = scf.nextInt();
        if (f==1) return true;
        else return false;
    }
    
    //retourne la carte selectionnée pour défendre
    public Case getCaseDefenseDep(Plateau p)
    {
        System.out.println("Entrez x: ");
        Scanner scx = new Scanner(System.in);
        int jx = scx.nextInt();

        System.out.println("Entrez y:");
        Scanner scy = new Scanner(System.in);
        int jy = scy.nextInt();
        //La carte doit être sur le terrain
        while (jy<0 || jy >3 || jx!= 4 || !p.getCase(jx, jy).contientCarte())
        {
            System.out.println("ERREUR!!! \n Entrez x: ");
            jx = scx.nextInt();

            System.out.println("Entrez y:");
            jy = scy.nextInt();
        }
        Case c1= new Case(jx, jy);
        return c1;
    }
    
    public Case getCaseDefenseDep(Plateau p, int jx, int jy) {
        if(jy<0 || jy >3 || jx!= 4 || !p.getCase(jx, jy).contientCarte()) {
            return null;
        }
        return new Case(jx, jy);
    }
    
    public Case getCaseDefenseArr(Plateau p, int jx, int jy) {
        if(jy<0 || jy >3 || jx!= 3 || !p.getCase(jx-1, jy).contientCarte()) {
            return null;
        }
        return new Case(jx, jy);
    }
    
    //retourne la case à être défendue selectionnée
    public Case getCaseDefenseArr(Plateau p)
    {
        System.out.println("Entrez x: ");
        Scanner scx = new Scanner(System.in);
        int jx = scx.nextInt();

        System.out.println("Entrez y:");
        Scanner scy = new Scanner(System.in);
        int jy = scy.nextInt();
        //La case doit être sur la ligne de combat et en face d'une carte adverse
        while (jy<0 || jy >3 || jx!= 3 || !p.getCase(jx-1, jy).contientCarte())
        {
            System.out.println("ERREUR!!! \n Entrez x: ");
            jx = scx.nextInt();

            System.out.println("Entrez y:");
            jy = scy.nextInt();
        }
        Case c1= new Case(jx, jy);
        return c1;
    }
    
    public Case getCaseInvocation(Plateau p, int jx, int jy) {
        if(jy<0 || jy >3 || jx!= 5 || !p.getCase(jx, jy).contientCarte() || (p.getCase(jx, jy).contientCarte() && p.getCase(jx, jy).getCarte().getCoup()>this.getRessources())) {
            return null;
        }
        return new Case(jx, jy);
    }
    
    //retourne la case selectionnée pour invoquer
    public Case getCaseInvocation(Plateau p)
    {
        System.out.println("Entrez x: ");
        Scanner scx = new Scanner(System.in);
        int jx = scx.nextInt();

        System.out.println("Entrez y:");
        Scanner scy = new Scanner(System.in);
        int jy = scy.nextInt();
        //la carte doit être dans la main et le joueur doit avoir assez de ressources
        while (jy<0 || jy >3 || jx!= 5 || !p.getCase(jx, jy).contientCarte() || (p.getCase(jx, jy).contientCarte() && p.getCase(jx, jy).getCarte().getCoup()>this.getRessources()))
        {
            System.out.println("ERREUR!!! \n Entrez x: ");
            jx = scx.nextInt();

            System.out.println("Entrez y:");
            jy = scy.nextInt();
        }
        Case c1= new Case(jx, jy);
        return c1;
    }
    
    //retourne la case selectionnée pour attaquer
    public Case getCaseAttaque(Plateau p)
    {
        System.out.println("Entrez x: ");
        Scanner scx = new Scanner(System.in);
        int jx = scx.nextInt();

        System.out.println("Entrez y:");
        Scanner scy = new Scanner(System.in);
        int jy = scy.nextInt();
        //la carte doit être sur le terrain
        while (jy<0 || jy >3 || jx!= 4 || !p.getCase(jx, jy).contientCarte())
        {
            System.out.println("ERREUR!!! \n Entrez x: ");
            jx = scx.nextInt();

            System.out.println("Entrez y:");
            jy = scy.nextInt();
        }
        Case c1= new Case(jx, jy);
        return c1;
        
    }
    
    public Case getCaseAttaque(Plateau p, int jx, int jy) {
        if(jy<0 || jy >3 || jx!= 4 || !p.getCase(jx, jy).contientCarte()) {
            return null;
        }
        return new Case(jx, jy);
    }
    
    @Override
    public CoupInvocation getCoupInvocation(Jeu j) throws InterruptedException
    {
        CoupInvocation c=new CoupInvocation(1);
        return c;
    }
     @Override
    public CoupAttaque getCoupAttaque(Jeu j) throws InterruptedException
    {
        CoupAttaque c=new CoupAttaque(1);
        return c;
    }
    
     @Override
    public CoupDefense getCoupDefense(Jeu j) throws InterruptedException
    {
        CoupDefense c=new CoupDefense(1);
        return c;
    }
    
}
