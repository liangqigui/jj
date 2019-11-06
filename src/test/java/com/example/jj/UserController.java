package com.example.jj;

import com.example.jj.bean.User;
import com.example.jj.service.IUserService;
import com.example.jj.util.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void login(){
        User user=new User();
        user.setAccount("15277335292");
        user.setPassword("lqg1234");
        user.setIP("127.0.0.1");
        user.setPort(8888);
       // user=  userService.login(user);
        String key="15277335292";
        if(user!=null) {
            System.out.println("登陆成功");
            ValueOperations<String, User> operations = redisTemplate.opsForValue();
            boolean hasKey = redisTemplate.hasKey(key);//判断是否有
            if (hasKey) {
                operations.getOperations().delete(key);//删除
            }
            operations.set(key, user);
        }
        else
            System.out.println("登录失败");

    }

    @Test
    public  void verifyemail(){
        MailUtils mailUtils=new MailUtils("2998275230@qq.com","123456");
        mailUtils.run();
    }
    @Test
    public  void sendMessageIpv4() {
        try {
            String message="hihihi";
            String ip="192.168.42.129";
            int port=8888;
            byte ips[] = new byte[4];
            String strs[] = ip.split("\\.");
            for (int i = 0; i < strs.length; i++) {
                ips[i] = (byte) Integer.parseInt(strs[i]);// 把字符串转整型
            }
            byte buff[] = message.getBytes();
            DatagramPacket p = new DatagramPacket(buff, buff.length);// 存储的收或发的数据
            p.setAddress(InetAddress.getByAddress(ips));
            p.setPort(port);
            DatagramSocket ds = new DatagramSocket();
            ds.send(p);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @Test
    public void addUser(){
        User user=new User();
        user.setAccount("18377504957");
        user.setPassword("lqg1234");
        user.setEmail("liangqg2019@foxmail.com");
        user.setName("南宫鹰");
        int a= userService.addUser(user);
       System.out.println("ddddddddddddd"+a);
       System.out.println("保存完毕");
    }
@Test
    public void test(){
    redisTemplate.opsForValue().set("123456","555",90,TimeUnit.SECONDS);
}
    @Test
    public void test1(){
        User user=new User();
        user.setAccount("15277335292");
        user.setPassword("lqg1234");
        user.setIP("127.0.0.1");
        user.setPort(8888);
        String key="18377504957";
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set(key, user,90, TimeUnit.SECONDS);
    }
}
