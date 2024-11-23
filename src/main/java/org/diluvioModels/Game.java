package org.diluvioModels;

import org.diluvioClient.DiluvioClient;
import org.diluvioClient.Target;

public class Game implements Runnable{
    Target activeGame;
    int targetCount;
    long chronoStart;
    long currentChrono;
    int points;
    int clickedTargets;
    int chronoMax;
    int clickCount;
    String key;
    public boolean endedGame;
    DiluvioClient menuref;

    public Game(DiluvioClient m) {
        this.key="";
        this.points = 0;
        this.clickedTargets = 0;
        this.chronoMax = 10000; // 60 seconds
        this.clickCount =0;
        this.chronoStart = System.currentTimeMillis();
        this.endedGame = false;
        this.menuref=m;
        Thread thread = new Thread(this);
        thread.start();
    }
    public Game() {
        this.key="";
        this.points = 0;
        this.clickedTargets = 0;
        this.chronoMax = 10000; // 60 secondes
        this.clickCount =0;
        this.chronoStart = System.currentTimeMillis();
        this.endedGame = false;
        Thread thread = new Thread(this);
        thread.start();
    }
    public void setKey(String key) {
        this.key=key;
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

    public boolean isTimeFinished() {
        long tempsEcoule = System.currentTimeMillis() - chronoStart;
        return tempsEcoule >= chronoMax;
    }


    /*              Setters             */
    public void setActiveGame(Target activeGame) {
        this.activeGame = activeGame;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }

    public void setCurrentChrono(long currentChrono) {
        this.currentChrono = currentChrono;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setClickedTargets(int clickedTargets) {
        this.clickedTargets = clickedTargets;
    }

    public void setChronoMax(int chronoMax) {
        this.chronoMax = chronoMax;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void setEndedGame(boolean endedGame) {
        this.endedGame = endedGame;
    }

    public void setMenuref(DiluvioClient menuref) {
        this.menuref = menuref;
    }


    /*              Getters             */
    public Target getActiveGame() {
        return activeGame;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public long getChronoStart() {
        return chronoStart;
    }

    public long getCurrentChrono() {
        return currentChrono;
    }

    public int getClickedTargets() {
        return clickedTargets;
    }

    public int getChronoMax() {
        return chronoMax;
    }

    public int getClickCount() {
        return clickCount;
    }

    public String getKey() {
        return key;
    }

    public boolean isEndedGame() {
        return endedGame;
    }

    public DiluvioClient getMenuref() {
        return menuref;
    }

    @Override
    public void run() {
        if(menuref==null) {
            while (!endedGame) {
                if (isTimeFinished()) {
                    endedGame = true;

                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }else {
            while (!endedGame) {
                if (isTimeFinished()) {
                    endedGame = true;
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