package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.MutualFundMaster;
import com.sharemarket.invest.repository.MutualFundMasterRepository;
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
public class MutualFundMasterDao implements MutualFundMasterRepository {

    private final MutualFundMasterRepository mutualFundMasterRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends MutualFundMaster> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends MutualFundMaster> List<S> saveAllAndFlush(Iterable<S> entities) {
        return mutualFundMasterRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<MutualFundMaster> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MutualFundMaster getOne(Long aLong) {
        return mutualFundMasterRepository.getOne(aLong);
    }

    @Override
    public MutualFundMaster getById(Long aLong) {
        return mutualFundMasterRepository.getById(aLong);
    }

    @Override
    public MutualFundMaster getReferenceById(Long aLong) {
        return mutualFundMasterRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends MutualFundMaster> Optional<S> findOne(Example<S> example) {
        return mutualFundMasterRepository.findOne(example);
    }

    @Override
    public <S extends MutualFundMaster> List<S> findAll(Example<S> example) {
        return mutualFundMasterRepository.findAll(example);
    }

    @Override
    public <S extends MutualFundMaster> List<S> findAll(Example<S> example, Sort sort) {
        return mutualFundMasterRepository.findAll(example, sort);
    }

    @Override
    public <S extends MutualFundMaster> Page<S> findAll(Example<S> example, Pageable pageable) {
        return mutualFundMasterRepository.findAll(example, pageable);
    }

    @Override
    public <S extends MutualFundMaster> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MutualFundMaster> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends MutualFundMaster, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return mutualFundMasterRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends MutualFundMaster> S save(S entity) {
        return mutualFundMasterRepository.save(entity);
    }

    @Override
    public <S extends MutualFundMaster> List<S> saveAll(Iterable<S> entities) {
        return mutualFundMasterRepository.saveAll(entities);
    }

    @Override
    public Optional<MutualFundMaster> findById(Long aLong) {
        return mutualFundMasterRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<MutualFundMaster> findAll() {
        return mutualFundMasterRepository.findAll();
    }

    @Override
    public List<MutualFundMaster> findAllById(Iterable<Long> longs) {
        return findAllById(longs);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(MutualFundMaster entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends MutualFundMaster> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<MutualFundMaster> findAll(Sort sort) {
        return mutualFundMasterRepository.findAll(sort);
    }

    @Override
    public Page<MutualFundMaster> findAll(Pageable pageable) {
        return mutualFundMasterRepository.findAll(pageable);
    }

    @Override
    public List<MutualFundMaster> getFilterListOfMutualFunds(List<String> isin, List<String> code) {
        return mutualFundMasterRepository.getFilterListOfMutualFunds(isin, code);
    }

}
