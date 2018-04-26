package com.gc.letter.servlet;

import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gc.letter.ws.PDFServiceImpl;

public class InitServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 2523458410958848236L;
  
  Logger logger = LoggerFactory.getLogger(InitServlet.class);
  
  public void destroy()
  {
    super.destroy();
  }
  
  public void init() throws ServletException
  {
	  String location = null;
	  try {
		  location = "http://"+InetAddress.getLocalHost().getHostAddress()+":9900/pdfService";
		  Endpoint.publish(location, new PDFServiceImpl());
		  logger.info("发布服务成功：{}", location);
		  
		  
	  } catch(Exception e) {
		  logger.error("发布服务失败：", e);
	  }
    
  }
}
