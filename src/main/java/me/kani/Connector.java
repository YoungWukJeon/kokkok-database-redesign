package me.kani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private Connection connection;

    public Connector(String host, int port, String schema, String user, String password) {
        final var url = String.format(
                "jdbc:mysql://%s:%d/%s?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=UTF-8",
                host, port, schema);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
