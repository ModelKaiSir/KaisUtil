package com.kais.components.utils.code.generator;

import com.google.common.base.Joiner;

/**
 * 代码
 * 代码内容由一个List保存。比如Field字段的代码，就可能包括作用域、类型、名称、初始化代码、以及结尾分号。
 *
 * @author QiuKai
 */
public interface Code {

    public static final Code NEW = Code.toCode("new");
    public static final Code END = Code.toCode(";");
    public static final Code EMPTY = Code.toCode("");

    /**
     * 输出格式化代码
     *
     * @return
     */
    @Override
    String toString();

    /**
     * 字符串转Code
     *
     * @param input
     * @return
     */
    static Code toCode(String input) {

        return new Code() {

            @Override
            public String toString() {

                return input;
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

    static Code toInstance(Code... params) {

        return new Code() {

            @Override
            public String toString() {

                return "(" + Joiner.on(", ").join(params) + ")";
            }
        };
    }
}
