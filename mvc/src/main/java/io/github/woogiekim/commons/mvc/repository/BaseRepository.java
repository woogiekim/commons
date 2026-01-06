package io.github.woogiekim.commons.mvc.repository;

import io.github.woogiekim.commons.mvc.controller.dto.SearchContext;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E, ID extends Serializable> extends JpaRepository<E, ID> {

    void detach(E entity);

    void clear();

    List<E> search(SearchContext<E> ctx);
}

