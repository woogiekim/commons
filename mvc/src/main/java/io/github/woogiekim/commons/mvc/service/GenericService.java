package io.github.woogiekim.commons.mvc.service;

import io.github.woogiekim.commons.mvc.controller.dto.SearchContext;
import io.github.woogiekim.commons.mvc.repository.BaseRepository;
import java.io.Serializable;
import java.util.List;

public interface GenericService<E, ID extends Serializable, R extends BaseRepository<E, ID>> {

    E add(E entity);

    E update(E entity);

    void deleteById(ID id);

    void delete(E entity);

    E get(ID id);

    E find(ID id);

    List<E> search(SearchContext<E> sc);
}
