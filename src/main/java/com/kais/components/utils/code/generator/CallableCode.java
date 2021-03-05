package com.kais.components.utils.code.generator;

/**
 * 支持调用的代码 比如方法，类，变量
 * @author QiuKai
 */
public interface CallableCode {

    /**
     *
     * 调用代码
     * @param target 句柄
     * @param parameters 参数
     * @return
     */
    SingleLineCode call(Object target, Object... parameters);
}
