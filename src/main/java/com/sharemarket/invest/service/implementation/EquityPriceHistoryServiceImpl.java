package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.EquityPriceHistoryDao;
import com.sharemarket.invest.dto.response.EquityPriceHistoryResponse;
import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.service.abstraction.EquityPriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EquityPriceHistoryServiceImpl implements EquityPriceHistoryService {

    private final EquityPriceHistoryDao equityPriceHistoryDao;

    private final List<Equity> equities = List.of(
            new Equity("RELIANCE", "EQ", "INE002A01018"),
            new Equity("TCS", "EQ", "INE467B01029"),
            new Equity("INFY", "EQ", "INE009A01021"),
            new Equity("HDFCBANK", "EQ", "INE040A01034"),
            new Equity("ICICIBANK", "EQ", "INE090A01021"),
            new Equity("SBIN", "EQ", "INE062A01020"),
            new Equity("LT", "EQ", "INE018A01030"),
            new Equity("ITC", "EQ", "INE154A01025"),
            new Equity("BAJFINANCE", "EQ", "INE296A01024"),
            new Equity("MARUTI", "EQ", "INE585B01010")
    );
    private final ModelMapper modelMapper;

    @Override
    public void generateAndInsertHistory() {

        LocalDate today = LocalDate.now();
        Random random = new Random();
        List<EquityPriceHistory> allPrices = new ArrayList<>();

        for (Equity equity : equities) {
            for (int i = 0; i < 30; i++) {
                EquityPriceHistory eph = new EquityPriceHistory();
                eph.setIsin(equity.isin);
                eph.setCode(equity.code);
                eph.setSeries(equity.series);
                eph.setTradeDate(today.minusDays(i));
                eph.setClosePrice(BigDecimal.valueOf(1000 + (random.nextDouble() * 5000))
                        .setScale(2, RoundingMode.HALF_UP));
                eph.setStatus(1);
                eph.setCreatedAt(LocalDateTime.now());
                eph.setUpdatedAt(LocalDateTime.now());
                allPrices.add(eph);
            }
        }

        equityPriceHistoryDao.saveAll(allPrices);
    }

    @Override
    public List<EquityPriceHistoryResponse> getALlEquityPriceHistory() {
        List<EquityPriceHistory> listOfEquityPriceHistory = equityPriceHistoryDao.findAllByStatus(1);
        return listOfEquityPriceHistory.stream().map(equityPriceHistory -> modelMapper
                .map(equityPriceHistory, EquityPriceHistoryResponse.class)).toList();
    }


    @Override
    public List<EquityPriceHistoryResponse> getByTradeDate(LocalDate trade_date) {
        List<EquityPriceHistory> listOfEquityPriceHistoryByDate = equityPriceHistoryDao
                .findByTradeDateSorted(trade_date);
        return listOfEquityPriceHistoryByDate.stream()
                .map(equityPriceHistory -> modelMapper
                        .map(equityPriceHistory, EquityPriceHistoryResponse.class)).toList();
    }

    @Override
    public void deleteEquityPriceHistory(Long id) {
       if (!equityPriceHistoryDao.existsByIdAndStatus(id,1)){
                   throw new ResponseStatusException(HttpStatus.NOT_FOUND,"id  not valid");
       }
        equityPriceHistoryDao.deleteById(id);
    }

/*  @Override
    public List<EquityPriceHistoryResponse> getByTradeDateRange(LocalDate start, LocalDate end) {
        List<EquityPriceHistory> listOfEquityPriceHistoryBetweenDate = equityPriceHistoryDao.findByTradeDateBetweenSorted(start, end);
        return listOfEquityPriceHistoryBetweenDate.stream().map(equityPriceHistory -> modelMapper.map(listOfEquityPriceHistoryBetweenDate, EquityPriceHistoryResponse.class)).toList();
    }*/

    private record Equity(String code, String series, String isin) {
    }
}



