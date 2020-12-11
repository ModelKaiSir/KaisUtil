package com.kais.components.utils.code.generator;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author QiuKai
 */
public class AbstractCode implements Code {

    static final Joiner EMPTY_JOINER = Joiner.on(" ");
    static final Joiner JOINER = Joiner.on("");

    protected List<Code> root = Lists.newArrayList();
    protected List<Code> codes = Lists.newArrayList();
    private boolean end;

    public static Code getScope(int modifier) {

        if (Modifier.isPublic(modifier)) {

            return Code.toCode("public");
        } else if (Modifier.isProtected(modifier)) {

            return Code.toCode("protected");
        } else if (Modifier.isPrivate(modifier)) {

            return Code.toCode("private");
        }else{

            return Code.EMPTY;
        }
    }

    @Override
    public String toString() {

        String code = EMPTY_JOINER.join(codes);
        root.add(Code.toCode(code));

        if (end) {

            root.add(Code.END);
        }

        return JOINER.join(root);
    }

    public <T extends AbstractCode> T end() {

        this.end = true;
        return (T) this;
    }
}
