package org.diluvioModels;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class LocalSettings implements Serializable {

    private Date lastAuth;
    private Dimension fenetreDimension;
    private String theme;
    private String savedVersion;
    private File userConfPath;
    private String language;

    @Override
    public String toString() {
        return "LocalSettings{" +
                "lastAuth=" + lastAuth +
                ", fenetreDimension=" + fenetreDimension +
                ", theme='" + theme + '\'' +
                ", savedVersion='" + savedVersion + '\'' +
                ", userConfPath=" + userConfPath +
                ", language='" + language + '\'' +
                '}';
    }

    public void setLastAuth(Date lastAuth) {
        this.lastAuth = lastAuth;
    }

    public void setFenetreDimension(Dimension fenetreDimension) {
        this.fenetreDimension = fenetreDimension;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setSavedVersion(String savedVersion) {
        this.savedVersion = savedVersion;
    }

    public void setUserConfPath(File userConfPath) {
        this.userConfPath = userConfPath;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getLastAuth() {
        return lastAuth;
    }

    public Dimension getFenetreDimension() {
        return fenetreDimension;
    }

    public String getTheme() {
        return theme;
    }

    public String getSavedVersion() {
        return savedVersion;
    }

    public File getUserConfPath() {
        return userConfPath;
    }

    public String getLanguage() {
        return language;
    }

    // Constructor
    LocalSettings(String currentVersion){
        this.lastAuth=null;
        this.fenetreDimension=new Dimension(1280,720);
        this.theme="Dark";
        this.savedVersion=currentVersion;
        this.userConfPath =  new File(System.getProperty("user.home"), "diluvioclicker");

        this.language="fr";
    }
    public LocalSettings(String savedVersion, String language, String theme, Dimension fenetreDimension) {
        this.lastAuth = new Date();
        this.fenetreDimension = fenetreDimension;
        this.theme = theme;
        this.savedVersion = savedVersion;
        this.userConfPath = new File(System.getProperty("user.home"), "diluvioclicker");
        this.language = language;
    }
}
