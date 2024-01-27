package com.producer.plaidclient.plaid.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.producer.plaidclient.plaid.PlaidErrorResponseContract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaidWebhook {
    @JsonProperty(value = "webhook_type")
    private String webhookType;

    @JsonProperty(value = "webhook_code")
    private String webhookCode;

    @JsonProperty(value = "item_id")
    private String itemId;

    @JsonProperty(value = "error")
    private PlaidErrorResponseContract error;

    @JsonProperty(value = "initial_update_complete")
    private Boolean initialUpdateComplete;

    @JsonProperty(value = "historical_update_complete")
    private Boolean historicalUpdateComplete;

    @JsonProperty(value = "consent_expiration_time")
    private LocalDateTime consentExpirationTime;

    @JsonProperty(value = "new_webhook_url")
    private String newWebhookUrl;

    @JsonProperty(value = "environment")
    private String environment;
}
