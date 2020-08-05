package com.bridgelabz.jdbc;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class AutoGenerationOfAccNoUsingMysql {

    private static final String INSERT_QUERY = "INSERT INTO BANKACCOUNT(HOLDERNAME,ADDRESS,BALANCE)VALUES(?,?,?)";


    public static void main(String[] args) {
        String filePath = "D:/FellowshipProgram/JDBCExample/src/main" +
                "/resources/DBDetails.properties";
        Scanner scanner = null;
        String name = null, address = null;
        float balance = 0.0f;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            scanner = new Scanner(System.in);
            if (scanner != null) {
                System.out.println("ENTER APPLICANT NAME::");
                name = scanner.nextLine();
                System.out.println("ENTER APPLICANT ADDRESS::");
                address = scanner.nextLine();
                System.out.println("ENTER INITIAL AMOUNT::");
                balance = scanner.nextFloat();
            }

           FileInputStream fileInputStream = new FileInputStream(filePath);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String DB_DRIVER = properties.getProperty("DB_DRIVER_CLASS");
            String DB_URL = properties.getProperty("DB_URL");
            String DB_USER = properties.getProperty("DB_USERNAME");
            String DB_PASSWORD = properties.getProperty("DB_PASSWORD");
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (connection != null)
                preparedStatement = connection.prepareStatement(INSERT_QUERY);
            if (preparedStatement != null) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.setFloat(3, balance);
                result = preparedStatement.executeUpdate();
            }
            if (result == 0)
                System.out.println("record not inserted");
            else
                System.out.println("record inserted");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (scanner != null)
                    scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
