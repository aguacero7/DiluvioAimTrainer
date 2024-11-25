package org.diluvioModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguagesTranslations {
    private final Map<String, Map<String, String>> translations = new HashMap<>();
    private String currentLanguage = "fr"; // default language
    public static final ArrayList<String> acceptedLanguages=new ArrayList<String>(List.of(
            new String[]{
                    "en",
                    "fr",
                    "es"
            }));
    public LanguagesTranslations() {
        loadTranslations();
    }
    public String getCurrentLanguage(){
        return this.currentLanguage;
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
        fr.put("save_apply", "Sauvegarder & Appliquer");
        fr.put("resolution", "Résolution");
        fr.put("theme", "Thème");
        fr.put("theme.light", "Clair");
        fr.put("theme.dark", "Sombre");
        fr.put("language", "Langue");
        fr.put("fr", "Français");
        fr.put("en", "Anglais");
        fr.put("es", "Espagnol");
        fr.put("remaining_time","Temps restant");

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
        en.put("home_button", "Return to main menu");
        en.put("start", "Start");
        en.put("welcome", "Welcome to \n Diluvio Aim Trainer !");
        en.put("play", "Play");
        en.put("see_profile", "Access Profile");
        en.put("login", "Log in");
        en.put("logout", "Log out");
        en.put("precision", "Precision");
        en.put("points", "Points");
        en.put("settings", "Settings");
        en.put("save_apply", "Save & Apply");
        en.put("resolution", "Resolution");
        en.put("theme", "Theme");
        en.put("language", "Language");
        en.put("theme.light", "Light");
        en.put("theme.dark", "Dark");
        en.put("fr", "French");
        en.put("en", "English");
        en.put("es", "Spanish");
        en.put("remaining_time","Remaining Time");
        translations.put("en", en);

        //Spanish
        Map<String, String> es = new HashMap<>();
        es.put("player_profile", "Perfil del Jugador");
        es.put("name", "Nombre");
        es.put("game_count", "Número de partidas");
        es.put("average", "Promedio");
        es.put("accuracy_mean", "Promedio de Precisión");
        es.put("creation_date", "Fecha de creación");
        es.put("status", "Estado");
        es.put("connected", "Conectado");
        es.put("disconnected", "Desconectado");
        es.put("home_button", "Volver al menú principal");
        es.put("start", "Comenzar");
        es.put("welcome", "¡Bienvenido a \n Diluvio Aim Trainer!");
        es.put("play", "Jugar");
        es.put("see_profile", "Ver perfil");
        es.put("login", "Iniciar sesión");
        es.put("logout", "Cerrar sesión");
        es.put("precision", "Precisión");
        es.put("points", "Puntos");
        es.put("settings", "Configuración");
        es.put("save_apply", "Guardar y Aplicar");
        es.put("resolution", "Resolución");
        es.put("theme", "Tema");
        es.put("theme.light", "Claro");
        es.put("theme.dark", "Oscuro");
        es.put("language", "Idioma");
        es.put("fr", "Francés");
        es.put("en", "Inglés");
        es.put("es", "Español");
        es.put("remaining_time","Tiempo quedando");
        translations.put("es", es);

    }

    public void setLanguage(String languageCode) {
        if (translations.containsKey(languageCode)) {
            this.currentLanguage = languageCode;
        } else {
            throw new IllegalArgumentException("Language not supported " + languageCode);
        }
    }
    public String reverseLookup(String value) {
        Map<String, String> currentTranslations = translations.getOrDefault(currentLanguage, translations.get("en"));
        for (Map.Entry<String, String> entry : currentTranslations.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public String reverseLookup(String value,String language) {
        if(!LanguagesTranslations.acceptedLanguages.contains(language)){
            System.out.println("Language {"+language+"} not recognized by the LanguagesTranslations system.");
            return null;
        }
        Map<String, String> currentTranslations = translations.getOrDefault(language, translations.get("en"));
        for (Map.Entry<String, String> entry : currentTranslations.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public String translate(String key) {
        return translations.getOrDefault(currentLanguage, translations.get("en")).getOrDefault(key, key);
    }
}
