package org.diluvioClient.Vue;

import org.diluvioClient.DiluvioClient;
import org.diluvioModels.FileManagement;
import org.diluvioModels.LanguagesTranslations;
import org.diluvioModels.LocalSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VueSettings extends JPanel implements ActionListener {

    private JComboBox<String> languageSelector;
    private JComboBox<String> themeSelector;
    private JComboBox<String> resolutionSelector;
    private JButton saveButton;
    private JButton backButton;
    private DiluvioClient menuref;
    private LanguagesTranslations lng;
    public VueSettings(LocalSettings currentSettings, DiluvioClient menuref, LanguagesTranslations lng) {
        // Configuration de base du panneau
        StyleUtils.stylePanel(this);
        this.setLayout(new BorderLayout(20, 20));
        this.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        this.menuref = menuref;
        this.lng = lng;

        // Title
        JPanel headerPanel = new JPanel();
        StyleUtils.stylePanel(headerPanel);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(lng.translate("settings"));
        StyleUtils.styleLabel(titleLabel, 32, Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        this.add(headerPanel, BorderLayout.NORTH);

        // Main Content
        JPanel mainPanel = new JPanel();
        StyleUtils.stylePanel(mainPanel);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Language
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel languageLabel = new JLabel(lng.translate("language") + " :");
        StyleUtils.styleLabel(languageLabel, 20, Color.LIGHT_GRAY);
        mainPanel.add(languageLabel, gbc);

        gbc.gridx = 1;
        languageSelector = new JComboBox<>(new String[]{lng.translate("fr"), lng.translate("en"), lng.translate("es")});
        StyleUtils.styleComboBox(languageSelector);
        languageSelector.setSelectedItem(lng.translate(currentSettings.getLanguage()));
        mainPanel.add(languageSelector, gbc);

        // Theme
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel themeLabel = new JLabel(lng.translate("theme") + " :");
        StyleUtils.styleLabel(themeLabel, 20, Color.LIGHT_GRAY);
        mainPanel.add(themeLabel, gbc);

        gbc.gridx = 1;
        themeSelector = new JComboBox<>(new String[]{lng.translate("theme.light"), lng.translate("theme.dark")});
        StyleUtils.styleComboBox(themeSelector);
        themeSelector.setSelectedItem(lng.translate(currentSettings.getTheme()));
        mainPanel.add(themeSelector, gbc);

        // Resolution
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel resolutionLabel = new JLabel(lng.translate("resolution") + " :");
        StyleUtils.styleLabel(resolutionLabel, 20, Color.LIGHT_GRAY);
        mainPanel.add(resolutionLabel, gbc);

        gbc.gridx = 1;
        resolutionSelector = new JComboBox<>(new String[]{"800x600","1280x720", "1920x1080", "2560x1440"});
        StyleUtils.styleComboBox(resolutionSelector);


        String currentResolution = currentSettings.getFenetreDimension().width + "x" + currentSettings.getFenetreDimension().height;
        resolutionSelector.setSelectedItem(currentResolution);
        mainPanel.add(resolutionSelector, gbc);

        this.add(mainPanel, BorderLayout.CENTER);

        // Buttons at the bottom
        JPanel footerPanel = new JPanel();
        StyleUtils.stylePanel(footerPanel);
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        backButton = new JButton(lng.translate("home_button"));
        StyleUtils.styleButton(backButton);
        backButton.addActionListener(this);
        footerPanel.add(backButton);

        saveButton = new JButton(lng.translate("save_apply"));
        StyleUtils.styleButton(saveButton);
        saveButton.addActionListener(this);
        footerPanel.add(saveButton);

        this.add(footerPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String selectedLanguage = lng.reverseLookup((String) languageSelector.getSelectedItem());
            String selectedTheme = lng.reverseLookup((String) themeSelector.getSelectedItem());
            String resolution = (String) resolutionSelector.getSelectedItem();

            String[] dimensions = resolution.split("x");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);
            Dimension selectedDimension = new Dimension(width, height);
            String oldLanguage = lng.getCurrentLanguage();

            DiluvioClient.currentSettings=new LocalSettings(DiluvioClient.VERSION,selectedLanguage,selectedTheme,selectedDimension);

            menuref.applySettings(oldLanguage);
            FileManagement.writeConf(DiluvioClient.currentSettings);
            menuref.resetMenu();
        } else if (e.getSource() == backButton) {
            // Return to main menu
            menuref.resetMenu();
        }
    }
}
