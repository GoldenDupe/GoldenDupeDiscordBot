package xyz.goldendupe.discord.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnector {
    private final String url;
    private Connection connection;

    public DriverConnector(String url) {
        this.url = url;
        connection = null;
    }

    public boolean isConnected(){
        try {
            if (connection == null || connection.isClosed()){
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean connect(){
        try {
            connection = DriverManager.getConnection(url);
            if (connection != null){
                System.out.println("Connected database with driver! Driver: "+connection.getMetaData().getDriverName()+":"+connection.getMetaData().getDriverVersion());
                System.out.println("New database connection created!");
            }
            return connection != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
