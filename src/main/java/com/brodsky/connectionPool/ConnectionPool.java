package com.brodsky.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private final static int LIMIT = 5;

    private Stack<Connection> connections = new Stack<>();

    private static ConnectionPool instance = new ConnectionPool();

    private Object lockFlag = new Object();

    private static final String connectionString =
            "jdbc:mysql://localhost:3306/john_bryce?" +
                    "allowPublicKeyRetrieval=true&useSSL=false" +
                    "&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        for (int i = 0; i < LIMIT; i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection
                        (connectionString, "root", "danydan1981");
                connections.add(connection);
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (lockFlag) {
            if(connections.isEmpty()) {
                lockFlag.wait();
            }
            Connection connection = connections.pop();
            return connection;
        }
    }

    public void restoreConnection(Connection connection) {
        synchronized (lockFlag) {
            connections.push(connection);
            lockFlag.notify();
        }
    }

    public void closeAllConnections() throws InterruptedException {

        synchronized(lockFlag) {
            while (connections.size() < LIMIT) {
                connections.wait();
            }
            for(Connection c: connections) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.getMessage();
                }
            }
        }
    }
}


