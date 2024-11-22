package org.diluvioClient.Vue;

import org.diluvioClient.Target;
import org.diluvioModels.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class VueGame extends JPanel {

    Game game;

    public VueGame(Game game, int x, int y) {
        this.game = game;
        this.setSize(x, y);
        this.setBackground(Color.GRAY);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int clicX = e.getX();
                int clicY = e.getY();
                if (game.getActiveGame() != null && game.getActiveGame().isClicked(clicX, clicY)) {
                    game.clickTarget(clicX, clicY);
                    generateNewTarget();
                }
                game.setClickCount(game.getClickCount() + 1);
            }
        });
        generateNewTarget(); // Generate a first target at the start
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game.getActiveGame() != null) {
            int x = game.getActiveGame().getX();
            int y = game.getActiveGame().getY();
            g.fillOval(x, y, game.getActiveGame().getSize(), game.getActiveGame().getSize());
        }
    }

    public void generateNewTarget() {
        Random random = new Random();
        int width = this.getWidth();
        int height = this.getHeight();


        if (width > Target.size && height > Target.size) {
            int x = random.nextInt(width - Target.size); //target size for the target to not get out of the screen
            int y = random.nextInt(height - Target.size);
            game.setActiveGame(new Target(x, y));
            repaint();
        } else {
            System.out.println("La taille de la fenêtre est trop petite pour générer une cible. : " + width + "x" + height);
        }
    }
}