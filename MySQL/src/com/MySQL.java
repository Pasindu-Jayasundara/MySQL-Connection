package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private static Connection connection;

    private static String ip;
    private static String port;
    private static String database;
    private static String userName;
    private static String password;

    public static void createConnection(String ip, String port, String database, String userName, String password) throws Exception {

        MySQL.ip = ip;
        MySQL.port = port;
        MySQL.database = database;
        MySQL.userName = userName;
        MySQL.password = password;

        if (MySQL.ip == null) {
            throw new NewException("Missing Database Ip Address");
        }
        if (MySQL.port == null) {
            throw new NewException("Missing Database Port Number");
        }
        if (MySQL.database == null) {
            throw new NewException("Missing Database Name");
        }
        if (MySQL.userName == null) {
            throw new NewException("Missing Database UserName");
        }
        if (MySQL.password == null) {
            throw new NewException("Missing Database Password");
        }

        if (MySQL.connection == null || MySQL.connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, userName, password);
            } catch (ClassNotFoundException | SQLException e) {
                throw new NewException("Failed to create connection", e);
            }
        }
    }

    public static ResultSet executeSearch(String query, Object... params) throws Exception {
        if (MySQL.connection == null || MySQL.connection.isClosed()) {
            throw new NewException("Create the connection first");
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new NewException("Error executing search query", e);
        } finally {
            close(resultSet, preparedStatement);
        }
    }

    public static Integer executeIUD(String query, Object... params) throws Exception {
        if (MySQL.connection == null || MySQL.connection.isClosed()) {
            throw new NewException("Create the connection first");
        }
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            setParameters(preparedStatement, params);
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            return null;
        } catch (SQLException e) {
            throw new NewException("Error executing IUD query", e);
        } finally {
            close(generatedKeys, preparedStatement);
        }
    }

    private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }

    private static void close(ResultSet resultSet, PreparedStatement preparedStatement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // Log or handle exception
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // Log or handle exception
            }
        }
    }
}
