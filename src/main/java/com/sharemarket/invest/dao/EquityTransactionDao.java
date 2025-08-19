package com.sharemarket.invest.dao;

import com.sharemarket.invest.dto.response.EquitySummaryResponse;
import com.sharemarket.invest.dto.response.EquitySummaryView;
import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.repository.EquityTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class EquityTransactionDao implements EquityTransactionRepository {

    private final EquityTransactionRepository equityTransactionRepository;

    @Override
    public void flush() {
        equityTransactionRepository.flush();
    }

    @Override
    public <S extends EquityTransaction> S saveAndFlush(S entity) {
        return equityTransactionRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends EquityTransaction> List<S> saveAllAndFlush(Iterable<S> entities) {
        return equityTransactionRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<EquityTransaction> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public EquityTransaction getOne(Long aLong) {
        return equityTransactionRepository.getOne(aLong);
    }

    @Override
    public EquityTransaction getById(Long aLong) {
        return equityTransactionRepository.getById(aLong);
    }

    @Override
    public EquityTransaction getReferenceById(Long aLong) {
        return equityTransactionRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends EquityTransaction> Optional<S> findOne(Example<S> example) {
        return equityTransactionRepository.findOne(example);
    }

    @Override
    public <S extends EquityTransaction> List<S> findAll(Example<S> example) {
        return equityTransactionRepository.findAll(example);
    }

    @Override
    public <S extends EquityTransaction> List<S> findAll(Example<S> example, Sort sort) {
        return equityTransactionRepository.findAll(example, sort);
    }

    @Override
    public <S extends EquityTransaction> Page<S> findAll(Example<S> example, Pageable pageable) {
        return equityTransactionRepository.findAll(example, pageable);
    }

    @Override
    public <S extends EquityTransaction> long count(Example<S> example) {
        return equityTransactionRepository.count();
    }

    @Override
    public <S extends EquityTransaction> boolean exists(Example<S> example) {
        return equityTransactionRepository.exists(example);
    }

    @Override
    public <S extends EquityTransaction, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return equityTransactionRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends EquityTransaction> S save(S entity) {
        return equityTransactionRepository.save(entity);
    }

    @Override
    public <S extends EquityTransaction> List<S> saveAll(Iterable<S> entities) {
        return equityTransactionRepository.saveAll(entities);
    }

    @Override
    public Optional<EquityTransaction> findById(Long aLong) {
        return equityTransactionRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return equityTransactionRepository.existsById(aLong);
    }

    @Override
    public List<EquityTransaction> findAll() {
        return equityTransactionRepository.findAll();
    }

    @Override
    public List<EquityTransaction> findAllById(Iterable<Long> longs) {
        return equityTransactionRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return equityTransactionRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(EquityTransaction entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends EquityTransaction> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<EquityTransaction> findAll(Sort sort) {
        return equityTransactionRepository.findAll(sort);
    }

    @Override
    public Page<EquityTransaction> findAll(Pageable pageable) {
        return equityTransactionRepository.findAll(pageable);
    }

    @Override
    public List<EquityTransaction> findAllByStatus(Integer status) {
        return equityTransactionRepository.findAllByStatus(status);
    }


    @Override
    public Integer getNetAvailableQuantity(String code, String series, String isin, Long portfolioId) {
        return equityTransactionRepository.getNetAvailableQuantity(code, series, isin, portfolioId);
    }

    @Override
    public EquitySummaryView getEquitySummary(String code, String series, String isin, Long portfolioId) {
        return equityTransactionRepository.getEquitySummary(code, series, isin, portfolioId);
    }

    @Override
    public List<EquityTransaction> findAllByCodeAndPortfolio(String code, String series, String isin, Long portfolioId) {
        return equityTransactionRepository.findAllByCodeAndPortfolio(code, series, isin, portfolioId);
    }

    @Override
    public List<EquityTransaction> findLIFOTransaction(String valuationMethod) {
        return equityTransactionRepository.findLIFOTransaction(valuationMethod);
    }

    @Override
    public List<EquityTransaction> findLifoTransactions(String code, String series, String isin, Long portfolioId) {
        return equityTransactionRepository.findLifoTransactions(code, series, isin, portfolioId);
    }

    @Override
    public List<EquityTransaction> findFifoTransaction(String code, String series, String isin, Long portfolioId) {
        return equityTransactionRepository.findFifoTransaction(code, series, isin, portfolioId);
    }

    @Override
    public EquitySummaryView getEquityTransactionByFifo(String code, String series, String isin, Long portfolioId) {
        return equityTransactionRepository.getEquityTransactionByFifo(code, series, isin, portfolioId);
    }


    @Override
    public List<EquityTransaction> findByIsinAndSeriesAndCodeAndPortfolioIdOrderByTransactionDateAsc(String code, String isin, String series, Long portfolioId) {
        return equityTransactionRepository.findByIsinAndSeriesAndCodeAndPortfolioIdOrderByTransactionDateAsc(code, isin, series, portfolioId);
    }


    @Override
    public List<EquityTransaction> findAllRequiredSecurities(LocalDate startDate, LocalDate endDate, Set<Long> portfolioIds, Set<String> isinList, Set<String> seriesList, Set<String> codeList) {
        return equityTransactionRepository.findAllRequiredSecurities(startDate,endDate,portfolioIds,isinList,seriesList,codeList);
    }
}
