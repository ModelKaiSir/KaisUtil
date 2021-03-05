package com.kais.components.utils.code.generator;

import com.sun.org.apache.regexp.internal.RESyntaxException;

import java.lang.reflect.Modifier;

/**
 * 描述Field字段
 * [SCOPE] [TYPE] [NAME] ? [INSTANCE];
 *
 * @author QiuKai
 */
public class FieldCode extends SingleLineCode implements CallableCode {

    private int modifier = Modifier.PROTECTED;

    private TypeCode fieldType;
    private Code name;

    public FieldCode(TypeCode type, String name) {

        this.fieldType = type;
        this.name = Code.toCode(name);

        addContent(Code.getScope(modifier), type, name);
    }

    public FieldCode(int modifier, TypeCode type, String name) {

        this.modifier = modifier;
        this.fieldType = type;
        this.name = Code.toCode(name);

        addContent(Code.getScope(modifier), type, name);
    }

    private FieldCode() {

    }

    public TypeCode getType() {
        return fieldType;
    }

    public Code getName() {

        return name;
    }

    public FieldCode instance(Object instance) {

        addContent("=", instance);
        return this;
    }

    public FieldCode unNewInstance(Object instance) {

        addContent(Codes.NEW, instance);
        return this;
    }

    public FieldCode newInstance(Object instance) {

        addContent("=", Codes.NEW, instance);
        return this;
    }

    public String toSimpleString() {

        return String.format("%s %s", fieldType, name);
    }

    public FieldCode convert(ConvertMode mode) {

        return mode.convert(this);
    }

    @Override
    public SingleLineCode call(Object target, Object... parameters) {

        if (parameters.length > 0) {

            //属于调用方法
            return SingleLineCode.valueOf(Code.toCode(name + "." + Code.toInstance(target, parameters)));
        } else {

            return SingleLineCode.valueOf(Code.toCode(name + "." + target));
        }
    }

    public enum ConvertMode {

        NO_SCOPE {

            @Override
            FieldCode convert(FieldCode source) {

                FieldCode result = new FieldCode();

                // 隐式调用，不需要scope
                result.name = source.name;
                result.fieldType = source.fieldType;
                result.addContent(source.fieldType, source.name);
                return result;
            }
        }, NO_NAME {

            @Override
            FieldCode convert(FieldCode source) {

                FieldCode result = new FieldCode();

                // 隐式调用，不需要scope
                result.name = source.name;
                result.fieldType = source.fieldType;
                return result;
            }
        },
        ONLY_NAME{

            @Override
            FieldCode convert(FieldCode source) {

                FieldCode result = new FieldCode();

                // 隐式调用，不需要scope
                result.name = source.name;
                result.fieldType = source.fieldType;
                result.addContent(source.name);
                return result;
            }
        };

        abstract FieldCode convert(FieldCode source);
    }
}
