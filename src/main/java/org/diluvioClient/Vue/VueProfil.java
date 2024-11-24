package org.diluvioClient.Vue;

import org.diluvioClient.DiluvioClient;
import org.diluvioModels.LanguagesTranslations;
import org.diluvioModels.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueProfil extends JPanel implements ActionListener {
    private final DiluvioClient diluvioClient;
    private final Player player;
    private final JButton homeButton;
    private final LanguagesTranslations translations;

    public VueProfil(DiluvioClient diluvioClient, Player player, LanguagesTranslations translations) {
        this.diluvioClient = diluvioClient;
        this.player = player;
        this.translations = translations;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel(translations.translate("player_profile"));
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(title);

        add(createInfoLabel(translations.translate("name") + " : " + player.name));
        add(createInfoLabel(translations.translate("game_count") + " : " + player.getGameCount()));
        add(createInfoLabel(translations.translate("average") + " : " + (player.getGameCount() > 0 ? player.getPointsAverage()+" "+translations.translate("points") : "N/A")));
        add(createInfoLabel(translations.translate("accuracy_mean") + " : " + (player.getGameCount() > 0 ? Math.round(player.getAccuracyAverage())+"%" : "N/A")));
        add(createInfoLabel(translations.translate("creation_date") + " : " + player.getDateOfCreation()));
        add(createInfoLabel(translations.translate("status") + " : " + (player.isPlayerAuthenticated() ? translations.translate("connected") : translations.translate("disconnected"))));

        homeButton = new JButton(translations.translate("home_button"));
        styleButton(homeButton);
        homeButton.addActionListener(this);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(homeButton);
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 20));
        label.setForeground(new Color(200, 200, 200));
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            this.diluvioClient.setContentPane(this.diluvioClient.menu);
            this.diluvioClient.revalidate();
        }
    }
}
