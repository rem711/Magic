/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.modele;

import java.util.ArrayList;
import magic.observeur.*;


/**
 *
 * @author rem711
 */
public class Plateau implements Observable {
    private Case tab[][];
    private int hauteur;
    private int largeur;
    
    private ArrayList<Observer> listObserver = new ArrayList<Observer>();
    private final int code = 9;
    private final String d1 = "Déplacement impossible";
    private final String d2 = "Pas de carte sur le terrain";
    private final String d3 = "Créature fatiguée";
    private final String a1 = "Créature fatiguée";
    private final String a2 = "Déplacement impossible";
    private final String a3 = "Pas de carte sur le terrain";
    private final String i1 = "Pas suffisamment de ressources";
    private final String errGenerale = "Coup impossible";
    private String s1;

    public Plateau(int h, int l) {
        this.hauteur = h;
        this.largeur = l;
        this.tab = new Case[hauteur][largeur];
        int i, j;
        for (i=0; i<6; i++)
            for (j=0; j<4; j++)
                this.tab[i][j]= new Case(i, j);
        s1 = "";
    }
    
    public int getHauteur() {
        return this.hauteur;
    }
    
    public int getLargeur() {
        return this.largeur;
    }
    
    public Case getCase(int x, int y)
    {
        return tab[x][y];
    }
    
    public void init() {
        int i;
        for (i=0; i<4; i++)
        {
            tab[0][i].ajouterCarte(monRandomCarte());
            tab[5][i].ajouterCarte(monRandomCarte());
        }
    }
    
    public void spliter() {
        Plateau p2= new Plateau(6,4);
        
        int i, j;
        for(i=0; i<4; i++)
            for(j=0; j<6; j++)
            {
                p2.tab[j][i]=tab[5-j][i];
                p2.tab[j][i].setX(j);
                
                
            }
        this.tab=p2.tab;
       // System.out.println(this.toString());
    }
    

    public Case appliquerCoupAttaque(Case dep) {
        int x=dep.getX();
        int y=dep.getY();
        
        if(tab[x][y].contientCarte() && x==4) {
            if(!tab[x][y].getCarte().getFat()) {
                int i=0;
                while(this.tab[3][i].contientCarte()==true) i++;
                tab[3][i].ajouterCarte(tab[x][y].getCarte());
                tab[x][y].retirerCarte();
                return new Case(3, i);
            }
            else {
                notification(code, a1);
            }
        }
        else {
            notification(code,a3);
        }
        return null;
    }
    
    public void appliquerCoupAttaque(CoupAttaque c)
    {
        Case c2;
       //j.setRessources(j.getRessources()-c.getCout());
       for (int i=0; i<c.getNb(); i++)
       {
           c2=appliquerCoupAttaque(c.getCase(i));
           if (c2!=null) 
           {
               notifyObserver(6, null, null, null, null, c.getCase(i).getX(), c.getCase(i).getY(), c2.getX(), c2.getY());
           }
       }
    }
    
     //coup de défense, true si le coup est possible, false sinon
    public boolean appliquerCoupDefense(Case dep, Case arr) { 
        int x=arr.getX();
        int y=arr.getY();
        int x1=dep.getX();
        int y1=dep.getY();
        //si il y a une carte sur la case de départ, pas sur celle d'arrivée, et une carte en face de la case d'arrivée
        
        if(tab[x1][y1].contientCarte()) {
            if(!tab[x][y].contientCarte() && this.tab[x-1][y].contientCarte() && x1==4 && x==3) {
                if(!tab[x1][y1].getCarte().getFat()) {
                    notification(code, d3);
                }
                else {
                    
                    tab[x][y].ajouterCarte(tab[x1][y1].getCarte());
                    tab[x1][y1].retirerCarte();
                    return true;
                }
            }
            else {
                notification(code, d1);
            }
        }
        else {
            notification(code, d2);
        }
        // il n'a pas pu jouer, mais maintenant la créature n'est plus fatiguée
        tab[x1][y1].getCarte().setFat(false);
        return false;
    }
    
    public void appliquerCoupDefense(CoupDefense c)
    {
       for (int i=0; i<c.getNb(); i++)
       {
           appliquerCoupDefense(c.getCaseDep(i), c.getCaseArr(i));
       }
    }
        
