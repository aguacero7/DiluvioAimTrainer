package org.diluvioClient.Vue;

import org.diluvioClient.Cible;
import org.diluvioModels.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class VueJeu extends JPanel {

    Jeu jeu;

    public VueJeu(Jeu jeu, int x, int y) {
        this.jeu = jeu;
        this.setSize(x, y);
        this.setBackground(Color.GRAY);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int clicX = e.getX();
                int clicY = e.getY();
                if (jeu.getCibleActive() != null && jeu.getCibleActive().estCliquee(clicX, clicY)) {
                    jeu.clicCible(clicX, clicY);
                    genererNouvelleCible();
                }
                jeu.setNbClics(jeu.getNbClics() + 1);
            }
        });
        genererNouvelleCible(); // Générer une première cible au démarrage
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (jeu.getCibleActive() != null) {
            int x = jeu.getCibleActive().getX();
            int y = jeu.getCibleActive().getY();
            g.fillOval(x, y, jeu.getCibleActive().getTaille(), jeu.getCibleActive().getTaille());
        }
    }

    public void genererNouvelleCible() {
        Random random = new Random();
        int largeur = this.getWidth();
        int hauteur = this.getHeight();


        if (largeur > Cible.taille && hauteur > Cible.taille) {
            int x = random.nextInt(largeur - Cible.taille); //- la taille de la cible pour pas que ça déborde
            int y = random.nextInt(hauteur - Cible.taille);
            jeu.setCibleActive(new Cible(x, y));
            repaint();
        } else {
            System.out.println("La taille de la fenêtre est trop petite pour générer une cible. : " + largeur + "x" + hauteur);
        }
    }
}