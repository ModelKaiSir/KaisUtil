package com.kais.components.utils.code.template.techtrans;

import java.util.List;

/***
 * 表信息
 * @author QiuKai
 */
public class TableInfo {

    private String name;
    private String alisa;
    private List<ColumnInfo> cols;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlisa() {
        return alisa;
    }

    public void setAlisa(String alisa) {
        this.alisa = alisa;
    }

    public List<ColumnInfo> getCols() {
        return cols;
    }

    public void setCols(List<ColumnInfo> cols) {
        this.cols = cols;
    }
}
