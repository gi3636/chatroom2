package com.example.chatroom2.controller;

import com.example.chatroom2.common.GlobalException.GlobalException;
import com.example.chatroom2.common.ResultCode.ResultCode;
import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.model.form.LoginForm;
import com.example.chatroom2.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.transform.Result;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/23/2021 9:18 AM
 */
@Api(tags = "登录管理")
@Controller
public class LoginController {

    @Autowired
    UserService userService;


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

    @PostMapping("/login")
    @ApiOperation("登入检查")
    public ResultVo loginCheck(@RequestBody LoginForm loginForm){
        String username =  loginForm.getUsername().trim();
        String password =  loginForm.getPassword().trim();

        //非空验证
        if(StringUtils.isBlank(username)
           || StringUtils.isBlank(password)){
            throw GlobalException.from(ResultCode.LOGIN_ERROR);
        }

        if(userService.findUser(username) == null){
            throw GlobalException.from(ResultCode.LOGIN_ERROR);
        }

        return ResultVo.ok();
    }





}
