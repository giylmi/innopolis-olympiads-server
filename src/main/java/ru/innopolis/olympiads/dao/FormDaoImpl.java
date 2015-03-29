package ru.innopolis.olympiads.dao;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.innopolis.olympiads.dao.adapter.DataSourceAdapter;
import ru.innopolis.olympiads.dao.adapter.QueryManager;
import ru.innopolis.olympiads.domain.Form;
import ru.innopolis.olympiads.domain.Input;
import ru.innopolis.olympiads.domain.ViewObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giylmi on 28.02.2015.
 */
@Repository
public class FormDaoImpl implements FormDao {

    Logger logger = LoggerFactory.getLogger(FormDaoImpl.class);

    @Autowired
    DataSourceAdapter ds;

    @Autowired
    ApplicationContext context;

    @Override
    public Boolean saveForm(Map<String, String> map, String formId) {
        Form form = getFormById(formId);

        try {
            ImmutableMap.Builder builder = ImmutableMap.builder();
            for (Input input : form.getInputs()) {

                String value = map.get(input.getColumnName());
                if (value != null)
                    map.put(input.getColumnName(), input.convertValue(map.get(input.getColumnName())));
            }
            builder.put("params", map);
            builder.put("tableName", form.getTableName());
            ds.execute(QueryManager.getQuery("sql/saveForm.ftl", builder.build()));
        } catch (Exception e) {
			e.printStackTrace();
            logger.error("failed", e);
            return false;
        }
        return true;
    }

    @Override
    public Form getFormById(String formId) {
        try{
            Form form = context.getBean(formId, Form.class);
            Boolean isActive = ds.query("select isActive from forms where `formName`='" + formId + "';", new ResultSetExtractor<Boolean>() {
                @Override
                public Boolean extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    boolean hasNext = resultSet.next();
                    return hasNext && resultSet.getBoolean("isActive");
                }
            });
            form.setIsActive(isActive);
            return form;
        } catch (BeansException e) {
			e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, String>> allValues(final ViewObject vo) {
        return ds.query(QueryManager.getQuery("sql/all.ftl", ImmutableMap.<String, Object>builder().put("vo", vo).build()), new RowMapper<Map<String, String>>() {
            @Override
            public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
                return vo.values(resultSet);
            }
        });
    }

    @Override
    public ViewObject getVOById(String voId) {
        try {
            return context.getBean(voId, ViewObject.class);
        } catch (BeansException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Form> allForms() {
        return context.getBeansOfType(Form.class);
    }

    @Override
    public boolean checkUnique(String formName, String[] values, String[] columnNames) {
        return ds.query(QueryManager.getQuery("sql/unique.ftl",
                        ImmutableMap.<String, Object>builder()
                                .put("values", values)
                                .put("columnNames", columnNames)
                                .put("formName", formName).build()),
                new ResultSetExtractor<Boolean>() {
                    @Override
                    public Boolean extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        resultSet.next();
                        return resultSet.getLong(1) == 0L;
                    }
                });
    }

    @Override
    public Map<String, List<Object>> getTableValues(String formId) {
        Form form = getFormById(formId);
        Map<String, List<Object>> result = new HashMap<>();
        if (form != null) {
            result = ds.query("select * from " + form.getTableName(), new ResultSetExtractor<Map<String, List<Object>>>() {
                @Override
                public Map<String, List<Object>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    Map<String, List<Object>> result = new HashMap<>();
                    while (resultSet.next()) {
                        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                            String columnName = resultSet.getMetaData().getColumnName(i);
                            List<Object> values = result.get(columnName);
                            if (values == null) {
                                values = new ArrayList<Object>();
                                result.put(columnName, values);
                            }
                            if (!columnName.equals("status"))
                                values.add(String.valueOf(resultSet.getObject(i)));
                            else {
                                Object value = resultSet.getObject(i);
                                values.add(value);
                            }
                        }
                    }
                    return result;
                }
            });
        }
        return result;
    }
}
