package com.fincore.app.data.db.util;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBUtility {
    public static boolean isEntityExists(String url, String tablename, String field, String entity) {
        String sql = String.format(
                "SELECT EXISTS(SELECT 1 FROM %s WHERE %s=? LIMIT 1)",
                tablename,
                field
        );
        ResultSet res;
        try (Connection conn = DriverManager.getConnection(url)) {
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, entity);
            res = pstmt.executeQuery();
            return res.getBoolean(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> getFirstRow(
            String url,
            String tablename,
            String field,
            String value
    ) {
        ResultSetMetaData metaData;
        ResultSet rs;
        String sql = String.format(
                "SELECT * FROM %s WHERE %s=? LIMIT 1",
                tablename,
                field
        );
        Map<String, Object> response = new HashMap<>();
        try (
                Connection conn = DriverManager.getConnection(url)
                ) {
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);
            rs = pstmt.executeQuery();
            metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                response.put(metaData.getColumnName(i), rs.getObject(i));
            }
            return response;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
