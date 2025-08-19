package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.MutualFundMasterDao;
import com.sharemarket.invest.dto.request.MutualFundMasterRequest;
import com.sharemarket.invest.dto.response.MutualFundMasterResponse;
import com.sharemarket.invest.entity.MutualFundMaster;
import com.sharemarket.invest.service.abstraction.MutualFundMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MutualFundMasterServiceImpl implements MutualFundMasterService {

    private final MutualFundMasterDao mutualFundMasterDao;
    private final ModelMapper modelMapper;

    private final List<mutual_fund_master> equities = List.of(

            new mutual_fund_master("AXISBLUCHIP", "INF846K01V59", "Axis Bluechip Fund", "Equity", "Axis Mutual Fund", "Large Cap", "2010-01-05"),
            new mutual_fund_master("HDFCEQTY", "INF179K01Z63", "HDFC Equity Fund", "Equity", "HDFC Mutual Fund", "Multi Cap", "1995-12-01"),
            new mutual_fund_master("SBIETF500", "INF200KA1FS3", "SBI ETF Nifty 500", "ETF", "SBI Mutual Fund", "Index Fund", "2017-09-10"),
            new mutual_fund_master("UTINIFTY", "INF789F1AUV9", "UTI Nifty Index Fund", "Equity", "UTI Mutual Fund", "Index Fund", "2001-01-01"),
            new mutual_fund_master("ICICIPRUBANK", "INF109K01LZ1", "ICICI Prudential Banking and Financial Services Fund", "Equity", "ICICI Prudential Mutual Fund", "Sectoral", "2008-08-01"),
            new mutual_fund_master("KOTAKEMERG", "INF174K01FR2", "Kotak Emerging Equity Fund", "Equity", "Kotak Mahindra Mutual Fund", "Mid Cap", "2007-02-16"),
            new mutual_fund_master("IRAEASSETLARGECAP", "INF769K01CV9", "Mirae Asset Large Cap Fund", "Equity", "Mirae Asset Mutual Fund", "Large Cap", "2008-04-01"),
            new mutual_fund_master("NIPPEQTYOPP", "INF204K01VQ3", "Nippon India Equity Opportunities Fund", "Equity", "Nippon India Mutual Fund", "Multi Cap", "2005-06-30"),
            new mutual_fund_master("IDFCSMALLCAP", "INF194KB1XT1", "IDFC Small Cap Fund", "Equity", "IDFC Mutual Fund", "Small Cap", "2014-10-01"),
            new mutual_fund_master("DSPMIDCAP", "INF740K01AN3", "DSP Midcap Fund", "Equity", "DSP Mutual Fund", "Mid Cap", "2007-11-14")
    );

    @Override
    public void generateMutualFundMaster() {

        List<MutualFundMaster> allPrices = new ArrayList<>();

        for (mutual_fund_master equity : equities) {
            MutualFundMaster mutualFundMaster = new MutualFundMaster();
            mutualFundMaster.setCode(equity.code);
            mutualFundMaster.setIsin(equity.isin);
            mutualFundMaster.setFundHouse(equity.fund_house);
            mutualFundMaster.setFundName(equity.fund_name);
            mutualFundMaster.setFundType(equity.fund_type);
            mutualFundMaster.setLaunchDate(LocalDate.parse(equity.launch_date));
            mutualFundMaster.setIsin(equity.isin);
            mutualFundMaster.setCategory(equity.category);
            mutualFundMaster.setStatus(1);
            mutualFundMaster.setCreatedAt(LocalDateTime.now());
            mutualFundMaster.setUpdatedAt(LocalDateTime.now());
            allPrices.add(mutualFundMaster);
        }
        mutualFundMasterDao.saveAll(allPrices);
    }

    @Override
    public List<MutualFundMasterResponse> getAllMutualFunds() {
        List<MutualFundMaster> listOfMutualFunds = mutualFundMasterDao.findAll();
        log.error("");
        List<MutualFundMasterResponse> responseListOfMutualFunds = listOfMutualFunds
                .stream()
                .map(mutualFundMaster -> modelMapper
                        .map(mutualFundMaster, MutualFundMasterResponse.class))
                .toList();

        log.info(" : {}", listOfMutualFunds.size());
        return responseListOfMutualFunds;
    }

    @Override
    public List<MutualFundMasterResponse> getFilteredMutualFunds(MutualFundMasterRequest mutualFundMasterRequest) {

        List<MutualFundMaster> mutualFundList = mutualFundMasterDao.getFilterListOfMutualFunds
                (mutualFundMasterRequest.getIsin(), mutualFundMasterRequest.getCode());

        return mutualFundList.stream().map(mutualFundMaster -> modelMapper
                .map(mutualFundMaster, MutualFundMasterResponse.class
                )).toList();

    }

    private record mutual_fund_master(String code, String isin, String fund_name, String fund_type, String fund_house,
                                      String category, String launch_date) {

    }
}
