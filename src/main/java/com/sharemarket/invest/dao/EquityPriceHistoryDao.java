package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.repository.EquityPriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class EquityPriceHistoryDao implements EquityPriceHistoryRepository {

    private final EquityPriceHistoryRepository equityPriceHistoryRepository;

    @Override
    public void flush() {
        equityPriceHistoryRepository.flush();
    }

    @Override
    public <S extends EquityPriceHistory> S saveAndFlush(S entity) {
        return equityPriceHistoryRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends EquityPriceHistory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return equityPriceHistoryRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<EquityPriceHistory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public EquityPriceHistory getOne(Long aLong) {
        return equityPriceHistoryRepository.getOne(aLong);
    }

    @Override
    public EquityPriceHistory getById(Long aLong) {
        return equityPriceHistoryRepository.getById(aLong);
    }

    @Override
    public EquityPriceHistory getReferenceById(Long aLong) {
        return equityPriceHistoryRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends EquityPriceHistory> Optional<S> findOne(Example<S> example) {
        return equityPriceHistoryRepository.findOne(example);
    }

    @Override
    public <S extends EquityPriceHistory> List<S> findAll(Example<S> example) {
        return equityPriceHistoryRepository.findAll(example);
    }

    @Override
    public <S extends EquityPriceHistory> List<S> findAll(Example<S> example, Sort sort) {
        return equityPriceHistoryRepository.findAll(example, sort);
    }

    @Override
    public <S extends EquityPriceHistory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends EquityPriceHistory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends EquityPriceHistory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends EquityPriceHistory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return equityPriceHistoryRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends EquityPriceHistory> S save(S entity) {
        return equityPriceHistoryRepository.save(entity);
    }

    @Override
    public <S extends EquityPriceHistory> List<S> saveAll(Iterable<S> entities) {
        return equityPriceHistoryRepository.saveAll(entities);
    }

    @Override
    public Optional<EquityPriceHistory> findById(Long aLong) {
        return equityPriceHistoryRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return equityPriceHistoryRepository.existsById(aLong);
    }

    @Override
    public List<EquityPriceHistory> findAll() {
        return equityPriceHistoryRepository.findAll();
    }

    @Override
    public List<EquityPriceHistory> findAllById(Iterable<Long> longs) {
        return equityPriceHistoryRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(EquityPriceHistory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends EquityPriceHistory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<EquityPriceHistory> findAll(Sort sort) {
        return equityPriceHistoryRepository.findAll(sort);
    }

    @Override
    public Page<EquityPriceHistory> findAll(Pageable pageable) {
        return equityPriceHistoryRepository.findAll(pageable);
    }


    @Override
    public List<EquityPriceHistory> findByTradeDateSorted(LocalDate tradeDate) {
        return equityPriceHistoryRepository.findByTradeDateSorted(tradeDate);
    }

    @Override
    public List<EquityPriceHistory> findByTradeDateBetweenSorted(LocalDate startDate, LocalDate endDate) {
        return equityPriceHistoryRepository.findByTradeDateBetweenSorted(startDate, endDate);
    }

    @Override
    public List<EquityPriceHistory> findAllByStatus(Integer status) {
        return equityPriceHistoryRepository.findAllByStatus(status);
    }

    @Override
    public boolean existsByIdAndStatus(Long id, Integer status) {
        return equityPriceHistoryRepository.existsByIdAndStatus(id, status);
    }

    @Override
    public List<EquityPriceHistory> findLatestPrice(Set<String> isinList, Set<String> codeList, Set<String> seriesList, LocalDate endDate) {
        return equityPriceHistoryRepository.findLatestPrice(isinList, codeList, seriesList, endDate);


    }

    @Override
    public List<EquityPriceHistory> findLatestPrices(Set<String> isinList, Set<String> codeList, Set<String> seriesList, LocalDate currentDate) {
        return List.of();
    }

    @Override
    public List<EquityPriceHistory> findLatestClosePrice(Set<String> isinList, Set<String> codeList, Set<String> seriesList, LocalDate currentDate) {
        return equityPriceHistoryRepository.findLatestClosePrice(isinList,codeList,seriesList,currentDate);
    }
}
