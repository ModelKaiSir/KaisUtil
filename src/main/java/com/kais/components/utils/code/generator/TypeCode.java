package com.kais.components.utils.code.generator;

import sun.applet.Main;

import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * 描述类型
 *
 * @author QiuKai
 */
public class TypeCode implements Code {

    private Code name;
    private Code classes;
    private Class<?> typeClass;

    public TypeCode(String name, String classes, Class<?> typeClass) {

        this.name = Code.toCode(name);

        if (null == classes) {

            this.classes = null;
        } else {

            this.classes = Code.toCode(classes);
        }

        this.typeClass = typeClass;
    }

    public Code getClasses() {
        return classes;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    @Override
    public String toString() {
        return name.toString();
    }

    public static TypeCode valueOf(String name) {

        return new TypeCode(name, null, null);
    }

    public static TypeCode valueOf(String name, String classes) {

        return new TypeCode(name, classes, null);
    }

    public static TypeCode classOf(Class<?> classes) {

        return new TypeCode(classes.getSimpleName(), classes.getName(), classes);
    }

    public static TypeCode classOf(String name, Class<?> classes) {

        return new TypeCode(name, classes.getName(), classes);
    }
}
