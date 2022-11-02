package com.eng_bot.springboot.db;

import java.sql.*;
import java.util.Properties;

abstract class DataBase {
    Connection connection;
    protected Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","123");
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/eng_bot", properties);
    }
}