    public Case appliquerCoupInvocation(Case dep, Joueur j) {
        int x= dep.getX();
        int y= dep.getY();
        int r=j.getRessources();
        if (tab[x][y].contientCarte() && x==5)//il faut que la carte soit dans la main
        {
            Carte c=tab[x][y].getCarte();
            if (r>= c.getCoup()) //si la carte ne coûte pas trop cher
            {
                for(int i = 0; i < 4; i++) {
                    if(tab[4][i].contientCarte()) {
                        if(tab[4][i].getCarte().getNom().equals("Ame soeur")) {                         
                            int att = c.getAtt() + 1;
                            int def = c.getDef() + 1;
                            c.setAtt(att);
                            c.setDef(def);
                        }
                    }
                }
                if(c.getNom().equals("Totem")) {
                    j.setPv(j.getPv()+2);
                }
                if((c.getAtt() == 0) && c.getDef() == 0) {
                    int i=0;
                    while(this.tab[4][i].contientCarte()==false) {
                        i++;
                        if(i == 4) {
                            notification(code, errGenerale);
                            return null;
                        }
                    }
                    Carte c2 = tab[4][i].getCarte();
                    
                    if(c.getNom().equals("Enchantement Attaque")) {
                        c2 = new EnchantementAtt(c2);
                    }
                    if(c.getNom().equals("Enchantement Défense")) {
                        c2 = new EnchantementDef(c2);
                    }
                    if(c.getNom().equals("Enchantement Vol")) {
                        c2 = new Vol(c2);
                    }
                    if(c.getNom().equals("Super Enchantement")) {
                        c2 = new SuperEnchantement(c2);
                    }
                    
                    this.tab[4][i].ajouterCarte(c2);
                    this.tab[x][y].retirerCarte();
                    j.setRessources(r-c.getCoup());
                    return new Case(4, i);
                }
                
                int i=0;
                while(this.tab[4][i].contientCarte()==true) i++;
                if(!c.estRapide()) {
                    c.setFat(true);
                }
                else {
                    c.setFat(false);
                }
                this.tab[4][i].ajouterCarte(c);
                this.tab[x][y].retirerCarte();
                j.setRessources(r-c.getCoup());
                return new Case(4, i);
            }
            else {
                notification(code, i1);
            }
        }
        else {
            notification(code, errGenerale);
        }
        //System.out.println("Coup impossible");
        return null; 
    }
    
    public void appliquerCoupInvocation(CoupInvocation c, Joueur j)
    {
        Case c2;
       //j.setRessources(j.getRessources()-c.getCout());
       for (int i=0; i<c.getNb(); i++)
       {
           c2=appliquerCoupInvocation(c.getCase(i),j);
           if (c2!=null) 
           {
               notifyObserver(6, null, null, null, null, c.getCase(i).getX(), c.getCase(i).getY(), c2.getX(), c2.getY());
           }
       }
    }
    
