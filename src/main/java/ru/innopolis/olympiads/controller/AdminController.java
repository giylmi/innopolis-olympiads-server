package ru.innopolis.olympiads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.olympiads.dao.FormDao;
import ru.innopolis.olympiads.domain.Form;
import ru.innopolis.olympiads.domain.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by giylmi on 24.03.2015.
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    FormDao formDao;

    @RequestMapping
    public String admin(Model model){
        Map<String, Form> forms = formDao.allForms();
        String form = "";
        try {
            form = forms.entrySet().iterator().next().getKey();
        } catch (Exception e) {

        }
        return admin(model, form);
    }

    @RequestMapping("{formId}")
    public String admin(Model model, @PathVariable("formId") String formId){
        Map<String, Form> formMap = formDao.allForms();
        model.addAttribute("forms", formMap);
        model.addAttribute("current", formId);
        Table table = formDao.getTableValues(formId);
        model.addAttribute("table", table);
        return "admin";
    }

    @RequestMapping(value = "update/{form}/{id}")
    @ResponseBody
    public String update(@PathVariable("form") String form, @PathVariable("id") String id, @RequestParam Boolean status){
        if (formDao.updateStatus(form, id, status))
            return "ok";
        return "no";
    }
}
