package com.producer.plaidclient.plaid.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBalanceRequest {
    @JsonProperty(value = "client_id", required = true)
    private String clientId;

    @JsonProperty(value = "secret", required = true)
    private String secret;

    @JsonProperty(value = "access_token", required = true)
    private String accessToken;

    @JsonProperty("options")
    private BalanceOption options;

}
