package com.fincore.app.model.identity;

import com.fincore.app.model.account.AccountId;

import java.util.UUID;

public record Session(AccountId accId, UUID id) {
    // private roles field


}
