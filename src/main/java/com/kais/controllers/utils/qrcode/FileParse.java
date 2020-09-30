package com.kais.controllers.utils.qrcode;

import java.io.File;

/**
 * 解析文件为内容
 * @author QiuKai
 */
public interface FileParse<T> {

    /**
     *
     * 解析
     * @return 结果
     */
    T parse();

    /**
     *
     * 通过校验才解析文件
     * @param file
     * @return 校验结果
     */
    boolean filter(File file);
}
