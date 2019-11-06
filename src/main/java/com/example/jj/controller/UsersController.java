package com.example.jj.controller;

import com.example.jj.bean.User;
import com.example.jj.service.IUserService;
import com.example.jj.util.MailUtils;
import com.example.jj.util.smsutil.CheckPhone;
import net.sf.json.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public void login(@RequestParam("user") String userjson,
                      HttpServletResponse response){
        PrintWriter out = null;//将json字符串转换为json对象
        User user = (User) JSONObject.toBean(JSONObject.fromObject(userjson),User.class);//将建json对象转换为Person对象
            System.out.println("ddddddddd    "+user.getAccount());
       /* try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        user=  userService.login(user);
       String key="S"+user.getAccount();
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set(key, user,90, TimeUnit.SECONDS);
        if(user!=null)
            out.print(true);
        else
            out.print(false);
*/
    }
    @RequestMapping("/sendMessage")
    public void sendMessage(@RequestParam("phone") String phone){
        String code = UUID.randomUUID().toString().toUpperCase().substring(0, 5);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey("P"+phone);//判断是否有
        if (hasKey) {
            redisTemplate.delete("P"+phone);
        }
        String content = "您的验证码是" + code + "，在10分钟内输入有效。如非本人操作请忽略此短信。";
       // CheckPhone.verifyphone(phone,content);
        System.out.println("code    "+code);
        operations.set("P"+phone, code);
    }
    @RequestMapping("/checkCode")
    public void checkCode(@RequestParam("code") String code,@RequestParam("phone") String phone,
                            HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String coderedis = ((String) redisTemplate.opsForValue().get("P"+phone)).toLowerCase();
        if (coderedis.equals(code.toLowerCase()))
            out.print(true);
        else
        out.print(false);
    }
    @RequestMapping("/sendEmail")
    public void sendEmail(@RequestParam("email") String email){
        String code = UUID.randomUUID().toString().toUpperCase().substring(0, 5);
        boolean hasKey = redisTemplate.hasKey("E"+email);//判断是否有
        if (hasKey) {
            redisTemplate.delete("E"+email);
        }
        //MailUtils mailUtils=new MailUtils(email,code);
        //mailUtils.run();
        String key="E"+email;
        System.out.println("email   code      "+code);
        redisTemplate.opsForValue().set(key,code,90,TimeUnit.SECONDS);
    }
    @RequestMapping("/checkEmail")
    public void checkEmail(@RequestParam("code") String code,@RequestParam("email") String email,
                          HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String coderedis = ((String) redisTemplate.opsForValue().get("E"+email)).toLowerCase();
        if (coderedis.equals(code.toLowerCase()))
            out.print(true);
        else
            out.print(false);
    }
    @RequestMapping("addUser")
    public void addUser(@RequestParam("user")  String userjson,HttpServletResponse response){
        User user = (User) JSONObject.toBean(JSONObject.fromObject(userjson),User.class);//将建json对象转换为User对象
        int a= userService.addUser(user);
        PrintWriter out = null;
        try {
            saveImage(user);
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(a==1)
            out.print(true);
        else
            out.print(false);
    }
    private void saveImage(User user){
        OutputStream os = null;
        ResourceHandlerRegistry registry;
        try {
            os = new FileOutputStream("G:/image/"+user.getImageName()+".png");
            os.write(user.getBytes(), 0, user.getBytes().length);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
