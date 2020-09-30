package com.kais.components.utils;

import javafx.scene.Parent;
import javafx.scene.layout.Region;

/**
 *
 * 工具组件，展示在界面上的工具。
 * @author QiuKai
 */
public interface UtilComponent {

    /**
     *
     * 启动小工具
     */
    void run();

    /**
     *
     * 展示工具的组件
     * @return
     */
    Parent getParent();

    /**
     *
     * 工具类型，用于分组
     * @return
     */
    UtilType getType();

    public static enum UtilType{

        MAIN,
        TECH_TRANS
    }
}
