/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.modele;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rem711
 */
public class JoueurAleatoireDifficile extends JoueurAleatoire {

    public JoueurAleatoireDifficile(int id, String nom) {
        super(id, nom);
    }
    
    public CoupInvocation getCoupInvocation(Jeu j) throws InterruptedException
    {
        
        ArrayList<CoupInvocation>  tab = j.getPlateau().coupsInvocationPossibles(this);
        ArrayList<CoupInvocation>  tab2 = new ArrayList<>();
        ArrayList<Integer>  result = new ArrayList<>();
        int r;
        for (int i=0; i<tab.size(); i++)
        {
            //on joue un coup 15 fois
            for (int z=0; z<15; z++)
            {
                try {
                    Jeu j2;
                    j2 = j.clone();
                    //on applique le coup
                    for (int k=0; k<tab.get(i).getNb(); k++)
                    {
                        j2.getPlateau().appliquerCoupInvocation(tab.get(i).getCase(k),j2.getActif());
                    }
                    j2.phaseSuivante(); 
                    //on joue la partie avec
                    jouerPartieMonteCarlo(j2);
                    
                    //si le joueur Aléatoire est gagnant on ajoute le coup au tableau
                    r=-1;
                    if (j2.getGagnant()==j2.getJ2())
                    {
                        for(int l=0; l<tab2.size(); l++)
                        {
                            //si il existe déjà
                            if (tab2.get(l).compare(tab.get(i))) r=l;
                        }
                        if (r==-1)
                        {
                            tab2.add(tab.get(i));
                            result.add(1);
                        }
                        else
                        {
                            result.set(r, result.get(r)+1);
                        }
                    }
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(JoueurAleatoireDifficile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        int max=0;
        int indice=-1;
        for (int m=0; m<result.size(); m++)
        {
            if (result.get(m)>=max)
            {
                max=result.get(m);
                indice=m;
            }
        }
        if (indice!=-1) 
        {
            StringBuilder sb = new StringBuilder();
            System.out.println("Résultat");
                for(int m=0; m<tab2.get(indice).getNb(); m++)
                {
                    sb.append("Action n°").append(m+1);
                    sb.append(" y= ").append(tab2.get(indice).getCase(m).getY());
                    sb.append("\n");  
                } 
            System.out.println(sb);
            return tab2.get(indice);
        }
        else
        {
            System.out.println("-1");
            return null;
        }
        
    }
    
    public void jouerPartieMonteCarlo(Jeu j)
    {    
        //System.out.println("MonteCarlo lancé");
        int tour=j.getTour();
        int points=tour;
        int phase= j.getPhase();
        Joueur joueur = j.getActif();
        Joueur joueur2 = j.getInactif();
        while(!j.PartieTerminee() && tour<30)
        {     
           //Invocation
           if (j.getPhase()==2)
           {
               //System.out.println("Invocation\n"+j.getPlateau().toString());
               joueur.setRessources(points);
              // System.out.println("yo");
               if(j.getPlateau().coupInvocationPossible(joueur))
               {
                   //System.out.println("yo 2");
                   Case c=joueur.getCaseInvocation(j.getPlateau());
                   //System.out.println("yo2");
                   j.getPlateau().appliquerCoupInvocation(c, joueur);
               }
               //System.out.println("yo");
               j.phaseSuivante();
               joueur.setRessources(0);
               //System.out.println("Invocation\n"+j.getPlateau().toString());
           }
           
           //Attaque
           if (j.getPhase()==3)
           {
               //System.out.println("Attaque\n"+j.getPlateau().toString());
               if(j.getPlateau().coupAttaquePossible())
               {
                Case c=joueur.getCaseAttaque(j.getPlateau());
                j.getPlateau().appliquerCoupAttaque(c);
               }
               //System.out.println("Attaque\n"+j.getPlateau().toString());
               j.getPlateau().spliter(); 
            
            /*****************************--NOUVEAU TOUR--***********************************************/
                j.setActif();
                tour++;
                if (tour%2 == 0) {
                    j.setTour(j.getTour()+1);
                } 
                j.phaseSuivante();
                points = j.getTour();
                joueur = j.getActif();
                joueur2 = j.getInactif();
           }
           
           //Défense
           if (j.getPhase()==1)
           {
               //System.out.println("Defense\n"+j.getPlateau().toString());
               if(j.getPlateau().coupDefensePossible())
               {
                Case c= joueur.getCaseDefenseDep(j.getPlateau());
                Case c2= joueur.getCaseDefenseArr(j.getPlateau());
                j.getPlateau().appliquerCoupDefense(c, c2);
               }
               //System.out.println("Defense\n"+j.getPlateau().toString());
               //System.out.println("pv joueur actif == " + j.getActif().getPv());
              // System.out.println("pv joueur inactif == " + j.getInactif().getPv());
               j.getPlateau().combat(joueur);
               j.getPlateau().apresCombat(joueur);
               /*System.out.println("pv joueur actif == " + j.getActif().getPv());
               System.out.println("pv joueur inactif == " + j.getInactif().getPv());
               System.out.println("Defense\n"+j.getPlateau().toString());*/
               j.phaseSuivante();
           }

        }
        //System.out.println("MonteCarlo terminé");
    }
    
    
    public CoupAttaque getCoupAttaque(Jeu j) throws InterruptedException
    {
        ArrayList<CoupAttaque>  tab = j.getPlateau().coupsAttaquePossibles(this);
        ArrayList<CoupAttaque>  tab2 = new ArrayList<>();
        ArrayList<Integer>  result = new ArrayList<>();
        int r;
        for (int i=0; i<tab.size(); i++)
        {
            //on joue un coup 15 fois
            for (int z=0; z<15; z++)
            {
                try {
                    Jeu j2;
                    j2 = j.clone();
                    //on applique le coup
                    for (int k=0; k<tab.get(i).getNb(); k++)
                    {
                        j2.getPlateau().appliquerCoupAttaque(tab.get(i).getCase(k));
                    }
                    
                    j2.getPlateau().spliter(); 

                    j2.setActif();
                    j2.setTour(j2.getTour()+1);
                    j2.phaseSuivante(); 
                    //on joue la partie avec
                    jouerPartieMonteCarlo(j2);
                    
                    //si le joueur Aléatoire est gagnant on ajoute le coup au tableau
                    r=-1;
                    if (j2.getGagnant()==j2.getJ2())
                    {
                        for(int l=0; l<tab2.size(); l++)
                        {
                            //si il existe déjà
                            if (tab2.get(l).compare(tab.get(i))) r=l;
                        }
                        if (r==-1)
                        {
                            tab2.add(tab.get(i));
                            result.add(1);
                        }
                        else
                        {
                            result.set(r, result.get(r)+1);
                        }
                    }
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(JoueurAleatoireDifficile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        int max=0;
        int indice=-1;
        for (int m=0; m<result.size(); m++)
        {
            if (result.get(m)>max)
            {
                max=result.get(m);
                indice=m;
            }
        }
        if (indice!=-1) 
        {
            StringBuilder sb = new StringBuilder();
            System.out.println("Résultat");
                for(int m=0; m<tab2.get(indice).getNb(); m++)
                {
                    sb.append("Action n°").append(m+1);
                    sb.append(" y= ").append(tab2.get(indice).getCase(m).getY());
                    sb.append("\n");  
                } 
            System.out.println(sb);
            return tab2.get(indice);
        }
        else
        {
            System.out.println("-1");
            return null;
        }
    }
    
    public CoupDefense getCoupDefense(Jeu j) throws InterruptedException
    {
         
        ArrayList<CoupDefense>  tab = j.getPlateau().coupsDefensePossibles(this);
        ArrayList<CoupDefense>  tab2 = new ArrayList<>();
        ArrayList<Integer>  result = new ArrayList<>();
        int r;
        for (int i=0; i<tab.size(); i++)
        {
            //on joue un coup 15 fois
            for (int z=0; z<15; z++)
            {
                try {
                    Jeu j2;
                    j2 = j.clone();
                    //on applique le coup
                    for (int k=0; k<tab.get(i).getNb(); k++)
                    {
                        j2.getPlateau().appliquerCoupDefense(tab.get(i));
                    }
                    j2.getPlateau().combat(j2.getActif());
                    j2.phaseSuivante(); 
                    //on joue la partie avec
                    jouerPartieMonteCarlo(j2);
                    
                    //si le joueur Aléatoire est gagnant on ajoute le coup au tableau
                    r=-1;
                    if (j2.getGagnant()==j2.getJ2())
                    {
                        for(int l=0; l<tab2.size(); l++)
                        {
                            //si il existe déjà
                            if (tab2.get(l).compare(tab.get(i))) r=l;
                        }
                        if (r==-1)
                        {
                            tab2.add(tab.get(i));
                            result.add(1);
                        }
                        else
                        {
                            result.set(r, result.get(r)+1);
                        }
                    }
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(JoueurAleatoireDifficile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        int max=0;
        int indice=-1;
        for (int m=0; m<result.size(); m++)
        {
            if (result.get(m)>max)
            {
                max=result.get(m);
                indice=m;
            }
        }
        if (indice!=-1) 
        {
            StringBuilder sb = new StringBuilder();
            System.out.println("Résultat");
                for(int m=0; m<tab2.get(indice).getNb(); m++)
                {
                    sb.append("Action n°").append(m+1);
                    sb.append(" y= ").append(tab2.get(indice).getCaseDep(m).getY());
                    sb.append("\n");  
                } 
            System.out.println(sb);
            return tab2.get(indice);
        }
        else
        {
            System.out.println("-1");
            return null;
        }
    }
    
    
     //retourne la case selectionnée pour attaquer
    public Case getCaseAttaque(Plateau p)
    {
        Case c= new Case(0,0);
        System.out.println("NOOOOON");
        return c;

    }
}
