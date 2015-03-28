package ru.innopolis.olympiads.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.innopolis.olympiads.dao.FormDao;
import ru.innopolis.olympiads.domain.Form;
import ru.innopolis.olympiads.domain.ViewObject;

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
    FormDao formDao;

    @RequestMapping(method = RequestMethod.POST, value = "save/{formId}")
    @ResponseBody
    public Map<String, List<String>> validateAndRegister(HttpServletRequest request, @PathVariable("formId") String formId){
        Map<String, String> regForm = convert(request.getParameterMap());
        //System.out.println("getting form " + formId);
        Form form = formDao.getFormById(formId);
        //System.out.println("form.isactive=" + form.getIsActive() + " tablename=" + form.getTableName());
        Map<String, List<String>> errors;
        if (form != null)
            errors = form.isValid(regForm);
        else {
            errors = new HashMap<>();
            errors.put("form", Lists.newArrayList("noSuchForm"));
            return errors;
        }
        if (!errors.isEmpty()) return errors;
        if (!formDao.saveForm(regForm, formId)) errors.put(form.getTableName(), Arrays.asList(form.getTableName() + "." + "notsaved"));
        return errors;
    }

    @RequestMapping(method = RequestMethod.POST, value = "values/{voId}")
    @ResponseBody
    public List<Map<String, String>> contestants(@PathVariable("voId") String voId){
        ViewObject vo = formDao.getVOById(voId);
        if (vo == null) return Lists.newArrayList();
        return formDao.allValues(vo);
    }

    private Map<String, String> convert(Map<String, String[]> parameterMap) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry: parameterMap.entrySet()){
            map.put(entry.getKey(), entry.getValue()[0]);
        }
        return map;
    }


}
