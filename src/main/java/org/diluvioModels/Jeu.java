package org.diluvioModels;

import org.diluvioClient.Cible;
import org.diluvioClient.Menu;

public class Jeu implements Runnable{
    Cible cibleActive;
    int nbCible;
    long chronoDebut;
    long chronoActuel;
    int points;
    int cibleCliquees;
    int chronoMax;
    int nbClics;
    String key;
    public boolean jeuTermine;
    Menu menuref;
    public Jeu(Menu m) {
        this.key="";
        this.points = 0;
        this.cibleCliquees = 0;
        this.chronoMax = 10000; // 60 secondes
        this.nbClics=0;
        this.chronoDebut = System.currentTimeMillis();
        this.jeuTermine = false;
        this.menuref=m;
        Thread thread = new Thread(this);
        thread.start();
    }
    public Jeu() {
        this.key="";
        this.points = 0;
        this.cibleCliquees = 0;
        this.chronoMax = 10000; // 60 secondes
        this.nbClics=0;
        this.chronoDebut = System.currentTimeMillis();
        this.jeuTermine = false;
        Thread thread = new Thread(this);
        thread.start();
    }
    public void setKey(String cle) {
        this.key=cle;
    }
    public void clicCible(int x, int y) {
        if (!jeuTermine) {
            points += 10;
            cibleCliquees++;
        }
    }

    public int getPoints() {
        return points;
    }

    public boolean estTempsEcoule() {
        long tempsEcoule = System.currentTimeMillis() - chronoDebut;
        return tempsEcoule >= chronoMax;
    }


    /*              Setters             */
    public void setCibleActive(Cible cibleActive) {
        this.cibleActive = cibleActive;
    }

    public void setNbCible(int nbCible) {
        this.nbCible = nbCible;
    }

    public void setChronoActuel(long chronoActuel) {
        this.chronoActuel = chronoActuel;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCibleCliquees(int cibleCliquees) {
        this.cibleCliquees = cibleCliquees;
    }

    public void setChronoMax(int chronoMax) {
        this.chronoMax = chronoMax;
    }

    public void setNbClics(int nbClics) {
        this.nbClics = nbClics;
    }

    public void setJeuTermine(boolean jeuTermine) {
        this.jeuTermine = jeuTermine;
    }

    public void setMenuref(Menu menuref) {
        this.menuref = menuref;
    }


    /*              Getters             */
    public Cible getCibleActive() {
        return cibleActive;
    }

    public int getNbCible() {
        return nbCible;
    }

    public long getChronoDebut() {
        return chronoDebut;
    }

    public long getChronoActuel() {
        return chronoActuel;
    }

    public int getCibleCliquees() {
        return cibleCliquees;
    }

    public int getChronoMax() {
        return chronoMax;
    }

    public int getNbClics() {
        return nbClics;
    }

    public String getKey() {
        return key;
    }

    public boolean isJeuTermine() {
        return jeuTermine;
    }

    public Menu getMenuref() {
        return menuref;
    }

    @Override
    public void run() {
        if(menuref==null) {
            while (!jeuTermine) {
                if (estTempsEcoule()) {
                    jeuTermine = true;

                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }else {
            while (!jeuTermine) {
                if (estTempsEcoule()) {
                    jeuTermine = true;
                    menuref.gameOver();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}