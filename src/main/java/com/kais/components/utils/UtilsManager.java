package com.kais.components.utils;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 界面工具管理类
 *
 * @author QiuKai
 */
public class UtilsManager {

    @Autowired
    private List<AbstractUtilComponent> utils;

    private Map<UtilComponent.UtilType, List<AbstractUtilComponent>> utilMap = new HashMap<>();

    @PostConstruct
    public void init() {

        utilMap = utils.stream().collect(Collectors.toMap(AbstractUtilComponent::getType, (AbstractUtilComponent c) -> {

            ArrayList<AbstractUtilComponent> value = new ArrayList<>();
            value.add(c);
            return value;
        }, (List<AbstractUtilComponent> a, List<AbstractUtilComponent> b) -> {

            a.addAll(b);
            return a;
        }));
    }

    public void setUtils(UtilComponent.UtilType type, Pane content) {

        List<AbstractUtilComponent> utils = utilMap.get(type);
        List<Parent> children = utils.stream().map((AbstractUtilComponent c) -> c.getParent()).collect(Collectors.toList());

        content.getChildren().addAll(children);
    }

    @PreDestroy
    public void exit(){

        System.out.println("Exit");
    }
}
