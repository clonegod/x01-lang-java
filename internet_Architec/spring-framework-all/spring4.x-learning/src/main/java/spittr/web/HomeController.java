package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// 类级别的@RequestMapping，路径将应用到控制器的所有处理器方法上。
@RequestMapping({"/", "/home"})
public class HomeController {
	
  // 处理器方法上的@RequestMapping注解会对类级别上的@RequestMapping的声明进行补充
  @RequestMapping(method = GET)
  public String home(Model model) {
	  return "home";
  }

}
