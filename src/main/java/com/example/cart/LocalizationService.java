package com.example.cart;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    public Map<String, String> loadStrings(String language) {
        Map<String, String> map = new HashMap<>();

        String sql = "SELECT \"key\", value FROM localization_strings WHERE language = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, language);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                map.put(rs.getString("key"), rs.getString("value"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }
}
