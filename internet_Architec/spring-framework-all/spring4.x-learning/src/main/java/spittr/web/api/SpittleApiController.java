package spittr.web.api;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import spittr.dao.SpittleRepository;
import spittr.domain.Spittle;
import spittr.exception.DuplicateSpittleException;
import spittr.exception.MethodArgumentNotValidException;
import spittr.exception.SpittleNotFoundException;

// A convenience annotation that is itself annotated with @Controller and @ResponseBody. 
@RestController
@RequestMapping("/api/spittles")
public class SpittleApiController {

  private static final String MAX_LONG_AS_STRING = "9223372036854775807";

  private SpittleRepository spittleRepository;

  @Autowired
  public SpittleApiController(SpittleRepository spittleRepository) {
    this.spittleRepository = spittleRepository;
  }

  /**
   * produces="application/json"  表明需要向客户端生成JSON格式数据
   * 
   * 控制器类上使用了@RestController， 不再需要@ResponseBody
   * 
   * @param max
   * @param count
   * @return
   */
  @RequestMapping(method=RequestMethod.GET, produces="application/json")
  public List<Spittle> spittles(
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count) {
    return spittleRepository.findSpittles(max, count);
  }
  
  /**
   * produces="application/json"  表明需要向客户端生成JSON格式数据
   * 
   * @PathVariable	提取路径参数
   * 
   * 控制器类上使用了@RestController， 不再需要@ResponseBody

   * 借助异常处理器以及@ResponseStatus， 避免使用ResponseEntity， 从而让代码更加整洁。
   * 
   * @param id
   * @return
   */
  @RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
  public Spittle spittleById(@PathVariable Long id) throws SpittleNotFoundException {
    return spittleRepository.findOne(id);
  }
  
  
  
  /**
   * @RequestBody	将请求参数使用HttpMethodConverter转换为对应的POJO
   * 
   * @ResponseStatus 设置HTTP状态码： 201 资源创建成功
   * 
   * ResponseEntity 提供比@ResponseBody更丰富的功能来封装响应结果，比如支持设置HTTP响应头。是@ResponseBody的增强版。
   * 
   * @param spittle
   * @param ucb
   * @return
   */
  @RequestMapping(method=RequestMethod.POST, consumes="application/json")
//  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Spittle> saveSpittle(@RequestBody Spittle spittle, 
			UriComponentsBuilder ucb, WebRequest request) throws Exception {
	  
		request.setAttribute("spittle", spittle, WebRequest.SCOPE_REQUEST);
		Spittle saved = spittleRepository.save(spittle);

		// 构建服务端的资源路径
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/spittles/") // UriComponentsBuilder可自动处理URL中“localhost”以及“8080”这两个部分
				.path(String.valueOf(saved.getId())).build().toUri();
		headers.setLocation(locationUri); // 将资源的URL放在响应的Location头部

		ResponseEntity<Spittle> responseEntity = new ResponseEntity<Spittle>(saved, headers, HttpStatus.CREATED);

		return responseEntity;
	}
  
  
  /**
   * 发送错误信息到客户端：
   * 一个好的REST API不仅能够在客户端和服务器之间传递资源， 它还能够给客户端提供额外的元数据，帮助客户端理解资源或者在请求中出现了什么情况。
   * 
   * @param e
   * @return
   */
  @ExceptionHandler(SpittleNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public /*@ResponseBody*/ Error spittleNotFound(SpittleNotFoundException e) {
    long spittleId = e.getSpittleId();
    return new Error(4, "Spittle [" + spittleId + "] not found");
  }
  
  @ExceptionHandler(DuplicateSpittleException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public /*@ResponseBody*/ Error duplicatedSpittleFound(DuplicateSpittleException e, WebRequest request) {
	  Spittle spittle = (Spittle) request.getAttribute("spittle", WebRequest.SCOPE_REQUEST);
	  return new Error(4, "Spittle [" + spittle.getMessage()  + "] already exists");
  }

  	// 模拟异常，接着在SpittrApiAspect切面中捕获到异常之后进行重试
  	@RequestMapping("/test/{type}")
	public Map<Object, Object> mockExceptionWillBeHandledByAop(
			@PathVariable String type) throws Exception {
  		
		double r = Math.random();
		System.out.println(r);
		if (r > 0.3) {
			throw new IOException("Mock IOException");
		}
		if (r > 0.5) {
			throw new MethodArgumentNotValidException("Mock apiException");
		}
		
		Map<Object, Object> model = new HashMap<>();
		model.put("success", Boolean.TRUE);
		return model;
	}
  
}
