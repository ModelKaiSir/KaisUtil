package com.kais.components.utils.code.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Date;

/**
 * 描述Field字段
 *
 * @author QiuKai
 */
public class FieldCode extends AbstractCode {

    private int modifier = Modifier.PROTECTED;

    private TypeCode type;
    private Code name;

    public FieldCode(TypeCode type, String name) {

        this.type = type;
        this.name = Code.toCode(name);

        super.codes.add(getScope(modifier));
        super.codes.add(type);
        super.codes.add(this.name);
    }

    public FieldCode(int modifier, TypeCode type, String name) {

        this.modifier = modifier;
        this.type = type;
        this.name = Code.toCode(name);

        super.codes.add(getScope(modifier));
        super.codes.add(type);
        super.codes.add(this.name);
    }

    public TypeCode getType() {
        return type;
    }

    public Code getName() {

        return name;
    }

    public FieldCode instance(Code value) {

        codes.add(Code.toCode("="));
        codes.add(value);
        return this;
    }

    public FieldCode newInstance(Code value, Code... params) {

        codes.add(Code.toCode("="));
        codes.add(Code.NEW);

        if (value instanceof TypeCode) {

            if (null != ((TypeCode) value).getTypeClass()) {

                codes.add(Code.toCode(((TypeCode) value).getTypeClass().getSimpleName()));
            } else {

                codes.add(value);
            }
        } else {

            codes.add(value);
        }

        if (null != params && params.length > 0) {

            codes.add(Code.toInstance(params));
        }
        return this;
    }

    public static void main(String[] args) {

        TypeCode t = TypeCode.valueOf(int.class);
        TypeCode t2 = TypeCode.valueOf(Date.class);

        FieldCode c = new FieldCode(t, "start").instance(Code.toCode("1")).end();
        FieldCode c2 = new FieldCode(t, "end").instance(Code.toCode("1")).end();

        FieldCode c3 = new FieldCode(t2, "sysDate").newInstance(t2, c.getName(), c2.getName()).end();
        System.out.println(c);
        System.out.println(c3);
    }
}
