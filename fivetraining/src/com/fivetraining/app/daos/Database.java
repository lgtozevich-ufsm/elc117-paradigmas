package com.fivetraining.app.daos;

import java.sql.Connection;

public class Database {
    private Connection connection;

    public Database(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
