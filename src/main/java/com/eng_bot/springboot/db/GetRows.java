package com.eng_bot.springboot.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetRows extends DataBase{
    public ArrayList<String[]> getRowsByTopic(String topic) {
        ArrayList<String[]> rows = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM words WHERE Topic = '" + topic + "'");

            while (rs.next()) {
                String[] rowData = new String[6];
                rowData[0] = rs.getString("Topic");
                rowData[1] = rs.getString("Word");
                rowData[2] = rs.getString("Translation");
                rowData[3] = rs.getString("Meaning");
                rowData[4] = rs.getString("Example");
                rowData[5] = rs.getString("Synonym");
                rows.add(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public ArrayList<String> getTopics() {
        ArrayList<String> topics = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT Topic FROM words");

            while (rs.next()) {
                topics.add(rs.getString("Topic"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }
}
