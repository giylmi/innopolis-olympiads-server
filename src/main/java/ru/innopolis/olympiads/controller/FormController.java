package ru.innopolis.olympiads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.innopolis.olympiads.dao.ContestantDao;
import ru.innopolis.olympiads.dao.FormDao;
import ru.innopolis.olympiads.domain.Contestant;
import ru.innopolis.olympiads.domain.Form;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giylmi on 28.02.2015.
 */
@Controller
public class FormController {

    @Autowired
    Form form;

    @Autowired
    FormDao formDao;

    @Autowired
    ContestantDao contestantDao;

    @RequestMapping(method = RequestMethod.POST, value = "save")
    @ResponseBody
    public Map<String, List<String>> validateAndRegister(HttpServletRequest request){
        Map<String, String> regForm = convert(request.getParameterMap());
        Map<String, List<String>> errors = form.isValid(regForm);
        if (!errors.isEmpty()) return errors;
        if (!formDao.saveForm(regForm, form.getTableName())) errors.put(form.getTableName(), Arrays.asList(form.getTableName() + "." + "notsaved"));
        return errors;
    }

    @RequestMapping(method = RequestMethod.POST, value = "contestants")
    @ResponseBody
    public List<Contestant> contestants(){
        return contestantDao.all();
    }

    private Map<String, String> convert(Map<String, String[]> parameterMap) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry: parameterMap.entrySet()){
            map.put(entry.getKey(), entry.getValue()[0]);
        }
        return map;
    }


}
