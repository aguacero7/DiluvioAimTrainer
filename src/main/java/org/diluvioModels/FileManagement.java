package org.diluvioModels;
import org.diluvioClient.DiluvioClient;

import javax.crypto.Cipher;


import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class FileManagement {
    private static final File PATH = new File(System.getProperty("user.home"), "diluvioclicker");
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "!!DiluvioSecret!".getBytes();

    static {
        if (!PATH.exists()) {
            PATH.mkdirs();
        }
    }

    public static Player readUser() {
        File userFile = new File(PATH, "user.dat");
        if (userFile.exists()) {
            System.out.println("Reading the player saved file");
            try (CipherInputStream cis = new CipherInputStream(
                    new FileInputStream(userFile), getCipher(Cipher.DECRYPT_MODE));
                 ObjectInputStream ois = new ObjectInputStream(cis)) {

                Player player = (Player) ois.readObject();
                System.out.println("Player loaded : " + player);
                return player;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return createDefaultPlayer();
            }
            catch(Exception e ) {
                return createDefaultPlayer();
            }
        } else {
            System.out.println("The user.dat file doesn't already exist");
            writeUser(new Player());
            return createDefaultPlayer();
        }
    }
    public static void deleteUser() {
        File userFile = new File(PATH, "user.dat");
        if (userFile.exists() && userFile.delete()) {
            System.out.println("User file deleted successfully.");
        } else {
            System.err.println("Error: Unable to delete user file or file does not exist.");
        }
    }
    public static void writeUser(Player player) {
        File userFile = new File(PATH, "user.dat");
        try (CipherOutputStream cos = new CipherOutputStream(
                new FileOutputStream(userFile), getCipher(Cipher.ENCRYPT_MODE));
             ObjectOutputStream oos = new ObjectOutputStream(cos)) {

            oos.writeObject(player);
            System.out.println("Player saved : " + player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Player createDefaultPlayer() {
        Player defaultPlayer = new Player();
        writeUser(defaultPlayer);
        return defaultPlayer;
    }
    private static LocalSettings createDefaultConf() {
        LocalSettings localSettings = new LocalSettings(DiluvioClient.VERSION);
        writeConf(localSettings);
        return localSettings;
    }
    public static void deleteConf() {
        File confFile = new File(PATH, "conf.dat");
        if (confFile.exists() && confFile.delete()) {
            System.out.println("Configuration file deleted successfully.");
        } else {
            System.err.println("Error: Unable to delete configuration file or file does not exist.");
        }
    }
    public static void writeConf(LocalSettings conf) {
        File confFile = new File(PATH, "conf.dat");
        try (CipherOutputStream cos = new CipherOutputStream(
                new FileOutputStream(confFile), getCipher(Cipher.ENCRYPT_MODE));
             ObjectOutputStream oos = new ObjectOutputStream(cos)) {

            oos.writeObject(conf);
            System.out.println("Conf saved : " + conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static LocalSettings readConf() {
        File confFile = new File(PATH, "conf.dat");
        if (confFile.exists()) {
            System.out.println("Reading the configuration saved File");
            try (CipherInputStream cis = new CipherInputStream(
                    new FileInputStream(confFile), getCipher(Cipher.DECRYPT_MODE));
                 ObjectInputStream ois = new ObjectInputStream(cis)) {

                LocalSettings conf = (LocalSettings) ois.readObject();
                System.out.println("Configuraion loaded : " + conf);
                return conf;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return createDefaultConf();
            }
            catch(Exception e ) {
                return createDefaultConf();
            }
        } else {
            System.out.println("The user.dat file doesn't already exist");
            writeUser(new Player());
            return createDefaultConf();
        }
    }
    private static Cipher getCipher(int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(mode, secretKey);
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException("Error while initializing the encryption/decryption", e);
        }
    }

}
