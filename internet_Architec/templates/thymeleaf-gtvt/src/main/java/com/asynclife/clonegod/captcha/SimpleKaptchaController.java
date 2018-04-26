package com.asynclife.clonegod.captcha;

import com.google.code.kaptcha.servlet.KaptchaExtend;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimpleKaptchaController extends KaptchaExtend {

    @RequestMapping(value = "/captcha")
    public String index() {
        return "captcha/login";
    }

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.captcha(req, resp);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@RequestParam String email,
            @RequestParam String password, HttpServletRequest request) {

        ModelAndView model = new ModelAndView("captcha/login");

        /* if (email.isEmpty())
            throw new RuntimeException("email empty");

        if (password.isEmpty())
            throw new RuntimeException("empty password");

        String captcha = request.getParameter("captchaChars");

        if (!captcha.equals(super.getGeneratedKey(request)))
            throw new RuntimeException("bad captcha");*/
        //eveyting is ok. proceed with your user registration / login process.
        String captcha = request.getParameter("captchaChars");

        if (captcha.equals(super.getGeneratedKey(request))) {
            model.addObject("result", "success");
        } else {
            model.addObject("result", "faild");
        }

        return model;
    }

}
