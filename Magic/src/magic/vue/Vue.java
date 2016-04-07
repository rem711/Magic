/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic.vue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.StyleConstants;
import magic.observeur.*;
import magic.controleur.*;

/**
 *
 * @author rem711
 */
public class Vue extends JFrame implements Observer {
    
    private JPanel contentPane = new JPanel();
    
    private int largeur = 900; //700
    private int hauteur = 608; //608
    private Dimension dimPanel1 = new Dimension(largeur,40);
    private Dimension dimPanel2 = new Dimension(largeur, 528);
    private Dimension dimPanel3 = new Dimension(largeur, 40);
    private Dimension dimGrille = new Dimension(528,528);// 768
    private Dimension dimPhases = new Dimension(324,528); // 174 528
    private Dimension dimCarte = new Dimension(112,112);// 156
    
    private ControleurJeu controleur;
    private JPanel grillePanels[][] = new JPanel[6][4];
    private JLabel j1;
    private JLabel j2;
    private JLabel ressourcesJ1;
    private JLabel ressourcesJ2;
    private JLabel phase;
    private JLabel type;
    private JTextArea erreurs;
    private JLabel info;
    private JButton nextPhase;
    
    private final int PVJ1 = 1;
    private final int PVJ2 = 2;
    private final int RESSJ1 = 3;
    private final int RESSJ2 = 4;
    private final int BTNNEXT = 5;
    private final int POSCARTE = 6;
    private final int TXTCARTE = 7;
    private final int TXTPHASES = 8;
    
