package cn.mrxu.spike.controller;

import cn.mrxu.spike.domain.User;
import cn.mrxu.spike.result.Result;
import cn.mrxu.spike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DatabaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }


    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        boolean flag = userService.tx();
        return Result.success(flag);
    }
}
