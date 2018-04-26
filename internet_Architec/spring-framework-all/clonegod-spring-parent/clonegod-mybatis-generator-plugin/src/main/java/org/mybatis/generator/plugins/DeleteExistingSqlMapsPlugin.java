package org.mybatis.generator.plugins;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 * Mybatis-generator 没有提供覆盖Mapper.xml的功能。其override仅对java文件进行覆盖。
 * 
 * 该类的功能就是在生成xml后，写入磁盘前，将原来的xml文件都删除掉！
 *
 */
public class DeleteExistingSqlMapsPlugin extends PluginAdapter {

    public DeleteExistingSqlMapsPlugin() {
		super();
	}

	@Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * sqlMapGenerated() is called after a Mapper.xml file is created in memory but before it is written to disk.
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
            IntrospectedTable introspectedTable)
    {
//        Path sqlMapPath = Paths.get(sqlMap.getTargetProject(), sqlMap.getTargetPackage().replaceAll("\\.", File.separator), sqlMap.getFileName());
//        
//        System.err.println("==================>>>>>>>>>>>>>>>>Delete sql mapper.xml: " + sqlMapPath.toString());
//        sqlMapPath.toFile().delete();
    	
    	String sqlMapPath = sqlMap.getTargetProject() + File.separator
//                + sqlMap.getTargetPackage().replaceAll("\\.", File.separator) // not work!
                + sqlMap.getTargetPackage().replaceAll("\\.", Matcher.quoteReplacement(File.separator))
                + File.separator + sqlMap.getFileName();
    	
        File sqlMapFile = new File(sqlMapPath);

        sqlMapFile.delete();
        
        return true;
    }
    
    public static void main(String[] args) {
		System.out.println(Matcher.quoteReplacement(File.separator)); // \\
		System.out.println(File.separator); // \
	}

}