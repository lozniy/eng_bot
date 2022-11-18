package com.eng_bot.springboot.db;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportWordsData extends DataBase{
    private static final String SQL_INSERT = "INSERT INTO words " +
            "(Hash, Topic, Word, Translation, Meaning, Example, Synonym) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public int addRow(
            String Topic,
            String Word,
            String Translation,
            String Meaning,
            String Example,
            String Synonym
    ) {
        int row = 0;
        try {
            String hash = this.getHashString(Topic + Word + Translation + Meaning + Example + Synonym);
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, hash);
            preparedStatement.setString(2, Topic);
            preparedStatement.setString(3, Word);
            preparedStatement.setString(4, Translation);
            preparedStatement.setString(5, Meaning);
            preparedStatement.setString(6, Example);
            preparedStatement.setString(7, Synonym);
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    private String getHashString(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
