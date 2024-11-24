package org.diluvioClient.Vue;

import org.diluvioClient.Target;
import org.diluvioModels.Game;
import org.diluvioModels.LanguagesTranslations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class VueGame extends JPanel {

    private static final int GAME_AREA_SIZE = 800; // Taille de la zone de jeu
    private final Game game;
    private final LanguagesTranslations translator;
    private final JLabel timeLabel;
    private final JLabel targetsLabel;

    public VueGame(Game game, int windowWidth, int windowHeight, LanguagesTranslations translator) {
        this.game = game;
        this.translator = translator;

        StyleUtils.stylePanel(this);
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        StyleUtils.stylePanel(infoPanel);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        timeLabel = new JLabel();
        StyleUtils.styleLabel(timeLabel, 20, Color.LIGHT_GRAY);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        targetsLabel = new JLabel();
        StyleUtils.styleLabel(targetsLabel, 20, Color.LIGHT_GRAY);
        targetsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(timeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(targetsLabel);

        this.add(infoPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int gameAreaX = (getWidth() - GAME_AREA_SIZE) / 2;
                int gameAreaY = (getHeight() - GAME_AREA_SIZE) / 2;

                g.setColor(new Color(50, 50, 50));
                g.fillRect(gameAreaX, gameAreaY, GAME_AREA_SIZE, GAME_AREA_SIZE);

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(gameAreaX, gameAreaY, GAME_AREA_SIZE, GAME_AREA_SIZE);

                if (game.getActiveGame() != null) {
                    int x = gameAreaX + game.getActiveGame().getX();
                    int y = gameAreaY + game.getActiveGame().getY();
                    g.setColor(new Color(70, 130, 180));
                    g.fillOval(x, y, game.getActiveGame().getSize(), game.getActiveGame().getSize());
                }
            }
        };

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleGameClick(e, gamePanel);
            }
        });

        gamePanel.setPreferredSize(new Dimension(GAME_AREA_SIZE, GAME_AREA_SIZE));
        gamePanel.setOpaque(false);
        this.add(gamePanel, BorderLayout.CENTER);

        generateNewTarget();
        updateInfoLabels();
    }

    private void handleGameClick(MouseEvent e, JPanel gamePanel) {
        int clicX = e.getX();
        int clicY = e.getY();

        int gameAreaX = (gamePanel.getWidth() - GAME_AREA_SIZE) / 2;
        int gameAreaY = (gamePanel.getHeight() - GAME_AREA_SIZE) / 2;

        if (clicX >= gameAreaX && clicX <= gameAreaX + GAME_AREA_SIZE &&
                clicY >= gameAreaY && clicY <= gameAreaY + GAME_AREA_SIZE) {

            int relativeX = clicX - gameAreaX;
            int relativeY = clicY - gameAreaY;

            if (game.getActiveGame() != null && game.getActiveGame().isClicked(relativeX, relativeY)) {
                game.clickTarget(relativeX, relativeY);
                generateNewTarget();
            }else{
                game.setPoints(game.getPoints() -2);

            }
            game.setClickCount(game.getClickCount() + 1);
            targetsLabel.setText(translator.translate("points") + " : " + game.getPoints());
        }
    }

    public void updateInfoLabels() {
        long timeRemaining = Math.max(0, game.getChronoMax() - (System.currentTimeMillis() - game.getChronoStart()));
        timeLabel.setText(translator.translate("remaining_time") + " : " + (timeRemaining / 1000) + "s");
        targetsLabel.setText(translator.translate("points") + " : " + game.getPoints());
    }

    public void generateNewTarget() {
        Random random = new Random();
        int maxX = GAME_AREA_SIZE - Target.size;
        int maxY = GAME_AREA_SIZE - Target.size;

        if (maxX > 0 && maxY > 0) {
            int x = random.nextInt(maxX);
            int y = random.nextInt(maxY);
            game.setActiveGame(new Target(x, y));
            repaint();
            updateInfoLabels();
        } else {
            System.out.println("La taille de la zone de jeu est trop petite pour générer une cible.");
        }
    }
}
