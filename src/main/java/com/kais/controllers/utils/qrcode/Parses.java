package com.kais.controllers.utils.qrcode;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 解析文件
 *
 * @author QiuKai
 */
public class Parses {

    /**
     * 需要处理的文件
     */
    private ObjectProperty<File> parseFileProperty = new SimpleObjectProperty<>();

    /**
     * 解析类
     */
    private ObservableList<FileParse> parses = FXCollections.observableArrayList();

    /**
     * 通过的校验的解析类
     */
    private ObservableList<FileParse> passPares = FXCollections.observableArrayList();

    public void setParseFileProperty(File parseFileProperty) {

        this.parseFileProperty.set(parseFileProperty);
    }

    public void add(Supplier<FileParse> supplier) {

        parses.add(supplier.get());
    }

    protected void parse(Consumer consumer) {

        //filter
        parses.forEach(item ->{

            if(item.filter(parseFileProperty.get())){

                passPares.add(item);
            }
        });

        //parse
        for (FileParse parse : passPares) {

            consumer.accept(parse.parse());
        }
    }


    public static Parses of(File file) {

        Parses p = new Parses();
        p.parseFileProperty.set(file);
        return p;
    }
}
