/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.modele;

import java.util.ArrayList;
import magic.observeur.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import magic.observeur.Observer;

/**
 *
 * @author rem711
 */
public class Jeu implements Observable, Cloneable {
    private Plateau plateau;
    private Joueur j1;
    private Joueur j2;
    private int actif;
    private int tour;
    private int phase;
    private int x1, y1, x2, y2;
    int code;
    String s1;
    String s2;
    String s3;
    String s4;
    private boolean move;
    private boolean next;
    
    private final int PVJ1 = 1;
    private final int PVJ2 = 2;
    private final int RESSJ1 = 3;
    private final int RESSJ2 = 4;
    private final int BTNNEXT = 5;
    private final int POSCARTE = 6;
    private final int TXTCARTE = 7;
    private final int TXTPHASES = 8;
    private final int ERR = 9;
    private final int FIN = 10;
    
    private ArrayList<Observer> listObserver = new ArrayList<Observer>(); 
    
    public Jeu(Joueur joueur1, Joueur joueur2) {
        this.j1 = joueur1;
        this.j2 = joueur2;
        this.actif = 1;
        this.tour = 1;
        this.phase = 2;
        this.plateau = new Plateau(6, 4);
        this.x1 = -1;
        this.x2 = -1;
        this.y1 = -1;
        this.y2 = -1;
        s1 = "";
        s2 = "";
        s3 = "";
        s4 = "";
        this.move = false;
        this.next = false;
    }
    public Jeu(Joueur joueur1, Joueur joueur2, int a, int t, int p, Plateau pl) {
        this.j1 = joueur1;
        this.j2 = joueur2;
        this.actif = a;
        this.tour = t;
        this.phase = p;
        this.plateau = pl;
    }
    
    public Joueur getJ2()
    {
        return j2;
    }
    
    public Plateau getPlateau()
    {
        return plateau;
    }
    
    public Joueur getActif()
    {
        if (this.actif ==1) return j1;
        return j2;
    }
    
    public Joueur getInactif()
    {
        if (this.actif ==1) return j2;
        return j1;
    }
    
    public void setActif()
    {
        if (this.actif ==1) this.actif=2;
        else this.actif=1;
    }
    
    public int getTour()
    {
        return this.tour;
    }
    
    public void setTour(int t)
    {
        this.tour=t;
    }
    
    //renvoie vrai si c'est le tour du joueur humain
    public boolean monTour()
    {
        if (actif==1) return true;
        return false;
    }
    
    public void setNext() {
        next = true;
    }
    
    private String getType() {
        switch(phase) {
            case 1:
                return "(défense)";
            case 2:
                return "(invocation)";
            case 3:
                return "(attaque)";
        }
        return "";
    }
    
    public int getPhase() {
        return this.phase;
    }
    
    public int phaseSuivante() {
        if((this.getPhase()+ 1) < 4) {
            this.phase++;
        }
        else {
            this.phase = 1;
        }
        return this.phase;
    }
    
    // reçoit un mouvement du controleur
    public void setMouvement(int xdep, int ydep, int xarr, int yarr) {
        this.x1 = xdep;
        this.y1 = ydep;
        this.x2 = xarr;
        this.y2 = yarr;
        this.move = true;
    }
    
    private String toStringNext() {
        if(phase == 3) {
            return "Fin de tour";
        }
        else {
            return "Phase suivante";
        }
    }    
    
    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //return true si la partie est terminée, false sinon
    public boolean PartieTerminee()
    {
        if ((j1.getPv()<=0) || (j2.getPv()<=0) || (countCartes(j1) <= 0) || (countCartes(j2) <= 0)) {
            return true;
        }
        return false; 
    }
    
    public int countCartes(Joueur joueur) {
        int compteur = 0;
        int min;
        int max;
        
        if(getActif() == joueur) {
            min = 3;
            max = plateau.getHauteur();
        }
        else {
            min = 0;
            max = (plateau.getHauteur() / 2);
        }
        
        for(int i = min; i < max; i++) {
            for(int j = 0; j < plateau.getLargeur(); j++) {
                if(plateau.getCase(i, j).contientCarte()) {
                    compteur++;
                }
            }
        }
        
        return compteur;   
    }
    
    public Joueur getGagnant() {
        if((j1.getPv() <= 0) || (countCartes(j1) <= 0)) {
            return j2;
        }
        return j1;
    }
    
