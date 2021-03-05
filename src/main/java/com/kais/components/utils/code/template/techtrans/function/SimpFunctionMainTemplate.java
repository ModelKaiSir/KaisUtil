package com.kais.components.utils.code.template.techtrans.function;

import com.kais.components.utils.code.generator.*;
import com.kais.components.utils.code.generator.FieldCode.ConvertMode;
import com.kais.components.utils.code.template.techtrans.ColumnInfo;
import com.kais.components.utils.code.template.techtrans.MessageLocalTemplate;
import com.kais.components.utils.code.template.techtrans.TableInfo;
import com.kais.components.utils.code.template.techtrans.TechTransType;

import java.util.ArrayList;
import java.util.List;

public class SimpFunctionMainTemplate extends BaseAppFunctionTemplate {

    FieldCode msgLocal = new FieldCode(TechTransType.MESSAGE_LOCAL, "msgLocal");
    FieldCode appFieldAttrib = new FieldCode(TechTransType.APP_FIELD_ATTRIB, "AppFieldAttrib");

    public SimpFunctionMainTemplate(String name, String packagePath) {

        super(name);
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, name, "extends", TechTransType.MALL_APP_FUNCTION_MAIN, Code.HEADER));
        packages(SingleLineCode.packageOf(packagePath));
        addImport(TechTransType.MALL_APP_FUNCTION_MAIN);
    }

    @Override
    void addImports() {

        //手动导入package
        addImport(TechTransType.SIMPLE_MESSAGE_LOCAL);
        addImport(TechTransType.APP_FIELD_ATTRIB);
        addImport(TechTransType.APP_PARAMETER);
        addImport(TechTransType.FUNCTION_PARAMETER);
        addImport(TechTransType.NAVIGATOR_MODE);
    }

    @Override
    void addFields() {

        addCode(msgLocal.newInstance(Code.toInstance(msgLocal.getType())).end());
    }

    @Override
    void addMethods() {

        init();
    }

    @Override
    void addClasses() {

        MessageLocalTemplate messageLocalTemplate = new MessageLocalTemplate();
        messageLocalTemplate.setAlisa(msgLocal.getName().toString());

        addCode(messageLocalTemplate);
    }

    private void init() {

        //init
        FieldCode appParameter = new FieldCode(TechTransType.APP_PARAMETER, "appParameter");
        FieldCode functionParameter = new FieldCode(TechTransType.FUNCTION_PARAMETER, "functionParameter");

        MethodTemplate init = MethodTemplate.of("init", appParameter, functionParameter).annotation(Override.class);

        FieldCode tableName = new FieldCode(TypeCode.classOf(String.class), "tableName");
        init.addCode(tableName.convert(ConvertMode.NO_SCOPE).instance(Code.toStrCode(tableInfo.getName())).end());
        init.addCode(Codes.SUPER.call(Code.toInstance("init", appParameter.getName(), functionParameter.getName(), msgLocal.getName())).end());

        addCode(init);
    }

    public static SimpFunctionMainTemplate of(String name, String packages, TableInfo tableInfo) {

        SimpFunctionMainTemplate result = new SimpFunctionMainTemplate(name, packages);
        result.complete(tableInfo);
        return result;
    }

    public static void main(String[] args) {

        List<ColumnInfo> cols = new ArrayList<>();
        cols.add(ColumnInfo.of("XF_STAFFCODE", "账号"));
        cols.add(ColumnInfo.of("XF_PASSWORD", "密码"));

        TableInfo staff = new TableInfo();
        staff.setName("XF_STAFF");
        staff.setCols(cols);

        SimpFunctionMainTemplate t = SimpFunctionMainTemplate.of("TestFunctionMain", "com.test", staff);

        CodeFormater r = new CodeFormater();
        r.format(t);
    }
}
