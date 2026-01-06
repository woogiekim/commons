package io.github.woogiekim.commons.mvc.controller.dto;

import static io.github.woogiekim.commons.core.exception.Preconditions.checkNotNull;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import lombok.Getter;
import lombok.Setter;

public class GenericReq<E> implements SearchContext<E> {

    @Getter
    @Setter
    private int page = 0;

    @Setter
    private int rows = 10;

    @Getter
    @Setter
    private int totalPage = 0;

    @Getter
    @Setter
    private int records = 0;

    private final EntityPath<E> path;
    private final OrderSpecifier<?> order;

    public GenericReq(EntityPath<E> path, OrderSpecifier<?> order) {
        this.path = path;
        this.order = order;
    }

    @Override
    public void applySearchCriteria(JPQLQuery<E> q) {
        applyFieldCriteria(q);
        applyPagingCriteria(q);
    }

    @Override
    public void applyFieldCriteria(JPQLQuery<E> q) {

    }

    @Override
    public void applyPagingCriteria(JPQLQuery<E> q) {
        if (this.rows <= 0) {
            return;
        }

        q.limit(rows);
        q.offset((long) (page - 1) * rows);
    }

    @Override
    public void applyOrder(JPQLQuery<E> q) {
        checkNotNull(this.order);

        q.orderBy(this.order);
    }

    @Override
    public EntityPath<E> getEntityPathBase() {
        return this.path;
    }

    @Override
    public int getRowsPerPage() {
        return this.rows;
    }
}
