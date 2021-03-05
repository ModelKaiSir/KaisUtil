package com.kais.components.utils.code.generator;

import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * 单行代码. 代码由空格分割。eg. public static final String A = "";
 *
 * @author QiuKai
 */
public class SingleLineCode implements Code {

    static final Joiner JOINER = Joiner.on(" ");

    private boolean end;
    private List<Object> content = Lists.newArrayList();

    public void addContent(Object arg, Object... args) {

        content.add(arg);

        if (args.length > 0) {

            Collections.addAll(content, args);
        }
    }

    public <T extends SingleLineCode> T end() {

        end = true;
        return (T) this;
    }

    @Override
    public String toString() {

        return JOINER.skipNulls().join(content) + (end ? ";" : "");
    }

    public static SingleLineCode valueOf(Object... values) {

        SingleLineCode r = new SingleLineCode();
        Collections.addAll(r.content, values);
        return r;
    }

    public static SingleLineCode packageOf(Object extend) {

        SingleLineCode r = new SingleLineCode();
        Collections.addAll(r.content, "package", extend);
        return r.end();
    }

    public static SingleLineCode returnOf(Object extend) {

        SingleLineCode r = new SingleLineCode();
        Collections.addAll(r.content, "return", extend);
        return r.end();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {

            return true;
        }

        if (o == null || getClass() != o.getClass()) {

            return false;
        }

        SingleLineCode that = (SingleLineCode) o;

        return content.toString().equals(that.content.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
