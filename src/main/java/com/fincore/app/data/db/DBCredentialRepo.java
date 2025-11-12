package com.fincore.app.data.db;

import com.fincore.app.data.db.util.DBUtility;
import com.fincore.app.domain.identity.CredentialRepo;
import com.fincore.app.domain.identity.Credentials;
import com.fincore.app.domain.shared.DuplicateEntityException;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class DBCredentialRepo implements CredentialRepo {
    private final String url;
    private final String tablename;

    @Override
    public Optional<Credentials> getByUsername(String username) {
        if (!DBUtility.isEntityExists(url, tablename,"username", username))
            return Optional.empty();

        Map<String, Object> response;
        Credentials credentials;
        try {
            response = DBUtility.getFirstRow(url, tablename, "username", username);
            credentials = new Credentials(
                    (String) response.get("username"),
                    (String) response.get("passwordHash"),
                    UUID.fromString((String) response.get("userId"))
            );

        } catch (RuntimeException e) {
            credentials = null;
        }
        return Optional.ofNullable(credentials);
    }

    @Override
    public void save(Credentials cred) {
        if (DBUtility.isEntityExists(url, tablename, "username", cred.username()))
            throw new DuplicateEntityException("Credentials already exists");
        try (Connection conn = DriverManager.getConnection(url)) {
            String[] headers = {"userId, username, passwordHash"};
            String sql = String.format("""
                    INSERT INTO %s
                    VALUES (?, ?, ?)""",
                    tablename);
            var stmt = conn.prepareStatement(sql, headers);
            stmt.setString(1, cred.userId().toString());
            stmt.setString(2, cred.username());
            stmt.setString(3, cred.passwordHash());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
