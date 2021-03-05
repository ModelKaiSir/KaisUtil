package com.kais.components.utils.code.template.techtrans;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class FormFieldBuilder {

    public static FormFieldBuilder newBuilder(List<ColumnInfo> cols){

        return new FormFieldBuilder();
    }
}
