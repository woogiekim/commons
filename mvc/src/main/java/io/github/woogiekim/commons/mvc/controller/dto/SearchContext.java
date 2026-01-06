package io.github.woogiekim.commons.mvc.controller.dto;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPQLQuery;

public interface SearchContext<E> {
    void applySearchCriteria(JPQLQuery<E> q);

    void applyFieldCriteria(JPQLQuery<E> q);

    void applyPagingCriteria(JPQLQuery<E> q);

    void applyOrder(JPQLQuery<E> q);

    EntityPath<E> getEntityPathBase();

    void setRows(int rows);

    void setTotalPage(int totalRows);

    void setRecords(int records);

    int getTotalPage();

    int getPage();

    int getRowsPerPage();
}
