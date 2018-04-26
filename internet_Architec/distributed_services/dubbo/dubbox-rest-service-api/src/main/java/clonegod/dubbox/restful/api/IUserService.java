package clonegod.dubbox.restful.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import clonegod.dubbox.restful.api.User;

@Path("/userService")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public interface IUserService {
	
	/**  
	 * GET 查询记录
	 *  */
	@GET @Path("/testGet")
	public void testGet();
	
    @GET @Path("/getUser")
	public User getUser();
    
	@GET @Path("/get/{id : \\d+}")
	public User getUser(@PathParam(value = "id") Integer id);
	
	// 请求路径中的参数必须与Path中的正则匹配
	@GET @Path("/get/{id : \\d+}/{name : [a-zA-Z0-9]+}")
	public User getUser(@PathParam(value = "id") Integer id, @PathParam(value = "name") String name);
	
    /**  
     * POST 新增记录 
     * */
	@POST @Path("/testPost")
	public void testPost();
	
    @POST @Path("/postUser")
    public User postUser(User user);
    
	@POST @Path("/post/{id}")
	public User postUser(@PathParam(value = "id") String id);
	
	/**  
	 * PUT 修改记录 
	 * */
	@PUT @Path("/testPut")
	public User testPut(User user);

	/**  
	 * PUT 删除记录 
	 * */
	@DELETE @Path("/testDelete/{id}")
	public boolean testDelete(@PathParam(value = "id") int id);
	
}
