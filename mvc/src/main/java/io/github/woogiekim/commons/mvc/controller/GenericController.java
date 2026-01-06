package io.github.woogiekim.commons.mvc.controller;

import io.github.woogiekim.commons.mvc.controller.dto.GenericReq;
import io.github.woogiekim.commons.mvc.controller.dto.GenericRes;
import io.github.woogiekim.commons.mvc.service.GenericService;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class GenericController<E, ID extends Serializable, RQ extends GenericReq<E>, RS extends GenericRes<E>, S extends GenericService<E, ID, ?>> {

    @Autowired
    protected S service;

    protected ModelMapper mapper = null;

    protected Class<E> entityClass = null;
    protected Class<RS> gridResClass = null;

    private String defaultModuleName = null;
    private String defaultModelName = null;

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void initInternal() {
        this.mapper = new ModelMapper().registerModule(new RecordModule());
        this.mapper.getConfiguration().setAmbiguityIgnored(false);

        final var actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();

        this.entityClass = (Class<E>) actualTypeArguments[0];
        this.gridResClass = (Class<RS>) actualTypeArguments[3];

        final var entityName = this.entityClass.getSimpleName();

        this.defaultModuleName = entityName.toLowerCase();
        this.defaultModelName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    protected String moduleName() {
        return this.defaultModuleName;
    }

    protected String modelName() {
        return this.defaultModelName;
    }

    protected String prefix() {
        return "";
    }

    protected String suffix() {
        return "";
    }

    protected void mainReferenceData(Model mode) {
    }

    @GetMapping
    public String main(Model model) {
        this.mainReferenceData(model);

        return prefix() + moduleName() + suffix() + "/" + moduleName() + "main";
    }

    @ResponseBody
    @RequestMapping(value = "/grid")
    public GenericRes<E> grid(RQ req) {
        final var list = service.search(req);

        return convertToData(req, list);
    }

    private GenericRes<E> convertToData(RQ req, List<E> list) {
        var res = (GenericRes<E>) null;

        try {
            res = this.gridResClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        var data = new ArrayList<E>();

        for (var index = 0; index < list.size(); index++) {
            final var c = list.get(index);
            final var dt = this.mapper.map(c, this.entityClass);

            data.add(index, dt);
        }

        res.bind(req, data);

        return res;
    }

    @GetMapping(value = "/add")
    public String addForm(Model model) {
        final var entity = this.createEntity();

        this.defaults(entity);

        model.addAttribute(modelName(), entity);

        this.formReferenceData(model, entity);
        this.addReferenceData(model, entity);

        return this.addFormView();
    }

    @PostMapping(value = "/add")
    public String addSubmit(
        @ModelAttribute @Validated E entity,
        BindingResult errors,
        SessionStatus status,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        validate(entity, errors);
        validateAdd(entity, errors);

        if (errors.hasErrors()) {
            formReferenceData(model, entity);
            addReferenceData(model, entity);

            return addFormView();
        }

        status.setComplete();
        addEntity(entity);

        return addSuccessView(entity, model, redirectAttributes);
    }

    private void addEntity(E entity) {
        service.add(entity);
    }

    protected String addSuccessView(E entity, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("msg", "");

        return "closeandrefresh";
    }

    protected void addReferenceData(Model model, E entity) {
    }

    protected void formReferenceData(Model model, E entity) {
    }

    protected void defaults(E entity) {
    }

    protected E createEntity() {
        try {
            return this.entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected String addFormView() {
        return prefix() + moduleName() + suffix() + "/" + moduleName() + "add";
    }

    protected void validate(E entity, BindingResult errors) {
    }

    protected void validateAdd(E entity, BindingResult errors) {
    }

    @GetMapping("/{id}")
    public String detailForm(@PathVariable ID id, Model model) {
        final var entity = service.find(id);

        model.addAttribute(modelName(), entity);

        formReferenceData(model, entity);
        editReferenceData(model, entity);

        return this.detailView();
    }

    protected void editReferenceData(Model model, E entity) {
    }

    @PutMapping(value = "/{id}")
    public String editSubmit(
        @PathVariable ID id,
        @ModelAttribute @Validated E entity,
        BindingResult errors,
        SessionStatus status,
        Model model,
        RedirectAttributes ra
    ) {
        validate(entity, errors);
        validateEdit(entity, errors);

        if (errors.hasErrors()) {
            formReferenceData(model, entity);
            editReferenceData(model, entity);

            return detailView();
        }

        status.setComplete();
        editEntity(entity);

        return editSuccessView(entity, model, ra);
    }

    private void editEntity(E entity) {
        service.update(entity);
    }

    protected String detailView() {
        return prefix() + moduleName() + suffix() + "/" + moduleName() + "detail";
    }

    protected void validateEdit(E entity, BindingResult errors) {
    }

    protected String editSuccessView(E entity, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("msg", "");

        return "closeandrefresh";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable ID id, Model model) {
        this.service.deleteById(id);

        return this.deleteSuccessView(id, model);
    }

    protected String deleteSuccessView(ID id, Model model) {
        model.addAttribute("msg", "");

        return "closeandrefresh";
    }
}