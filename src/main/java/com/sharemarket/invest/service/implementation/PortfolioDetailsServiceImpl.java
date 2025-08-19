package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.PortfolioDetailsDao;
import com.sharemarket.invest.dao.UserDao;
import com.sharemarket.invest.dto.request.PortfolioRequest;
import com.sharemarket.invest.dto.response.PortfolioResponse;
import com.sharemarket.invest.entity.PortfolioDetails;
import com.sharemarket.invest.service.abstraction.PortfolioDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioDetailsServiceImpl implements PortfolioDetailsService {

    private final PortfolioDetailsDao portfolioDetailsDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    @Override
    public PortfolioResponse creatPortfolio(PortfolioRequest portfolioRequest) {
       if(!userDao.existsByUserIdAndStatus(portfolioRequest.getUserId(),1)){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
       }
        PortfolioDetails entity = modelMapper.map(portfolioRequest, PortfolioDetails.class);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(1);
        PortfolioDetails saved = portfolioDetailsDao.save(entity);
        return modelMapper.map(saved, PortfolioResponse.class);
    }

    @Override
    public List<PortfolioResponse> getAllPortfolio() {
        return portfolioDetailsDao.findAllByStatus(1)
                .stream().
                map(portfolio -> modelMapper.map(portfolio, PortfolioResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public PortfolioResponse getByPortfolioId(Long id) {
        if (!portfolioDetailsDao.existsByPortfolioIdAndStatus(id, 1)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found" +id);
        }
        PortfolioDetails portfolio = portfolioDetailsDao.getById(id);
        return modelMapper.map(portfolio, PortfolioResponse.class);
    }

    @Override
    public void deletePortfolioById(Long id) {
        if (!portfolioDetailsDao.existsByPortfolioIdAndStatus(id, 1)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found" +id);

        }
        portfolioDetailsDao.deleteById(id);
    }
}
