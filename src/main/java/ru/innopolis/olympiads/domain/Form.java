package ru.innopolis.olympiads.domain;

import com.google.common.collect.Lists;
import ru.innopolis.olympiads.config.ApplicationContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giylmi on 25.02.2015.
 */
public class Form {

    private String tableName;
    private List<Input> inputs;
    private Boolean isActive;

    public Map<String, List<String>> isValid(Map<String, String> form){
        Map<String, List<String>> errorsMap = new HashMap<>();
        if (isActive) {
            errorsMap.put(tableName, Lists.newArrayList(getProperty("notActive")));
            return errorsMap;
        }
        for (Input field : inputs) {
            String value = form.get(field.getColumnName());
            List<String> errors = field.validate(value);
            if (!errors.isEmpty())
                errorsMap.put(field.getColumnName(), errors);
        }
        return errorsMap;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    private String getProperty(String unreadable) {
        return ApplicationContextAware.getPropertiesHolder().getProperty(tableName + "." + unreadable);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }
}
