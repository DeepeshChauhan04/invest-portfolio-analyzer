package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecuritiesRequest {


    @JsonProperty(value = "portfolio_Id")
    @NotNull
    private Set<Long> portfolioIds;

    @JsonProperty(value = "isin")
    private Set<String> isinList;

    @JsonProperty(value = "code")
    private Set<String> codeList;

    @JsonProperty(value = "series")
    private Set<String> seriesList;

    @JsonProperty(value = "startDate")
    private LocalDate startDate;

    @JsonProperty(value = "endDate")
    private LocalDate endDate;
}
