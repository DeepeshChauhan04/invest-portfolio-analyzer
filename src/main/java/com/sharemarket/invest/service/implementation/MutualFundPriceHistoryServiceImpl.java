package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.MutualFundPriceHistoryDao;
import com.sharemarket.invest.dto.response.MutualFundPriceHistoryResponse;
import com.sharemarket.invest.entity.MutualFundPriceHistory;
import com.sharemarket.invest.service.abstraction.MutualFundPriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MutualFundPriceHistoryServiceImpl implements MutualFundPriceHistoryService {
    private final MutualFundPriceHistoryDao mutualFundPriceHistoryDao;

    private final List<MutualFund> funds = List.of(
            new MutualFund("AXISBLUCHIP", "INF846K01V59"),
            new MutualFund("HDFCEQTY", "INF179K01Z63"),
            new MutualFund("SBIETF500", "INF200KA1FS3"),
            new MutualFund("UTINIFTY", "INF789F1AUV9"),
            new MutualFund("ICICIPRUBANK", "INF109K01LZ1"),
            new MutualFund("KOTAKEMERG", "INF174K01FR2"),
            new MutualFund("MIRAEASSETLARGECAP", "INF769K01CV9"),
            new MutualFund("NIPPEQTYOPP", "INF204K01VQ3"),
            new MutualFund("IDFCSMALLCAP", "INF194KB1XT1"),
            new MutualFund("DSPMIDCAP", "INF740K01AN3")
    );
    private final ModelMapper modelMapper;

    @Override
    public void generateAndInsertHistory() {
        LocalDate today = LocalDate.now();
        Random random = new Random();
        List<MutualFundPriceHistory> allRecords = new ArrayList<>();

        for (MutualFund mf : funds) {
            for (int i = 0; i < 30; i++) {
                MutualFundPriceHistory price = new MutualFundPriceHistory();
                price.setIsin(mf.isin);
                price.setCode(mf.code);
                price.setNavDate(today.minusDays(i));
                price.setNav(BigDecimal.valueOf(10 + (random.nextDouble() * 100))
                        .setScale(2, RoundingMode.HALF_UP));
                price.setStatus(1);
                price.setCreatedAt(LocalDateTime.now());
                price.setUpdatedAt(LocalDateTime.now());
                allRecords.add(price);
            }
        }

        mutualFundPriceHistoryDao.saveAll(allRecords);
    }

    @Override
    public List<MutualFundPriceHistoryResponse> getAllListOfMutualFundHistory() {
        List<MutualFundPriceHistory> listOfMutualFundHistory = mutualFundPriceHistoryDao.findAll();
        return listOfMutualFundHistory.stream().map(equityPriceHistory -> modelMapper.map(equityPriceHistory, MutualFundPriceHistoryResponse.class)).toList();

    }

    @Override
    public List<MutualFundPriceHistoryResponse> getByNaveDate(LocalDate date) {
        List<MutualFundPriceHistory> listOfEquityPriceHistoryByDate = mutualFundPriceHistoryDao.findByNavDate(date);
        return listOfEquityPriceHistoryByDate.stream().map(mutualFundPriceHistory -> modelMapper.map(mutualFundPriceHistory, MutualFundPriceHistoryResponse.class)).toList();
    }

    private record MutualFund(String code, String isin) {
    }
}

