package com.cmsh.projectarpan.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PageResponse {
    @SerializedName("total")
    @Expose
    int total;
    @SerializedName("page")
    @Expose
    int page;
    @SerializedName("pages")
    @Expose
    int pages;
    @SerializedName("list")
    @Expose
    ArrayList<ListResponse> maidList;

    public PageResponse(int total, int page, int pages, ArrayList<ListResponse> maidList) {
        this.total = total;
        this.page = page;
        this.pages = pages;
        this.maidList = maidList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ArrayList<ListResponse> getMaidList() {
        return maidList;
    }

    public void setMaidList(ArrayList<ListResponse> maidList) {
        this.maidList = maidList;
    }
}
