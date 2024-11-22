package org.diluvioClient;

import org.diluvioClient.Vue.*;
import org.diluvioModels.Game;
import org.diluvioModels.FileManagement;
import org.diluvioModels.LanguagesTranslations;
import org.diluvioModels.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class Menu extends JFrame implements ActionListener, WindowListener {
    static String VERSION = "0.1";
    public JPanel menu;
    int xSize;
    int ySize;
    Player j;
    Game activeGame;
    JButton jouer;
    JButton loginButton;
    JButton profilButton;
    JButton settingsButton;
    VueTransition vueTransition;
    VueSettings vueSettings;
    VueGame vueGame;
    VueProfil vueProfil;
    LanguagesTranslations translations;

    Menu() {
        this.xSize = 1280;
        this.ySize = 720;
        this.setSize(xSize, ySize);
        this.setTitle("Diluvio Aim Trainer "+ VERSION);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);

        this.menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setBackground(new Color(30, 30, 30)); // Arrière-plan sombre

        //Translantions Part
        translations = new LanguagesTranslations();
        translations.setLanguage("en"); // must be loaded from local conf


        JLabel title = new JLabel("Diluvio Aim Trainer", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(255, 255, 255));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        menu.add(title, BorderLayout.NORTH);

        JPanel jpHaut = new JPanel();
        jpHaut.setBackground(new Color(50, 50, 50)); // Bande grise en haut
        jpHaut.setLayout(new BorderLayout());
        this.settingsButton = new JButton(translations.translate("settings"));
        StyleUtils.styleButton(settingsButton);
        jpHaut.add(this.settingsButton,BorderLayout.WEST);
        jpHaut.add(this.loginButton = new JButton(translations.translate("login")),BorderLayout.EAST);
        StyleUtils.styleButton(loginButton);
        this.settingsButton.addActionListener(this);
        this.loginButton.addActionListener(this);

        menu.add(jpHaut, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(40, 40, 40));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel welcomeLabel = new JLabel(translations.translate("welcome"));
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        welcomeLabel.setForeground(new Color(200, 200, 200));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Espacement

        this.jouer = new JButton(translations.translate("play"));
        StyleUtils.styleButton(jouer);
        jouer.setAlignmentX(Component.CENTER_ALIGNMENT);
        jouer.addActionListener(this);
        centerPanel.add(jouer);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espacement



        this.profilButton = new JButton(translations.translate("see_profile"));
        StyleUtils.styleButton(profilButton);
        profilButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilButton.addActionListener(this);
        centerPanel.add(profilButton);

        menu.add(centerPanel, BorderLayout.CENTER);


        this.setContentPane(menu);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Player j = FileManagement.readUser();
        Menu m;
        if (!j.isPlayerAuthenticated()) {
            m = new Menu();
            m.j = j;
        } else {
            m = new Menu(); // #TODO change for an authenticated menu
        }
    }



    public void resetMenu() {
        this.setContentPane(menu);
        this.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jouer) {
            // display transition screen
            vueTransition = new VueTransition(this,this.translations);
            this.setContentPane(vueTransition);
            this.validate();
        } else if (e.getSource() == profilButton) {
            // display player profile
            vueProfil = new VueProfil(this, j,this.translations);
            this.setContentPane(vueProfil);
            this.validate();
        } else if (e.getActionCommand().equals(this.translations.translate("home_button"))) {
            this.setContentPane(menu);
            this.validate();
        }
        else if (e.getActionCommand().equals(this.translations.translate("settings"))) {
            this.setContentPane(vueSettings);
            this.validate();
        }
        else if (e.getActionCommand().equals(this.translations.translate("login"))) {
            System.out.println("AUTH TODO");
        }
    }

    public void gameOver() {
        int points = this.activeGame.getPoints();
        double accuracy = (this.activeGame.getClickCount() > 0)
                ? (this.activeGame.getClickedTargets() * 100.0 / this.activeGame.getClickCount())
                : 0;
        this.j.addGames(1);
        this.j.addPoints(points);
        this.j.addAccuracy(accuracy);
        JPanel jp = new JPanel(new BorderLayout());
        StyleUtils.stylePanel(jp);

        JLabel pointsLabel = new JLabel(this.translations.translate("points")+" : " + points);
        StyleUtils.styleLabel(pointsLabel, 24, Color.WHITE);

        JLabel accuracyLabel = new JLabel(this.translations.translate("precision")+" : " + String.format("%.2f", accuracy) + " %");
        StyleUtils.styleLabel(accuracyLabel, 24, Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        StyleUtils.stylePanel(infoPanel);

        infoPanel.add(pointsLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacement
        infoPanel.add(accuracyLabel);

        jp.add(infoPanel, BorderLayout.CENTER);

        JButton backToMenu = new JButton(this.translations.translate("home_button"));
        StyleUtils.styleButton(backToMenu);
        backToMenu.addActionListener(this);
        jp.add(backToMenu, BorderLayout.SOUTH);

        this.setContentPane(jp);
        this.validate();

        FileManagement.writeUser(j);
    }

    public void lancerJeu() {
        activeGame = new Game(this);
        vueGame = new VueGame(activeGame, this.xSize, this.ySize);
        this.setContentPane(vueGame);
        this.validate();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (!this.j.isPlayerAuthenticated()) {
            FileManagement.writeUser(this.j);
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