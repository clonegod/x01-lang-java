package com.gc.letter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gc.lafe.laws.webservice.letter.pip.PipDataRec;
import com.gc.lafe.laws.webservice.letter.pip.impl.PipDataService;
import com.gc.letter.consts.SystemConfig;
import com.gc.letter.util.FileUtil;
import com.gc.letter.vo.Letter;

public class LetterManager
{
  public static final String COMPANY_CODE = "2";
  public static final String TEMPLATE_NAME = "letter-main.xml";
  
  private static final Logger logger = LoggerFactory.getLogger(LetterManager.class);
  
  public static byte[] process(String policyNo) throws Exception
  {
	
	long start = System.currentTimeMillis();
	  
	logger.info(">>>开始生成电子保单，保单号：{}", policyNo);
	PipDataRec pipData = null;
	try {
		logger.info(">>>从400获取保单数据，保单号: {}", policyNo);
		
		PipDataService pipDataService = SystemConfig.getPipDataService();
		pipData = pipDataService.retrieve(COMPANY_CODE, policyNo);
		
		logger.info(">>>从400获取数据成功，保单号：{}", policyNo);
	} catch (Exception e) {
		logger.error("~~~从400获取保单数据发生异常, 保单号： {} ", policyNo, e);
		throw new RuntimeException("从400获取保单数据发生异常,保单号-"+policyNo);
	}
    
    Letter letter = new Letter();
    MappingHelper.mapping(pipData, letter);
    
    Context context = new Context();
    TemplateService.getInstance().prepareTemplateContext(letter, context);
    
    TemplateEngine engine = TemplateService.getTemplateEngine();
    String fo = engine.process(TEMPLATE_NAME, context);
    FileUtil.getInstance().recordFoXML(policyNo, fo);
    logger.info(">>>Thymeleaf模板引擎工作正常, 保单号： {} ", policyNo);
    
    byte[] bytes = PDFService.getInstance().renderFO(fo);
    logger.info(">>>FOP引擎转换FO到PDF完成, 保单号： {} ", policyNo);
    
    logger.info(">>>生成保单{}结束，耗时: {} s", policyNo, (System.currentTimeMillis()-start)/1000f);
    
    return bytes;
  }
}
