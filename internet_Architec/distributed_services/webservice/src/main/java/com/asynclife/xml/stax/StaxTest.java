package com.asynclife.xml.stax;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class StaxTest {
	
	/**
	 * 基于光标的处理方式
	 */
	@Test
	public void testReadXML() throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream in = null;
		in = StaxTest.class.getClassLoader().getResourceAsStream("books.xml");
		
		XMLStreamReader reader = factory.createXMLStreamReader(in);
		while(reader.hasNext()) {
			int type = reader.next();
			//判断节点类型
			if(type == XMLStreamConstants.START_ELEMENT) { //开始节点
				String nodeName = reader.getName().toString();
				System.out.println(nodeName);
				if("book".equals(nodeName)) { //获取属性
					System.out.println(reader.getAttributeName(0)+":"+reader.getAttributeValue(0));
				}
			} else if(type == XMLStreamConstants.CHARACTERS) { //文本节点
				System.out.println(reader.getText().trim());
			} else if(type == XMLStreamConstants.END_ELEMENT) { //结束节点
				System.out.println("/"+reader.getName().toString());
			}
		}
		
		in.close();
		
	}
	
	/**
	 * 基于迭代模型的遍历
	 */
	@Test
	public void testReadByEvent() throws Exception {

		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream in = null;
		in = StaxTest.class.getClassLoader().getResourceAsStream("books.xml");
		
		XMLEventReader reader = factory.createXMLEventReader(in);
		int count = 0;
		while(reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if(event.isStartElement()) {
				String name = event.asStartElement().getName().toString();
				if("title".equals(name)) {
					System.out.print(reader.getElementText()+"=");
				} else if("price".equals(name)) {
					System.out.println(reader.getElementText());
				}
			}
			count++;
		}
		
		System.out.println(count);
		
		in.close();
		
	
	}
	
	/**
	 * 带过滤器的读取---开发中可以考虑设计过滤器到系统中
	 * 
	 * 	读取方式可以是XMLStreamReader，也可以是XMLEventReader
	 */
	@Test
	public void testReadByEventUseFilter() throws Exception {
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		InputStream in = null;
		in = StaxTest.class.getClassLoader().getResourceAsStream("books.xml");
		
		XMLEventReader eventReader = factory.createXMLEventReader(in);
		
		XMLEventReader reader = factory.createFilteredReader(eventReader, new EventFilter() {
			public boolean accept(XMLEvent event) {
				if(event.isStartElement()) {
					String nodeName = event.asStartElement().getName().toString();
					return "title".equals(nodeName) || "price".equals(nodeName); 
				}
				return false;
			}
		});
		
		int count = 0;
		while(reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			String name = event.asStartElement().getName().toString();
			//if(event.isStartElement()) { //过滤器筛选后肯定是StartElement，这里不需要再判断类型了
			//}
			if("title".equals(name)) {
				System.out.print(reader.getElementText()+"=");
			} else if("price".equals(name)) {
				System.out.println(reader.getElementText());
			}
			count++;
		}
		
		System.out.println(count);
		
		in.close();
		
		
	}
	
	
	/**
	 * XPath查找节点
	 */
	@Test
	public void testXPath() throws Exception {
		InputStream in = null;

		in = StaxTest.class.getClassLoader().getResourceAsStream("books.xml");

		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();

		Document doc = builder.parse(in); //加载整个文档，或者某个片段Document对象

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xpath.evaluate(
				"//book[@category='web']", doc, XPathConstants.NODESET);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element ele = (Element) nodeList.item(i);
			Element titleEle = (Element) ele.getElementsByTagName("title").item(0);
			System.out.println(titleEle.getTextContent());
		}

		in.close();
	}
	
	/**
	 * 输出XML
	 */
	@Test
	public void testWriteXML() throws Exception {
		OutputStream out = System.out;
		
		XMLStreamWriter xmlWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(out);
		
		xmlWriter.writeStartDocument("UTF-8", "1.0");
		
		xmlWriter.writeStartElement("person");
		xmlWriter.writeNamespace("", "http://abc.xml.com");
		
		xmlWriter.writeStartElement("id");
		xmlWriter.writeCharacters("100");
		xmlWriter.writeEndElement();

		xmlWriter.writeEndElement();
		
		
		xmlWriter.writeEndDocument();
		
		xmlWriter.flush();
		xmlWriter.close();
		
	}

	/**
	 * 更新文档节点
	 */
	@Test
	public void testUpdateDocument() throws Exception {

		InputStream in = null;

		in = StaxTest.class.getClassLoader().getResourceAsStream("books.xml");

		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();

		Document doc = builder.parse(in);

		Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.setOutputProperty(OutputKeys.VERSION, "1.0");
		trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xpath.evaluate(
				"//book[title='Learning XML']", doc, XPathConstants.NODESET);
		Element bookEle = (Element) nodeList.item(0);
		Element ele = (Element) bookEle.getElementsByTagName("price").item(0);

		// 更新price
		BigDecimal newPrice = new BigDecimal(ele.getTextContent()).multiply(
				new BigDecimal("10")).setScale(2);
		ele.setTextContent(newPrice.toString());

		Result output = new StreamResult(System.out);
		trans.transform(new DOMSource(doc), output);

		in.close();
	}

}
