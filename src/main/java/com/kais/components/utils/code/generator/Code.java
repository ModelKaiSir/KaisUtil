package com.kais.components.utils.code.generator;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * 代码
 * 代码内容由一个List保存。比如Field字段的代码，就可能包括作用域、类型、名称、初始化代码、以及结尾分号。
 *
 * @author QiuKai
 */
public interface Code {

    Code HEADER = Code.toCode("{");
    Code FOOT = Code.toCode("}");
    Code EMPTY = Code.toCode("");
    Code END = Code.toCode(";");

    /**
     * 输出代码
     *
     * @return
     */
    @Override
    String toString();

    default CodeFormat getCodeFormat() {

        return CodeFormats.GENERAL;
    }

    static Code getScope(int modifier) {

        if (Modifier.isPublic(modifier)) {

            return Code.toCode("public");
        } else if (Modifier.isProtected(modifier)) {

            return Code.toCode("protected");
        } else if (Modifier.isPrivate(modifier)) {

            return Code.toCode("private");
        } else {

            return Code.EMPTY;
        }
    }

    /**
     * @param name
     * @param target
     * @param parameters
     * @return
     */
    static SingleLineCode call(Object name, Object target, Object... parameters) {

        if (parameters.length > 0) {

            //属于调用方法
            return SingleLineCode.valueOf(Code.toCode(name + "." + Code.toInstance(target, parameters)));
        } else {

            return SingleLineCode.valueOf(Code.toCode(name + "." + target));
        }
    }

    /**
     * 字符串转Code
     *
     * @param input
     * @return
     */
    static Code toCode(String input, Object... args) {

        return toCode(input, CodeFormats.GENERAL, args);
    }

    static Code toCode(String input, CodeFormat format, Object... args) {

        return new Code() {

            @Override
            public String toString() {

                return String.format(input, args);
            }

            @Override
            public CodeFormat getCodeFormat() {
                return format;
            }
        };
    }

    /**
     * 字符串
     *
     * @param input
     * @return
     */
    static Code toStrCode(String input) {

        return new Code() {

            @Override
            public String toString() {

                return "\"" + input + "\"";
            }
        };
    }

    /**
     * new Object(arg0, arg1, ...)
     *
     * @param params
     * @return
     */
    static Code toInstance(Object start, Object... params) {

        return new Code() {

            @Override
            public String toString() {

                return start + "(" + Joiner.on(", ").join(params) + ")";
            }
        };
    }
}
