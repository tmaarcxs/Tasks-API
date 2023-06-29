package com.marcos.API.Model;

public class FactoryConnection {
    public static Motor getConnection(String motor) {
        switch (motor) {
            case "postgresql":
                return new Motor("org.postgresql.Driver", "root", "root", "jdbc:postgresql://192.168.1.49:5432/", "tasks");
            default:
                return null;
        }
    }
}
