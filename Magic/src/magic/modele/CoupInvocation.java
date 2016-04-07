/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.modele;

/**
 *
 * @author Audrey
 */
public class CoupInvocation {
    private int nb;
    private int taille;
    int cout;
    private Case tab[];
    
    public CoupInvocation(int n)
    {
        this.nb=n;
        this.taille=0;
        this.cout=0;
        this.tab=new Case[nb];
    }
   
    //retourne si le coup a pu être ajouté ou pas
    public boolean addCoup(Case c)
    {
        if(taille<nb)
        {
            this.tab[taille]=c;
            this.cout=cout+(c.getCarte().getCoup());
            taille++;
            return true;
        }
        return false;
    }
    
    public int getCout()
    {
        return this.cout;
    }
    
    public boolean app(Case c)
    {
        int i=nb;
        for(i=0; i<nb; i++)
        {
            if (tab[i]==c) return true;
        }
        return false;
    }
    
    public int getNb()
    {
        return nb;
    }
    
    public Case getCase(int i)
    {
        return tab[i];
    }
    
    public boolean compare(CoupInvocation c)
    {
        int r1=0;
        int r2=0;
        if (taille!=c.taille) return false;
        for(int i=0; i<taille; i++)
        {
            r1=r1+tab[i].getY();
            r2=r2+c.tab[i].getY();
        }
        if (r1!=r2)return false;
        return true;
    }

    
}
