package ru.innopolis.olympiads.domain;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import ru.innopolis.olympiads.config.ApplicationContextAware;
import ru.innopolis.olympiads.dao.FormDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giylmi on 25.02.2015.
 */
public class Form {

    @Autowired
    FormDao formDao;

    private String tableName;
    private List<Input> inputs;
    private Boolean isActive;

    private Map<String, List<String>> uniqueCompositeKeysMap;

    public Map<String, List<String>> isValid(Map<String, String> form){
        Map<String, List<String>> errorsMap = new HashMap<>();
        if (isActive != null && !isActive) {
            errorsMap.put(tableName, Lists.newArrayList(getProperty("notActive")));
            return errorsMap;
        }
        for (Input field : inputs) {
            String value = form.get(field.getColumnName());
            List<String> errors = field.validate(value, tableName);
            if (!errors.isEmpty())
                errorsMap.put(field.getColumnName(), errors);
        }
        if (errorsMap.isEmpty()) {
            if (uniqueCompositeKeysMap != null && !uniqueCompositeKeysMap.isEmpty())
                for (Map.Entry<String, List<String>> entry: uniqueCompositeKeysMap.entrySet()){
                    String[] values = new String[entry.getValue().size()];
                    int i = 0;
                    for (String columnName: entry.getValue()) {
                        String value = form.get(columnName);
                        for (Input input: inputs)
                            if (input.getColumnName().equals(columnName)) {
                                values[i] = input.convertValue(value);
                            }
                        i++;
                    }
                    if (!formDao.checkUnique(tableName, values, (String[]) entry.getValue().toArray()))
                        errorsMap.put(entry.getKey(), Lists.newArrayList(getProperty(entry.getKey() + ".unique")));
                }
        }
        return errorsMap;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Map<String, List<String>> getUniqueCompositeKeysMap() {
        return uniqueCompositeKeysMap;
    }

    public void setUniqueCompositeKeysMap(Map<String, List<String>> uniqueCompositeKeysMap) {
        this.uniqueCompositeKeysMap = uniqueCompositeKeysMap;
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
