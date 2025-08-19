package com.sharemarket.invest.dao;

import com.sharemarket.invest.entity.EquityMaster;
import com.sharemarket.invest.repository.EquityMasterRepository;
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
public class EquityMasterDao implements EquityMasterRepository {

    private final EquityMasterRepository equityMasterRepository;

    @Override
    public void flush() {

    }

    @Override
    public <S extends EquityMaster> S saveAndFlush(S entity) {
        return equityMasterRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends EquityMaster> List<S> saveAllAndFlush(Iterable<S> entities) {
        return equityMasterRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<EquityMaster> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public EquityMaster getOne(Long aLong) {
        return equityMasterRepository.getOne(aLong);
    }

    @Override
    public EquityMaster getById(Long aLong) {
        return equityMasterRepository.getById(aLong);
    }

    @Override
    public EquityMaster getReferenceById(Long aLong) {
        return equityMasterRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends EquityMaster> Optional<S> findOne(Example<S> example) {
        return equityMasterRepository.findOne(example);
    }

    @Override
    public <S extends EquityMaster> List<S> findAll(Example<S> example) {
        return equityMasterRepository.findAll(example);
    }

    @Override
    public <S extends EquityMaster> List<S> findAll(Example<S> example, Sort sort) {
        return equityMasterRepository.findAll(example, sort);
    }

    @Override
    public <S extends EquityMaster> Page<S> findAll(Example<S> example, Pageable pageable) {
        return equityMasterRepository.findAll(example, pageable);
    }

    @Override
    public <S extends EquityMaster> long count(Example<S> example) {
        return equityMasterRepository.count(example);
    }

    @Override
    public <S extends EquityMaster> boolean exists(Example<S> example) {
        return equityMasterRepository.exists(example);
    }

    @Override
    public <S extends EquityMaster, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return equityMasterRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends EquityMaster> S save(S entity) {
        return equityMasterRepository.save(entity);
    }

    @Override
    public <S extends EquityMaster> List<S> saveAll(Iterable<S> entities) {
        return equityMasterRepository.saveAll(entities);
    }

    @Override
    public Optional<EquityMaster> findById(Long aLong) {
        return equityMasterRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return equityMasterRepository.existsById(aLong);
    }

    @Override
    public List<EquityMaster> findAll() {
        return equityMasterRepository.findAll();
    }

    @Override
    public List<EquityMaster> findAllById(Iterable<Long> longs) {
        return equityMasterRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return equityMasterRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(EquityMaster entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends EquityMaster> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<EquityMaster> findAll(Sort sort) {
        return equityMasterRepository.findAll(sort);
    }

    @Override
    public Page<EquityMaster> findAll(Pageable pageable) {
        return equityMasterRepository.findAll(pageable);
    }

    @Override
    public List<EquityMaster> findByFilterEqList(List<String> isin, List<String> code, List<String> series) {
        return equityMasterRepository.findByFilterEqList(isin, code, series);
    }

    @Override
    public boolean existsAllByStatus(Integer status) {
        return equityMasterRepository.existsAllByStatus(status);
    }

    @Override
    public List<EquityMaster> findAllByStatus(Integer status) {
        return equityMasterRepository.findAllByStatus(status);
    }

    @Override
    public boolean existsByEqIdAndStatus(Long eqId,Integer status) {
        return equityMasterRepository.existsByEqIdAndStatus(eqId,status);
    }
}
