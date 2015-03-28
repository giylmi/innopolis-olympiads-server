package ru.innopolis.olympiads.domain;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giylmi on 28.02.2015.
 */
public class NumberInput extends Input {

    Double maxValue;
    Double minValue;

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    @Override
    public List<String> doValidate(String o, String formName) {
        if (o.isEmpty()) return ImmutableList.of();
        Double value = null;
        List<String> errors = new ArrayList<>();
        try{
            value = Double.valueOf(o);
        } catch (Exception e) {
            errors.add(getProperty(formName, "type"));
            return errors;
        }
        if (maxValue != null && maxValue < value) errors.add(getProperty(formName, "maxValue"));
        if (minValue != null && minValue > value) errors.add(getProperty(formName, "minValue"));
        return errors;
    }

    @Override
    public String convertValue(String s) {
        return s;
    }
}