    //Case a= case attaquante, Case b= Case de défense (joueur actif), renvoie le nombre de points de vie perdu par la défense
    public int combatBis(Case a, Case b)
    {
        int xa= a.getX();
        int ya= a.getY();
        int xb= b.getX();
        int yb= b.getY();
        int i=0;
        if (tab[xb][yb].contientCarte()==false)// s'il n'y a pas eu de défense 
        {
            while (tab[1][i].contientCarte() && i<3) i++;
            tab[1][i].ajouterCarte(tab[xa][ya].getCarte());//on pose la carte sur la première case de terrain vide
            tab[xa][ya].retirerCarte();//on retire la carte sur la case d'attaque
            tab[1][i].getCarte().setFat(true);//la carte attaquante devient fatiguée
            return tab[1][i].getCarte().getAtt(); // retourne son attaque
        }
        if (tab[xa][ya].contientCarte() && tab[xb][yb].contientCarte())//si combat
        {
            if(tab[xa][ya].getCarte().estVolant()) {
                if(tab[xb][yb].getCarte().estVolant()) {
                    int aAtt=a.getCarte().getAtt();
                    int bAtt=b.getCarte().getAtt();
                    int aDef= a.getCarte().getDef();
                    int bDef= a.getCarte().getDef();

                    if (aAtt >= bDef && bAtt >= aDef ) //les 2 meurent
                    {
                        tab[xb][yb].retirerCarte();
                        tab[xa][ya].retirerCarte();
                    }
                    else if (aAtt >= bDef)// si a gagne et b perd
                    {
                        tab[xb][yb].retirerCarte();
                        while (tab[1][i].contientCarte()&& i<3) i++;
                        tab[1][i].ajouterCarte(tab[xa][ya].getCarte());//on remet la carte sur le terrain
                        tab[1][i].getCarte().setFat(true);//la carte attaquante devient fatiguée
                        tab[xa][ya].retirerCarte();//on retire la carte sur la case d'attaque

                    }
                    else if (bAtt >= aDef) //si b gagne et a perd
                    {
                        tab[xa][ya].retirerCarte();
                        while (tab[4][i].contientCarte()&& i<3) i++;
                        tab[4][i].ajouterCarte(tab[xb][yb].getCarte());//on remet la carte sur le terrain
                        tab[xb][yb].retirerCarte();//on retire la carte sur la case de défense
                    }
                    else
                    {
                        //on s'occupe de la carte attaquante
                        while (tab[1][i].contientCarte()&& i<3) i++;
                        tab[1][i].ajouterCarte(tab[xa][ya].getCarte());//on remet la carte sur le terrain
                        tab[1][i].getCarte().setFat(true);//la carte attaquante devient fatiguée
                        tab[xa][ya].retirerCarte();//on retire la carte sur la case d'attaque

                        //on s'occupe de la carte de défense
                        while (tab[4][i].contientCarte()&& i<3) i++;
                        tab[4][i].ajouterCarte(tab[xb][yb].getCarte());//on remet la carte sur le terrain
                        tab[xb][yb].retirerCarte();//on retire la carte sur la case de défense
                    }
                }
                else {
                    i = 0;
                    //on s'occupe de la carte de défense
                    while (tab[4][i].contientCarte() && i<3) i++;
                    tab[4][i].ajouterCarte(tab[xb][yb].getCarte());//on remet la carte sur le terrain
                    tab[xb][yb].retirerCarte();//on retire la carte sur la case de défense

                    i = 0;
                    //on s'occupe de la carte attaquante
                    while (tab[1][i].contientCarte() && i<3) i++;
                    tab[1][i].ajouterCarte(tab[xa][ya].getCarte());//on remet la carte sur le terrain
                    tab[1][i].getCarte().setFat(true);//la carte attaquante devient fatiguée
                    tab[xa][ya].retirerCarte();//on retire la carte sur la case d'attaque

                    return tab[1][i].getCarte().getAtt();
                }
            }
            else {
                int aAtt=a.getCarte().getAtt();
                int bAtt=b.getCarte().getAtt();
                int aDef= a.getCarte().getDef();
                int bDef= a.getCarte().getDef();

                if (aAtt >= bDef && bAtt >= aDef ) //les 2 meurent
                {
                    tab[xb][yb].retirerCarte();
                    tab[xa][ya].retirerCarte();
                }
                else if (aAtt >= bDef)// si a gagne et b perd
                {
                    tab[xb][yb].retirerCarte();
                    while (tab[1][i].contientCarte()&& i<3) i++;
                    tab[1][i].ajouterCarte(tab[xa][ya].getCarte());//on remet la carte sur le terrain
                    tab[1][i].getCarte().setFat(true);//la carte attaquante devient fatiguée
                    tab[xa][ya].retirerCarte();//on retire la carte sur la case d'attaque

                }
                else if (bAtt >= aDef) //si b gagne et a perd
                {
                    tab[xa][ya].retirerCarte();
                    while (tab[4][i].contientCarte() && i<3) i++;
                    tab[4][i].ajouterCarte(tab[xb][yb].getCarte());//on remet la carte sur le terrain
                    tab[xb][yb].retirerCarte();//on retire la carte sur la case de défense
                }
                else
                {
                    //on s'occupe de la carte attaquante
                    while (tab[1][i].contientCarte() && i<3) i++;
                    tab[1][i].ajouterCarte(tab[xa][ya].getCarte());//on remet la carte sur le terrain
                    tab[1][i].getCarte().setFat(true);//la carte attaquante devient fatiguée
                    tab[xa][ya].retirerCarte();//on retire la carte sur la case d'attaque

                    //on s'occupe de la carte de défense
                    while (tab[4][i].contientCarte() && i<3) i++;
                    tab[4][i].ajouterCarte(tab[xb][yb].getCarte());//on remet la carte sur le terrain
                    tab[xb][yb].retirerCarte();//on retire la carte sur la case de défense
                }
            }
        }
         return 0;
    }
    
