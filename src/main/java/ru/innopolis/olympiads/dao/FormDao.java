package ru.innopolis.olympiads.dao;

import java.util.Map;

/**
 * Created by giylmi on 28.02.2015.
 */
public interface FormDao {

    Boolean saveForm(Map<String, String> form, String tableName);
}
