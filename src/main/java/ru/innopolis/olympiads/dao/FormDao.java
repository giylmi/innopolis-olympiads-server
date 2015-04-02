package ru.innopolis.olympiads.dao;

import ru.innopolis.olympiads.domain.Form;
import ru.innopolis.olympiads.domain.Table;
import ru.innopolis.olympiads.domain.ViewObject;

import java.util.List;
import java.util.Map;

/**
 * Created by giylmi on 28.02.2015.
 */
public interface FormDao {

    Boolean saveForm(Map<String, String> form, String tableName);

    Form getFormById(String formId);

    List<Map<String, Object>> allValues(ViewObject vo);

    ViewObject getVOById(String voId);

    Map<String, Form> allForms();

    boolean checkUnique(String formName, String[] values, String[] columnNames);

    Table getTableValues(String formId);

    boolean updateStatus(String form, String id, Boolean status);
}
