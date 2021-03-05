package com.kais.components.utils.code.generator;

import com.kais.components.utils.code.generator.CodeFormater.CodeFormatAdapter;
import com.kais.components.utils.code.template.techtrans.TechTransType;
import org.w3c.dom.traversal.TreeWalker;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 类模板代码
 *
 * @author QiuKai
 */
public class ClassTemplate extends MultilineCode implements CallableCode {

    /**
     * 类默认为公共的
     */
    protected int modifier = Modifier.PUBLIC;

    protected TypeCode type = TypeCode.valueOf("class");

    private String name;
    private String alisa;

    private Code packages;
    private Set<Code> imports = new HashSet<>();

    public ClassTemplate(int modifier, String name) {

        this.modifier = modifier;
        this.name = name;
    }

    public ClassTemplate(String name) {

        this.name = name;
        header(SingleLineCode.valueOf(Code.getScope(modifier), type, name, Code.HEADER));
        foot(Code.FOOT);
    }

    /**
     *
     * 导入
     * @param code
     */
    public void addImport(TypeCode code) {

        imports.add(SingleLineCode.valueOf("import", code.getClasses()).end());
    }

    /**
     *
     * 静态导入
     * @param code
     */
    public void addStaticImport(TypeCode code) {

        imports.add(SingleLineCode.valueOf("import static", code.getClasses() + ".*").end());
    }

    public void addImport(SingleLineCode anImport, SingleLineCode... lineCodes) {

        imports.add(anImport);

        if (lineCodes.length > 0) {

            Collections.addAll(imports, lineCodes);
        }
    }

    public <T extends ClassTemplate> T packages(SingleLineCode packages) {

        this.packages = CodeFormatAdapter.adapter(packages, CodeFormats.BREAK);
        return (T) this;
    }

    @Override
    public SingleLineCode call(Object target, Object... parameters) {

        String name = Objects.isNull(alisa) ? this.name.toString() : alisa;
        return Code.call(name, target, parameters);
    }

    public Code getPackages() {

        return packages;
    }

    public Set<Code> getImports() {

        return new Object() {

            boolean check(TypeCode code) {

                return null != code && null != code.getClasses() && !code.getClasses().toString().equals("null");
            }

            TypeCode getType(Code c) {

                if (c instanceof FieldCode) {

                    return ((FieldCode) c).getType();
                } else if (c instanceof TypeCode) {

                    return (TypeCode) c;
                } else {

                    return null;
                }
            }

            Set<Code> get(List<Code> codes) {

                for (Code code : codes) {

                    TypeCode t = getType(code);

                    if (check(t)) {

                        imports.add(SingleLineCode.valueOf("import", t.getClasses()).end());
                    } else if (code instanceof MultilineCode) {

                        get(((MultilineCode) code).getContent());
                    }
                }

                return imports;
            }
        }.get(getContent());
    }

    public FieldCode toField() {

        return new FieldCode(type, name);
    }

    public String getName() {
        return name;
    }

    public void setAlisa(String alisa) {
        this.alisa = alisa;
    }

    public String getAlisa() {
        return alisa;
    }

    public static ClassTemplate classOf(Class<?> clazz) {

        ClassTemplate r = new ClassTemplate(clazz.getSimpleName());
        return r;
    }
}
