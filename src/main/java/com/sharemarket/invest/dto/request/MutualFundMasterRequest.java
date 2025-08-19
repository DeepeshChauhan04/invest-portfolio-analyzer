package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutualFundMasterRequest {

    @NotEmpty(message = "Code list must not be empty")
    @JsonProperty(value = "code")
    private List<String> code;

    @NotEmpty(message = "ISIN list must not be empty")
    @JsonProperty(value = "isin")
    private List<String> isin;

}
