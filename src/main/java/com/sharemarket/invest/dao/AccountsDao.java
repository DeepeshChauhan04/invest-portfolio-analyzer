package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.AccountDetails;
import com.sharemarket.invest.repository.AccountRepository;
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
public class AccountsDao implements AccountRepository {

    private final AccountRepository accountRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends AccountDetails> S saveAndFlush(S entity) {
        return accountRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends AccountDetails> List<S> saveAllAndFlush(Iterable<S> entities) {
        return accountRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<AccountDetails> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AccountDetails getOne(Long aLong) {
        return accountRepository.getOne(aLong);
    }

    @Override
    public AccountDetails getById(Long aLong) {
        return accountRepository.getById(aLong);
    }

    @Override
    public AccountDetails getReferenceById(Long aLong) {
        return accountRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends AccountDetails> Optional<S> findOne(Example<S> example) {
        return accountRepository.findOne(example);
    }

    @Override
    public <S extends AccountDetails> List<S> findAll(Example<S> example) {
        return accountRepository.findAll(example);
    }

    @Override
    public <S extends AccountDetails> List<S> findAll(Example<S> example, Sort sort) {
        return accountRepository.findAll(example, sort);
    }

    @Override
    public <S extends AccountDetails> Page<S> findAll(Example<S> example, Pageable pageable) {
        return accountRepository.findAll(example, pageable);
    }

    @Override
    public <S extends AccountDetails> long count(Example<S> example) {
        return accountRepository.count(example);
    }

    @Override
    public <S extends AccountDetails> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AccountDetails, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return accountRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends AccountDetails> S save(S entity) {
        return accountRepository.save(entity);
    }

    @Override
    public <S extends AccountDetails> List<S> saveAll(Iterable<S> entities) {
        return accountRepository.saveAll(entities);
    }

    @Override
    public Optional<AccountDetails> findById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<AccountDetails> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDetails> findAllById(Iterable<Long> longs) {
        return accountRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(AccountDetails entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends AccountDetails> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<AccountDetails> findAll(Sort sort) {
        return accountRepository.findAll(sort);
    }

    @Override
    public Page<AccountDetails> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public AccountDetails findByAccountIdAndStatus(Long accountId, Integer status) {
        return accountRepository.findByAccountIdAndStatus(accountId, status);
    }

    @Override
    public Optional<AccountDetails> findByUserIdAndDefaultAccountTrue(Long userId) {
        return accountRepository.findByUserIdAndDefaultAccountTrue(userId);
    }

    @Override
    public Optional<AccountDetails> findDefaultAccountByPortfolioId(Long portfolioId) {
        return accountRepository.findDefaultAccountByPortfolioId(portfolioId);
    }

    @Override
    public List<AccountDetails> findAllByStatus(Integer status) {
        return accountRepository.findAllByStatus(status);
    }

    @Override
    public boolean existsByAccountIdAndStatus(Long accountId, Integer status) {
        return accountRepository.existsByAccountIdAndStatus(accountId,status);

    }

    @Override
    public List<AccountDetails> findAccountDetailsByUserIdAndStatus(Long userId ,Integer status) {
        return accountRepository.findAccountDetailsByUserIdAndStatus(userId,status);
    }
}
