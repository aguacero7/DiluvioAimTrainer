package org.diluvioClient;

//Target model
public class Target {
    public static int size = 80; // default diameter
    private final int x;
    private final int y;

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    // Method to check if the click point is in the target range
    public boolean isClicked(int clicX, int clicY) {
        int rayon = size / 2;
        int centreX = x + rayon;
        int centreY = y + rayon;
        return Math.sqrt(Math.pow(clicX - centreX, 2) + Math.pow(clicY - centreY, 2)) <= rayon;

    }
}