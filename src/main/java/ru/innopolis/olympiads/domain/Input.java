package ru.innopolis.olympiads.domain;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import ru.innopolis.olympiads.config.ApplicationContextAware;
import ru.innopolis.olympiads.dao.FormDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giylmi on 25.02.2015.
 */
public abstract class Input {

    @Autowired
    FormDao formDao;

    protected String columnName;
    protected Boolean isRequired;
    protected Boolean unique;

    public Boolean getUnique() {
        return unique != null && unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

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

    protected abstract List<String> doValidate(String value, String formName);

    public List<String> validate(String value, String formName){
        List<String> errors = new ArrayList<>();
        if (isRequired != null && isRequired && value.isEmpty() ) errors.add(getProperty(formName, "required"));
        List<String> doValidate = doValidate(value, formName);
        List<String> uniqueError = new ArrayList<>();
        if (errors.isEmpty() && doValidate.isEmpty() &&  unique != null && unique) uniqueError = validateUnique(value, formName);
        return ImmutableList.<String>builder().addAll(errors).addAll(doValidate).addAll(uniqueError).build();
    }

    private List<String> validateUnique(String value, String formName) {
        List<String> uniqueError = new ArrayList<>();
        if (!formDao.checkUnique(formName, new String[]{convertValue(value)}, new String[]{columnName})) uniqueError.add(getProperty(formName, "unique"));
        return uniqueError;
    }

    protected String getProperty(String formName, String propertyName){
        return ApplicationContextAware.getPropertiesHolder().getProperty(formName + "." + columnName + "." + propertyName);
    }

    public abstract String convertValue(String s);
}
