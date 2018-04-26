package com.gc.letter.service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.gc.letter.util.DateUtil;


public class TemplateService
{
  private static TemplateService thymeleafSevice = new TemplateService();
  private static TemplateEngine templateEngineEngineXML;
  
  public static TemplateService getInstance() { return thymeleafSevice; }
  
  static
  {
    initializeTemplateEngine();
  }
  
  private static void initializeTemplateEngine() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setTemplateMode(StandardTemplateModeHandlers.XML.getTemplateModeName());
    templateResolver.setCacheable(false);
    templateResolver.setCharacterEncoding("UTF-8");
    
    templateEngineEngineXML = new TemplateEngine();
    templateEngineEngineXML.setTemplateResolver(templateResolver);
  }
  
  public static TemplateEngine getTemplateEngine() {
    return templateEngineEngineXML;
  }
  
  public void prepareTemplateContext(Object source, Context context) {
    org.thymeleaf.context.VariablesMap<String, Object> vMap = context.getVariables();
    vMap.put("now", DateUtil.getToday());
    
    
	
	
	
  }
}
