package clonegod.rmi.ws.restful;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * jax-ws restful
 * 
Question 1 so there is a default mapper for xml and not for Json?
JAXB is shipped with JVM since 1.6. It is a Java to XML mapper. 
Mapping to JSON requires an additional library like Jackson or Jettison

Question 2 
the error message "No message body writer has been found for class" refers to the provider's absense or to an annotation error to my Pojo User?
It means CXF could not serialize the object. You need to add a serialization provider or configure JAXB.

Question 3 
when i use providers why i get valid data only the for the first argument of @Produce({"application/xml","application/json"})
You need to set the Accept header at client side to the desired content type. 
CXF will generate a response according the acceptable media types or the first one in @Produces if you do not specify anyone.
 
 */
@WebService
@Path("/users")
public interface IUserService {
	
	/**
	 * 客户端在请求的时候，需要告诉服务端返回哪种格式的内容: json or xml ?
	 * 第一种方式，在url中通过_type参数
	// http://localhost:8080/rpc-ws-restful/ws/users?_type=json
	// http://localhost:8080/rpc-ws-restful/ws/users?_type=xml
	
	 * 第二种方式，在header中设置
	// Accept:application/json
	// Accept:application/xml
	*/
	
	@GET
	@Path("")	// http://localhost:8080/rpc-ws-restful/ws/users
	@Produces(value= {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})  // 支持多种格式，如果没指定，默认返回第一个
	Response getAll();
	
	@GET
	@Path("{id}") // http://localhost:8080/rpc-ws-restful/ws/users/1
	@Produces(value= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response get(@PathParam("id") int id);
	
	@POST
	@Path("add") // http://localhost:8080/rpc-ws-restful/ws/users/add
	@Produces(value= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response insert(User user);
	
	@DELETE
	@Path("{id}") // http://localhost:8080/rpc-ws-restful/ws/users/1
	@Produces(value= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response delete(@PathParam("id") int id);
	
	@PUT
	@Path("update") // http://localhost:8080/rpc-ws-restful/ws/users/update
	@Produces(value= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Response update(User user);
	
}