    public Vue(ControleurJeu c) {
        this.setSize(largeur, hauteur);
        this.setTitle("It's Magic!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.contentPane.setLayout(new BorderLayout());
        initComposants();
        this.controleur = c;
        
        this.setContentPane(contentPane);
        this.setVisible(true);
    }

    private void initComposants() {
        initPanel1();
        initPanel2();
        initPanel3();      
    }
    
    private void initPanel1() {
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(dimPanel1);
        panel1.setLayout(new BorderLayout());
        
        JPanel panelJ1 = new JPanel();
        panelJ1.setPreferredSize(new Dimension(528,40));
        j1 = new JLabel();
        j1.setName("joueur1");
        j1.setText("");
        j1.setHorizontalAlignment(SwingConstants.CENTER);
        panelJ1.add(j1);
        
        JPanel panelRessources = new JPanel();
        panelRessources.setPreferredSize(new Dimension(172,40));
        ressourcesJ1 = new JLabel();
        ressourcesJ1.setName("ressources1");
        ressourcesJ1.setText("");
        ressourcesJ1.setHorizontalAlignment(SwingConstants.CENTER);
        ressourcesJ1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        panel1.add(j1, BorderLayout.CENTER);
        panel1.add(ressourcesJ1, BorderLayout.EAST);
        this.contentPane.add(panel1, BorderLayout.NORTH);
    }
    
    private void initPanel2() {
        JPanel panel2 = new JPanel();
        panel2.setPreferredSize(dimPanel2);
        panel2.setLayout(new BorderLayout());
        
        JPanel grille = new JPanel();
        grille.setPreferredSize(dimGrille);
        grille.setLayout(new GridLayout(6,4));
        
        JPanel phases = new JPanel();
        phases.setPreferredSize(dimPhases);
        phases.setLayout(new GridLayout(6,0));        
        
        
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 4; j++) {   
                JButton b = new JButton();
                b.setPreferredSize(dimCarte);
                b.setName("carte");
                b.addActionListener(new ButtonListener());   
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                
                JPanel text = new JPanel();
                text.setName("");
                text.setLayout(new GridLayout(2,0));
                text.setPreferredSize(dimCarte);
                text.setOpaque(false);
                text.setBorder(new LineBorder(new Color(0,0,0), 1, true));
                
                Dimension d = new Dimension(28,28);
                JLabel nom = new JLabel();
                nom.setName("nom");
                nom.setText("");
                nom.setPreferredSize(d);
                nom.setHorizontalAlignment(SwingConstants.CENTER);
                nom.setVerticalAlignment(SwingConstants.TOP);
                
                
                JLabel cout = new JLabel();
                cout.setName("cout");
                cout.setText("");
                cout.setPreferredSize(d);
                cout.setHorizontalAlignment(SwingConstants.CENTER);
                cout.setVerticalAlignment(SwingConstants.TOP);
                
                JLabel att = new JLabel();
                att.setName("attaque");
                att.setText("");
                att.setPreferredSize(d);
                att.setHorizontalAlignment(SwingConstants.CENTER);
                att.setVerticalAlignment(SwingConstants.TOP);
                
                JLabel def = new JLabel();
                def.setName("defense");
                def.setText("");
                def.setPreferredSize(d);
                def.setHorizontalAlignment(SwingConstants.CENTER);
                def.setVerticalAlignment(SwingConstants.TOP);
                
                text.add(nom);
                text.add(cout);
                text.add(att);
                text.add(def);                                
                
                
                grillePanels[i][j] = new JPanel();
                String s = Integer.toString(i) + Integer.toString(j); 
                grillePanels[i][j].setName(s);
                grillePanels[i][j].setBorder(new LineBorder(new Color(0,0,0), 1, true));
                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(grillePanels[i][j]);
                grillePanels[i][j].setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 112, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 112, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                );
                switch(i) {
                    case 0:
                    case 5:
                        //grillePanels[i][j].setBackground(Color.WHITE);                                         
                        grillePanels[i][j].setBackground(new Color(165,229,220));
                        break;
                    case 1:
                    case 4:
                        //grillePanels[i][j].setBackground(Color.BLUE);
                        grillePanels[i][j].setBackground(new Color(220,230,75));
                        break;
                    case 2:
                    case 3:
                        //grillePanels[i][j].setBackground(Color.LIGHT_GRAY);
                        //grillePanels[i][j].setBackground(new Color(223,221,213));
                        break;
                } 
                
                
                grillePanels[i][j].add(b);  
                grillePanels[i][j].add(text);               
                
                
                grille.add(grillePanels[i][j]);
            }   
        }
        
        JPanel informations = new JPanel();
        informations.setLayout(new BorderLayout());
        info = new JLabel();
        info.setText("");
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setFont(new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 12));
        informations.add(info, BorderLayout.CENTER);
        
        erreurs = new JTextArea();
        erreurs.setText("");
        erreurs.setLineWrap(true);
        erreurs.setWrapStyleWord(true);
        erreurs.setFont(new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 12));
        erreurs.setOpaque(false);
        erreurs.setAlignmentX(CENTER_ALIGNMENT);
        erreurs.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        
        JPanel textes = new JPanel();   
        textes.setLayout(new BorderLayout());
        phase = new JLabel();
        phase.setName("tour");
        phase.setText("");
        phase.setHorizontalAlignment(SwingConstants.CENTER);
        phase.setFont(new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 15));
        type = new JLabel();
        type.setName("type");
        type.setText("");
        type.setHorizontalAlignment(SwingConstants.CENTER);
        type.setFont(new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 15));
        textes.add(phase, BorderLayout.NORTH);
        textes.add(type, BorderLayout.CENTER);
        
        
        nextPhase = new JButton();
        nextPhase.setName("next");
        nextPhase.setText("");
        nextPhase.addActionListener(new ButtonListener());
        
        phases.add(new JPanel());
        phases.add(new JPanel());
        phases.add(informations);
        phases.add(erreurs);
        phases.add(textes); 
        phases.add(nextPhase);
        
        panel2.add(grille, BorderLayout.CENTER);
        panel2.add(phases, BorderLayout.EAST);
        this.contentPane.add(panel2, BorderLayout.CENTER);
    }
    
    private void initPanel3() {
        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(dimPanel3);
        panel3.setLayout(new BorderLayout());
        
        JPanel panelJ2 = new JPanel();
        panelJ2.setPreferredSize(new Dimension(528,40));
        j2 = new JLabel();
        j2.setName("joueur2");
        j2.setText("");
        j2.setHorizontalAlignment(SwingConstants.CENTER);
        panelJ2.add(j2);
        
        JPanel panelRessources2 = new JPanel();
        panelRessources2.setPreferredSize(new Dimension(172,40));
        ressourcesJ2 = new JLabel();
        ressourcesJ2.setName("ressources2");
        ressourcesJ2.setText("");
        ressourcesJ2.setHorizontalAlignment(SwingConstants.CENTER);
        ressourcesJ2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        panel3.add(j2, BorderLayout.CENTER);
        panel3.add(ressourcesJ2, BorderLayout.EAST);
        this.contentPane.add(panel3, BorderLayout.SOUTH);
    }
        
    class ButtonListener implements ActionListener {        
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
            if(((JButton)e.getSource()).getName().equals("next")) {
                actionNext(e);
            }
            else {
                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 4; j++) {  
                        switch(i) {
                            case 0:
                            case 5:                                       
                                grillePanels[i][j].setBackground(new Color(165,229,220));
                                break;
                            case 1:
                            case 4:
                                grillePanels[i][j].setBackground(new Color(220,230,75));
                                break;
                            case 2:
                            case 3:
                                grillePanels[i][j].setBackground(UIManager.getColor ("Panel.background"));
                                break;
                        } 
                    }
                }
                info.setText(((JLabel)((JPanel)((JButton)e.getSource()).getAccessibleContext().getAccessibleParent().getAccessibleContext().getAccessibleChild(1)).getComponent(0)).getText());
                ((JPanel)((JButton)e.getSource()).getAccessibleContext().getAccessibleParent()).setBackground(new Color(218,138,68));
                actionCarte(e);
            }
        }            
    }
    
    private void actionCarte(ActionEvent e) {
        String s = ((JButton)e.getSource()).getParent().getName();
        int x = Integer.parseInt(s.substring(0, 1));
        int y = Integer.parseInt(s.substring(1));
        
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 4; j++) {  
                switch(i) {
                    case 0:
                    case 5:                                       
                        grillePanels[i][j].setBackground(new Color(165,229,220));
                        break;
                    case 1:
                    case 4:
                        grillePanels[i][j].setBackground(new Color(220,230,75));
                        break;
                    case 2:
                    case 3:
                        grillePanels[i][j].setBackground(UIManager.getColor ("Panel.background"));
                        break;
                } 
            }
        }
        grillePanels[x][y].setBackground(new Color(160,80,45));
        
        controleur.clicCarte(x, y);
    }
    
    private void actionNext(ActionEvent e) {
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 4; j++) {  
                switch(i) {
                    case 0:
                    case 5:                                       
                        grillePanels[i][j].setBackground(new Color(165,229,220));
                        break;
                    case 1:
                    case 4:
                        grillePanels[i][j].setBackground(new Color(220,230,75));
                        break;
                    case 2:
                    case 3:
                        grillePanels[i][j].setBackground(UIManager.getColor ("Panel.background"));
                        break;
                } 
            }
        }
        controleur.phaseSuivante();
    } 
    
    @Override
    public void update(int code, String s1, String s2, String s3, String s4, int x1, int y1, int x2, int y2) {
        switch(code) {
            case 1: // nom + pv joueur actif
                this.j2.setText(s1);
                this.j2.repaint();                
                break;
            case 2: // nom + pv joueur inactif
                this.j1.setText(s1);
                this.j1.repaint();
                break;
            case 3: // ressources joueur actif
                this.ressourcesJ2.setText(s1);
                this.ressourcesJ2.repaint();                
                break;
            case 4: // ressources joueur inactif
                this.ressourcesJ1.setText(s1);
                this.ressourcesJ1.repaint();
                break;
            case 5: // texte bouton phases
                this.nextPhase.setText(s1);
                this.nextPhase.repaint();
                break;
            case 6: // déplacement carte                      
                String temp1 = ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(0)).getText();
                String temp2 = ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(1)).getText();
                String temp3 = ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(2)).getText();
                String temp4 = ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(3)).getText();

                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(0)).setText(((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(0)).getText());
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(1)).setText(((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(1)).getText());
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(2)).setText(((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(2)).getText());
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(3)).setText(((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(3)).getText());

                ((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(0)).setText(temp1);
                ((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(1)).setText(temp2);
                ((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(2)).setText(temp3);
                ((JLabel)((JPanel)grillePanels[x2][y2].getComponent(1)).getComponent(3)).setText(temp4);
                
                ((JPanel)grillePanels[x1][y1].getComponent(1)).repaint();
                ((JPanel)grillePanels[x2][y2].getComponent(1)).repaint();
                break;
            case 7: // texte carte
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(0)).setText(s1);
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(1)).setText(s2);
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(2)).setText(s3);
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(3)).setText(s4);
                
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(0)).repaint();
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(1)).repaint();
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(2)).repaint();
                ((JLabel)((JPanel)grillePanels[x1][y1].getComponent(1)).getComponent(3)).repaint();
                break;
            case 8: // phases
                this.phase.setText(s1);
                this.type.setText(s2);
                this.info.setText("");
                this.phase.repaint();
                this.type.repaint();
                this.info.repaint();
                break;
            case 9:
                this.erreurs.setText(s1);
                break;
            case 10:
                this.erreurs.setText(s1);
        }
    }
}
