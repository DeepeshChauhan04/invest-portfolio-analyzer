package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.PortfolioRequest;
import com.sharemarket.invest.dto.response.PortfolioResponse;
import com.sharemarket.invest.service.abstraction.PortfolioDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
public class PortfolioController {
    private final PortfolioDetailsService portfolioDetailsService;

    @PostMapping("/creatPortfolio")
    public ResponseEntity<PortfolioResponse> createUser(@Valid @RequestBody PortfolioRequest portfolioRequest) {
        PortfolioResponse savePortfolio = portfolioDetailsService.creatPortfolio(portfolioRequest);
        return ResponseEntity.ok(savePortfolio);
    }

    @GetMapping("/getAllPortfolio")
    public ResponseEntity<List<PortfolioResponse>> getAllDataUser() {

        List<PortfolioResponse> allUserList = portfolioDetailsService.getAllPortfolio();
        return ResponseEntity.ok(allUserList);
    }

    @GetMapping("/getByPortfolioId/{id}")
    public ResponseEntity<PortfolioResponse> getDataByPortfolioId(@Valid @PathVariable Long id) {

        System.out.println("portfolio id =====" + id);
        PortfolioResponse portfolioData = portfolioDetailsService.getByPortfolioId(id);
        return ResponseEntity.ok(portfolioData);
    }

    @DeleteMapping("/deletePortfolioId/{id}")
    public void deletesPortfolioById(@PathVariable Long id) {
        portfolioDetailsService.deletePortfolioById(id);
    }

}


