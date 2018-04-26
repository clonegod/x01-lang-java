package com.asynclife.xml.jaxb;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.mycompany.schemas.LoanAcctReq;

public class JAXBTest {
	
	@Test
	public void testBean2XML() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(User.class);
		Marshaller marshaller = ctx.createMarshaller();
		
		User user = new User();
		user.setId(1L);
		user.setName("abc");
		user.setSex("man");
		user.setAge(12);
		
		marshaller.marshal(user, System.out);
	}
	
	@Test
	public void testXML2Bean() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><user><age>12</age><id>1</id><name>abc</name><sex>man</sex></user>";
		JAXBContext ctx = JAXBContext.newInstance(User.class);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(new StringReader(xml));
		User user = (User) obj;
		System.out.println(user.getName()+","+user.getAge());
		
	}
	
	@Test
	public void testESB() throws Exception {
		LoanAcctReq req = new LoanAcctReq();
		req.setTxCode("001");
		req.setDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		req.setTime(new SimpleDateFormat("HHmmss").format(new Date()));
		req.setName("张三");
		req.setAge(20);
		
		
		StringWriter sw = new StringWriter(1024);
		
		JAXBContext ctx = JAXBContext.newInstance(LoanAcctReq.class);
		Marshaller marshaller = ctx.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "GB18030");
		marshaller.marshal(req, sw);
		sw.flush();
		
		String xml = sw.toString();
		xml = xml.replaceFirst(" standalone=\"yes\"", "");
		xml = xml.replaceAll("LoanAcctReq", "MSG");
		
		System.out.println(xml);
	}
	
	/**
	 * 处理异常：
	 * unexpected element (uri:"", local:"LoanAcctReq"). Expected elements are <{http://www.example.org/esb}LoanAcctReq>
	 * 
	 * 问题原因：
	 * 	由XJC转换schema得到的Java类中有namespace声明，但是报文中没有namespace，在使用JAXB的Unmarshaller转换时就会报namespace不匹配的异常
	 * 
	 * 解决办法：
	 * 	自定义CustomerXMLReaderWithNamespace继承StreamReaderDelegate，重写getNamespaceURI()，返回xsd中声明的namespace给Unmarshaller
	 */
	@Test
	public void testNoNamespaceXML() throws Exception {
		String response = "<?xml version=\"1.0\" encoding=\"GB18030\"?><LoanAcctReq><txCode>001</txCode><date>20151008</date><time>215845</time><name>张三</name><age>20</age></LoanAcctReq>";
		
		JAXBContext ctx = JAXBContext.newInstance(LoanAcctReq.class);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		
		StringReader reader = new StringReader(response);  
		XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
		CustomerXMLReaderWithNamespace xr = new CustomerXMLReaderWithNamespace(xsr);
		
		Object o = unmarshaller.unmarshal(xr);
		System.out.println(o);
	}
	
	/**
	 * 通过给document根节点添加namespace
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddNamespaceToDom() throws Exception {
		String response = "<?xml version=\"1.0\" encoding=\"GB18030\"?><LoanAcctReq><txCode>001</txCode><date>20151008</date><time>215845</time><name>张三</name><age>20</age></LoanAcctReq>";
		
		InputSource inputSource =  new InputSource(new StringReader(response));   
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(false);
		Document doc = dbf.newDocumentBuilder().parse(inputSource);     
		
		Element root = doc.getDocumentElement();
		root.setAttribute("xmlns", "http://www.example.org/esb");
		String xmlWithNamespace = transformXmlNodeToXmlString(root);
		
		System.out.println(xmlWithNamespace);
		
		JAXBContext ctx = JAXBContext.newInstance(LoanAcctReq.class);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		
		Object o = unmarshaller.unmarshal(new StringReader(xmlWithNamespace));
		System.out.println(o);
	}
	
	private static String transformXmlNodeToXmlString(Node node)
	        throws TransformerException {
	    TransformerFactory transFactory = TransformerFactory.newInstance();
	    Transformer transformer = transFactory.newTransformer();
	    StringWriter buffer = new StringWriter();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    transformer.transform(new DOMSource(node), new StreamResult(buffer));
	    String xml = buffer.toString();
	    return xml;
	}
	
}

class CustomerXMLReaderWithNamespace extends StreamReaderDelegate {
    public CustomerXMLReaderWithNamespace(XMLStreamReader reader) {
      super(reader);
    }
    @Override
    public String getAttributeNamespace(int arg0) {
      return "";
    }
    @Override
    public String getNamespaceURI() {
      return "http://www.example.org/esb";
    }
}
