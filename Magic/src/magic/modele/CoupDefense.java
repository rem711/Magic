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
public class CoupDefense {
    private int nb;
    private int taille;
    private Case tab[][];
    
    public CoupDefense(int n)
    {
        this.nb=n;
        this.taille=0;
        this.tab=new Case[nb][2];
    }
    
      //retourne si le coup a pu être ajouté ou pas
    public boolean addCoup(Case dep, Case arr)
    {
        if(taille<nb)
        {
            for(int i=0; i<taille; i++)
            {
                if (this.tab[i][1].getY()==arr.getY() || this.tab[i][0].getY()== dep.getY()) return false;

            }
            this.tab[taille][0]=dep;
            this.tab[taille][1]=arr;
            taille++;
            return true;
        }
        
        
        return false;
    }
    
     public int getNb()
    {
        return nb;
    }
     
    public Case getCaseDep(int i)
    {
        return tab[i][0];
    }
    
     public Case getCaseArr(int i)
    {
        return tab[i][1];
    }
     
   public Case[] getCoup(int i)
   {
       return tab[i];
   }
   
   public boolean app(Case dep, Case arr)
   {
      for(int i=0; i<taille; i++)
      {
          if (dep==tab[i][0] && arr==tab[i][1]) return true;
              
      }
      return false;
   }

   public boolean compare(CoupDefense c)
   {
        if (taille!=c.taille) return false;
        for(int i=0; i<taille; i++)
        {
            if (!app(c.getCaseDep(i), c.getCaseArr(i))) return false;
        }
        return true;
   }
}
