package com.kais.components.utils.code.template.techtrans;

import com.kais.components.utils.code.generator.ClassTemplate;
import com.kais.components.utils.code.generator.Code;
import com.kais.components.utils.code.generator.SingleLineCode;

import java.util.List;

public class MessageLocalTemplate extends ClassTemplate {

    public MessageLocalTemplate() {

        super(TechTransType.MESSAGE_LOCAL.toString());
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, TechTransType.MESSAGE_LOCAL, "extends", TechTransType.SIMPLE_MESSAGE_LOCAL, Code.HEADER));
    }

    public void setTransMessages(List<ColumnInfo> cols) {

        for (ColumnInfo col : cols) {

            addCode(SingleLineCode.valueOf("public", "String", col.getName().toUpperCase(), "=", Code.toStrCode(col.getComment())).end());
        }
    }
}
