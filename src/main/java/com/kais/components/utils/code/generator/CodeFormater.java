package com.kais.components.utils.code.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 格式化代码
 *
 * @author QiuKai
 */
public class CodeFormater {

    public void format(Code c){

        if(c instanceof ClassTemplate){

            format((ClassTemplate) c);
        }else{

            List<String> format = new ArrayList<>();
            tab(format, 0, c);

            for (String f : format) {

                //newLine
                System.out.println(f);
            }
        }
    }

    public void format(ClassTemplate code) {

        //package
        List<String> format = new ArrayList<>();
        tab(format, 0, code.getPackages());
        tab(format, 0, code.getImports());
        tab(format, 0, code.getAnnotation());
        // add tab
        tab(format, 0, code);

        for (String f : format) {

            //newLine
            System.out.println(f);
        }
    }

    void tab(List<String> source, int tab, Collection<Code> code) {

        for (Code c : code) {

            tab(source, tab, c);
        }
    }

    void tab(List<String> source, int tab, Code code) {

        if (null == code) {

            //shutdown
            return;
        }

        String tabCode = generateTabs(tab);

        if (code instanceof MultilineCode) {

            tab(source, tab, ((MultilineCode) code).getAnnotation());
            tab(source, tab, ((MultilineCode) code).getHeader());

            for (Code c : ((MultilineCode) code).getContent()) {

                tab(source, tab + 1, c);
            }

            tab(source, tab, ((MultilineCode) code).getFoot());
        } else {

            codeFormat(source, tabCode + code, code.getCodeFormat());
        }
    }

    private void codeFormat(List<String> source, String content, CodeFormat codeFormat) {

        if (codeFormat == CodeFormats.NEWLINE || codeFormat == CodeFormats.NEWLINE_BREAK) {

            source.add("");
        }

        source.add(content);

        if (codeFormat == CodeFormats.BREAK || codeFormat == CodeFormats.NEWLINE_BREAK) {

            source.add("");
        }
    }

    private String generateTabs(int tabs) {

        if (tabs < 1) {

            return "";
        }

        char[] ts = new char[tabs];

        for (int i = 0; i < ts.length; i++) {

            ts[i] = '\t';
        }

        return new String(ts);
    }

    public static class CodeFormatAdapter implements Code {

        private Code code;
        private CodeFormat format;

        public CodeFormatAdapter(Code code, CodeFormat format) {

            this.code = code;
            this.format = format;
        }

        @Override
        public String toString() {

            return code.toString();
        }

        @Override
        public CodeFormat getCodeFormat() {
            return format;
        }

        static CodeFormatAdapter adapter(Code code, CodeFormat format) {

            return new CodeFormatAdapter(code, format);
        }
    }
}
