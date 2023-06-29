package com.marcos.API.Model;

import java.sql.*;

public class Motor {

    private final String user;
    private final String driver;
    private final String pass;
    private final String DBNAME;
    private final String url;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Motor(String driver, String user, String pass, String url, String DBNAME) {
        this.driver = driver;
        this.user = user;
        this.pass = pass;
        this.url = url;
        this.DBNAME = DBNAME;
    }

    public String getUser() {
        return user;
    }

    public String getDriver() {
        return driver;
    }

    public String getPass() {
        return pass;
    }

    public String getDBNAME() {
        return DBNAME;
    }

    public String getUrl() {
        return url;
    }


    public void connect() {
        try {
            Class.forName(this.getDriver());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el puente JDBC.");
            return;
        }
        try {
            connection = DriverManager.getConnection(this.getUrl() + this.getDBNAME(),
                    this.getUser(),
                    this.getPass());
            statement = connection.createStatement();
            //this.preparedStatement = connection.prepareStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public ResultSet executeQuery(String SQL) {

        try {
            resultSet = statement.executeQuery(SQL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public int executeUpdate(String SQL) {
        int result = 0;
        try {
            result = statement.executeUpdate(SQL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void disconnect() {

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
