package org.diluvioClient.Vue;

import javax.swing.*;
import java.awt.*;


public class StyleUtils {

    public static void styleLabel(JLabel label, int fontSize, Color color) {
        label.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        label.setForeground(color);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    }

    public static void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180)); // Bleu
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setFocusable(false);
        comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
    }


    public static void stylePanel(JPanel panel) {
        panel.setBackground(new Color(30, 30, 30));
    }
}
