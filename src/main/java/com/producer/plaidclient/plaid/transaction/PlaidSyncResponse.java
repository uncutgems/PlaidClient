package com.producer.plaidclient.plaid.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.producer.plaidclient.plaid.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaidSyncResponse extends BaseResponse {
    @JsonProperty(value = "added")
    private List<PlaidTransactionContract> added;

    @JsonProperty(value = "has_more")
    private Boolean hasMore;

    @JsonProperty(value = "modified")
    private List<PlaidTransactionContract> modified;

    @JsonProperty(value = "next_cursor")
    private String nextCursor;

    @JsonProperty(value = "removed")
    private List<String> removed;

}
