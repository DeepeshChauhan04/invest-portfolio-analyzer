package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.PortfolioDetails;
import com.sharemarket.invest.repository.PortfolioDetailsRepository;
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
public class PortfolioDetailsDao implements PortfolioDetailsRepository {

    private final PortfolioDetailsRepository portfolioDetailsRepository;

    @Override
    public <S extends PortfolioDetails> S save(S entity) {
        return portfolioDetailsRepository.save(entity);
    }

    @Override
    public List<PortfolioDetails> findAll() {
        return portfolioDetailsRepository.findAll();
    }

    @Override
    public boolean existsById(Long aLong) {

        return portfolioDetailsRepository.existsById(aLong);
    }

    @Override
    public PortfolioDetails getById(Long aLong) {
        return portfolioDetailsRepository.getById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        portfolioDetailsRepository.deleteById(aLong);
    }

    @Override
    public List<PortfolioDetails> findAllById(Iterable<Long> longs) {
        return portfolioDetailsRepository.findAllById(longs);
    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends PortfolioDetails> S saveAndFlush(S entity) {
        return portfolioDetailsRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends PortfolioDetails> List<S> saveAllAndFlush(Iterable<S> entities) {
        return portfolioDetailsRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<PortfolioDetails> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PortfolioDetails getOne(Long aLong) {
        return portfolioDetailsRepository.getOne(aLong);
    }


    @Override
    public PortfolioDetails getReferenceById(Long aLong) {
        return portfolioDetailsRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends PortfolioDetails> Optional<S> findOne(Example<S> example) {
        return portfolioDetailsRepository.findOne(example);
    }

    @Override
    public <S extends PortfolioDetails> List<S> findAll(Example<S> example) {
        return portfolioDetailsRepository.findAll(example);
    }

    @Override
    public <S extends PortfolioDetails> List<S> findAll(Example<S> example, Sort sort) {
        return portfolioDetailsRepository.findAll(example, sort);
    }

    @Override
    public <S extends PortfolioDetails> Page<S> findAll(Example<S> example, Pageable pageable) {
        return portfolioDetailsRepository.findAll(example, pageable);
    }

    @Override
    public <S extends PortfolioDetails> long count(Example<S> example) {
        return portfolioDetailsRepository.count(example);
    }

    @Override
    public <S extends PortfolioDetails> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PortfolioDetails, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return portfolioDetailsRepository.findBy(example, queryFunction);
    }


    @Override
    public <S extends PortfolioDetails> List<S> saveAll(Iterable<S> entities) {
        return portfolioDetailsRepository.saveAll(entities);
    }

    @Override
    public Optional<PortfolioDetails> findById(Long aLong) {
        return portfolioDetailsRepository.findById(aLong);
    }


    @Override
    public long count() {
        return 0;
    }


    @Override
    public void delete(PortfolioDetails entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends PortfolioDetails> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PortfolioDetails> findAll(Sort sort) {
        return portfolioDetailsRepository.findAll(sort);
    }

    @Override
    public Page<PortfolioDetails> findAll(Pageable pageable) {
        return portfolioDetailsRepository.findAll(pageable);
    }

    @Override
    public PortfolioDetails findByPortfolioIdAndStatus(Long portfolioId, Integer status) {
        return portfolioDetailsRepository.findByPortfolioIdAndStatus(portfolioId, status);
    }

    @Override
    public boolean existsByPortfolioIdAndStatus(Long portfolioId, Integer status) {
        return portfolioDetailsRepository.existsByPortfolioIdAndStatus(portfolioId, status);
    }

    @Override
    public  List<PortfolioDetails>  findAllByStatus(Integer status) {
        return portfolioDetailsRepository.findAllByStatus(status);
    }
}