    public Joueur jouerPartie2() throws InterruptedException{        
        plateau.init();
        initAffichage();
                
        int t = 0;
        int points;
        points = getTour();
        Joueur joueur = getActif();
        Joueur joueur2 = getInactif();
        
        majJ1(joueur);
        majJ2(joueur2);        
        majPhase();    
        
        while(!this.PartieTerminee())
        {      
            joueur.setRessources(points);
            
            majPlateau();
            majJ1(joueur);
            majJ2(joueur2);        
            majPhase();  
            
            /*****************************--INVOCATION--***********************************************/
            invocation(joueur);
            /*****************************--FIN INVOCATION--***********************************************/
            
            this.phaseSuivante();
            joueur.setRessources(0);
            majPlateau();
            majJ1(joueur);
            majJ2(joueur2);        
            majPhase();
            
            /*****************************--ATTAQUE--***********************************************/
            attaque(joueur);
            /*****************************--FIN ATTAQUE--***********************************************/
            
            
            /*****************************--FIN TOUR--***********************************************/            
            plateau.spliter(); 
            majPlateau();
            
            /*****************************--NOUVEAU TOUR--***********************************************/
            setActif();
            t++;
            if (t%2 == 0) {
                setTour(getTour()+1);
            }   
            this.phaseSuivante();
            points = getTour();
            joueur = getActif();
            joueur2 = getInactif();
            
            majPlateau();
            majJ1(joueur);
            majJ2(joueur2);        
            majPhase();
            
            /*****************************--DEFENSE--***********************************************/
            plateau.coupsDefensePossibles(joueur);
            defense(joueur); 
            plateau.combat(joueur);
            plateau.apresCombat(joueur);  
            /*****************************--FIN DEFENSE--***********************************************/
            
            this.phaseSuivante();
        }
        majJ1(joueur);
        majJ2(joueur2);
        majPlateau();
        
        // envoyer fin partie, vue crée fenêtre puis ferme tout
        code = FIN;
        s1 = getGagnant().getNom() + " a gagné!";
        notifyObserver(code, s1, null, null, null, 0, 0, 0, 0);
        
        return getGagnant();
    }
    
    public void defense(Joueur joueur) throws InterruptedException{
        boolean r = true;
        Case c1;
        Case c2;
        if(getActif() == j2) { // joueur aleatoire
             wait(1000);
            System.out.println("Debut defense");
            CoupDefense c= joueur.getCoupDefense(this);
            if (c!=null)
            {
                plateau.appliquerCoupDefense(c);
                majJ2(joueur);
                majPlateau();
            }
            System.out.println("Fin defense");
            wait(2000);
        }
        else { // joueur humain
            while(!next) {
                
                if(move) {
                    c1 = joueur.getCaseDefenseDep(plateau, x1, y1);
                    c2 = joueur.getCaseDefenseArr(plateau, x2, y2);
                    if((c1 != null) && (c2 != null)) {
                        if(plateau.appliquerCoupDefense(c1, c2)) {
                            notifyObserver(POSCARTE, null, null, null, null, c1.getX(), c1.getY(), c2.getX(), c2.getY());
                        }
                    }
                    move = false;
                }
                
                wait(5);
            }
            next = false;
        }   
    }
    
    public void invocation(Joueur joueur) throws InterruptedException{
        boolean r = true;
        Case c1;
        Case c2;
        if(getActif() == j2) { // joueur aleatoire
            wait(1000);
            System.out.println("Debut invocation");
            CoupInvocation c= joueur.getCoupInvocation(this);
            if (c!=null)
            {
                plateau.appliquerCoupInvocation(c, joueur);
                majJ2(joueur);
                majPlateau();
            }
             System.out.println("Fin invocation");   
            wait(2000);
        }
        else { // joueur humain
            while(!next) {
                if(move) {
                    c1 = joueur.getCaseInvocation(plateau, x1, y1);
                    if(c1 != null) {
                        c2 = plateau.appliquerCoupInvocation(c1, joueur);
                        if(c2 != null) {
                            majPlateau();
                            //notifyObserver(POSCARTE, null, null, null, null, c1.getX(), c1.getY(), c2.getX(), c2.getY());
                        }
                    }
                    move = false;
                    majJ1(joueur);
                }
                wait(5);
            }
            next = false;
        }
    }
    
