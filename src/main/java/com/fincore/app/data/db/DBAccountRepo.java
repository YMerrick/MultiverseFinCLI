package com.fincore.app.data.db;

import com.fincore.app.data.db.util.DBUtility;
import com.fincore.app.domain.account.*;
import com.fincore.app.domain.shared.Money;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.fincore.app.domain.account.AccountType.*;

@AllArgsConstructor
public class DBAccountRepo implements AccountRepo {
    private String url;
    private String tablename;

    @Override
    public void save(Account account) {
        AccountType accountType = getAccountType(account);
        String[] headers = {"id", "userId", "accountHolder", "balance", "currencyCode", "type"};
        String questionMarks = getQuestionMarks(headers);
        String sql = String.format(
                "INSERT INTO %s VALUES (%s)",
                tablename,
                questionMarks
        );

        try (
                Connection conn = DriverManager.getConnection(url)
                ) {
            var pstmt = conn.prepareStatement(sql, headers);
            pstmt.setString(1, account.getId().toString());
            pstmt.setString(2, account.getUserId().toString());
            pstmt.setString(3, account.getAccountHolder());
            pstmt.setLong(4, account.getBalance().asMinorUnits());
            pstmt.setString(5, account.getBalance().getCurrency().getCurrencyCode());
            pstmt.setString(6, accountType.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Account> getById(UUID id) {
        if (DBUtility.isEntityExists(url, tablename, id.toString()))
            return Optional.empty();
        Account account;
        ResultSet res;
        UUID accountId, userId;

        String[] headers = {"id", "userId", "accountHolder", "balance", "currencyCode", "type"};

        try {
            res = DBUtility.getFirstRow(
                    url,
                    tablename,
                    "id",
                    id.toString()
            );
            accountId = UUID.fromString(res.getString(headers[0]));
            userId = UUID.fromString(res.getString(headers[1]));
            String accountType = res.getString(headers[5]);

            account = switch (accountType) {
                case "CURRENT" -> new CurrentAccount(
                        accountId,
                        userId,
                        res.getString(headers[2]),
                        Money.ofMinor(res.getInt(headers[3]), Currency.getInstance(headers[4]))
                );
                case "OVERDRAFT" -> new OverdraftAccount(
                        accountId,
                        userId,
                        res.getString(headers[2]),
                        Money.ofMinor(res.getInt(headers[3]), Currency.getInstance(headers[4]))
                );
                case null, default -> null;
            };

        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(account);
    }

    private AccountType getAccountType(Account account) {
        return switch (account.getClass().toString()) {
            case "CurrentAccount.class" -> CURRENT;
            case "OverdraftAccount.class" -> OVERDRAFT;
            default -> throw new RuntimeException();
        };
    }

    private String getQuestionMarks(String[] headers) {
        return Arrays.stream(headers)
                .map(ignored -> "?")
                .collect(Collectors.joining(","));
    }
}
