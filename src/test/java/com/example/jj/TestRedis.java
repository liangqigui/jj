package com.example.jj;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@SpringBootTest
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }
    @Test
    public void setRedis() {
        redisTemplate.opsForValue().set("first","siwei");
        redisTemplate.opsForValue().set("second","siweiWu",30, TimeUnit.SECONDS);
        System.out.println("存入缓存成功");
    }
    @Test
    public void getRedis(){
        String first = (String) redisTemplate.opsForValue().get("first");
        String second = (String) redisTemplate.opsForValue().get("second");

        System.out.println("取出缓存中first的数据是:"+first);
        System.out.println("取出缓存中second的数据是:"+second);

    }
}
