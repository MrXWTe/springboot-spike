package cn.mrxu.spike.controller;

import cn.mrxu.spike.result.CodeMsg;
import cn.mrxu.spike.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "Hello World";
    }


    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello, mrxu");
    }


    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }


    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "mrxu");
        return "hello";
    }

}
