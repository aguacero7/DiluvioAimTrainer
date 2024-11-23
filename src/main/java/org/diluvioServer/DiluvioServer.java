package org.diluvioServer;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.diluvioModels.*;

class ConnectedGame implements Runnable {
    private Game game;
    private Player player;
    private final Socket clientSocket;
    private final String key;

    ConnectedGame(Socket clientSocket) {
        this.game = new Game();
        this.clientSocket = clientSocket;
        this.key = genID(16);
    }

    public String genID(int size) {
        byte[] array = new byte[size];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            out.writeObject(game);
            System.out.println("Game démarré pour "+this.player.getName()+"("+this.clientSocket.getInetAddress()+")");

            game = (Game) in.readObject();

            if (game.endedGame) {
                System.out.println("Game fini pour : "+this.player.getName()+" ("+ clientSocket.getRemoteSocketAddress()+")");
                if(this.key.equals(game.getKey())) {
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

public class DiluvioServer {

    public static void main(String[] args) {
        if (args.length >= 1) {
            try (ServerSocket serverSocket = new ServerSocket(1234)) {
                System.out.println("DiluvioServer started and waiting for connections...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected");

                    // Création d'un nouveau game pour chaque client
                    ConnectedGame connectedGame = new ConnectedGame(clientSocket);
                    Thread thread = new Thread(connectedGame);
                    thread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("BAD USAGE - usage : java DiluvioServer {port} \n\n\tExample : java DiluvioServer 3450");
            System.exit(256);
        }
    }
}
