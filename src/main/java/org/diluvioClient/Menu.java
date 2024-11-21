package org.diluvioClient;

import org.diluvioClient.Vue.StyleUtils;
import org.diluvioClient.Vue.TransitionPanel;
import org.diluvioClient.Vue.VueJeu;
import org.diluvioClient.Vue.VueProfil;
import org.diluvioModels.GestionFichier;
import org.diluvioModels.Jeu;
import org.diluvioModels.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Menu extends JFrame implements ActionListener, WindowListener {
    static float VERSION = (float) 0.1;
    public JPanel menu;
    int tailleX;
    int tailleY;
    Joueur j;
    Jeu activeGame;
    JButton jouer;
    JButton connect;
    JButton profilButton;
    TransitionPanel transitionPanel;
    VueJeu vueJeu;
    VueProfil vueProfil;

    Menu() {
        this.tailleX = 1280;
        this.tailleY = 720;
        this.setSize(tailleX, tailleY);
        this.setTitle("Diluvio Aim Trainer");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);

        this.menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setBackground(new Color(30, 30, 30)); // Arrière-plan sombre

        JLabel title = new JLabel("Diluvio Aim Trainer", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(255, 255, 255));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        menu.add(title, BorderLayout.NORTH);

        JPanel jpHaut = new JPanel();
        jpHaut.setBackground(new Color(50, 50, 50)); // Bande grise en haut
        jpHaut.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jpHaut.add(this.connect = new JButton("Se connecter"));
        styleButton(connect);

        menu.add(jpHaut, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(40, 40, 40));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel welcomeLabel = new JLabel("Bienvenue dans \n Diluvio Aim Trainer !");
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        welcomeLabel.setForeground(new Color(200, 200, 200));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Espacement

        this.jouer = new JButton("Jouer");
        styleButton(jouer);
        jouer.setAlignmentX(Component.CENTER_ALIGNMENT);
        jouer.addActionListener(this);
        centerPanel.add(jouer);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacement

        this.profilButton = new JButton("Voir le profil");
        styleButton(profilButton);
        profilButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilButton.addActionListener(this);
        centerPanel.add(profilButton);

        menu.add(centerPanel, BorderLayout.CENTER);

        this.setContentPane(menu);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Joueur j = GestionFichier.readUser();
        Menu m;
        if (!j.isConnecte()) {
            m = new Menu();
            m.j = j;
        } else {
            m = new Menu(); // À changer pour un menu authentifié
        }
    }

    // Méthode pour styliser un bouton
    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180)); // Bleu
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void resetMenu() {
        this.setContentPane(menu);
        this.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jouer) {
            // Afficher l'écran de transition
            transitionPanel = new TransitionPanel(this);
            this.setContentPane(transitionPanel);
            this.validate();
        } else if (e.getSource() == profilButton) {
            // Afficher le profil du joueur
            vueProfil = new VueProfil(this, j);
            this.setContentPane(vueProfil);
            this.validate();
        } else if (e.getActionCommand().equals("Retour au menu")) {
            this.setContentPane(menu);
            this.validate();
        }
    }

    public void gameOver() {
        int points = this.activeGame.getPoints();
        double accuracy = (this.activeGame.getNbClics() > 0)
                ? (this.activeGame.getCibleCliquees() * 100.0 / this.activeGame.getNbClics())
                : 0;
        this.j.addGames(1);
        this.j.addPoints(points);
        this.j.addAccuracy(accuracy);
        JPanel jp = new JPanel(new BorderLayout());
        StyleUtils.stylePanel(jp);

        JLabel pointsLabel = new JLabel("Points : " + points);
        StyleUtils.styleLabel(pointsLabel, 24, Color.WHITE);

        JLabel accuracyLabel = new JLabel("Précision : " + String.format("%.2f", accuracy) + " %");
        StyleUtils.styleLabel(accuracyLabel, 24, Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        StyleUtils.stylePanel(infoPanel);

        infoPanel.add(pointsLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement
        infoPanel.add(accuracyLabel);

        jp.add(infoPanel, BorderLayout.CENTER);

        JButton backToMenu = new JButton("Retour au menu");
        StyleUtils.styleButton(backToMenu);
        backToMenu.addActionListener(this);
        jp.add(backToMenu, BorderLayout.SOUTH);

        this.setContentPane(jp);
        this.validate();

        GestionFichier.writeUser(j);
    }

    public void lancerJeu() {
        activeGame = new Jeu(this);
        vueJeu = new VueJeu(activeGame, this.tailleX, this.tailleY);
        this.setContentPane(vueJeu);
        this.validate();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (!this.j.isConnecte()) {
            GestionFichier.writeUser(this.j);
        }
        this.dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}