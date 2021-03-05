package com.kais.components.utils.code.template.techtrans.function;

import com.kais.components.utils.code.generator.ClassTemplate;
import com.kais.components.utils.code.template.techtrans.TableInfo;

public abstract class BaseAppFunctionTemplate extends ClassTemplate {

    protected TableInfo tableInfo;

    public BaseAppFunctionTemplate(String name) {
        super(name);
    }

    protected void beforeComplete(TableInfo tableInfo){

        this.tableInfo = tableInfo;
    }
    /**
     * 导入包代码
     */
    abstract void addImports();

    /**
     *
     * 导入字段代码
     */
    abstract void addFields();

    /**
     *
     * 添加方法代码
     */
    abstract void addMethods();

    /**
     *
     * 添加内部类代码
     */
    abstract void addClasses();

    public final void complete(TableInfo tableInfo){

        beforeComplete(tableInfo);
        addImports();
        addFields();
        addMethods();
        addClasses();
    }
}
