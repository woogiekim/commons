package io.github.woogiekim.commons.mvc.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import io.github.woogiekim.commons.mvc.controller.dto.SearchContext;
import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class SimpleBaseRepository<E, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements BaseRepository<E, ID> {

    private final EntityManager entityManager;
    private final Querydsl querydsl;

    public SimpleBaseRepository(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityManager = entityManager;

        final var pathBuilder = new PathBuilderFactory().create(entityInformation.getJavaType());

        this.querydsl = new Querydsl(this.entityManager, pathBuilder);
    }

    @Override
    public void detach(E entity) {
        this.entityManager.detach(entity);
    }

    @Override
    public void clear() {
        this.entityManager.clear();
    }

    @Override
    public List<E> search(SearchContext<E> sc) {
        if (sc.getEntityPathBase() == null) {
            return Collections.emptyList();
        }

        return getResults(sc, sc.getEntityPathBase());
    }

    private List<E> getResults(SearchContext<E> sc, EntityPath<E> path) {
        @SuppressWarnings("unchecked") final var q = (JPQLQuery<E>) this.querydsl.createQuery(path);

        sc.applyFieldCriteria(q);
        sc.applyOrder(q);

        final var records = q.fetchCount();

        sc.setRecords((int) records);
        sc.setTotalPage((int) Math.ceil((double) records / (double) sc.getRowsPerPage()));

        return q.select(path).fetch();
    }
}
