package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.entity.MutualFundPriceHistory;
import com.sharemarket.invest.repository.MutualFundPriceHistoryRepository;
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
public class MutualFundPriceHistoryDao implements MutualFundPriceHistoryRepository {

    private final MutualFundPriceHistoryRepository mutualFundPriceHistoryRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends com.sharemarket.invest.entity.MutualFundPriceHistory> S saveAndFlush(S entity) {
        return mutualFundPriceHistoryRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends com.sharemarket.invest.entity.MutualFundPriceHistory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return mutualFundPriceHistoryRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<com.sharemarket.invest.entity.MutualFundPriceHistory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MutualFundPriceHistory getOne(Long aLong) {
        return mutualFundPriceHistoryRepository.getOne(aLong);
    }

    @Override
    public MutualFundPriceHistory getById(Long aLong) {
        return mutualFundPriceHistoryRepository.getById(aLong);
    }

    @Override
    public MutualFundPriceHistory getReferenceById(Long aLong) {
        return mutualFundPriceHistoryRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends MutualFundPriceHistory> Optional<S> findOne(Example<S> example) {
        return mutualFundPriceHistoryRepository.findOne(example);
    }

    @Override
    public <S extends MutualFundPriceHistory> List<S> findAll(Example<S> example) {
        return mutualFundPriceHistoryRepository.findAll(example);
    }

    @Override
    public <S extends MutualFundPriceHistory> List<S> findAll(Example<S> example, Sort sort) {
        return mutualFundPriceHistoryRepository.findAll(example, sort);
    }

    @Override
    public <S extends MutualFundPriceHistory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return mutualFundPriceHistoryRepository.findAll(example, pageable);
    }

    @Override
    public <S extends MutualFundPriceHistory> long count(Example<S> example) {
        return mutualFundPriceHistoryRepository.count(example);
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
        return mutualFundPriceHistoryRepository.saveAll(entities);
    }

    @Override
    public Optional<MutualFundPriceHistory> findById(Long aLong) {
        return mutualFundPriceHistoryRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<com.sharemarket.invest.entity.MutualFundPriceHistory> findAll() {
        return mutualFundPriceHistoryRepository.findAll();
    }

    @Override
    public List<com.sharemarket.invest.entity.MutualFundPriceHistory> findAllById(Iterable<Long> longs) {
        return mutualFundPriceHistoryRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(com.sharemarket.invest.entity.MutualFundPriceHistory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends com.sharemarket.invest.entity.MutualFundPriceHistory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<com.sharemarket.invest.entity.MutualFundPriceHistory> findAll(Sort sort) {
        return mutualFundPriceHistoryRepository.findAll(sort);
    }

    @Override
    public Page<com.sharemarket.invest.entity.MutualFundPriceHistory> findAll(Pageable pageable) {
        return mutualFundPriceHistoryRepository.findAll(pageable);
    }

    @Override
    public List<MutualFundPriceHistory> findByNavDate(LocalDate navDate) {
        return  mutualFundPriceHistoryRepository.findByNavDate(navDate);
    }


}
