package io.github.woogiekim.commons.mvc.controller.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class GenericRes<E> {
    protected int page = 0;
    protected int totalPage = 0;
    protected int records = 0;
    protected List<E> rows = new ArrayList<>();

    public <RQ> void bind(GenericReq<RQ> req, List<E> data) {
        this.page = req.getPage();
        this.totalPage = req.getTotalPage();
        this.records = req.getRecords();
        this.rows = data;
    }
}