    //fait tous les combats et enlève les points de vie à j (joueur actif)
    public void combat(Joueur j)
    {
        int i,s;
        for (i=0; i<4; i++)
        {
            if (tab[2][i].contientCarte()) 
            {
                s=j.getPv()-combatBis(tab[2][i], tab[3][i]);
                j.setPv(s);    
            }
        }
    }
    //Les créatures de j1 (actif) deviennent reposées
    public void apresCombat(Joueur j1)
    {
        int i;
        for(i=0; i<4; i++)
        {
            if (tab[4][i].contientCarte())
            {
                tab[4][i].getCarte().setFat(false);
            }
        }
    }
    
    @Override
    public String toString()
    {
        //return getClass().getName() + "@" + Integer.toHexString(hashCode());
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<6; i++)
        {
            for (int j=0; j<4; j++)
            {
                 if (!this.tab[i][j].contientCarte()) 
                     sb.append("-  - - - | ");
                 else sb.append(this.tab[i][j].getCarte().getNom()).append(this.tab[i][j].getCarte().getCoup()).append(" ").append(this.tab[i][j].getCarte().getAtt()).append(" ").append(this.tab[i][j].getCarte().getDef()).append(" | ");
                
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public Carte monRandomCarte()
    {
        int a = Tool.monRandom(1,41);
        Carte c1= new Creature("AL ", 1, 2, 1, 0);
        Carte c2= new Creature("Ame soeur", 2, 3, 1, 0);
        Carte c3= new Creature("CL ", 3, 4, 2, 0);
        Carte c4= new Creature("RC ", 4, 4, 3, 0);
        Carte c5= new Creature("BG ", 1, 1, 2, 0);
        Carte c6= new Creature("SA ", 2, 1, 3, 0);
        Carte c7= new Creature("CG ", 3, 2, 4, 0);
        Carte c8= new Creature("Totem", 4, 3, 4, 0);
        Carte c9 = new Creature("Enchantement Attaque", 2, 0, 0, 0);
        Carte c10 = new Creature("Enchantement Défense", 2, 0, 0, 0);
        Carte c11 = new Creature("Enchantement Vol", 3, 0, 0, 0);
        Carte c12 = new Creature("Super Enchantement", 4, 0, 0, 0);
        
        switch(a)
        {
            case 1:
                return c1;
            case 2:
                c1 = new Rapide(c1);
                return c1;
            case 3:
                c5 = new Rapide(c5);
                return c5;
            case 4:
                return c6;
            case 5:
                c6 = new Rapide(c6);
                return c6;
            case 6:
            case 7:
                return c9;
            case 8:
            case 9:
                return c10;
            case 10:
            case 11:
                return c11;
            case 12:
            case 13:
                return c12;
            case 14:
                return c2;
            case 15:
                c2 = new EnchantementDef(c2);
                return c2;
            case 16:
                return c7;
            case 17:
                c7 = new EnchantementAtt(c7);
                return c7;
            case 18:
                return c8;
            case 19:
                return c3;
            case 20:
                return c4;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                c1 = new Vol(c1);
                return c1;
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                return c2;
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                return c3;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
                return c4;
            default :
                c1 = new Vol(c1);
                return c1;
        }
    }
    
    
    //renvoie true si il peut avoir un coup de défense, false sinon
    public boolean coupDefensePossible() {
        int i;
        int j=0;
        int k=0;
        for (i=0; i<4; i++)
        {
            if (this.tab[2][i].contientCarte()) j++;
        }
        for (i=0; i<4; i++)
        {
            if (this.tab[4][i].contientCarte()) k++;
            if (this.tab[3][i].contientCarte()) j--;//si une attaque est déjà défendue on ne doit pas la prendre en compte
        }
        if (k!=0 && j!=0) return true;// il faut qu'il y ait des cartes en attaques et des cartes pour défendre sur le terrain
        return false;
    }
    
    //renvoie true si il peut avoir un coup d'invocation, false sinon
    public boolean coupInvocationPossible(Joueur j) {
        int k= j.getRessources();
        int min=-1;
        for(int i=0; i<4; i++)
        {
            if (this.tab[5][i].contientCarte())
            {
                if (min==-1)min = this.tab[5][i].getCarte().getCoup();
                else if ((this.tab[5][i].getCarte().getCoup()<min)) min = this.tab[5][i].getCarte().getCoup();
            }
        }
        if ((min <= k) && (min!=-1)) return true; //si une carte peut être achetée alors un coup est possible
        return false;
    }
    
    //renvoie true si il peut avoir un coup d'attaque, false sinon
    public boolean coupAttaquePossible()
    {   
        boolean r=false;
        for (int i=0; i<4; i++)
        {
            //si il y a au moins une carte sur le terrain qui n'est pas fatiguée
            if (this.tab[4][i].contientCarte() && !this.tab[4][i].getCarte().getFat()) r=true;
        }
        return r;
    }
    
    private void notification(int code, String s1) {
        notifyObserver(code, s1, null, null, null, 0, 0, 0, 0);
    }

    @Override
    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
    }

    @Override
    public void notifyObserver(int code, String s1, String s2, String s3, String s4, int x1, int y1, int x2, int y2) {
        for(Observer obs : listObserver) {
            obs.update(code, s1, s2, s3, s4, x1, y1, x2, y2);
        }
    }

    @Override
    public void removeObserver() {
        this.listObserver = new ArrayList<Observer>();
    }

    //retourne l'ensemble des coups d'invocations possibles
    ArrayList<CoupInvocation>  coupsInvocationPossibles(Joueur j)
    {
        int i;
        int lp;
        ArrayList<CoupInvocation> c = new ArrayList<>();
        ArrayList<CoupInvocation> c2 = new ArrayList<>();
        ArrayList<CoupInvocation> c3 = new ArrayList<>();
        ArrayList<CoupInvocation> c4 = new ArrayList<>();
        int r=j.getRessources();
        
        //ajoute toutes les coups de nombre d'actions=1
        for(i=0; i<4; i++)
        {
            //si il y a une carte qui peut être achetée dans la main
            if(this.tab[5][i].contientCarte() && this.tab[5][i].getCarte().getCoup()<=r)
            {
                CoupInvocation ci= new CoupInvocation(1);
                ci.addCoup(tab[5][i]);
                c.add(ci);
            }
        }
        
        int taille=c.size();
        //ajoute tous les coups de nombre d'actions =2
        for (i=0; i<taille-1; i++)
        {
            for(int k=i+1; k<taille; k++)
            if ((c.get(i).getCout()+ c.get(k).getCout())<=r)
            {
                CoupInvocation ci= new CoupInvocation(2);
                ci.addCoup(c.get(i).getCase(0));
                ci.addCoup(c.get(k).getCase(0));
                c2.add(ci);
            }    
        }
        
        //ajoute tous les coups de nombre d'actions =3
        for(i=0; i<c.size(); i++)
        {
            for(int k=0; k<c2.size(); k++)
            {
                if (((c.get(i).getCout()+ c2.get(k).getCout())<=r) && !c2.get(k).app(c.get(i).getCase(0)))
                {
                    CoupInvocation ci= new CoupInvocation(3);
                    ci.addCoup(c.get(i).getCase(0));
                    ci.addCoup(c2.get(k).getCase(0));
                    ci.addCoup(c2.get(k).getCase(1));
                    lp=0;
                    for (int y=0; y<c3.size(); y++)
                    {
                        if (c3.get(y).compare(ci)) lp=1;
                    }
                    if (lp==0) c3.add(ci);
                }
            }
        }
        for(i=0; i<c2.size(); i++)
        {
            c.add(c2.get(i));
        }
        for(i=0; i<c3.size(); i++)
        {
            c.add(c3.get(i));
        }
        
        //ajoute le coup de nombre d'actions 4 si c'est possible
        if (tab[5][0].contientCarte() && tab[5][1].contientCarte() && tab[5][2].contientCarte() && tab[5][3].contientCarte()
             && (tab[5][0].getCarte().getCoup()+tab[5][1].getCarte().getCoup()+tab[5][2].getCarte().getCoup()+tab[5][3].getCarte().getCoup())<=r )
        {
            CoupInvocation ci= new CoupInvocation(4);
            ci.addCoup(tab[5][0]);
            ci.addCoup(tab[5][1]);
            ci.addCoup(tab[5][2]);
            ci.addCoup(tab[5][3]);
            c.add(ci);
        }
        StringBuilder sb = new StringBuilder();
        for(int l=0; l<c.size(); l++)
        {
            sb.append("Coup n° ").append(l); 
            for(int m=0; m<c.get(l).getNb(); m++)
            sb.append(" y= ").append(c.get(l).getCase(m).getY());
            sb.append("\n");       
        }
        
        System.out.println(sb);
        return c;
    }
    
     //retourne l'ensemble des coups d'attaque possibles
   ArrayList<CoupAttaque>  coupsAttaquePossibles(Joueur j)
    {
        ArrayList<CoupAttaque> c = new ArrayList<>();
        ArrayList<CoupAttaque> c2 = new ArrayList<>();
        ArrayList<CoupAttaque> c3 = new ArrayList<>();
        
        //ajoute tous les coups de nombre d'actions=1
        for(int i=0; i<4; i++)
        {
            //si il y a une carte qui est sur le terrain et n'est pas fatiguée
            if(this.tab[4][i].contientCarte() && !this.tab[4][i].getCarte().getFat())
            {
                CoupAttaque ci= new CoupAttaque(1);
                ci.addCoup(tab[4][i]);
                c.add(ci);
            }
        }
        
        //ajoute tous les coups de nombre d'actions=2
        int taille=c.size();
        int lp;
        for (int i=0; i<taille-1; i++)
        {
            for(int k=i+1; k<taille; k++)
            {
                CoupAttaque ci= new CoupAttaque(2);
                ci.addCoup(c.get(i).getCase(0));
                ci.addCoup(c.get(k).getCase(0));
                c2.add(ci);
            }    
        }
        
         //ajoute tous les coups de nombre d'actions =3
        for(int i=0; i<c.size(); i++)
        {
            for(int k=0; k<c2.size(); k++)
            {
                //si le coup de c1 n'existe pas déjà dans c2
                if (!c2.get(k).app(c.get(i).getCase(0)))
                {
                    CoupAttaque ci= new CoupAttaque(3);
                    ci.addCoup(c.get(i).getCase(0));
                    ci.addCoup(c2.get(k).getCase(0));
                    ci.addCoup(c2.get(k).getCase(1));
                    lp=0;
                    for (int y=0; y<c3.size(); y++)
                    {
                        if (c3.get(y).compare(ci)) lp=1;
                    }
                    if (lp==0) c3.add(ci);
                }
            }
        }
        
        
        for(int i=0; i<c2.size(); i++)
        {
            c.add(c2.get(i));
        }
        
         for(int i=0; i<c3.size(); i++)
        {
            c.add(c3.get(i));
        }
        
        if (tab[4][0].contientCarte() && tab[4][1].contientCarte() && tab[4][2].contientCarte() && tab[4][3].contientCarte() 
                && !this.tab[4][0].getCarte().getFat() && !this.tab[4][1].getCarte().getFat() && !this.tab[4][2].getCarte().getFat() && !this.tab[4][3].getCarte().getFat())
        {
            CoupAttaque ci= new CoupAttaque(4);
            ci.addCoup(tab[4][0]);
            ci.addCoup(tab[4][1]);
            ci.addCoup(tab[4][2]);
            ci.addCoup(tab[4][3]);
            c.add(ci);
        }
        
        StringBuilder sb = new StringBuilder();
        for(int l=0; l<c.size(); l++)
        {
            sb.append("Coup n° ").append(l); 
            for(int m=0; m<c.get(l).getNb(); m++)
            sb.append(" y= ").append(c.get(l).getCase(m).getY());
            sb.append("\n");       
        }
        
        System.out.println(sb);
        return c;
    }
  
    //retourne l'ensemble des coups de defense possibles
    ArrayList<CoupDefense>  coupsDefensePossibles(Joueur j)
    {
        ArrayList<CoupDefense> c = new ArrayList<>();
        ArrayList<CoupDefense> c2 = new ArrayList<>();
        ArrayList<CoupDefense> c3 = new ArrayList<>();
        ArrayList<CoupDefense> c4 = new ArrayList<>();
        
        //ajoute tous les coups de nombre d'actions=1
        for(int i=0; i<4; i++)
        {
            if(tab[2][i].contientCarte())
            {
                for(int k=0; k<4; k++)
                {
                    //si il y a une carte sur le terrain qui n'est pas fatiguée on ajoute le coup
                    if (tab[4][k].contientCarte() && !tab[4][k].getCarte().getFat())
                    {
                        CoupDefense ci= new CoupDefense(1);
                        ci.addCoup(tab[4][k], tab[3][i]);
                        c.add(ci);
                    }
                }
            }
        }
        
         //ajoute tous les coups de nombre d'actions=2
        int taille=c.size();
        int lp;
        for (int i=0; i<taille-1; i++)
        {
            
            for(int k=i+1; k<taille; k++)
            {
                if ( (c.get(i).getCaseDep(0) != c.get(k).getCaseDep(0)) &&  (c.get(i).getCaseArr(0) != c.get(k).getCaseArr(0)))
                {
                    CoupDefense ci= new CoupDefense(2);
                    ci.addCoup(c.get(i).getCaseDep(0), c.get(i).getCaseArr(0));
                    ci.addCoup(c.get(k).getCaseDep(0), c.get(k).getCaseArr(0));
                    c2.add(ci);
                }
               
            }    
        }
        
        boolean b1,b2;
        //ajoute tous les coups de nombre d'actions =3
        for(int i=0; i<c.size(); i++)
        {
            for(int k=0; k<c2.size(); k++)
            {
                if ((!c2.get(k).app(c.get(i).getCaseDep(0), c.get(i).getCaseArr(0))))
                {
                    CoupDefense ci= new CoupDefense(3);
                    ci.addCoup(c.get(i).getCaseDep(0), c.get(i).getCaseArr(0));
                    b1=ci.addCoup(c2.get(k).getCaseDep(0), c2.get(k).getCaseArr(0));
                    b2=ci.addCoup(c2.get(k).getCaseDep(1), c2.get(k).getCaseArr(1));
                    lp=0;
                    for (int y=0; y<c3.size(); y++)
                    {
                        if (c3.get(y).compare(ci)) lp=1;
                    }
                    if (lp==0 && b1 && b2) c3.add(ci);
                }
            }
        }
        
        boolean b3;
         //ajoute tous les coups de nombre d'actions =4
        for(int i=0; i<c.size(); i++)
        {
            for(int k=0; k<c3.size(); k++)
            {
                if ((!c3.get(k).app(c.get(i).getCaseDep(0), c.get(i).getCaseArr(0))))
                {
                    CoupDefense ci= new CoupDefense(4);
                    ci.addCoup(c.get(i).getCaseDep(0), c.get(i).getCaseArr(0));
                    b1=ci.addCoup(c3.get(k).getCaseDep(0), c3.get(k).getCaseArr(0));
                    b2=ci.addCoup(c3.get(k).getCaseDep(1), c3.get(k).getCaseArr(1));
                    b3=ci.addCoup(c3.get(k).getCaseDep(2), c3.get(2).getCaseArr(1));
                    lp=0;
                    for (int y=0; y<c4.size(); y++)
                    {
                        if (c4.get(y).compare(ci)) lp=1;
                    }
                    if (lp==0 && b1 && b2 && b3) c4.add(ci);
                }
            }
        }
        
        
        
        
        
        for(int i=0; i<c2.size(); i++)
        {
            c.add(c2.get(i));
        }
        
        for(int i=0; i<c3.size(); i++)
        {
            c.add(c3.get(i));
        }
        
        for(int i=0; i<c4.size(); i++)
        {
            c.add(c4.get(i));
        }
        
        StringBuilder sb = new StringBuilder();
        for(int l=0; l<c.size(); l++)
        {
            sb.append("Coup n° ").append(l); 
            for(int m=0; m<c.get(l).getNb(); m++)
            sb.append(" {").append(c.get(l).getCaseDep(m).getY()).append(",").append(c.get(l).getCaseArr(m).getY()).append("} ");
            sb.append("\n");       
        }
        System.out.println(sb);
        return c;
    }
    
    @Override
     public Plateau clone() {
         Plateau o= new Plateau (6,4);
         for (int i=0; i<6; i++)
            for (int j=0; j<4; j++)
            {
                o.tab[i][j] = tab[i][j].clone();
            }
         return o;
    }
}
