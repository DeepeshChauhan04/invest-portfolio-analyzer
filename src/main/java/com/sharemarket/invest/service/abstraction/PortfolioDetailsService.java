package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.PortfolioRequest;
import com.sharemarket.invest.dto.response.PortfolioResponse;

import java.util.List;

public interface PortfolioDetailsService {


    PortfolioResponse creatPortfolio(PortfolioRequest portfolioRequest);

    List<PortfolioResponse> getAllPortfolio();

    PortfolioResponse getByPortfolioId(Long id);

    void deletePortfolioById(Long id);

}
