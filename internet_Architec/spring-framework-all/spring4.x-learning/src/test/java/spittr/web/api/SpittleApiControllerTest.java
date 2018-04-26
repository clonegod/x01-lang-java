package spittr.web.api;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import spittr.domain.Spittle;

public class SpittleApiControllerTest {

	static RestTemplate restTemplate = new RestTemplate();
	
	@BeforeClass
	public static void setFiddlerProxy() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy= new Proxy(Type.HTTP, new InetSocketAddress("localhost", 8888));
		requestFactory.setProxy(proxy);
		restTemplate.setRequestFactory(requestFactory);
	}
	
	/**
	 * getForObject
	 */
	@Test
	public void testGetSpittles() {
		@SuppressWarnings("unchecked")
		List<Spittle> spittles = restTemplate.getForObject("http://localhost/spittr/api/spittles", 
				List.class);
		Assert.assertNotNull(spittles);
	}
	
	/**
	 * getForObject
	 * 	url parameter
	 */
	@Test
	public void testGetSpittle() {
		Spittle spittle = restTemplate.getForObject("http://localhost/spittr/api/spittles/{id}", 
				Spittle.class, 1);
		Assert.assertNotNull(spittle);
	}
	
	/**
	 * 	getForObject	使用Map封装url参数
	 */
	@Test
	public void testGetSpittle2() {
		Map<String, Integer> urlVarables = new HashMap<>();
		urlVarables.put("id", 1);
		Spittle spittle = restTemplate.getForObject("http://localhost/spittr/api/spittles/{id}", 
				Spittle.class, urlVarables);
		Assert.assertNotNull(spittle);
	}
	
	/**
	 * getForEntity
	 */
	@Test
	public void testGetSpittle3() {
		Map<String, Integer> urlVarables = new HashMap<>();
		urlVarables.put("id", 1);
		ResponseEntity<Spittle> response = restTemplate.getForEntity("http://localhost/spittr/api/spittles/{id}", 
				Spittle.class, urlVarables);
		Assert.assertNotNull(response);
	}
	
	
	/**
	 * postForObject
	 */
	@Test
	public void testPostSpittle() {
		Spittle spittle = newSpittle();
		Spittle saved = restTemplate.postForObject("http://localhost/spittr/api/spittles", 
				spittle, Spittle.class);
		Assert.assertNotNull(saved);
	}
	
	
	/**
	 * postForEntity
	 */
	@Test
	public void testPostSpittle2() {
		Spittle spittle = newSpittle();
		ResponseEntity<Spittle> response = restTemplate.postForEntity("http://localhost/spittr/api/spittles", 
				spittle, Spittle.class);
		Assert.assertNotNull(response.getHeaders().getLocation());
		Assert.assertNotNull(response.getBody());
	}
	
	/**
	 * GET
	 * exchange
	 */
	@Test
	public void testGetSpittleByExchange1() {
		ResponseEntity<Spittle> response = restTemplate.exchange("http://localhost/spittr/api/spittles/{id}", 
				HttpMethod.GET, null, Spittle.class, 1);
		Assert.assertNotNull(response.getBody());
	}
	
	/**
	 * GET
	 * exchange & HttpEntity - set headers
	 */
	@Test
	public void testGetSpittleByExchange2() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", "application/json");
		headers.add("User-Agent", "Spring4 RestTemplate");
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		
		ResponseEntity<Spittle> response = restTemplate.exchange("http://localhost/spittr/api/spittles/{id}", 
				HttpMethod.GET, requestEntity, Spittle.class, 1);
		Assert.assertNotNull(response.getBody());
	}
	
	
	/**
	 * POST
	 * exchange & RequestEntity - set headers and post body
	 */
	@Test
	public void testPostSpittleByExchange3() throws URISyntaxException {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Accept", "application/json");
		headers.add("User-Agent", "Spring4 RestTemplate");
		
		Spittle body = newSpittle();
		
		RequestEntity<Spittle> requestEntity = 
				new RequestEntity<>(body, HttpMethod.POST, new URI("http://localhost/spittr/api/spittles"));
		
		ResponseEntity<Spittle> response = restTemplate.exchange(requestEntity, Spittle.class);
		Assert.assertNotNull(response.getBody());
	}

	private Spittle newSpittle() {
		return new Spittle("Message from resttemplate " + Math.random(), new Date());
	}
	
	/**
	 * Test Aspect to handle exception
	 */
	@Test
	public void testAspect() {
		String response = restTemplate.getForObject("http://localhost/spittr/api/spittles/test/aspect", String.class);
		Assert.assertNotNull(response);
	}
	
}
