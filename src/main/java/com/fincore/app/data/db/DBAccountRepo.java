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
        if (DBUtility.isEntityExists(url, tablename, "id", id.toString()))
            return Optional.empty();
        Account account;
        Map<String, Object> res;
        UUID accountId, userId;

        String[] headers = {"id", "userId", "accountHolder", "balance", "currencyCode", "type"};

        try {
            res = DBUtility.getFirstRow(
                    url,
                    tablename,
                    "id",
                    id.toString()
            );
            accountId = UUID.fromString((String) res.get(headers[0]));
            userId = UUID.fromString((String) res.get(headers[1]));
            String accountType = res.get(headers[5]).toString();

            account = makeAccount(
                    accountType,
                    accountId,
                    userId,
                    (String) res.get(headers[2]),
                    Money.ofMinor(
                            (long) res.get(headers[3]),
                            Currency.getInstance((String) res.get(headers[4]))
                    )
            );

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(account);
    }

    @Override
    public List<Account> getAccounts(UUID userId) {
        List<Account> accountList = new ArrayList<>();
        List<Map<String, Object>> resultList;
        String[] headers = {"id", "userId", "accountHolder", "balance", "currencyCode", "type"};

        resultList = DBUtility.getAllRows(
                url,
                tablename,
                headers[1],
                userId.toString()
        );

        for (Map<String, Object> item : resultList) {

            accountList.add(
                    makeAccount(
                            item.get(headers[5]).toString(),
                            UUID.fromString(item.get(headers[0]).toString()),
                            UUID.fromString(item.get(headers[1]).toString()),
                            item.get(headers[2]).toString(),
                            Money.ofMinor(
                                    (int) item.get(headers[3]),
                                    Currency.getInstance(item.get(headers[4]).toString())
                            )
                    )
            );
        }
        return accountList;
    }

    private AccountType getAccountType(Account account) {
        String[] className = account.getClass().toString().split("\\.");
        String type = className[className.length - 1];
        return switch (type) {
            case "CurrentAccount" -> CURRENT;
            case "OverdraftAccount" -> OVERDRAFT;
            default -> throw new RuntimeException();
        };
    }

    private String getQuestionMarks(String[] headers) {
        return Arrays.stream(headers)
                .map(ignored -> "?")
                .collect(Collectors.joining(","));
    }

    private Account makeAccount(
            String accountType,
            UUID accountId,
            UUID userId,
            String accountHolder,
            Money balance
    ) {
        return switch (accountType) {
            case "CURRENT" -> new CurrentAccount(
                    accountId,
                    userId,
                    accountHolder,
                    balance
            );
            case "OVERDRAFT" -> new OverdraftAccount(
                    accountId,
                    userId,
                    accountHolder,
                    balance
            );
            case null, default -> null;
        };
    }
}
