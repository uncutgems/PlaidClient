package com.producer.plaidclient.plaid.http_client;

import com.producer.plaidclient.plaid.balance.AccountBalanceRequest;
import com.producer.plaidclient.plaid.transaction.PlaidTransactionRequest;
import com.producer.plaidclient.plaid.transaction.PlaidTransactionResponse;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

public interface IPlaidClient {
    CompletableFuture<?> getAccountBalanceAsync(AccountBalanceRequest requestBody) throws URISyntaxException;

    CompletableFuture<PlaidTransactionResponse> getTransactionAsync(PlaidTransactionRequest requestBody) throws URISyntaxException;
}
