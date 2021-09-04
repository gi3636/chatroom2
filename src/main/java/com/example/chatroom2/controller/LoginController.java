package com.example.chatroom2.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.chatroom2.common.GlobalException.GlobalException;
import com.example.chatroom2.common.ResultCode.ResultCode;
import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.util.RedisKeyEnum;
import com.example.chatroom2.util.RedisUtils;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.model.form.LoginForm;
import com.example.chatroom2.service.UserService;
import com.example.chatroom2.util.JwtInfo;
import com.example.chatroom2.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/23/2021 9:18 AM
 */
@Api(tags = "登录管理")
@Controller
public class LoginController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    @ApiOperation("跳转登入页面")
    public String firstLogin(){
        return "login";
    }

    @GetMapping("/login")
    @ApiOperation("跳转登录界面")
    public String login(){
        return "login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    @ApiOperation("登入检查")
    public ResultVo loginCheck(@RequestBody LoginForm loginForm){
        System.out.println(loginForm);
        String username =  loginForm.getUsername().trim();
        String password =  loginForm.getPassword().trim();

        //非空验证
        if(StringUtils.isBlank(username)
                || StringUtils.isBlank(password)){
            throw GlobalException.from(ResultCode.LOGIN_ERROR);
        }
        User user = userService.findUser(username);
        if(user == null){
            throw GlobalException.from(ResultCode.LOGIN_ERROR);
        }
        //密码验证

        //生成token
        JwtInfo jwtInfo= new JwtInfo();
        jwtInfo.setId(user.getId());
        jwtInfo.setUsername(user.getUsername());
        jwtInfo.setAvatar(user.getAvatar());
        String token = JwtUtils.genToken(jwtInfo);
        //redisTemplate.opsForValue().set("login::"+username,token);
        redisUtils.set(RedisKeyEnum.OAUTH_APP_TOKEN.keyBuilder(String.valueOf(jwtInfo.getId())), JSONObject.toJSONString(jwtInfo), 60*60*24);
        return new ResultVo(ResultCode.LOGIN_SUCCESS).data("token",token);
    }





}
