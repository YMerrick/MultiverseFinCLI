package com.fincore.app.data.db;

import com.fincore.app.data.db.util.DBUtility;
import com.fincore.app.domain.shared.AuthException;
import com.fincore.app.domain.shared.DuplicateEntityException;
import com.fincore.app.domain.user.User;
import com.fincore.app.domain.user.UserRepo;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class DBUserRepo implements UserRepo {
    private final String url;
    private final String tablename;

    @Override
    public Optional<User> getById(UUID userId) {
        if (!isUserExists(userId)) return Optional.empty();

        User user;
        Map<String, Object> res;
        UUID id;
        String[] headers = new String[] {"userId", "firstName", "lastName"};

        try {
            res = DBUtility.getFirstRow(
                    url,
                    tablename,
                    "userId",
                    userId.toString()
            );
            id = UUID.fromString((String) res.get(headers[0]));

            user = new User(
                    id,
                    (String) res.get(headers[1]),
                    (String) res.get(headers[2])
            );
        } catch (RuntimeException e) {
            throw new AuthException(e.getMessage());
        }

        return Optional.of(user);
    }

    @Override
    public void save(User user) {
        if (isUserExists(user.userId())) throw new DuplicateEntityException("User already exists");
        String[] headers = {"userId, firstName, lastName"};
        String sql = String.format("""
                INSERT INTO %s
                VALUES (?, ?, ?)""",
                tablename);

        try (Connection conn = DriverManager.getConnection(url)) {
            var stmt = conn.prepareStatement(sql, headers);
            stmt.setString(1, user.userId().toString());
            stmt.setString(2, user.firstName());
            stmt.setString(3, user.lastName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new AuthException(e.getMessage());
        }
    }

    private boolean isUserExists(UUID userId) {
        ResultSet res;
        try (
                Connection conn = DriverManager.getConnection(url)
                ) {
            String sql = String.format(
                    "SELECT EXISTS(SELECT 1 FROM %s WHERE userId=? LIMIT 1)",
                    tablename
            );
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId.toString());
            res = pstmt.executeQuery();
            return res.getBoolean(1);
        } catch (SQLException e) {
            // TODO Handle Exception properly
            throw new AuthException(e.getMessage());
        }
    }
}
