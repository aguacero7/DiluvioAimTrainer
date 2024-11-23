package org.diluvioClient;

import org.diluvioClient.Vue.*;
import org.diluvioModels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.spi.LocaleNameProvider;


public class DiluvioClient extends JFrame implements ActionListener, WindowListener {
    public static String VERSION = "0.1";
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
    public static LocalSettings currentSettings;


    DiluvioClient() {

        this.setTitle("Diluvio Aim Trainer " + VERSION);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);

        this.menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setBackground(new Color(30, 30, 30)); // Arrière-plan sombre
        this.currentSettings = FileManagement.readConf();
        this.xSize = this.currentSettings.getFenetreDimension().width;
        this.ySize = this.currentSettings.getFenetreDimension().height;
        this.setSize(xSize, ySize);
        //Translantions Part

        translations = new LanguagesTranslations();
        translations.setLanguage(currentSettings.getLanguage());


        JLabel title = new JLabel("Diluvio Aim Trainer", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(255, 255, 255));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        menu.add(title, BorderLayout.NORTH);

        JPanel jpHaut = new JPanel();
        jpHaut.setBackground(new Color(50, 50, 50));
        jpHaut.setLayout(new BorderLayout());
        jpHaut.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.settingsButton = new JButton(translations.translate("settings"));
        StyleUtils.styleButton(settingsButton);
        jpHaut.add(this.settingsButton, BorderLayout.WEST);
        jpHaut.add(this.loginButton = new JButton(translations.translate("login")), BorderLayout.EAST);
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
        DiluvioClient m;
        if (!j.isPlayerAuthenticated()) {
            m = new DiluvioClient();
            m.j = j;
        } else {
            m = new DiluvioClient(); // #TODO change for an authenticated menu
        }
    }


    public void resetMenu() {
        this.setContentPane(this.menu);
        this.validate();
    }
    private void updateComponentText(JComponent component, String oldLanguage) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String oldText = button.getText();
            String key = translations.reverseLookup(oldText,oldLanguage);
            if (key != null) {
                button.setText(translations.translate(key));
            }
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            String oldText = label.getText();
            String key = translations.reverseLookup(oldText);
            System.out.println(oldText+"  "+key);

            if (key != null) {

                label.setText(translations.translate(key));
            }
        }
    }
    private void refreshAllComponents(Container container,String oldLanguage) {
        System.out.println("Old langiuage"+oldLanguage+"    new :"+this.translations.getCurrentLanguage());
            for (Component component : container.getComponents()) {
                if (component instanceof JComponent) {
                    updateComponentText((JComponent) component, oldLanguage);
                }
                if (component instanceof Container) {
                    refreshAllComponents((Container) component, oldLanguage); // Recurse for nested components
                }
            }
        }

    public void applySettings(String oldLanguage) {
        this.translations.setLanguage(DiluvioClient.currentSettings.getLanguage());
        this.setSize(DiluvioClient.currentSettings.getFenetreDimension());
        refreshAllComponents(menu,oldLanguage);
        resetMenu();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jouer) {
            // display transition screen
            vueTransition = new VueTransition(this, this.translations);
            this.setContentPane(vueTransition);
            this.validate();
        } else if (e.getSource() == profilButton) {
            // display player profile
            vueProfil = new VueProfil(this, j, this.translations);
            this.setContentPane(vueProfil);
            this.validate();
        } else if (e.getActionCommand().equals(this.translations.translate("home_button"))) {
            this.setContentPane(menu);
            this.validate();
        } else if (e.getActionCommand().equals(this.translations.translate("settings"))) {
            //display settings screen
            this.vueSettings = new VueSettings(currentSettings,this,this.translations);
            this.setContentPane(vueSettings);
            this.validate();
        } else if (e.getActionCommand().equals(this.translations.translate("login"))) {
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

        JLabel pointsLabel = new JLabel(this.translations.translate("points") + " : " + points);
        StyleUtils.styleLabel(pointsLabel, 24, Color.WHITE);

        JLabel accuracyLabel = new JLabel(this.translations.translate("precision") + " : " + String.format("%.2f", accuracy) + " %");
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