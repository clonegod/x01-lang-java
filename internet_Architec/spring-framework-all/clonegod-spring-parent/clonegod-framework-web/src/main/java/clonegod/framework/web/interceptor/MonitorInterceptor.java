package clonegod.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import clonegod.framework.web.controllers.MonitorHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorInterceptor implements HandlerInterceptor, Ordered {

    private MonitorHelper monitorHelper;

    public int getOrder() {
        return 0;
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("MonitorInterceptor run ...");
    	monitorHelper.count(httpServletRequest.getRequestURI());
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public MonitorHelper getMonitorHelper() {
        return monitorHelper;
    }

    public void setMonitorHelper(MonitorHelper monitorHelper) {
        this.monitorHelper = monitorHelper;
    }
}