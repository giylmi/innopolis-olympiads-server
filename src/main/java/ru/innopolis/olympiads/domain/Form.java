package ru.innopolis.olympiads.domain;

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

    public Map<String, List<String>> isValid(Map<String, String> form){
        Map<String, List<String>> errorsMap = new HashMap<>();
        for (Input field : inputs) {
            String value = form.get(field.getColumnName());
            List<String> errors = field.validate(value);
            if (!errors.isEmpty())
                errorsMap.put(field.getColumnName(), errors);
        }
        return errorsMap;
    }

//    public Map<String, List<String>> isValid(String form){
//        try {
//            JsonFactory factory = new JsonFactory();
//            JsonParser jsonParser = factory.createParser(form);
//            Map<String, List<String>> errorsMap = new HashMap<>();
//            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
//                String fieldName = jsonParser.getCurrentName();
//                Object value = jsonParser.getCurrentValue();
//                for (Input field : inputs)
//                    if (fieldName.equals(field.getColumnName())) {
//                        List<String> errors = field.validate(value);
//                        if (!errors.isEmpty())
//                            errorsMap.put(field.getColumnName(), errors);
//                    }
//            }
//            return errorsMap;
//        } catch (IOException e) {
//            return ImmutableMap.<String, List<String>>builder().put(tableName, Arrays.asList(getProperty("unreadable"))).build();
//        }
//    }

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
