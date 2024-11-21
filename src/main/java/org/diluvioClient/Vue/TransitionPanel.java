package org.diluvioClient.Vue;

import org.diluvioClient.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransitionPanel extends JPanel implements ActionListener {
    private final JButton commencerButton;
    private final Menu menu;

    public TransitionPanel(Menu menu) {
        this.menu = menu;


        StyleUtils.stylePanel(this);
        this.setLayout(new GridBagLayout());

        // Bouton Commencer
        this.commencerButton = new JButton("Commencer");
        StyleUtils.styleButton(commencerButton);
        commencerButton.setPreferredSize(new Dimension(300, 80));
        commencerButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        this.add(commencerButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == commencerButton) {

            menu.lancerJeu();
        }
    }
}
