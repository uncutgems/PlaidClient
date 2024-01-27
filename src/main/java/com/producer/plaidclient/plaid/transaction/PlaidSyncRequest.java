package com.producer.plaidclient.plaid.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaidSyncRequest {
    @JsonProperty(value = "access_token", required = true)
    private String accessToken;

    @JsonProperty(value = "client_id", required = true)
    private String clientId;

    @JsonProperty(value = "secret", required = true)
    private String secret;

    @JsonProperty(value = "cursor")
    private String cursor;

    @JsonProperty(value = "count")
    private String count;

    @JsonProperty(value = "options")
    private SyncOption options;
}
