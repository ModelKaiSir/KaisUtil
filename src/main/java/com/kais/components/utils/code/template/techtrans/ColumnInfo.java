package com.kais.components.utils.code.template.techtrans;

/**
 * 字段信息
 *
 * @author QiuKai
 */
public class ColumnInfo {

    /**
     * 字段名
     */
    private String name;
    /**
     * 描述
     */
    private String comment;
    /**
     * 长度
     */
    private int length;

    private boolean primaryKey;
    private boolean nullable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public static ColumnInfo of(String name, String comment) {

        ColumnInfo r = new ColumnInfo();
        r.name = name;
        r.comment = comment;
        r.length = 0;
        return r;
    }

    public static void builder(String name){

    }
}
