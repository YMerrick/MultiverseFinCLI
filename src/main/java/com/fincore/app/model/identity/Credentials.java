package com.fincore.app.model.identity;

import com.fincore.app.model.account.AccountId;

public record Credentials(String username, String passwordHash, AccountId accId) {
}
