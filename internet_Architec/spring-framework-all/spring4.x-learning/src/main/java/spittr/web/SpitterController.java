package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.validation.Valid;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spittr.dao.SpitterRepository;
import spittr.domain.Spitter;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

  private SpitterRepository spitterRepository;

  @Autowired
  public SpitterController(SpitterRepository spitterRepository) {
    this.spitterRepository = spitterRepository;
  }
  
  /**
   * 视图registerForm.jsp中使用了form:标签，因此需要在pageContext上下文中存在一个名称为spitterForm的model
   * 这里使用@ModelAttribute 声明一个空的spitterForm对象即可
   * 
   * java.lang.IllegalStateException: Neither BindingResult nor plain target object for bean name 'spitterForm' available as request attribute
   * 
   * @param spitterForm
   * @return
   */
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm(@ModelAttribute SpitterForm spitterForm) {
    return "registerForm";
  }
  
  /**
   * 在请求处理完成后， 最好进行一下重定向， 这样浏览器的刷新就不会重复提交表单了
   * 
   * 校验表单参数合法性：
   * 使用Spring对Java校验API（Java Validation API， 又称JSR-303） 的支持
   * 在Spring MVC中要使用Java校验API的话， 并不需要什么额外的配置。 只要保证在类路径下包含这个Java API的实现即可， 比如Hibernate Validator。
   * Errors参数要紧跟在带有@Valid注解的参数后面
   * 
   * 存在两个问题：
   * 1. 无法直接通过SpitterForm接受到MultipartFile
   * 2. MultipartFile transferTo(dest) 会出现文件路径错误---相对于设置的上传路径处理，导致错误
    * 
   * @param spitter
   * @param errors
   * @return	重定向到新用户的基本信息页
 * @throws Exception 
   */
  @RequestMapping(value="/register", method=POST)
  public String processRegistration(
	  @RequestParam("profilePicture") MultipartFile profilePicture,
      @Valid SpitterForm spitterForm, Errors errors,
      Model model,
      RedirectAttributes redirectModel) throws Exception {
    if (errors.hasErrors()) {
      return "registerForm";
    }
    Spitter spitter = spitterForm.toSpitter();
    
    // 保存基本信息
    spitterRepository.save(spitter);
    
    // 保存图片
    saveProfilePicture(profilePicture, spitter.getUsername());
    
    // POST成功后进行重定向，防止浏览器重复提交
    
    //return "redirect:/spitter/" + spitter.getUsername();
    
    // 通过URL模板进行重定向---不安全字符都会进行转义。有一定的限制: 它只能用来发送简单的值， 如String和数字的值。
//    model.addAttribute("username", spitterForm.getUsername());
//    model.addAttribute("firstName", spitterForm.getFirstName()); // 自动以查询参数的形式附加到重定向URL上
    
    redirectModel.addAttribute("username", spitter.getUsername());
    // 通过RedirectAttributes设置flash属性
    redirectModel.addFlashAttribute(spitter); // 设置spitter为flash属性
    
    return "redirect:/spitter/{username}";
  }

/**
 * 在重定向发生之前将Spitter放到会话中， 并在重定向后， 从会话中将其取出。----这样就不用再从数据库查询了。
 * 
 * @param username
 * @param model
 * @return
 */
  @RequestMapping(value="/{username}", method=GET)
  public String showSpitterProfile(@PathVariable String username, Model model) {
	  // 如果processRegistration中使用了addFlashAttribute添加spitter，接着访问该方法，spirng会自动将spitter转移到model中。
	  
	  // 当模型中不包含spitter属性时，才从数据库查找。
	  if(! model.containsAttribute("spitter")) {
		  model.addAttribute(
				  spitterRepository.findByUsername(username));
	  }
	  
	  return "profile";
  }
  
  
  private void saveProfilePicture(MultipartFile profilePicture, String username) throws IOException, FileNotFoundException {
		//    	MultipartFile profilePicture = spitterForm.getProfilePicture();
		//    	profilePicture.transferTo(dest);
		    	
		    	File dest = new File("E:/tmp/spittr/uploads/" + username + ".jpg");
		    	dest.createNewFile();
		    	OutputStream out = new FileOutputStream(dest);
		    	StreamUtils.copy(profilePicture.getBytes(), out);
		    	IOUtils.closeQuietly(out);
	}
}
