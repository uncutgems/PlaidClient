package com.producer.plaidclient.plaid.transaction;

import com.producer.plaidclient.plaid.BaseResponse;
import com.producer.plaidclient.plaid.PlaidAccountContract;
import com.producer.plaidclient.plaid.PlaidItemContract;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaidTransactionResponse extends BaseResponse {
    @JsonProperty(value = "accounts", required = true)
    private List<PlaidAccountContract> accounts;

    @JsonProperty(value = "item", required = true)
    private PlaidItemContract item;

    @JsonProperty(value = "transactions", required = true)
    private List<PlaidTransactionContract> transactions;

    @JsonProperty(value = "total_transactions", required = true)
    private Integer totalTransactions;

}
