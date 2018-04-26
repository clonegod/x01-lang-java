package com.asynclife.ws.soap;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.junit.Test;

public class TestSoap {

	@Test
	public void test01() throws Exception {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage msg = factory.createMessage();

		// msg.getAttachment(element); // 传递二进制数据，附件

		// SOAPPart内部封装SOAPEnvelpe
		SOAPPart part = msg.getSOAPPart();
		// 信封
		SOAPEnvelope envelope = part.getEnvelope();

		// SOAPEnvelpe中封装SOAPHeader和SOAPBody
		SOAPHeader header = envelope.getHeader(); // HEADER做权限认证
		SOAPBody body = envelope.getBody();

		// 什么是QName：创建1个带有命名空间的节点
		String namespaceURI = "http://www.asynclife.com/ws/soap"; // 节点的命名空间
		String localPart = "add"; // 节点名称
		String prefix = "ns"; // 节点的前缀
		QName qname = new QName(namespaceURI, localPart, prefix);
		// => <ns:add xmlns="http://www.asynclife.com/ws/soap">
		
		// 将QName添加到body中
		SOAPBodyElement bodyEle = body.addBodyElement(qname);
		bodyEle.addChildElement("num1").setValue("1");
		bodyEle.addChildElement("num2").setValue("2");
		msg.writeTo(System.out);
		
		/*
		<SOAP-ENV:Envelope
		    xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
		    <SOAP-ENV:Header/>
		    <SOAP-ENV:Body>
		        <ns:add
		            xmlns:ns="http://www.asynclife.com/ws/soap">
		            <num1>1</num1>
		            <num2>2</num2>
		        </ns:add>
		    </SOAP-ENV:Body>
		</SOAP-ENV:Envelope>
		 */
	}

}
