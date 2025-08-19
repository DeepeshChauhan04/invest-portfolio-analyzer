package com.sharemarket.invest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecuritiesUnrealizedGainLoss {


    private Long portfolioId;
    List<Securities> securities;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Accessors(chain = true)
    public static class Securities {
        private String isin;
        private String series;
        private String code;
        private BigDecimal costValue;
        private BigDecimal unRealizingGainLoss;
    }


}
