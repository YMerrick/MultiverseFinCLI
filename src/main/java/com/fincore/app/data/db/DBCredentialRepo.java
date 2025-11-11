package com.fincore.app.data.db;

import com.fincore.app.domain.identity.CredentialRepo;
import com.fincore.app.domain.identity.Credentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DBCredentialRepo implements CredentialRepo {
    private final String url;
    private final String tableName;

    public DBCredentialRepo(String url, String tableName) {
        this.url = url;
        this.tableName = tableName;
    }
    @Override
    public Optional<Credentials> getByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = """
                    """;

        } catch (SQLException ignored) {}
        return Optional.empty();
    }

    @Override
    public void save(Credentials cred) {
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = String.format("""
                    INSERT INTO %s
                    VALUES (?, ?, ?)""",
                    tableName);
            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, cred.userId().toString());
            stmt.setString(2, cred.username());
            stmt.setString(3, cred.passwordHash());
            stmt.execute(sql, new String[]{"userId, username, passwordHash"});

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
