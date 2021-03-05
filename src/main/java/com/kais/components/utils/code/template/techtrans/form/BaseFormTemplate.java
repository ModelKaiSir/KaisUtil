package com.kais.components.utils.code.template.techtrans.form;

import com.kais.components.utils.code.generator.Code;
import com.kais.components.utils.code.template.techtrans.ColumnInfo;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseFormTemplate {

    protected List<ColumnInfo> cols;
    protected List<Code> methods = new ArrayList<>();
    protected Code call;
    protected Code template;

    public abstract Code get();

    public abstract Code call();

    protected abstract void addMethods();

    public final void complete(){

        beforeComplete(cols);
        addMethods();
    }

    public void beforeComplete(List<ColumnInfo> cols){

        //checkNotNull(cols);
        this.cols = cols;
    }

    public List<Code> getMethods() {
        return methods;
    }
}
