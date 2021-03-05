package com.kais.components.utils.code.template.techtrans.function;

import com.kais.components.utils.code.generator.*;
import com.kais.components.utils.code.generator.FieldCode.ConvertMode;
import com.kais.components.utils.code.template.techtrans.ColumnInfo;
import com.kais.components.utils.code.template.techtrans.MessageLocalTemplate;
import com.kais.components.utils.code.template.techtrans.TableInfo;
import com.kais.components.utils.code.template.techtrans.TechTransType;

import java.util.ArrayList;
import java.util.List;

public class SimpFunctionNavigatorTemplate extends BaseAppFunctionTemplate {

    FieldCode msgLocal = new FieldCode(TechTransType.MESSAGE_LOCAL, "msgLocal");
    FieldCode appFieldAttrib = new FieldCode(TechTransType.APP_FIELD_ATTRIB, "AppFieldAttrib");
    FieldCode navigatorMode = new FieldCode(TechTransType.NAVIGATOR_MODE, "NavigatorMode");

    private NavigatorColumnTemplate navigatorColumn;

    public SimpFunctionNavigatorTemplate(String name, String packagePath) {

        super(name);
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, name, "extends", TechTransType.MALL_APP_FUNCTION_NAVIGATOR, Code.HEADER));
        packages(SingleLineCode.packageOf(packagePath));
        addImport(TechTransType.MALL_APP_FUNCTION_NAVIGATOR);
    }

    /**
     * 导入包
     */
    @Override
    protected void addImports() {

        //手动导入package
        addImport(TechTransType.SIMPLE_MESSAGE_LOCAL);
        addImport(TechTransType.APP_FIELD_ATTRIB);
        addImport(TechTransType.APP_PARAMETER);
        addImport(TechTransType.FUNCTION_PARAMETER);
        addImport(TechTransType.NAVIGATOR_MODE);
    }

    @Override
    protected void addFields() {

        addCode(msgLocal.newInstance(Code.toInstance(msgLocal.getType())));
    }

    @Override
    protected void addMethods() {

        init();
        getFormFields();
    }

    @Override
    protected void addClasses() {

        MessageLocalTemplate messageLocalTemplate = new MessageLocalTemplate();
        messageLocalTemplate.setAlisa(msgLocal.getName().toString());

        addCode(navigatorColumn);
        addCode(messageLocalTemplate);
    }

    private void init() {

        //init
        FieldCode appParameter = new FieldCode(TechTransType.APP_PARAMETER, "appParameter");
        FieldCode functionParameter = new FieldCode(TechTransType.FUNCTION_PARAMETER, "functionParameter");

        MethodTemplate init = MethodTemplate.of("init", appParameter, functionParameter).annotation(Override.class);

        FieldCode tableName = new FieldCode(TypeCode.classOf(String.class), "tableName");
        init.addCode(tableName.convert(ConvertMode.NO_SCOPE).instance(Code.toStrCode(tableInfo.getName())).end());
        init.addCode(Codes.SUPER.call(Code.toInstance("init", appParameter.getName(), functionParameter.getName(), msgLocal.getName(), Codes.NULL, tableName.getName(), navigatorMode.call("NON_CRITERIA_INDEX"))).end());

        addCode(init);
    }

    public void getFormFields() {

        MethodTemplate getFormFields = MethodTemplate.of("getFormFields").annotation(Override.class);
        FieldCode formFields = new FieldCode(TypeCode.classOf("ArrayList<FormField>", ArrayList.class), "formFields");
        getFormFields.addCode(formFields.convert(ConvertMode.NO_SCOPE).newInstance(Code.toInstance(formFields.getType())).end());

        navigatorColumn = new NavigatorColumnTemplate("NavigatorColumn");
        navigatorColumn.complete(tableInfo);
        generateFormFields(getFormFields, formFields, navigatorColumn);

        addCode(getFormFields);
    }

    private void generateFormFields(MethodTemplate getFormFields, FieldCode formFields, NavigatorColumnTemplate columnTemplate) {

        for (ColumnInfo col : columnTemplate.getCols()) {

            FieldCode local = new FieldCode(TechTransType.FORM_FIELD, "formField").convert(ConvertMode.NO_NAME);
            Code a = columnTemplate.call(col.getName());
            Code b = local.unNewInstance(Code.toInstance(local.getType(), a, msgLocal.call("get", a), appFieldAttrib.call("STRING")));
            getFormFields.addCode(formFields.call("add", b).end());
        }

        getFormFields.addCode(SingleLineCode.returnOf(formFields.getName()).end());
    }

    public static SimpFunctionNavigatorTemplate of(String name, String packages, TableInfo tableInfo) {

        SimpFunctionNavigatorTemplate result = new SimpFunctionNavigatorTemplate(name, packages);
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

        SimpFunctionNavigatorTemplate t = SimpFunctionNavigatorTemplate.of("TestFunctionNavigator", "com.test", staff);

        CodeFormater r = new CodeFormater();
        r.format(t);
    }

    /**
     * Column class
     */
    public class NavigatorColumnTemplate extends ClassTemplate {

        private TableInfo tableInfo;

        public NavigatorColumnTemplate(String name) {
            super(name);
        }

        public void complete(TableInfo tableInfo) {

            this.tableInfo = tableInfo;
            for (ColumnInfo c : tableInfo.getCols()) {

                addCode(Code.toCode("@Column(\"%s\")", c.getComment()));
                addCode(SingleLineCode.valueOf("public", "static", "final", "String", c.getName().toUpperCase(), "=", Code.toStrCode(c.getName())).end());
            }
        }

        public List<ColumnInfo> getCols() {
            return tableInfo.getCols();
        }
    }
}
