package com.gc.letter.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;

public class PDFService
{
  private static PDFService fopService = new PDFService();
  
  public static PDFService getInstance() {
    return fopService;
  }
  
  private static FopFactory fopFactory = FopFactory.newInstance();
  private static TransformerFactory transFactory = TransformerFactory.newInstance();
  
  private static FOUserAgent getFOUserAgent()
  {
    String xconf = PDFService.class.getClassLoader().getResource("fop.xconf").getPath();
    try {
      fopFactory.setUserConfig(xconf);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fopFactory.newFOUserAgent();
  }
  
  public static void convertFO2PDF(InputStream fo, File pdf)
  {
    OutputStream out = null;
    ByteArrayOutputStream outStream = null;
    try {
      FOUserAgent userAgent = getFOUserAgent();
      outStream = new ByteArrayOutputStream();
      
      Fop fop = fopFactory.newFop("application/pdf", userAgent, outStream);
      
      Transformer transformer = transFactory.newTransformer();
      
      Source source = new StreamSource(fo);
      
      Result result = new SAXResult(fop.getDefaultHandler());
      
      transformer.transform(source, result);
      
      byte[] pdfbytes = outStream.toByteArray();
      
      if (pdf.exists()) {
        pdf.delete();
      }
      out = new BufferedOutputStream(new FileOutputStream(pdf));
      out.write(pdfbytes);
      out.flush(); return;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (outStream != null) {
        try {
          outStream.close();
          outStream = null;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (out != null) {
        try {
          out.close();
          out = null;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  public byte[] renderFO(String fo)
    throws FOPException, TransformerException, IOException
  {
    Source foSrc = new StreamSource(new StringReader(fo));
    
    Transformer transformer = transFactory.newTransformer();
    
    return render(foSrc, transformer);
  }
  
  public byte[] render(Source src, Transformer transformer)
    throws FOPException, TransformerException, IOException
  {
    FOUserAgent foUserAgent = getFOUserAgent();
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    Fop fop = fopFactory.newFop("application/pdf", foUserAgent, out);
    
    Result res = new SAXResult(fop.getDefaultHandler());
    
    transformer.transform(src, res);
    
    return out.toByteArray();
  }
  
  public void renderFO(String fo, HttpServletResponse response)
    throws FOPException, TransformerException, IOException
  {
    Source foSrc = new StreamSource(new StringReader(fo));
    
    Transformer transformer = transFactory.newTransformer();
    
    render(foSrc, transformer, response);
  }
  
  public void render(Source src, Transformer transformer, HttpServletResponse response)
    throws FOPException, TransformerException, IOException
  {
    FOUserAgent foUserAgent = getFOUserAgent();
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    Fop fop = fopFactory.newFop("application/pdf", foUserAgent, out);
    
    Result res = new SAXResult(fop.getDefaultHandler());
    
    transformer.transform(src, res);
    
    sendPDF(out.toByteArray(), response);
  }
  
  private void sendPDF(byte[] content, HttpServletResponse response) throws IOException
  {
    response.setContentType("application/pdf");
    response.setContentLength(content.length);
    response.getOutputStream().write(content);
    response.getOutputStream().flush();
  }
}
