package com.kais.components.utils.code.generator;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.kais.components.utils.code.generator.CodeFormater.CodeFormatAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 多行代码
 *
 * @author QiuKai
 */
public class MultilineCode implements Code {

    protected Joiner JOINER = Joiner.on("\n");

    private List<Code> content = Lists.newArrayList();

    private Code annotation;
    private Code header;
    private Code foot;

    public void addCode(Code code, Code... codes) {

        content.add(code);

        if (codes.length > 0) {

            Collections.addAll(content, codes);
        }
    }

    public <T extends MultilineCode> T annotation(Class<?> clazz) {

        annotation = Code.toCode("@%s", CodeFormats.NEWLINE, clazz.getSimpleName());
        return (T) this;
    }

    public <T extends MultilineCode> T header(Code code) {

        header = new CodeFormatAdapter(code, CodeFormats.BREAK);
        return (T) this;
    }

    public <T extends MultilineCode> T foot(Code code) {

        foot = code;
        return (T) this;
    }

    public Code getAnnotation() {
        return annotation;
    }

    public Code getHeader() {

        if(null == annotation){

            //
            return CodeFormatAdapter.adapter(header, CodeFormats.NEWLINE_BREAK);
        }

        return header;
    }

    public List<Code> getContent() {
        return content;
    }

    public Code getFoot() {
        return foot;
    }

    @Override
    public String toString() {

        List<Code> root = new ArrayList<>();

        root.add(annotation);
        root.add(header);
        root.addAll(content);
        root.add(foot);
        return JOINER.skipNulls().join(root);
    }

    public static void tryWishResource() {

    }

    public static <T extends Code> MultilineCode tryCatch(T content) {

        MultilineCode result = new MultilineCode();
        result.header(Code.toCode("try {"));
        result.addCode(content);

        MultilineCode catchCode = new MultilineCode();

        catchCode.header(Code.toCode("} catch (Exception e) {"));
        catchCode.addCode(Code.toCode("e.printStackTrace();"));
        catchCode.foot(Code.toCode("}"));
        result.foot(catchCode);
        return result;
    }

    public static void main(String[] args) {


        TypeCode msgLocalType = TypeCode.valueOf("MessageLocal");
        TypeCode appParameterType = TypeCode.valueOf("AppParameter", "com.techtrans.vaadin.framework.AppParameter");

        FieldCode msgLocal = new FieldCode(msgLocalType, "msgLocal").instance(Code.toInstance(msgLocalType)).end();
        FieldCode isCharge = new FieldCode(TypeCode.classOf(Boolean.class), "isCharge").instance("false").end();
        FieldCode priceNum = new FieldCode(TypeCode.classOf(String.class), "priceNum").instance(Code.call(msgLocal.getName(), "A")).end();

        MethodTemplate init = MethodTemplate.of("init", msgLocal, isCharge);
        init.addCode(Code.call(Codes.SUPER, Code.toInstance("init", msgLocal.getName(), isCharge.getName())));

        System.out.println(init);
    }
}