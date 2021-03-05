package com.kais.components.utils.code.generator;

import com.google.common.base.Joiner;

import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 方法模板代码
 *
 * @author QiuKai
 */
public class MethodTemplate extends MultilineCode {

    private int modifier = Modifier.PROTECTED;

    /**
     * 默认void
     */
    private TypeCode type = TypeCode.valueOf("void");
    private Code name;

    private FieldCode[] params = new FieldCode[0];

    public MethodTemplate(int modifier, TypeCode type, Code name, FieldCode[] params) {

        this.params = params;
        this.type = type;
        this.modifier = modifier;
        this.name = name;

        Code paramCode = Code.toCode(name.toString() + "(" + joinParams() + ")");
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, paramCode, Code.HEADER));
        foot(Code.FOOT);
    }

    public MethodTemplate(int modifier, TypeCode type, Code name) {

        this.type = type;
        this.modifier = modifier;
        this.name = name;

        Code paramCode = Code.toCode(name.toString() + "()");
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, paramCode, Code.HEADER));
        foot(Code.FOOT);
    }

    public MethodTemplate(Code name, FieldCode[] params) {

        this.name = name;
        this.params = params;

        Code paramCode = Code.toCode(name.toString() + "(" + joinParams() + ")");
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, paramCode, Code.HEADER));
        foot(Code.FOOT);
    }

    public MethodTemplate(Code name) {

        this.name = name;

        Code paramCode = Code.toCode(name.toString() + "()");
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, paramCode, Code.HEADER));
        foot(Code.FOOT);
    }

    private Code joinParams() {

        Joiner joiner = Joiner.on(", ");
        Stream<FieldCode> stream = Stream.of(params);

        return Code.toCode(joiner.join(stream.map(p -> p.toSimpleString()).collect(Collectors.toList())));
    }

    public static MethodTemplate newInstance(int modifier, String name, TypeCode reType, FieldCode... params) {

        MethodTemplate result = new MethodTemplate(modifier, reType, Code.toCode(name), params);
        return result;
    }

    public Code getName() {
        return name;
    }

    public static MethodTemplate parameterOf(int modifier, String name, TypeCode reType) {

        MethodTemplate result = new MethodTemplate(modifier, reType, Code.toCode(name));
        return result;
    }

    public static MethodTemplate parameterOf(String name, TypeCode reType, FieldCode... params) {

        MethodTemplate result = new MethodTemplate(Modifier.PROTECTED, reType, Code.toCode(name), params);
        return result;
    }

    public static MethodTemplate of(String name, FieldCode... params) {

        MethodTemplate result = new MethodTemplate(Code.toCode(name), params);
        return result;
    }
}
