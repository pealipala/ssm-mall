package com.taotao.common.pojo;

import java.util.List;

/**
 * 查询返回结果pojo
 * @author : yechaoze
 * @date : 2019/5/13 8:11
 */
public class SearchResult {
    private long recordCount;
    private int totalPages;
    private List<SearchItem> itemList;
    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }


}
