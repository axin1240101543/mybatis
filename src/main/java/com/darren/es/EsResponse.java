package com.darren.es;

import java.util.List;

/**
 * <h3>mybatis</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2022年07月31日 11:03:33
 **/
public class EsResponse<T> {

    /**
     * 所有的列名 + 类型
     */
    private List<Column> columns;

    /**
     * 数据行
     */
    private T rows;

    /**
     * 游标
     */
    private String cursor;

    public static class Column{

        private String name;

        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Column{" +
                    "name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    @Override
    public String toString() {
        return "EsResponse{" +
                "columns=" + columns +
                ", rows=" + rows +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}

