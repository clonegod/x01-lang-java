package spittr.web;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import spittr.config.web.MultipleViewResolverConfig;
import spittr.dao.SpittleRepository;
import spittr.domain.Spittle;
import spittr.exception.DuplicateSpittleException;
import spittr.exception.SpittleNotFoundException;
import spittr.web.view.SpittleListExcelView;
import spittr.web.view.SpittleListPdfView;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

  private static final String MAX_LONG_AS_STRING = "9223372036854775807"; // Long.toString(Long.MAX_VALUE);
  
  private SpittleRepository spittleRepository;

  @Autowired
  public SpittleController(SpittleRepository spittleRepository) {
    this.spittleRepository = spittleRepository;
  }
  

  /**
   * 此处理器方法没有显示设置返回的逻辑视图名称， 也没有显式地设定模型，
   *  逻辑视图的名称：将会根据请求路径推断得出。 因为这个方法处理针对“/spittles”的GET请求， 因此视图的名称将会是spittles（去掉开头的斜线） ；
   *  模型的key：这个方法返回的是Spittle列表。 当处理器方法像这样返回对象或集合时， 这个值会放到模型中， 模型的key会根据其类型推断得出（在本例中， 也就是spittleList） 。
   *  
   * @param max	列表按照最新的Spittle在前的方式进行排序。 因此， 下一页中第一条的ID肯定会小于当前页最后一条的ID: max=lastSpittr.id-1
   * @param count 每页多少条
   * @param spittleForm 为spring form 标签提供一个模型对象，form将根据模型中对象的属性填充值.否则spring form标签找不到指定的模型会报错
   * @return
   */
  @RequestMapping(method=RequestMethod.GET)
  public List<Spittle> spittles(
	// 使用查询参数接受输入
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count,
      @ModelAttribute SpittleForm spittleForm) {
	  spittleForm.setMessage("Add your message here!"); // 可以对表单对象预先填充默认值
    return spittleRepository.findSpittles(max, count);
  }
  
  /**
   * 1. 请求参数自动绑定到POJO
   * 2. 表单提交成功后，对客户端进行重定向，可防止客户端重复提交表单
   * 
   * @param form
   * @param model
   * @return
   * @throws DuplicateSpittleException
   */
  @RequestMapping(method=RequestMethod.POST)
	public String saveSpittle(@Validated SpittleForm spittleForm, BindingResult bindingResult, WebRequest request) throws DuplicateSpittleException {
		if (bindingResult.hasErrors()) {
			return "spittles";
		}
		
		// 由于可能发生DuplicateSpittleException异常，因此需要预先手动spitter业务对象到request作用域。
		// 这里不能使用model来绑定属性，因为发生异常后，不会执行“将模型属性附加到request对象”这个流程。
		request.setAttribute("spittle", spittleForm, WebRequest.SCOPE_REQUEST);
		
		spittleRepository
			.save(new Spittle(null, spittleForm.getMessage(), new Date(), spittleForm.getLongitude(), spittleForm.getLatitude()));
		
		return "redirect:/spittles";
	}
  
  

  /**
   * 通过路径参数接受输入---REST 构建面向资源的服务
   * 
   * @param spittleId
   * @param model
   * @return
   */
  @RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
  public String spittle(
      @PathVariable("spittleId") long spittleId, 
			Model model) {
		Spittle spittle = spittleRepository.findOne(spittleId);
		if (spittle == null) {
			throw new SpittleNotFoundException();
		}
		model.addAttribute(spittle);
		return "spittle";
	}
  
  /**
   * 自定义视图解析器
   * 
   * @param type  视图类型：excel, pdf....
   * @param model	data to render 
   * @return
   */
  @RequestMapping(value="/view/{type}")
  public String spittles(@PathVariable String type, Model model) {
	  model.addAttribute("data", spittleRepository.findSpittles(Long.MAX_VALUE, 20));
	  if(Objects.equals(type, "excel")) {
		  return SpittleListExcelView.BANE_NAME;
	  }
	  else if(Objects.equals(type, "pdf")) {
		  return SpittleListPdfView.BANE_NAME;
	  }
	  else if(Objects.equals(type, "xml")) {
		  return MultipleViewResolverConfig.XML_VIEW;
	  }
	  else if(Objects.equals(type, "json")) {
		  return MultipleViewResolverConfig.JSON_VIEW;
	  }
	  else {
		  throw new RuntimeException("Not support view type: " + type);
	  }
  }
  
}
