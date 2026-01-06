package io.github.woogiekim.commons.mvc.service;

import io.github.woogiekim.commons.mvc.controller.dto.SearchContext;
import io.github.woogiekim.commons.mvc.repository.BaseRepository;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseGenericService<E, ID extends Serializable, R extends BaseRepository<E, ID>> implements GenericService<E, ID, R> {
    
    @Autowired
    protected R repository;

    @Override
    public E add(E entity) {
        return this.repository.save(entity);
    }

    @Override
    public E update(E entity) {
        return this.repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        this.repository.deleteById(id);
    }

    @Override
    public void delete(E entity) {
        this.repository.delete(entity);
    }

    @Override
    public E get(ID id) {
        return this.repository.getReferenceById(id);
    }

    @Override
    public E find(ID id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public List<E> search(SearchContext<E> sc) {
        return this.repository.search(sc);
    }
}
