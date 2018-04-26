package spittr.web;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.web.HomeController;

public class HomeControllerTest {

  @Test
  public void testHomePage() throws Exception {
    HomeController controller = new HomeController();
    MockMvc mockMvc = standaloneSetup(controller)
		    			.setSingleView(new InternalResourceView("/WEB-INF/views/home.jsp"))
		    			.build();
    mockMvc.perform(get("/"))
           .andExpect(view().name("home"));
    
    mockMvc.perform(get("/home"))
    		.andExpect(view().name("home"));
  }

}
