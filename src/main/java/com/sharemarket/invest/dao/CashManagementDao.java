package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.CashManagement;
import com.sharemarket.invest.repository.CashManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class CashManagementDao implements CashManagementRepository {
    private final CashManagementRepository cashManagementRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends CashManagement> S saveAndFlush(S entity) {
        return cashManagementRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends CashManagement> List<S> saveAllAndFlush(Iterable<S> entities) {
        return cashManagementRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<CashManagement> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public CashManagement getOne(Long aLong) {
        return cashManagementRepository.getOne(aLong);
    }

    @Override
    public CashManagement getById(Long aLong) {
        return cashManagementRepository.getById(aLong);
    }

    @Override
    public CashManagement getReferenceById(Long aLong) {
        return cashManagementRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends CashManagement> Optional<S> findOne(Example<S> example) {
        return cashManagementRepository.findOne(example);
    }

    @Override
    public <S extends CashManagement> List<S> findAll(Example<S> example) {
        return cashManagementRepository.findAll(example);
    }

    @Override
    public <S extends CashManagement> List<S> findAll(Example<S> example, Sort sort) {
        return cashManagementRepository.findAll(example, sort);
    }

    @Override
    public <S extends CashManagement> Page<S> findAll(Example<S> example, Pageable pageable) {
        return cashManagementRepository.findAll(example, pageable);
    }

    @Override
    public <S extends CashManagement> long count(Example<S> example) {
        return cashManagementRepository.count(example);
    }

    @Override
    public <S extends CashManagement> boolean exists(Example<S> example) {
        return cashManagementRepository.exists(example);
    }

    @Override
    public <S extends CashManagement, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return cashManagementRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends CashManagement> S save(S entity) {
        return cashManagementRepository.save(entity);
    }

    @Override
    public <S extends CashManagement> List<S> saveAll(Iterable<S> entities) {
        return cashManagementRepository.saveAll(entities);
    }

    @Override
    public Optional<CashManagement> findById(Long aLong) {
        return cashManagementRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return cashManagementRepository.existsById(aLong);
    }

    @Override
    public List<CashManagement> findAll() {
        return cashManagementRepository.findAll();
    }

    @Override
    public List<CashManagement> findAllById(Iterable<Long> longs) {
        return cashManagementRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(CashManagement entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends CashManagement> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<CashManagement> findAll(Sort sort) {
        return cashManagementRepository.findAll(sort);
    }

    @Override
    public Page<CashManagement> findAll(Pageable pageable) {
        return cashManagementRepository.findAll(pageable);
    }

    @Override
    public List<CashManagement> findByAccountNumber(Long accountNo) {
        return cashManagementRepository.findByAccountNumber(accountNo);
    }

    @Override
    public List<CashManagement> findByPortfolioId(Long portfolioId) {
        return cashManagementRepository.findByPortfolioId(portfolioId);
    }
}
