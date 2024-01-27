package com.producer.plaidclient.plaid.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SyncOption {
    @JsonProperty(value = "include_original_description")
    private Boolean includeOriginalDescription;

    @JsonProperty(value = "include_personal_finance_category")
    private Boolean includePersonalFinanceCategory;
}
