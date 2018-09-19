package com.sunny.maven;

import java.sql.*;

/**
 * Hello world!
 */
public class JDBCDemo {

    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement psm = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8"
                    , "root", "123");
            String sql = "select * from user where username = ?";
            psm = connection.prepareStatement(sql);
            psm.setString(1, "王五");
            resultSet = psm.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + " : "
                        + resultSet.getString("username"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psm != null) {
                try {
                    psm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
