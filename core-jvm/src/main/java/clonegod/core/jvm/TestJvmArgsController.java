package clonegod.core.jvm;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class TestJvmArgsController {

    @RequestMapping("/test")
    @ResponseBody
    Boolean testJVMArgs() {
    	byte[] data = new byte[1024 * 1024]; // 1M
    	return Boolean.TRUE;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestJvmArgsController.class, args);
    }
}