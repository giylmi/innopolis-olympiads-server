package ru.innopolis.olympiads.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giylmi on 24.03.2015.
 */
public class ViewObject {

    private String tableName;
    private List<String> columnsList;

    public Map<String, String> values(ResultSet resultSet) throws SQLException {
        Map<String, String> map = new HashMap<>();
        for (String column: columnsList)
            map.put(column, String.valueOf(resultSet.getObject(column)));
        return map;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<String> columnsList) {
        this.columnsList = columnsList;
    }
}
