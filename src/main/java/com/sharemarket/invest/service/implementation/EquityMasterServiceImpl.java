package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.EquityMasterDao;
import com.sharemarket.invest.dto.request.EquityMasterRequest;
import com.sharemarket.invest.dto.response.EquityMasterResponse;
import com.sharemarket.invest.entity.EquityMaster;
import com.sharemarket.invest.service.abstraction.EquityMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquityMasterServiceImpl implements EquityMasterService {

    private final EquityMasterDao equityMasterDao;
    private final ModelMapper modelMapper;


    private final List<EquityMasterRecord> equities = List.of(
            new EquityMasterRecord("RELIANCE", "EQ", "INE002A01018", "Reliance Industries Ltd", "Energy", "NSE", LocalDate.of(1995, 1, 1), BigDecimal.valueOf(10.00), 1),
            new EquityMasterRecord("TCS", "EQ", "INE467B01029", "ata Consultancy Services Ltd", "IT", "NSE", LocalDate.of(2004, 8, 25), BigDecimal.valueOf(1.00), 1),
            new EquityMasterRecord("INFY", "EQ", "INE009A01021", "Infosys Ltd'", "IT", "BSE", LocalDate.of(1993, 2, 14), BigDecimal.valueOf(5.00), 1),
            new EquityMasterRecord("HDFCBANK", "EQ", "INE040A01034", "HDFC Bank Ltd", "Banking", "NSE", LocalDate.of(1995, 5, 19), BigDecimal.valueOf(2.00), 1),
            new EquityMasterRecord("ICICIBANK", "EQ", "INE090A01021", "ICICI Bank Ltd", "Banking", "BSE", LocalDate.of(1997, 9, 10), BigDecimal.valueOf(2.00), 1),
            new EquityMasterRecord("SBIN", "EQ", "INE062A01020", "State Bank of India", "Banking", "NSE", LocalDate.of(1997, 1, 1), BigDecimal.valueOf(1.00), 1),
            new EquityMasterRecord("LT", "EQ", "INE018A01030", "Larsen & Toubro Ltd", "Construction", "BSE", LocalDate.of(1980, 6, 1), BigDecimal.valueOf(2.00), 1),
            new EquityMasterRecord("ITC", "EQ", "INE154A01025", "ITC Ltd", "FMCG", "NSE", LocalDate.of(1970, 7, 1), BigDecimal.valueOf(1.00), 1),
            new EquityMasterRecord("BAJFINANCE", "EQ", "INE296A01024", "Bajaj Finance Ltd", "Finance", "BSE", LocalDate.of(2001, 3, 20), BigDecimal.valueOf(2.00), 1),
            new EquityMasterRecord("MARUTI", "EQ", "INE585B01010", "Maruti Suzuki India Ltd", "Automobile", "NSE", LocalDate.of(2003, 7, 9), BigDecimal.valueOf(5.00), 1)
    );

    public void generateEquityMaster() {

        List<EquityMaster> allPrices = new ArrayList<>();

        for (EquityMasterRecord equity : equities) {
            EquityMaster equityMaster = new EquityMaster();
            equityMaster.setCode(equity.code);
            equityMaster.setIsin(equity.isin);
            equityMaster.setSeries(equity.series);
            equityMaster.setCompanyName(equity.company_name);
            equityMaster.setSector(equity.sector);
            equityMaster.setExchange(equity.exchange);
            equityMaster.setListingDate(equity.listing_date);
            equityMaster.setFaceValue(equity.face_value);
            equityMaster.setStatus(equity.status);
            allPrices.add(equityMaster);
        }
        equityMasterDao.saveAll(allPrices);
    }

    @Override
    public List<EquityMasterResponse> getAllEquities() {

        List<EquityMaster> equityMasterList = equityMasterDao.findAllByStatus(1);
        log.info("List Of All Equity :  {}", equityMasterList.size());
        List<EquityMasterResponse> responseListOfEquityMaster = equityMasterList
                .stream()
                .map(equityMaster -> modelMapper
                        .map(equityMaster, EquityMasterResponse.class))
                .collect(Collectors.toList());

log.info("Equity Mater {} ", responseListOfEquityMaster.size());
        return responseListOfEquityMaster;
    }

    @Override
    public List<EquityMasterResponse> getFilterEquitiesList(EquityMasterRequest request) {

        List<EquityMaster> allEquities = equityMasterDao.findByFilterEqList(request.getIsin(),
                request.getCode(), request.getSeries());
        return allEquities
                .stream()
                .map(equityMaster -> modelMapper
                        .map(equityMaster, EquityMasterResponse.class))
                .toList();
    }

    @Override
    public EquityMasterResponse getByEqId(Long id) {
        if (!equityMasterDao.existsByEqIdAndStatus(id, 1)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EQ ID NOT VALID");
        }
        return modelMapper.map(equityMasterDao.findById(id), EquityMasterResponse.class);
    }

    private record EquityMasterRecord(String code, String series, String isin, String company_name, String sector,
                                      String exchange, LocalDate listing_date, BigDecimal face_value, Integer status) {

    }
}
