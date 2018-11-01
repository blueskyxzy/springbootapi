package com.xzy.springbootapi.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xzy on 18/11/1  .
 */

public class PageResultVo<T>{
    private Long start;

    private Long limit;

    private Long returnCount;

    private Long totalCount;

    private List<T> returnList = new ArrayList<>();

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Long returnCount) {
        this.returnCount = returnCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<T> returnList) {
        this.returnList = returnList;
    }
}
