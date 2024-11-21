package org.diluvioClient;
//Target model
public class Cible {
    public static int taille = 80; // taille par défaut
    private final int x;
    private final int y;

    public Cible(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTaille() {
        return taille;
    }

    // Méthode pour vérifier si la cible a été cliquée
    public boolean estCliquee(int clicX, int clicY) {
        int rayon = taille / 2;
        int centreX = x + rayon;
        int centreY = y + rayon;
        return Math.sqrt(Math.pow(clicX - centreX, 2) + Math.pow(clicY - centreY, 2)) <= rayon;

    }
}