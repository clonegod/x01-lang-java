package clonegod.framework.web.filter;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import clonegod.framework.web.controllers.MonitorHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

public class MonitorFilter implements Filter{

    private ServletContext sc;

    public void init(FilterConfig filterConfig) throws ServletException {
        sc = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        System.out.println(Arrays.toString(wac.getBeanDefinitionNames()));
        MonitorHelper monitorHelper =  wac.getBean(MonitorHelper.class);
        monitorHelper.count(((HttpServletRequest)request).getRequestURI());
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