    public void attaque(Joueur joueur) throws InterruptedException{
        boolean r = true;
        Case c1;
        Case c2;
        if(getActif() == j2) { // joueur aleatoire
            wait(1000);
            System.out.println("Debut attaque");
            CoupAttaque c= joueur.getCoupAttaque(this);
            if (c!=null)
            {
                plateau.appliquerCoupAttaque(c);
                majJ2(joueur);
                majPlateau();
            }
            System.out.println("Fin attaque");
                wait(2000);
            }
            else { // joueur humain
                while(!next) {
                    if(move) {
                        c1 = joueur.getCaseAttaque(plateau, x1, y1);
                        if(c1 != null) {
                            c2 = plateau.appliquerCoupAttaque(c1);
                            if(c2 != null) {
                                notifyObserver(POSCARTE, null, null, null, null, c1.getX(), c1.getY(), c2.getX(), c2.getY());
                            }
                        }
                        move = false;
                    }
                    wait(5);
                }
                next = false;
            }
    }
    
    private void majJ1(Joueur joueur) {
        code  = PVJ1;
        s1 = joueur.getNom() + ": " + joueur.getPv() + "pv";
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);        
        code = RESSJ1;
        s1 = "Ressources: " + joueur.getRessources() + "/" + getTour();
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
        code = ERR;
        s1 = "";
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
    }
    
    private void majJ2(Joueur joueur2) {
        code  = PVJ2;
        s1 = joueur2.getNom() + ": " + joueur2.getPv() + "pv";
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);        
        code = RESSJ2;
        s1 = "Ressources: " + joueur2.getRessources() + "/" + getTour(); 
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
        code = ERR;
        s1 = "";
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
    }
    
    private void majPhase() {
        code = TXTPHASES;
        s1 = "Tour " + getTour() + ", phase " + getPhase(); 
        s2 = getType();
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
        
        code = BTNNEXT;
        s1 = toStringNext();
        notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
    }
    
    private void initAffichage() {
        for(int i = 0; i < plateau.getHauteur(); i++) {
            for(int j = 0; j < plateau.getLargeur(); j++) {
                code = TXTCARTE;
                x1 = i;
                y1 = j;
                x2 = -1;
                y2 = -1;
                if((i == 0) || (i == plateau.getHauteur()-1)) {
                    Carte c = plateau.getCase(i, j).getCarte();                    
                    s1 = c.getNom(); 
                    s2 = Integer.toString(c.getCoup());
                    s3 = Integer.toString(c.getAtt());
                    s4 = Integer.toString(c.getDef()); 
                }
                else {
                    s1 = "";
                    s2 = "";
                    s3 = "";
                    s4 = "";
                }                
                notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
            }
        }
    }
    
    private void majPlateau() {
        for(int i = 0; i < plateau.getHauteur(); i++) {
            for(int j = 0; j < plateau.getLargeur(); j++) {
                code = TXTCARTE;
                x1 = i;
                y1 = j;
                x2 = -1;
                y2 = -1;
                if(plateau.getCase(i, j).contientCarte()) {
                    Carte c = plateau.getCase(i, j).getCarte();                    
                    s1 = c.getNom(); 
                    s2 = Integer.toString(c.getCoup());
                    s3 = Integer.toString(c.getAtt());
                    s4 = Integer.toString(c.getDef());  
                }
                else {
                    s1 = "";
                    s2 = "";
                    s3 = "";
                    s4 = "";
                }                
                notifyObserver(code, s1, s2, s3, s4, x1, y1, x2, y2);
            }
        }
    }
    
    @Override
    public Jeu clone() throws CloneNotSupportedException
    {
        Joueur j3 = new JoueurAleatoireFacile(3, "00000");
        Joueur j4 = new JoueurAleatoireFacile(2, "aaaaa");
        j3.setRessources(j1.getRessources());
        j3.setPv(j1.getPv());
        j4.setRessources(j2.getRessources());
        j4.setPv(j2.getPv());
        Jeu c = new Jeu(j3, j4, 4, tour, phase, plateau.clone());
	return c;
    }

    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
        plateau.addObserver(obs);
    }
    
    public void notifyObserver(int code, String s1, String s2, String s3, String s4, int x1, int y1, int x2, int y2) {
        for(Observer obs : listObserver) {
            obs.update(code, s1, s2, s3, s4, x1, y1, x2, y2);
        }
    }
    
    public void removeObserver() {
        this.listObserver = new ArrayList<Observer>();
    }  
}
