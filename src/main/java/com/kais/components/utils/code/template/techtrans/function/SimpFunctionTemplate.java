package com.kais.components.utils.code.template.techtrans.function;

import com.kais.components.utils.code.generator.*;
import com.kais.components.utils.code.generator.FieldCode.ConvertMode;
import com.kais.components.utils.code.template.techtrans.TechTransType;

/**
 * extends com.techtrans.vaadin.espos61.mis.mall.common.MallAppFunction
 */
public class SimpFunctionTemplate extends ClassTemplate {

    public SimpFunctionTemplate(String name, String packagePath) {

        super(name);
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, name, "extends", TechTransType.MALL_APP_FUNCTION, Code.HEADER));
        packages(SingleLineCode.valueOf("package", packagePath).end());
        addImport(SingleLineCode.valueOf("import", TechTransType.MALL_APP_FUNCTION.getClasses()).end());
    }

    private void complete() {

        //手动导入package
        addImport(TechTransType.SIMPLE_MESSAGE_LOCAL);

        FieldCode appParameter = new FieldCode(TechTransType.APP_PARAMETER, "appParameter");
        FieldCode functionParameter = new FieldCode(TechTransType.FUNCTION_PARAMETER, "functionParameter");

        //init
        MethodTemplate init = MethodTemplate.of("init", appParameter, functionParameter).annotation(Override.class);
        init.addCode(Code.call(Codes.SUPER, Code.toInstance("init", appParameter.getName(), functionParameter.getName(), Codes.NULL)).end());
        init.addCode(Code.call(Codes.SIMPLE_MESSAGE_LOCAL, Code.toInstance("setCaption", Codes.THIS, appParameter.getName(), functionParameter.getName())).end());

        //createMain
        MethodTemplate createMain = MethodTemplate.parameterOf("createMain", TechTransType.FUNCTION_MAIN).annotation(Override.class);
        FieldCode main = new FieldCode(TypeCode.valueOf("TestFunctionMain", "com.test.TestFunctionMain"), "main");
        createMain.addCode(main.convert(ConvertMode.NO_SCOPE).instance(Codes.NULL).end());
        createMain.addCode(Code.call(main.getName(), Code.toInstance("setFunctionRequestListener", Codes.THIS)).end());
        createMain.addCode(SingleLineCode.valueOf("return", "main").end());

        //createLayout
        MethodTemplate createLayout = MethodTemplate.parameterOf("createNavigator", TechTransType.FUNCTION_NAVIGATOR).annotation(Override.class);
        FieldCode navigator = new FieldCode(TechTransType.FUNCTION_NAVIGATOR, "navigator");
        createLayout.addCode(navigator.convert(ConvertMode.NO_SCOPE).instance(Codes.NULL).end());
        createLayout.addCode(Code.call(navigator.getName(), Code.toInstance("setFunctionRequestListener", Codes.THIS)).end());
        createLayout.addCode(SingleLineCode.valueOf("return", "navigator").end());

        addCode(init, createMain, createLayout);
    }

    @Override
    public String toString() {

        String a = JOINER.skipNulls().join(getImports());
        return JOINER.skipNulls().join(new String[]{ getPackages().toString(), a, super.toString() });
    }

    public static SimpFunctionTemplate of(String name, String packages) {

        SimpFunctionTemplate result = new SimpFunctionTemplate(name, packages);
        result.complete();
        return result;
    }

    public static void main(String[] args) {

        SimpFunctionTemplate t = SimpFunctionTemplate.of("TestFunction", "com.test");
        t.setAlisa("testFunction");
        CodeFormater r = new CodeFormater();
        r.format(t);
    }
}
