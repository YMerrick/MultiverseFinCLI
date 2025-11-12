package com.fincore.app.data.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtility {
    public static boolean isEntityExists(String url, String tablename, String entity) {
        String sql = String.format(
                "SELECT EXISTS(SELECT 1 FROM %s WHERE username=? LIMIT 1)",
                tablename
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

    public static ResultSet getFirstRow(
            String url,
            String tablename,
            String field,
            String value
    ) {
        String sql = String.format(
                "SELECT FROM %s WHERE %s=? LIMIT 1",
                tablename,
                field
        );
        try (
                Connection conn = DriverManager.getConnection(url)
                ) {
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
