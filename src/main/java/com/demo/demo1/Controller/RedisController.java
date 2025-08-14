package com.demo.demo1.Controller;

import com.demo.demo1.Service.RedisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/set/{key}")
    public String set(@PathVariable String key, @RequestBody String value) {
        redisService.setData(key, value);
        return "Data set successfully";
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return redisService.getData(key);
    }

    @DeleteMapping("/delete/{key}")
    public String delete(@PathVariable String key) {
        redisService.deleteData(key);
        return "Data deleted";
    }
}
