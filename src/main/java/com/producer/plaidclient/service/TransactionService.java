package com.producer.plaidclient.service;

import com.producer.plaidclient.config.Environment;
import com.producer.plaidclient.entity.Item;
import com.producer.plaidclient.entity.Transaction;
import com.producer.plaidclient.plaid.transaction.PlaidSyncRequest;
import com.producer.plaidclient.plaid.transaction.PlaidSyncResponse;
import com.producer.plaidclient.plaid.transaction.PlaidTransactionContract;
import com.producer.plaidclient.repository.ItemRepository;
import com.producer.plaidclient.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final String dateFormat = "dd-MM-yyyy";
    private final ItemRepository itemRepository;
    private final TransactionRepository transactionRepository;

    public void fetchTransaction(String itemId) throws URISyntaxException, ParseException {
        boolean hasMore = true;
        Item item = itemRepository.findByItemId(itemId);
        if (item != null) {

            // Sync transaction data with Plaid
            while (hasMore) {
                URI uri = new URI(Environment.sandboxEnv + "/transactions/sync");

                // Populate Sync Request, update Transaction Cursor if needed
                PlaidSyncRequest plaidSyncRequest = PlaidSyncRequest.builder()
                        .accessToken(item.getAccessToken())
                        .clientId(Environment.sandboxClientId)
                        .secret(Environment.sandboxSecret)
                        .build();
                if (item.getCursor() != null)
                    plaidSyncRequest.setCursor(item.getCursor());

                // Send request to Plaid, see if there is more transaction available
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<PlaidSyncResponse> plaidSyncResponse = restTemplate.postForEntity(uri, plaidSyncRequest, PlaidSyncResponse.class);
                if (plaidSyncResponse.getBody() != null) {
                    hasMore = plaidSyncResponse.getBody().getHasMore();
                    if (!hasMore) {
                        item.setCursor(plaidSyncResponse.getBody().getNextCursor());
                        item.setLastUpdated(LocalDateTime.now());
                        itemRepository.save(item);
                    }
                }

                for (PlaidTransactionContract plaidTransactionContract : plaidSyncResponse.getBody().getAdded()) {
                    Transaction transaction = Transaction.builder()
                            .plaidTransactionId(plaidTransactionContract.getTransactionId())
                            .plaidAccount(plaidTransactionContract.getAccountId())
                            .merchantName(plaidTransactionContract.getMerchantName())
                            .paymentChannel(plaidTransactionContract.getPaymentChannel())
                            .pending(plaidTransactionContract.getPending())
                            .amount(plaidTransactionContract.getAmount())
                            .date(new SimpleDateFormat(dateFormat).parse(plaidTransactionContract.getDate()))
                            .spendingCategory(Transaction.SpendingCategory
                                    .valueOf(plaidTransactionContract.getPersonalFinanceCategory().getPrimary()))
                            .subCategory(Transaction.SubCategory
                                    .valueOf(plaidTransactionContract.getPersonalFinanceCategory().getDetailed()))
                            .build();
                    transactionRepository.save(transaction);
                }

                for (PlaidTransactionContract plaidTransactionContract : plaidSyncResponse.getBody().getModified()) {
                    Transaction transaction =
                            transactionRepository.findByPlaidTransactionId(plaidTransactionContract.getTransactionId());
                    if (transaction != null) {
                        transaction.setPlaidAccount(plaidTransactionContract.getAccountId());
                        transaction.setMerchantName(plaidTransactionContract.getMerchantName());
                        transaction.setPaymentChannel(plaidTransactionContract.getPaymentChannel());
                        transaction.setPending(plaidTransactionContract.getPending());
                        transaction.setAmount(plaidTransactionContract.getAmount());
                        transaction.setDate(new SimpleDateFormat(dateFormat).parse(plaidTransactionContract.getDate()));
                        transaction.setSpendingCategory(Transaction.SpendingCategory
                                .valueOf(plaidTransactionContract.getPersonalFinanceCategory().getPrimary()));
                        transaction.setSubCategory(Transaction.SubCategory
                                .valueOf(plaidTransactionContract.getPersonalFinanceCategory().getDetailed()));
                    } else {
                        transaction = Transaction.builder()
                                .plaidTransactionId(plaidTransactionContract.getTransactionId())
                                .plaidAccount(plaidTransactionContract.getAccountId())
                                .merchantName(plaidTransactionContract.getMerchantName())
                                .paymentChannel(plaidTransactionContract.getPaymentChannel())
                                .pending(plaidTransactionContract.getPending())
                                .amount(plaidTransactionContract.getAmount())
                                .date(new SimpleDateFormat(dateFormat).parse(plaidTransactionContract.getDate()))
                                .spendingCategory(Transaction.SpendingCategory
                                        .valueOf(plaidTransactionContract.getPersonalFinanceCategory().getPrimary()))
                                .subCategory(Transaction.SubCategory
                                        .valueOf(plaidTransactionContract.getPersonalFinanceCategory().getDetailed()))
                                .build();
                    }
                    transactionRepository.save(transaction);
                }
                for (String plaidTransactionContract : plaidSyncResponse.getBody().getRemoved()) {
                    if (transactionRepository.findByPlaidTransactionId(plaidTransactionContract) != null)
                        transactionRepository.deleteByPlaidTransactionId(plaidTransactionContract);
                }
            }
        }


    }

}
