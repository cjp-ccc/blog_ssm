package com.cjp.entity;

public class PageBean {
    //当前页面，从1开始
    private int page;
    //页面大小
    private int pageSize;
    //从第几条数据查询
    private int start;

    public PageBean(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (this.page-1)*this.pageSize;
    }


}
