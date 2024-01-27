package com.producer.plaidclient.service;

import com.producer.plaidclient.config.Environment;
import com.producer.plaidclient.entity.Account;
import com.producer.plaidclient.entity.Item;
import com.producer.plaidclient.plaid.PlaidAccountContract;
import com.producer.plaidclient.plaid.balance.AccountBalanceRequest;
import com.producer.plaidclient.plaid.balance.AccountBalanceResponse;
import com.producer.plaidclient.repository.AccountRepository;
import com.producer.plaidclient.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final ItemRepository itemRepository;
    private final AccountRepository accountRepository;

    public void fetchBalance(String itemId) throws URISyntaxException {
        Item item = itemRepository.findByItemId(itemId);
        if (item != null) {
            URI uri = new URI(Environment.sandboxEnv + "/accounts/balance/get");
            AccountBalanceRequest accountBalanceRequest = AccountBalanceRequest.builder()
                    .accessToken(item.getAccessToken())
                    .clientId(Environment.sandboxClientId)
                    .secret(Environment.sandboxSecret)
                    .build();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<AccountBalanceResponse> plaidBalanceResponse =
                    restTemplate.postForEntity(uri, accountBalanceRequest, AccountBalanceResponse.class);
            if (plaidBalanceResponse.getBody() != null) {
                for (PlaidAccountContract plaidAccountContract : plaidBalanceResponse.getBody().getAccounts()) {
                    Account account = accountRepository.getAccountByPlaidAccountId(plaidAccountContract.getAccountId());
                    if (account != null) {
                        account.setPlaidAccountId(plaidAccountContract.getAccountId());
                        account.setAvailableBalance(plaidAccountContract.getBalances().getAvailable());
                        account.setCurrentBalance(plaidAccountContract.getBalances().getCurrent());
                        account.setName(plaidAccountContract.getName());
                        account.setType(plaidAccountContract.getType());
                        account.setSubtype(plaidAccountContract.getSubtype());
                    }
                    else {
                        account = Account.builder()
                                .plaidAccountId(plaidAccountContract.getAccountId())
                                .availableBalance(plaidAccountContract.getBalances().getAvailable())
                                .currentBalance(plaidAccountContract.getBalances().getCurrent())
                                .name(plaidAccountContract.getName())
                                .type(plaidAccountContract.getType())
                                .subtype(plaidAccountContract.getSubtype())
                                .build();
                    }

                    accountRepository.save(account);
                }
            }
        }
    }
}
