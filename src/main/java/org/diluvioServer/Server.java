package org.diluvioServer;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Random;
import java.io.*;
import java.net.*;
import org.diluvioModels.*;

class ConnectedGame implements Runnable {
    private Jeu jeu;
    private Joueur joueur;
    private Socket clientSocket;
    private String key;

    ConnectedGame(Socket clientSocket) {
        this.jeu = new Jeu();
        this.clientSocket = clientSocket;
        this.key = genID(16);
    }

    public String genID(int size) {
        byte[] array = new byte[size];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            out.writeObject(jeu);
            System.out.println("Jeu démarré pour "+this.joueur.getName()+"("+this.clientSocket.getInetAddress()+")");

            jeu = (Jeu) in.readObject();

            if (jeu.jeuTermine) {
                System.out.println("Jeu fini pour : "+this.joueur.getName()+" ("+ clientSocket.getRemoteSocketAddress()+")");
                if(this.key.equals(jeu.getKey())) {
                    //ecrire la partie dans la bdd
                }else {
                    //repondre pas OK tricheur
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server started and waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                // Création d'un nouveau jeu pour chaque client
                ConnectedGame connectedGame = new ConnectedGame(clientSocket);
                Thread thread = new Thread(connectedGame);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
