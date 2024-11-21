package org.diluvioModels;

import java.io.Serializable;
import java.sql.Date;

public class Joueur implements Serializable, Comparable<Joueur>{
    private static final long serialVersionUID =-5288532070946724022L;
    public String nom;
    int nb_part;
    int nb_pt;
    double total_accuracy;
    Date dateCreation;
    Date lastSynchro;
    boolean connecte;
    int id;

    Joueur(){//nv joueur
        connecte=false;
        nom="Joueur cool mais anonyme";
        dateCreation=new Date(0);
        lastSynchro=null;
        nb_part=0;
        nb_pt=0;
        total_accuracy=0;
        id=-1;
    }

    Joueur(int id,String nom,int nb_part,int nb_pt,Date dateCreation,double total_acc){//Joueur 100% athentifié
        this.nom=nom;
        this.nb_part=nb_part;
        this.nb_pt=nb_pt;
        this.dateCreation=dateCreation;
        this.connecte=true;
        this.total_accuracy=total_acc;
        lastSynchro=new Date(0);
        this.id=id;
    }
    Joueur(String nom,int nb_part,int nb_pt,double total_acc){ //Joueur pas connecté mais recup du fichier local
        this.nom=nom;
        this.nb_part=nb_part;
        this.nb_pt=nb_pt;
        this.total_accuracy=total_acc;
        this.connecte=false;
        lastSynchro=null;
        this.id=-1;
    }
    public String toString() {
        return this.getClass()+" : "+this.nom+";"+this.nb_part+" parties; créé le : "+this.dateCreation;

    }
    public double getMoyenne(){
        return nb_pt/nb_part;
    }
    public void addPoints(int point) {
        this.nb_pt+=point;
    }
    public void addGames(int nb_game) {
        this.nb_part+=nb_game;
    }
    public void addAccuracy(double nb_accur) {
        this.total_accuracy+=nb_accur;
    }
    public double getAccMoyenne() {
        return total_accuracy/nb_part;
    }

    public String getName() {
        return nom;
    }

    public int getNb_part() {
        return nb_part;
    }

    public int getNb_pt() {
        return nb_pt;
    }

    public double getTotal_accuracy() {
        return total_accuracy;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getLastSynchro() {
        return lastSynchro;
    }

    public boolean isConnecte() {
        return connecte;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Joueur j2) {
        if(this.getMoyenne()>j2.getMoyenne()) {
            return 1;
        }else if(this.nb_pt==j2.nb_pt) {
            if(this.getAccMoyenne()>j2.getAccMoyenne()) {
                return 1;
            }else if(this.getAccMoyenne()==j2.getAccMoyenne()) {
                if(this.nb_part>j2.nb_part) {
                    return 1;
                }else if(nb_part==j2.nb_part) {
                    return this.nom.compareTo(j2.nom);
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
