package com.src.oauth2.searchfilter;


import com.src.oauth2.constants.ApplicationConstant;

public class PaginationFilter {

    public PaginationFilter() {
    }

    public PaginationFilter(Integer offset, Integer max) {
        if (offset == null) offset = 0;
        if (max == null) max = ApplicationConstant.DEFAULT_PAGE_SIZE;//default ...// TODO: 21/4/18 make way to replace this val with configurable one
        this.offset = offset;
        this.max = max;
    }

    private Integer offset;
    private Integer max;

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
