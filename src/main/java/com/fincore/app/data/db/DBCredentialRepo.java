package com.fincore.app.data.db;

import com.fincore.app.domain.identity.CredentialRepo;
import com.fincore.app.domain.identity.Credentials;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class DBCredentialRepo implements CredentialRepo {
    private final String url;
    private final String tableName;

    @Override
    public Optional<Credentials> getByUsername(String username) {
        String sql = String.format("""
                SELECT * FROM %s
                WHERE username = ?""", tableName);
        ResultSet response;
        Credentials credentials;
        try (Connection conn = DriverManager.getConnection(url)) {
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            response = pstmt.executeQuery();
            credentials = new Credentials(
                    response.getString("username"),
                    response.getString("passwordHash"),
                    UUID.fromString(response.getString("userId"))
            );

        } catch (SQLException e) {
            credentials = null;
        }
        return Optional.ofNullable(credentials);
    }

    @Override
    public void save(Credentials cred) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = String.format("""
                    INSERT INTO %s
                    VALUES (?, ?, ?)""",
                    tableName);
            var stmt = conn.prepareStatement(sql, new String[]{"userId, username, passwordHash"});
            stmt.setString(1, cred.userId().toString());
            stmt.setString(2, cred.username());
            stmt.setString(3, cred.passwordHash());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
