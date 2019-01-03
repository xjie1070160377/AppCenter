package cn.mooc.jooq.demo;

import org.jooq.tools.StringUtils;
import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;
import org.jooq.util.GeneratorStrategy.Mode;

public class DefaultGeneratorStrategy2 extends DefaultGeneratorStrategy {
	@Override
	public String getJavaClassName(Definition definition, Mode mode) {
        return getJavaClassName0(definition, mode);
    }
	private String getJavaClassName0(Definition definition, Mode mode) {
        StringBuilder result = new StringBuilder();

        // [#4562] Some characters should be treated like underscore
        result.append(StringUtils.toCamelCase(
            definition.getOutputName()
            		.replace("t_mall_", "")
                      .replace(' ', '_')
                      .replace('-', '_')
                      .replace('.', '_')
        ));
        if (mode == Mode.DEFAULT) {
            result.append("TB");
        }
        else if (mode == Mode.RECORD) {
            result.append("Record");
        }
        else if (mode == Mode.DAO) {
            result.append("Dao");
        }
        else if (mode == Mode.INTERFACE) {
            result.insert(0, "I");
        }
        
        return result.toString();
    }
}
