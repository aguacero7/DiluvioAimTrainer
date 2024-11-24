package org.diluvioModels;

import java.io.Serializable;
import java.util.Date;

public class Player implements Serializable, Comparable<Player>{
    private static final long serialVersionUID =-5288532070946724022L;
    public String name;
    int gameCount;
    int pointCount;
    double total_accuracy;
    Date dateOfCreation;
    Date lastSynchro;
    boolean playerAuthenticated;
    int id;

    Player(){
        //New Player from scratch
        playerAuthenticated =false;
        name ="Anonymous";
        dateOfCreation =new Date();
        lastSynchro=null;
        gameCount =0;
        pointCount =0;
        total_accuracy=0;
        id=-1;
    }

    Player(int id, String name, int gameCount, int pointCount, Date dateOfCreation, double total_acc){
        //Authenticated player
        this.name = name;
        this.gameCount = gameCount;
        this.pointCount = pointCount;
        this.dateOfCreation = dateOfCreation;
        this.playerAuthenticated =true;
        this.total_accuracy=total_acc;
        lastSynchro=new Date();
        this.id=id;
    }
    Player(String name, int gameCount, int pointCount, double total_acc){
        //Player recovered from local file
        this.name = name;
        this.gameCount = gameCount;
        this.pointCount = pointCount;
        this.total_accuracy=total_acc;
        this.playerAuthenticated =false;
        lastSynchro=null;
        this.id=-1;
    }
    public String toString() {
        return this.getClass()+" : "+this.name +";"+this.gameCount +" games ; created on  : "+this.dateOfCreation;

    }
    public double getPointsAverage(){
        return pointCount / gameCount;
    }
    public void addPoints(int point) {
        this.pointCount +=point;
    }
    public void addGames(int nb_game) {
        this.gameCount +=nb_game;
    }
    public void addAccuracy(double nb_accur) {
        this.total_accuracy+=nb_accur;
    }
    public double getAccuracyAverage() {
        return total_accuracy/ gameCount;
    }

    public String getName() {
        return name;
    }

    public int getGameCount() {
        return gameCount;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Date getLastSynchro() {
        return lastSynchro;
    }

    public boolean isPlayerAuthenticated() {
        return playerAuthenticated;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Player j2) {
        if(this.getPointsAverage()>j2.getPointsAverage()) {
            return 1;
        }else if(this.pointCount ==j2.pointCount) {
            if(this.getAccuracyAverage()>j2.getAccuracyAverage()) {
                return 1;
            }else if(this.getAccuracyAverage()==j2.getAccuracyAverage()) {
                if(this.gameCount >j2.gameCount) {
                    return 1;
                }else if(gameCount ==j2.gameCount) {
                    return this.name.compareTo(j2.name);
                }else {
                    return -1;
                }
            }else {
                return -1;
            }
        }else {
            return -1;
        }
    }

}
