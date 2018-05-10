package test.feignclient;

import org.junit.Test;
import org.spring.cloud.feigh.api.domain.Person;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestFeignClient {
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Test
	public void savePerson() {
		
		Person person = new Person();
		person.setId(1);
		person.setName("alice");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		HttpEntity<Person> entity = new HttpEntity<>(person);
		
		ResponseEntity<String> rs = restTemplate.exchange("http://localhost:8080/person/save", HttpMethod.POST, entity, String.class);
		System.out.println(rs.getBody());
	}
	
}
