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
        this.classes = Code.toCode(classes);
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

        try {
            return new TypeCode(name, classes, Class.forName(classes));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TypeCode valueOf(Class<?> classes) {

        return new TypeCode(classes.getSimpleName(), classes.getName(), classes);
    }
}
