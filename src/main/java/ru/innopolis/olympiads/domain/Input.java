package ru.innopolis.olympiads.domain;

import com.google.common.collect.ImmutableList;
import ru.innopolis.olympiads.config.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giylmi on 25.02.2015.
 */
public abstract class Input {

    protected String columnName;
    protected Boolean isRequired;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    protected abstract List<String> doValidate(String value);

    public List<String> validate(String value){
        List<String> errors = new ArrayList<>();
        if (isRequired != null && isRequired && value.isEmpty() ) errors.add(getProperty("required"));
        return ImmutableList.<String>builder().addAll(errors).addAll(doValidate(value)).build();
    };

    protected String getProperty(String propertyName){
        return ApplicationContextAware.getPropertiesHolder().getProperty(columnName + "." + propertyName);
    }

    public abstract String convertValue(String s);
}
