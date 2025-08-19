package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquityMasterRequest {

    @NotNull(message = "Code must not be null")
    @JsonProperty(value = "code")
    private List<String> code;

    @NotNull(message = "Series must not be null")
    @JsonProperty(value = "series")
    private List<String> series;

    @NotNull(message = "Isin must not be null")
    @JsonProperty(value = "isin")
    private List<String> isin;


}
