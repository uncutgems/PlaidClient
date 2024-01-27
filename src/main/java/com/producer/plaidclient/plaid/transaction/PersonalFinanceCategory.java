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
public class PersonalFinanceCategory {
    @JsonProperty(value = "primary")
    private String primary;

    @JsonProperty(value = "detailed")
    private String detailed;
}
