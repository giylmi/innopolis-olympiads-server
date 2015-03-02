package ru.innopolis.olympiads.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by giylmi on 28.02.2015.
 */
public class TextInput extends Input {

    protected Long minLength;
    protected Long maxLength;
    protected String regex;

    public Long getMinLength() {
        return minLength;
    }

    public void setMinLength(Long minLength) {
        this.minLength = minLength;
    }

    public Long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public List<String> doValidate(String o) {
        if (o.isEmpty()) return ImmutableList.of();
        String value = null;
        List<String> errors = new ArrayList<>();
        try{
            value = (String) o;
        } catch (Exception e) {
            errors.add(getProperty("value.type"));
            return errors;
        }
        if (maxLength != null && value.length() > maxLength) errors.add(getProperty("value.maxLength"));
        if (minLength != null && value.length() < minLength) errors.add(getProperty("value.minLength"));
        if (regex != null) {
            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) errors.add(getProperty("value.regex"));
        }
        return errors;
    }

    @Override
    public String convertValue(String s) {
        return "'" + s + "'";
    }
}
