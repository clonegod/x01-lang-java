
## 首先为什么要用RESTful结构呢？

近年来移动互联网的发展，各种类型的Client层出不穷，RESTful可以通过一套统一的接口为 Web，iOS和Android提供服务。

另外对于广大平台来说，比如Facebook platform，微博开放平台，微信公共平台等，它们不需要有显式的前端，只需要一套提供服务的接口，于是RESTful更是它们最好的选择。在RESTful架构下：

![RESTFul](https://github.com/clonegod/tools/blob/master/images/RESTFul.jpg)

### Use HTTP methods explicitly
[RESTful Web services: The basics](https://www.ibm.com/developerworks/library/ws-restful/index.html/)


This basic REST design principle establishes a one-to-one mapping between create, read, update, and delete (CRUD) operations and HTTP methods. 

According to this mapping:

	To create a resource on the server, use POST.
	To retrieve a resource, use GET.
	To change the state of a resource or to update it, use PUT.
	To remove or delete a resource, use DELETE.


## Server的API如何设计才满足RESTful要求?

### 1、URL 具有完整的资源表述性
	https://example.org/api/v1/user/alice/account
	https://api.example.com/v2/product/10010

### 2、API versioning
API版本标识可以放在URL里面，也可以用HTTP的header：/api/v1/

### 3、URI使用名词而不是动词，且推荐用复数
BAD

	/getProducts
	/listOrders
	/retrieveClientByOrder?orderId=1
GOOD

	GET /products : will return the list of all products
	POST /products : will add a product to the collection
	GET /products/4 : will retrieve product #4
	PATCH/PUT /products/4 : will update product #44. 

### 4、保证  HEAD 和 GET 方法是安全的，不会对资源状态有所改变（污染）
比如严格杜绝如下情况（GET应该是对资源进行只读操作，而不是写操作）：

	GET /deleteProduct?id=1

### 5、资源的地址推荐用嵌套结构
	GET /friends/10375923/profile
	UPDATE /profile/primaryAddress/city

### 6、警惕返回结果的大小
如果过大，及时进行分页（pagination）或者加入限制（limit）。

HTTP协议支持分页（Pagination）操作，在Header中使用 Link 即可。

### 7、使用正确的HTTP Status Code表示访问状态
[HTTP/1.1: Status Code Definitions](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)

### 8、在返回结果用明确易懂的文本
返回字符串String。
注意返回的错误是要给人看的，避免用 1001 这种错误信息，而且适当地加入注释。

### 9、数据传输安全性
使用Application Level的加密手段把整个HTTP的payload加密

如果是平台的API，可以用成熟但是复杂的OAuth2

### 10、客户端调用RESTful接口
Server统一提供一套RESTful API，web+ios+android作为同等公民调用API。

各端发展到现在，都有一套比较成熟的框架来帮开发者事半功倍。

#### -- Server -- 

推荐： Spring MVC 或者 Jersey 或者 Play Framework

[Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

#### -- Web --
推荐随便搞！可以用重量级的AngularJS，也可以用轻量级 Backbone + jQuery 等。

#### -- iOS --
xxx

#### -- Android --
xxx

