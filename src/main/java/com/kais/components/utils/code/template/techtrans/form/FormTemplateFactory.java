package com.kais.components.utils.code.template.techtrans.form;

import com.kais.components.utils.code.generator.Code;
import com.kais.components.utils.code.generator.MethodTemplate;

public class FormTemplateFactory {

    private FormType formType;
    /** format to generate{name}Form*/
    private String name;

    public void complete(FormType formType){

    }

    enum FormType{

        FREE_FORM, TABLE_FORM;
    }
}
