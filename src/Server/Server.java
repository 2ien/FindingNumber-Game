package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) {
        // Khởi động server socket để xử lý kết nối cơ sở dữ liệu
        new Thread(new DatabaseServer()).start();

        // Khởi động server socket để xử lý kết nối trò chơi
        new Thread(new GameServer()).start();
    }

}
