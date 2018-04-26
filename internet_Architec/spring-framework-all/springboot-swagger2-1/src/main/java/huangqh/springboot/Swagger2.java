package huangqh.springboot;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2 {
	
//    @Bean
//    public Docket apiExposeAll() { 
//        return new Docket(DocumentationType.SWAGGER_2)  
//          .select()                                  
//          .apis(RequestHandlerSelectors.any())              
//          .paths(PathSelectors.any())                          
//          .build();                                           
//    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("huangqh.springboot"))
                .paths(PathSelectors.any())
                .build()
                // Custom Methods Response Messages
                .useDefaultResponseMessages(false)                            
                .globalResponseMessage(RequestMethod.GET,                     
            		  Lists.newArrayList(
            				new ResponseMessageBuilder()   
                      		    .code(500)
                    		    .message("500 message")
                    		    .responseModel(new ModelRef("Error"))
                    		    .build(),
                		    new ResponseMessageBuilder() 
                    		      .code(403)
                    		      .message("Forbidden!")
                    		      .build()
            		));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
          "My REST API", 
          "使用Swagger2构建强大的RESTful API", 
          "API TOS", 
          "https://swagger.io/", 
          new Contact("John Doe", 
        		  "http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api", 
        		  "myeaddress@company.com"), 
          "License of API", "API license URL", Collections.emptyList());
   }

//    private ApiInfo apiInfo2() {
//        return new ApiInfoBuilder()
//                .title("Spring Boot中使用Swagger2构建RESTful APIs")
//                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
//                .termsOfServiceUrl("http://blog.didispace.com/")
//                .contact(new Contact("John Doe", "www.example.com", "myeaddress@company.com"))
//                .version("1.0")
//                .build();
//    }
    
}