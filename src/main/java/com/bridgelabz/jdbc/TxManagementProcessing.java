package com.bridgelabz.jdbc;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class TxManagementProcessing {

    public static void main(String[] args) {
        String filePath = "D:/FellowshipProgram/JDBCExample/src/main" +
                "/resources/DBDetails.properties";
        Scanner scanner;
        int srcAccNo = 0, destAccNo = 0;
        float ammount = 0.0f;
        Connection connection = null;
        Statement statement = null;
        int result[] = null;
        boolean flag = false;
        try {
            scanner = new Scanner(System.in);
            if (scanner != null) {

                System.out.println("ENTER SOURCE ACCOUNT NUMBER::");
                srcAccNo = scanner.nextInt();
                System.out.println("ENTER DESTINATION ACCOUNT NUMBER::");
                destAccNo = scanner.nextInt();
                System.out.println("ENTER AMMOUNT TO BE TRANSFER::");
                ammount = scanner.nextFloat();
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
                connection.setAutoCommit(false);

            if (connection != null)
                statement = connection.createStatement();

            if (statement != null) {
                statement.addBatch("UPDATE BANKACCOUNT SET BALANCE=BALANCE-" + ammount +
                        "WHERE ACCOUNTNUMBER=" + srcAccNo);
                statement.addBatch("UPDATE BANKACCOUNT SET BALANCE=BALANCE+" + ammount +
                        "WHERE ACCOUNTNUMBER=" + destAccNo);
            }

            if (statement != null)
                result = statement.executeBatch();


            for (int value : result) {
                if (value == 0)
                    flag = true;
                break;
            }
            if (flag) {
                connection.rollback();
                System.out.println("Transaction is rollback -- money not transferred");
            } else {
                connection.commit();
                System.out.println("Transaction committed -- money transferred");
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
        }
    }
}
