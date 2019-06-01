package cn.mrxu.spike.controller;

import cn.mrxu.spike.domain.User;
import cn.mrxu.spike.redis.RedisService;
import cn.mrxu.spike.redis.UserKey;
import cn.mrxu.spike.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        boolean isSuccess = redisService.set(UserKey.getById,"key2", "654321");
        return Result.success(isSuccess);
    }

    @RequestMapping("/redis/getUser")
    @ResponseBody
    public Result<User> redisGetUser(){
        User u1 = redisService.get(UserKey.getById, "key1", User.class);
        return Result.success(u1);
    }

    @RequestMapping("/redis/setUser")
    @ResponseBody
    public Result<Boolean> redisSetUser(){
        User user = new User();
        user.setId(1);
        user.setName("aaa");
        boolean s = redisService.set(UserKey.getById, "user1", user);
        return Result.success(s);
    }
}
