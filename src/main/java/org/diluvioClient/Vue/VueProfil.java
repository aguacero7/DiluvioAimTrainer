package org.diluvioClient.Vue;

import org.diluvioClient.Menu;
import org.diluvioModels.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueProfil extends JPanel implements ActionListener {
    private final Menu menu;
    private final Joueur joueur; // Joueur à afficher
    private final JButton homeButton;

    public VueProfil(Menu menu, Joueur joueur) {
        this.menu = menu;
        this.joueur = joueur;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(30, 30, 30)); // Fond sombre

        JLabel title = new JLabel("Profil du Joueur");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 20))); // Espacement
        add(title);

        add(createInfoLabel("Nom : " + joueur.nom));
        add(createInfoLabel("Nombre de parties : " + joueur.getNb_part()));
        add(createInfoLabel("Moyenne : " + (joueur.getNb_part() > 0 ? joueur.getMoyenne() : "N/A")));
        add(createInfoLabel("Moyenne de Précision : " + (joueur.getNb_part() > 0 ? joueur.getAccMoyenne() : "N/A")));
        add(createInfoLabel("Date de création : " + joueur.getDateCreation()));
        add(createInfoLabel("Statut : " + (joueur.isConnecte() ? "Connecté" : "Non connecté")));

        homeButton = new JButton("Retour au menu principal");
        styleButton(homeButton);
        homeButton.addActionListener(this);
        add(Box.createRigidArea(new Dimension(0, 20))); // Espacement
        add(homeButton);
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 20));
        label.setForeground(new Color(200, 200, 200)); // Gris clair
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180)); // Bleu
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            this.menu.setContentPane(this.menu.menu);
            this.menu.revalidate();
        }
    }
}
