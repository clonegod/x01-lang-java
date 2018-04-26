package clonegod.framework.test.dal;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.AuthorEnumMapper;
import clonegod.framework.dal.enums.CurrencyEnum;
import clonegod.framework.test.DalBaseTest;

@FixMethodOrder
public class TypeHandlerTest extends DalBaseTest {

	@Autowired
	AuthorEnumMapper authorMapper;
	
    @Test
    public void test01_InsertEnum() throws FileNotFoundException {
    	Author author = new Author().withUsername("alice1")
    			.withPassword("alice123")
    			.withEmail("alice@mybatis.com")
    			.withCurrency(CurrencyEnum.RMB);
    	authorMapper.insertEnum(author);
    	
    	Assert.assertTrue(author.getId() > 0);
    }
    
	/**
	 * 自定义TypeHandler将结果映射为枚举实例
	 */
    @Test
    public void test02_selectEnum() throws FileNotFoundException {
    	Author author = authorMapper.selectByPrimaryKeyEnum(1);
    	
    	System.out.println(author);
    	
    	Assert.assertTrue(author.getId() > 0);
    }
    
    
}
