package com.bridgelabz.jdbc;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class JdbcSimpleStatementDemo {

    public static void main(String[] args) {
        String filePath = "D:/FellowshipProgram/JDBCExample/src/main" +
                "/resources/DBDetails.properties";
        Scanner scanner;
        int accountNumber = 0;
        Connection connection = null;
        Statement statement = null;
        String query = null;
        ResultSet resultSet = null;
        Boolean flag = false;


        scanner = new Scanner(System.in);
        try {
            if (scanner != null) {
                System.out.println("Enter the account number::");
                accountNumber = scanner.nextInt();
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
                statement = connection.createStatement();
            query = "SELECT * FROM BANKACCOUNT WHERE ACCOUNTNUMBER=" + accountNumber;
            System.out.println(query);
            if (statement != null)
                resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                while (resultSet.next()) {
                    flag = true;
                    System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3) + "  " + resultSet.getInt(4));
                }
                if (flag == true)
                    System.out.println("record is found and display");
                else
                    System.out.println("record is not found to display");
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (statement != null)
                    statement.close();
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
