package com.jing.clean_test.model;

import java.util.List;

/**
 * Created by Ding.pengqiang
 * on 2017/3/16.
 */

public class JavaBean {

    private String title;

    private List<RowsBean> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }
}
