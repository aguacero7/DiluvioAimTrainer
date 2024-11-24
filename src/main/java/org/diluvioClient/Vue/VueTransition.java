package org.diluvioClient.Vue;

import org.diluvioClient.DiluvioClient;
import org.diluvioModels.LanguagesTranslations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueTransition extends JPanel implements ActionListener {
    private final JButton beginButton;
    private final DiluvioClient diluvioClient;

    public VueTransition(DiluvioClient diluvioClient, LanguagesTranslations trans) {
        this.diluvioClient = diluvioClient;


        StyleUtils.stylePanel(this);
        this.setLayout(new GridBagLayout());

        // Beginning Button
        this.beginButton = new JButton(trans.translate("start"));
        StyleUtils.styleButton(beginButton);
        beginButton.setPreferredSize(new Dimension(300, 80));
        beginButton.addActionListener(this);
        diluvioClient.setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        this.add(beginButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == beginButton) {

            diluvioClient.lancerJeu();
        }
    }
}
