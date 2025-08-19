package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.entity.MutualFundPriceHistory;
import com.sharemarket.invest.entity.MutualFundTransaction;
import com.sharemarket.invest.repository.MutualFundReportRepository;
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
@RequiredArgsConstructor
@Repository
public class MutualFundReportDao implements MutualFundReportRepository {
      private final MutualFundReportRepository mutualFundReportRepository;

    @Override
    public List<MutualFundPriceHistory> findLatestClosePrice(Set<String> isinList, Set<String> codeList, LocalDate currentDate) {
        return mutualFundReportRepository.findLatestClosePrice(isinList,codeList,currentDate);
    }

    @Override
    public List<MutualFundTransaction> findAllRequiredSecurities(LocalDate startDate, LocalDate endDate, Set<Long> portfolioIds, Set<String> isinList, Set<String> codeList) {
        return mutualFundReportRepository.findAllRequiredSecurities(startDate,endDate,portfolioIds,isinList,codeList);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends MutualFundPriceHistory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends MutualFundPriceHistory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<MutualFundPriceHistory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MutualFundPriceHistory getOne(Long aLong) {
        return null;
    }

    @Override
    public MutualFundPriceHistory getById(Long aLong) {
        return null;
    }

    @Override
    public MutualFundPriceHistory getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends MutualFundPriceHistory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MutualFundPriceHistory> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends MutualFundPriceHistory> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends MutualFundPriceHistory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MutualFundPriceHistory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MutualFundPriceHistory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends MutualFundPriceHistory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends MutualFundPriceHistory> S save(S entity) {
        return null;
    }

    @Override
    public <S extends MutualFundPriceHistory> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<MutualFundPriceHistory> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<MutualFundPriceHistory> findAll() {
        return List.of();
    }

    @Override
    public List<MutualFundPriceHistory> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(MutualFundPriceHistory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends MutualFundPriceHistory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<MutualFundPriceHistory> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<MutualFundPriceHistory> findAll(Pageable pageable) {
        return null;
    }
}
