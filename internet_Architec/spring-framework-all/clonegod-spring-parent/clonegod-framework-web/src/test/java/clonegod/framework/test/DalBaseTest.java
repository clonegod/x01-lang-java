package clonegod.framework.test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import clonegod.framework.dal.config.DataSourceConfig;
import clonegod.framework.dal.config.MybatisConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, MybatisConfig.class})
public abstract class DalBaseTest {
}
