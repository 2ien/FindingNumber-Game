package Server;


import findingnumber.Register;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DatabaseServer implements Runnable {
    private static final int DATABASE_PORT = 12345;
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/test";
    static final String DATABASE_USER = "root";
    static final String DATABASE_PASSWORD = "";
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(DATABASE_PORT)) {
            System.out.println("Database server is listening on port " + DATABASE_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New database client connected");

                new DatabaseClientHandler(socket).start();
            }
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class DatabaseClientHandler extends Thread {
    private Socket socket;
    private Connection connection;
    public DatabaseClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.connection = DriverManager.getConnection(
                DatabaseServer.DATABASE_URL,
                DatabaseServer.DATABASE_USER,
                DatabaseServer.DATABASE_PASSWORD
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println("Received request: " + request);
                
                switch (request) {
                    case "LOGIN":
                        handleLogin(reader, writer);
                        break;
                    case "REGISTER":
                        handleRegister(reader, writer);
                        break;
                    case "CHECK_USERNAME":
                        handleCheckUsername(reader, writer);
                        break;
                    case "CHECK_CONNECTION":
                        writer.println("CONNECTION_OK");
                        break;
                    default:
                        System.out.println("Unknown request: " + request);
                        break;
                    }
                }
            } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
    private void handleLogin(BufferedReader reader, PrintWriter writer) throws IOException {
        PreparedStatement st = null;
        ResultSet rs = null; 
        
        String username = reader.readLine();
        String password = reader.readLine();
        
        String query = "SELECT * FROM `users` WHERE `username` = ? AND `password` = ?";  
        
        try {
            st = connection.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            
            rs = st.executeQuery();

            if( rs.next()){
                writer.println("LOGIN_SUCCESS");
            } // Return true if a user with the provided credentials is found
            else{
                writer.println("LOGIN_FAIL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            writer.println("ERROR");
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
      
    }

    private void handleRegister(BufferedReader reader, PrintWriter writer) throws IOException {
        String username = reader.readLine();
        String password = reader.readLine();
        
       
        boolean registered = register(username, password);
        writer.println(registered ? "REGISTER_SUCCESS" : "REGISTER_FAIL");
    }



    private boolean register(String username, String password) {
        // Replace with actual registration logic
        // For simplicity, always return true here
        return true;
    }

    private void handleCheckUsername(BufferedReader reader, PrintWriter writer) throws IOException {
        PreparedStatement st;
        ResultSet rs;
        
        boolean username_exist = false;
        String username = reader.readLine();
        String query = "SELECT * FROM `users` WHERE `username` = ?";
        
        try {
            st = connection.prepareStatement(query);
            st.setString(1, username);
            rs = st.executeQuery();
            if(rs.next()){
                writer.println("CHECK_SUCCESS");              
            }
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

