Thymeleaf 
	特点：简单、功能强大. 主要是基于DOM处理XML文档。适合用于WEB页面HTML5模板。
	Thymeleaf is a Java library. 
	It is a template engine capable of processing and generating HTML, XML, JavaScript, CSS and text, 
		and can work both in web and non-web environments. 
	It is better suited for serving the view layer of web applications, 
		but it can process files in many formats, even in offline environments.
	
	
Velocity
	另一种教简单的模板引擎，如：可作为邮件模板
	
	
Thymeleaf与Spring集成后，表达式从OGNL变为SpringEL
因此，对于Thymeleaf的聚合函数写法，要改为SpringEL的写法：
	<b>TOTAL:</b> <span th:text="*{#aggregates.sum(orderLines.{purchasePrice * amount})}">35.23</span>
	===>>>
	<b>TOTAL:</b> <span th:text="*{#aggregates.sum(orderLines.![purchasePrice * amount])}">35.23</span>
	
	
	