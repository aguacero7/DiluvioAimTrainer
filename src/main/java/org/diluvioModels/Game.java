package org.diluvioModels;

import org.diluvioClient.DiluvioClient;
import org.diluvioClient.Target;
import org.diluvioClient.Vue.VueGame;

public class Game implements Runnable {
    public boolean endedGame;
    Target activeGame;
    int targetCount;
    long chronoStart;
    long currentChrono;
    int points;
    int clickedTargets;
    int chronoMax;
    int clickCount;
    String key;
    DiluvioClient menuref;
    VueGame vueGame;

    public Game(DiluvioClient m) {
        this.key = "";
        this.points = 0;
        this.clickedTargets = 0;
        this.chronoMax = 20000; // 20 seconds
        this.clickCount = 0;
        this.chronoStart = System.currentTimeMillis();
        this.endedGame = false;
        this.vueGame = m.getVueGame();
        this.menuref = m;
        Thread thread = new Thread(this);
        thread.start();
    }

    public Game() {
        this.key = "";
        this.points = 0;
        this.clickedTargets = 0;
        this.chronoMax = 20000; // 20 seconds
        this.clickCount = 0;
        this.chronoStart = System.currentTimeMillis();
        this.endedGame = false;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void clickTarget(int x, int y) {
        if (!endedGame) {
            points += 10;
            clickedTargets++;
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isTimeFinished() {
        long tempsEcoule = System.currentTimeMillis() - chronoStart;
        return tempsEcoule >= chronoMax;
    }

    /*              Getters             */
    public Target getActiveGame() {
        return activeGame;
    }

    /*              Setters             */
    public void setActiveGame(Target activeGame) {
        this.activeGame = activeGame;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }

    public long getChronoStart() {
        return chronoStart;
    }

    public long getCurrentChrono() {
        return currentChrono;
    }

    public void setCurrentChrono(long currentChrono) {
        this.currentChrono = currentChrono;
    }

    public int getClickedTargets() {
        return clickedTargets;
    }

    public void setClickedTargets(int clickedTargets) {
        this.clickedTargets = clickedTargets;
    }

    public int getChronoMax() {
        return chronoMax;
    }

    public void setChronoMax(int chronoMax) {
        this.chronoMax = chronoMax;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEndedGame() {
        return endedGame;
    }

    public void setEndedGame(boolean endedGame) {
        this.endedGame = endedGame;
    }

    public DiluvioClient getMenuref() {
        return menuref;
    }

    public void setMenuref(DiluvioClient menuref) {
        this.menuref = menuref;
    }

    @Override
    public void run() {
        if (menuref == null) {
            while (!endedGame) {
                if (isTimeFinished()) {
                    endedGame = true;

                }
                if (this.vueGame != null) {
                    vueGame.updateInfoLabels();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } else {
            while (!endedGame) {
                if (isTimeFinished()) {
                    endedGame = true;
                    menuref.gameOver();
                }
                if (this.vueGame != null) {
                    vueGame.updateInfoLabels();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void setVue(VueGame vueGame) {
        this.vueGame = vueGame;
    }
}