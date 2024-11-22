package org.diluvioModels;

import java.util.HashMap;
import java.util.Map;

public class LanguagesTranslations {
    private final Map<String, Map<String, String>> translations = new HashMap<>();
    private String currentLanguage = "fr"; // default language

    public LanguagesTranslations() {
        loadTranslations();
    }

    private void loadTranslations() {
        // French
        Map<String, String> fr = new HashMap<>();
        fr.put("player_profile", "Profil du Joueur");
        fr.put("name", "Nom");
        fr.put("game_count", "Nombre de parties");
        fr.put("average", "Moyenne");
        fr.put("accuracy_mean", "Moyenne de Précision");
        fr.put("creation_date", "Date de création");
        fr.put("status", "Statut");
        fr.put("connected", "Connecté");
        fr.put("disconnected", "Non connecté");
        fr.put("home_button", "Retour au menu principal");
        fr.put("start", "Commencer");
        fr.put("welcome", "Bienvenue dans \n Diluvio Aim Trainer !");
        fr.put("play", "Jouer");
        fr.put("see_profile", "Voir le profil");
        fr.put("login", "Se connecter");
        fr.put("logout", "Se déconnecter");
        fr.put("precision", "Precision");
        fr.put("points", "Points");
        fr.put("settings", "Paramètres");

        translations.put("fr", fr);

        // English
        Map<String, String> en = new HashMap<>();
        en.put("player_profile", "Player Profile");
        en.put("name", "Name");
        en.put("game_count", "Games Count");
        en.put("average", "Average");
        en.put("accuracy_mean", "Accuracy Average");
        en.put("creation_date", "Creation Date");
        en.put("status", "Status");
        en.put("connected", "Connected");
        en.put("disconnected", "Disconnected");
        en.put("home_button", "Return to Main Menu");
        en.put("start", "Start");
        en.put("welcome", "Welcome to \n Diluvio Aim Trainer !");
        en.put("play", "Play");
        en.put("see_profile", "Access Profile");
        en.put("login", "Log in");
        en.put("logout", "Log out");
        en.put("precision", "Precision");
        en.put("points", "Points");
        en.put("settings", "Settings");

        translations.put("en", en);
    }

    public void setLanguage(String languageCode) {
        if (translations.containsKey(languageCode)) {
            this.currentLanguage = languageCode;
        } else {
            throw new IllegalArgumentException("Language not supported " + languageCode);
        }
    }

    public String translate(String key) {
        return translations.getOrDefault(currentLanguage, translations.get("en")).getOrDefault(key, key);
    }
}
