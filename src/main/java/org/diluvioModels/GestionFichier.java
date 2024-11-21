package org.diluvioModels;
import javax.crypto.Cipher;


import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class GestionFichier {
    private static final File PATH = new File(System.getProperty("user.home"), "diluvioclicker");
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "!!DiluvioSecret!".getBytes();

    static {
        if (!PATH.exists()) {
            PATH.mkdirs();
        }
    }

    public static Joueur readUser() {
        File userFile = new File(PATH, "user.dat");
        if (userFile.exists()) {
            System.out.println("Lecture du fichier de l'utilisateur");
            try (CipherInputStream cis = new CipherInputStream(
                    new FileInputStream(userFile), getCipher(Cipher.DECRYPT_MODE));
                 ObjectInputStream ois = new ObjectInputStream(cis)) {

                Joueur joueur = (Joueur) ois.readObject();
                System.out.println("Joueur chargé : " + joueur);
                return joueur;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new Joueur();
            }
            catch(Exception e ) {
                return new Joueur();
            }
        } else {
            System.out.println("Le fichier de l'utilisateur n'existe pas encore.");
            writeUser(new Joueur());
            return new Joueur();
        }
    }

    public static void writeUser(Joueur joueur) {
        File userFile = new File(PATH, "user.dat");
        try (CipherOutputStream cos = new CipherOutputStream(
                new FileOutputStream(userFile), getCipher(Cipher.ENCRYPT_MODE));
             ObjectOutputStream oos = new ObjectOutputStream(cos)) {

            oos.writeObject(joueur);
            System.out.println("Joueur sauvegardé : " + joueur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Cipher getCipher(int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(mode, secretKey);
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'initialisation du chiffrement/déchiffrement", e);
        }
    }
}
