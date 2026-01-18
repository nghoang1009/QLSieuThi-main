package com.mycompany.qlst.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/QLSieuThi";
<<<<<<< HEAD:src/main/java/com/mycompany/qlst/Helpers/DatabaseConnector.java
    private static final String USER = "alcen";
    private static final String PASSWORD = "alcenium"; // Thay password của bạn
=======
    private static final String USER = "root";
    private static final String PASSWORD = "";
>>>>>>> 35d5952d9752823841b6046a6e23e426e771a534:src/main/java/com/mycompany/qlst/database/DatabaseConnection.java
    
    private static Connection connection = null;

    // Lấy kết nối
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối database!");
            e.printStackTrace();
        }
        return connection;
    }

    // Đóng kết nối
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}