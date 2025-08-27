/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {
    private static final int GAME_PORT = 12346;

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(GAME_PORT)) {
            System.out.println("Game server is listening on port " + GAME_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New game client connected");

                new GameClientHandler(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }
    }

class GameClientHandler extends Thread {
    private Socket socket;

    public GameClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true)) {

                String request;
                while ((request = reader.readLine()) != null) {
                    System.out.println("Received game request: " + request);

                    // Handle game request here
                    writer.println("GAME_RESPONSE");
                } 
            }
        catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
}

