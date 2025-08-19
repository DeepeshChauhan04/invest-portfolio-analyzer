package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.entity.MutualFundTransaction;
import com.sharemarket.invest.repository.MutualFundTransactionRepository;
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
public class MutualFundTransactionDao implements MutualFundTransactionRepository {

    private final MutualFundTransactionRepository mutualFundTransactionRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends MutualFundTransaction> S saveAndFlush(S entity) {
        return mutualFundTransactionRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends MutualFundTransaction> List<S> saveAllAndFlush(Iterable<S> entities) {
        return mutualFundTransactionRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<MutualFundTransaction> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MutualFundTransaction getOne(Long aLong) {
        return mutualFundTransactionRepository.getOne(aLong);
    }

    @Override
    public MutualFundTransaction getById(Long aLong) {
        return mutualFundTransactionRepository.getById(aLong);
    }

    @Override
    public MutualFundTransaction getReferenceById(Long aLong) {
        return mutualFundTransactionRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends MutualFundTransaction> Optional<S> findOne(Example<S> example) {
        return mutualFundTransactionRepository.findOne(example);
    }

    @Override
    public <S extends MutualFundTransaction> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends MutualFundTransaction> List<S> findAll(Example<S> example, Sort sort) {
        return mutualFundTransactionRepository.findAll(example, sort);
    }

    @Override
    public <S extends MutualFundTransaction> Page<S> findAll(Example<S> example, Pageable pageable) {
        return mutualFundTransactionRepository.findAll(example, pageable);
    }

    @Override
    public <S extends MutualFundTransaction> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MutualFundTransaction> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends MutualFundTransaction, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return mutualFundTransactionRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends MutualFundTransaction> S save(S entity) {
        return mutualFundTransactionRepository.save(entity);
    }

    @Override
    public <S extends MutualFundTransaction> List<S> saveAll(Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public Optional<MutualFundTransaction> findById(Long aLong) {
        return findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<MutualFundTransaction> findAll() {
        return mutualFundTransactionRepository.findAll();
    }

    @Override
    public List<MutualFundTransaction> findAllById(Iterable<Long> longs) {
        return mutualFundTransactionRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(MutualFundTransaction entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends MutualFundTransaction> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<MutualFundTransaction> findAll(Sort sort) {
        return mutualFundTransactionRepository.findAll(sort);
    }

    @Override
    public Page<MutualFundTransaction> findAll(Pageable pageable) {
        return mutualFundTransactionRepository.findAll(pageable);

    }

    @Override
    public int getNetAvailableQuantity(String code, String isin, Long portfolioId) {
        return mutualFundTransactionRepository.getNetAvailableQuantity(code,isin,portfolioId);
    }

    @Override
    public List<EquityTransaction> findAllRequiredSecurities(LocalDate startDate, LocalDate endDate, Long portfolioId, Set<String> isinList, Set<String> codeList) {
        return mutualFundTransactionRepository.findAllRequiredSecurities(startDate,endDate,portfolioId,isinList,codeList);
    }
}
