package ru.innopolis.olympiads.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giylmi on 30.03.2015.
 */
public class Table {

    private Column[] data;
    private int rows;
    private int columns;

    public Table(int columns) {
        data = new Column[columns];
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Column[] getData() {
        return data;
    }

    public void setData(Column[] data) {
        this.data = data;
    }

    public static class Column implements Comparable<Column> {

        private String name;
        private List<Object> data;

        public Column(String columnName) {
            this.name = columnName;
            data = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }

        @Override
        public int compareTo(Column o) {
            if ("id".equals(name) || "status".equals(o.name))
                return -1;
            if ("status".equals(name) || "id".equals(o.name))
                return 1;
            return name.compareTo(o.name);
        }
    }
}
