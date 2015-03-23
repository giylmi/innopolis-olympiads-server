package ru.innopolis.olympiads.dao;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.olympiads.dao.adapter.DataSourceAdapter;
import ru.innopolis.olympiads.dao.adapter.QueryManager;
import ru.innopolis.olympiads.domain.Form;
import ru.innopolis.olympiads.domain.Input;

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
    Form form;

    @Override
    public Boolean saveForm(Map<String, String> map, String tableName) {
        Boolean formActive = false;

        try {
            if (formActive) {
                ImmutableMap.Builder builder = ImmutableMap.builder();
                for (Input input : form.getInputs()) {

                    String value = map.get(input.getColumnName());
                    if (value != null)
                        map.put(input.getColumnName(), input.convertValue(map.get(input.getColumnName())));
                }
                builder.put("params", map);
                builder.put("tableName", tableName);
                ds.execute(QueryManager.getQuery("sql/saveForm.ftl", builder.build()));
            }
        } catch (Exception e) {
            logger.error("failed", e);
            return false;
        }
        return true;
    }
}
