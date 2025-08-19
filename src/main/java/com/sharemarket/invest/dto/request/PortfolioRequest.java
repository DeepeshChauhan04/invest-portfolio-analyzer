package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioRequest {

    @NotNull(message = "User Id must not be null")
    @JsonProperty(value = "userId")
    private Long userId;

    @NotBlank(message = "Portfolio Name  must not be blank")
    @JsonProperty(value = "portfolioName")
    private String portfolioName;

    @NotBlank(message = "description must not be blank")
    @JsonProperty(value = "description")
    private String description;

}
