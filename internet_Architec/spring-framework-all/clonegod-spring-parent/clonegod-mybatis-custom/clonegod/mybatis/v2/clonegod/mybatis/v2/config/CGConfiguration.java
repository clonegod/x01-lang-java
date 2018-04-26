package clonegod.mybatis.v2.config;

import java.io.IOException;

import clonegod.mybatis.v2.plugin.Interceptor;
import clonegod.mybatis.v2.plugin.InterceptorChain;
import lombok.Getter;

public class CGConfiguration {
	private String basePackage;

	@Getter
    private CGMapperRegistry mapperRegistry = new CGMapperRegistry();
	
	@Getter
	protected final InterceptorChain interceptorChain = new InterceptorChain();

	public CGConfiguration setBasePackage(String basePackage) {
		this.basePackage = basePackage;
		return this;
	}
    
	public void build() throws IOException {
        if (null == basePackage || basePackage.length() < 1) {
            throw new RuntimeException("scan path is required .");
        }
        mapperRegistry.registry(basePackage);
        addPlugins();
    }
	
	/**
	 * 注册所有的plugin
	 * 
	 */
	  private void addPlugins() {
		  try {
			ClassPathHelper.getClasses("clonegod.mybatis.v2.plugin").stream().filter( cls -> {
				 return !cls.isInterface() && Interceptor.class.isAssignableFrom(cls);
			  }).forEach(cls -> {
				  try {
					interceptorChain.addInterceptor((Interceptor)cls.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			  });
		}catch (Exception e) {
			e.printStackTrace();
		}
	  }

    public static void main(String[] args) throws Exception {
        new CGConfiguration().setBasePackage("clonegod.mybatis.dao").build();
    }

}
