package org.diluvioClient;

import org.diluvioClient.Vue.*;
import org.diluvioModels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The main class of the Diluvio Aim Trainer application.
 * This class provides the graphical user interface (GUI) and manages the game state, user interactions,
 * and navigation between different views (menu, settings, game, profile).
 * It extends JFrame and implements ActionListener and WindowListener for handling events.
 *
 * @version 0.1
 */
public class DiluvioClient extends JFrame implements ActionListener, WindowListener {

    /** Application version */
    public static String VERSION = "0.1";

    /** Main menu panel */
    public JPanel menu;

    /** Window dimensions */
    int xSize;
    int ySize;

    /** Player object */
    Player j;

    /** Active game object */
    Game activeGame;

    /** UI components */
    JButton jouer;
    JButton loginButton;
    JButton profilButton;
    JButton settingsButton;
    VueTransition vueTransition;
    VueSettings vueSettings;
    VueGame vueGame;
    VueProfil vueProfil;

    /** Translations object for language handling */
    LanguagesTranslations translations;

    /** Current settings for the application */
    public static LocalSettings currentSettings;

    /**
     * Constructor for initializing the DiluvioClient.
     * Sets up the window size, title, and components. Loads translations and current settings.
     */
    DiluvioClient() {
        // Setup the frame and its properties
        this.setTitle("Diluvio Aim Trainer " + VERSION);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);

        // Set up the menu panel
        this.menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setBackground(new Color(30, 30, 30));

        // Load current settings
        this.currentSettings = FileManagement.readConf();
        this.xSize = this.currentSettings.getFenetreDimension().width;
        this.ySize = this.currentSettings.getFenetreDimension().height;
        this.setSize(xSize, ySize);

        // Initialize translations
        translations = new LanguagesTranslations();
        translations.setLanguage(currentSettings.getLanguage());

        // Title label
        JLabel title = new JLabel("Diluvio Aim Trainer", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        menu.add(title, BorderLayout.NORTH);

        // Panel for top buttons (settings and login)
        JPanel jpHaut = new JPanel();
        jpHaut.setBackground(new Color(50, 50, 50));
        jpHaut.setLayout(new BorderLayout());
        jpHaut.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.settingsButton = new JButton(translations.translate("settings"));
        StyleUtils.styleButton(settingsButton);
        jpHaut.add(this.settingsButton, BorderLayout.WEST);
        jpHaut.add(this.loginButton = new JButton(translations.translate("login")), BorderLayout.EAST);
        StyleUtils.styleButton(loginButton);
        this.settingsButton.addActionListener(this);
        this.loginButton.addActionListener(this);

        menu.add(jpHaut, BorderLayout.NORTH);

        // Center panel with play and profile buttons
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

    /**
     * Main method to launch the DiluvioClient application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Player j = FileManagement.readUser();
        DiluvioClient m;
        if (!j.isPlayerAuthenticated()) {
            m = new DiluvioClient();
            m.j = j;
        } else {
            m = new DiluvioClient(); // TODO: Add authenticated menu logic
        }
    }

    /**
     * Resets the menu content pane to its original state.
     */
    public void resetMenu() {
        this.setContentPane(this.menu);
        this.validate();
    }

    /**
     * Updates the text of a component based on the selected language.
     *
     * @param component The component whose text should be updated.
     * @param oldLanguage The previous language.
     */
    private void updateComponentText(JComponent component, String oldLanguage) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;
            String oldText = button.getText();
            String key = translations.reverseLookup(oldText, oldLanguage);
            if (key != null) {
                button.setText(translations.translate(key));
            }
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            String oldText = label.getText();
            String key = translations.reverseLookup(oldText);
            if (key != null) {
                label.setText(translations.translate(key));
            }
        }
    }

    /**
     * Recursively refreshes all components inside a container with the updated translations.
     *
     * @param container The container whose components should be refreshed.
     * @param oldLanguage The old language used for comparison.
     */
    private void refreshAllComponents(Container container, String oldLanguage) {
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent) {
                updateComponentText((JComponent) component, oldLanguage);
            }
            if (component instanceof Container) {
                refreshAllComponents((Container) component, oldLanguage); // Recursion for nested containers
            }
        }
    }

    /**
     * Applies the settings from the current configuration.
     * If the language has changed, it updates the translations and refreshes the UI components.
     *
     * @param oldLanguage The previously set language.
     */
    public void applySettings(String oldLanguage) {
        if (!oldLanguage.equals(DiluvioClient.currentSettings.getLanguage())) {
            this.translations.setLanguage(DiluvioClient.currentSettings.getLanguage());
            refreshAllComponents(menu, oldLanguage);
        }
        this.setSize(DiluvioClient.currentSettings.getFenetreDimension());
        resetMenu();
    }

    /**
     * Handles button click events.
     *
     * @param e The action event triggered by a button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jouer) {
            // Display transition screen
            vueTransition = new VueTransition(this, this.translations);
            this.setContentPane(vueTransition);
            this.validate();
        } else if (e.getSource() == profilButton) {
            // Display player profile
            vueProfil = new VueProfil(this, j, this.translations);
            this.setContentPane(vueProfil);
            this.validate();
        } else if (e.getActionCommand().equals(this.translations.translate("home_button"))) {
            this.setContentPane(menu);
            this.validate();
        } else if (e.getActionCommand().equals(this.translations.translate("settings"))) {
            // Display settings screen
            this.vueSettings = new VueSettings(currentSettings, this, this.translations);
            this.setContentPane(vueSettings);
            this.validate();
        } else if (e.getActionCommand().equals(this.translations.translate("login"))) {
            System.out.println("AUTH TODO");
        }
    }

    /**
     * Displays the game over screen with the player's performance (points and accuracy).
     */
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
        this.setSize(DiluvioClient.currentSettings.getFenetreDimension());
        this.setContentPane(jp);
        this.validate();

        // Save player data to file
        FileManagement.writeUser(j);
    }

    /**
     * Starts a new game and transitions to the game view.
     */
    public void lancerJeu() {
        activeGame = new Game(this);

        vueGame = new VueGame(activeGame, this.xSize, this.ySize, this.translations);
        this.activeGame.setVue(vueGame);
        this.setContentPane(vueGame);
        this.validate();
    }

    /**
     * Window listener methods for handling window events.
     */
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (!this.j.isPlayerAuthenticated()) {
            FileManagement.writeUser(this.j);
        }
        this.dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    /**
     * Gets the current game view.
     *
     * @return The current VueGame instance.
     */
    public VueGame getVueGame() {
        return this.vueGame;
    }
}
