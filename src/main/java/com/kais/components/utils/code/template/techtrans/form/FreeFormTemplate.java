package com.kais.components.utils.code.template.techtrans.form;

import com.kais.app.AppUtils;
import com.kais.components.utils.code.generator.*;
import com.kais.components.utils.code.generator.FieldCode.ConvertMode;
import com.kais.components.utils.code.template.techtrans.TechTransType;

import java.util.ArrayList;

/**
 * 构建表单Form代码模板
 *
 * @author QiuKai
 */
public class FreeFormTemplate extends BaseFormTemplate {

    TypeCode formFieldsType = TypeCode.classOf("ArrayList<FormField>", ArrayList.class);

    FieldCode dataBaseHelers = new FieldCode(TechTransType.DATABASE_HELPERS, "DataBaseHelpers");
    FieldCode appParameter = new FieldCode(TechTransType.APP_PARAMETER, "appParameter");
    FieldCode formContainer = new FieldCode(TechTransType.FORM_CONTAINER, "formContainer");
    FieldCode freeForm = new FieldCode(TechTransType.FREE_FORM, "FreeForm");
    FieldCode formFields = new FieldCode(formFieldsType, "formFields");

    private String name;

    private String createFormField;

    public FreeFormTemplate(String name) {
        this.name = name;
    }

    protected void createFormFields() {

        String localName = AppUtils.formatLower(AppUtils.keyWords("create", name, "formFields"));

        MethodTemplate createFormFields = MethodTemplate.parameterOf(localName, formFieldsType);

        // add Cols
        createFormFields.addCode(formFields.convert(ConvertMode.NO_SCOPE).newInstance(Code.toInstance(formFields.getType())).end());
        createFormFields.addCode(SingleLineCode.returnOf(formFields.getName()));

        methods.add(createFormFields);
    }

    protected void createForm() {

        String localName = AppUtils.formatLower(AppUtils.keyWords("generate", name, "form"));

        MethodTemplate createForm = MethodTemplate.of(localName, appParameter);
        FieldCode tableName = new FieldCode(TypeCode.classOf(String.class), "tableName");

        FieldCode form = new FieldCode(TechTransType.FREE_FORM, AppUtils.formatLower(AppUtils.keyWords(name, "form")));

        createForm.addCode(tableName.convert(ConvertMode.NO_SCOPE).instance("XF_STAFF").end());
        createForm.addCode(formFields.convert(ConvertMode.NO_SCOPE).instance(Code.toInstance(localName)).end());
        createForm.addCode(formContainer.convert(ConvertMode.NO_SCOPE).instance(dataBaseHelers.call("createFormContainer", appParameter.getName(), "query", tableName.getName(), formFields.getName(), appParameter.call("getConnectionPool", ""))).end());
        createForm.addCode(form.convert(ConvertMode.ONLY_NAME).instance(freeForm.call("createFreeForm", appParameter.getName(), "ca", tableName.getName(), formContainer.getName(), formFields.getName())).end());

        call = Code.toInstance(createForm.getName(), appParameter.getName());
        template = createForm;
    }

    @Override
    public Code get() {
        return template;
    }

    @Override
    public Code call() {
        return call;
    }

    @Override
    protected void addMethods() {

        createFormFields();
        createForm();
    }

    public static void main(String[] args) {

        FreeFormTemplate t = new FreeFormTemplate("header");
        t.complete();

        CodeFormater r = new CodeFormater();

        r.format(t.call());

        r.format(t.get());

        for (Code code : t.getMethods()) {

            r.format(code);
        }
    }
}
