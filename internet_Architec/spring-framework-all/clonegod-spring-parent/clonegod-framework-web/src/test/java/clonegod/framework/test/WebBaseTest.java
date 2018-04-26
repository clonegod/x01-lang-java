package clonegod.framework.test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import clonegod.framework.web.config.ServiceConfigEx;
import clonegod.framework.web.config.WebConfig;
import clonegod.framework.web.config.WebConfigEx;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class,
	classes = {WebConfig.class, WebConfigEx.class, ServiceConfigEx.class})
public abstract class WebBaseTest extends DalBaseTest {
}
