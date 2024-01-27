package com.producer.plaidclient.plaid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaidErrorResponseContract {
    @JsonProperty(value = "error_type")
    private String errorType;

    @JsonProperty(value = "error_code")
    private String errorCode;

    @JsonProperty(value = "error_message")
    private String errorMessage;

    @JsonProperty(value = "display_message")
    private String displayMessage;

    @JsonProperty(value = "request_id")
    private String requestId;

    @JsonProperty(value = "causes")
    private String causes;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "documentation_url")
    private String documentationUrl;

    @JsonProperty(value = "suggested_action")
    private String suggestedAction;
}
