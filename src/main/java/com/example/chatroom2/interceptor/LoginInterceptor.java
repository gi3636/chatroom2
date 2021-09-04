package com.example.chatroom2.interceptor;

import com.example.chatroom2.common.GlobalException.GlobalException;
import com.example.chatroom2.common.ResultCode.ResultCode;
import com.example.chatroom2.util.JwtUtils;
import com.example.chatroom2.util.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/26/2021 9:26 AM
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过预先处理");
        String token = request.getHeader("token");
        System.out.println("token : "+token);
        String requestURI = request.getRequestURI().replaceAll("/+","/");
        log.info("requestURI: {}",requestURI);

        //验证token
        if (StringUtils.isBlank(token)){
            throw GlobalException.from(ResultCode.AUTH_FAIL);
        }

        if (!JwtUtils.checkToken(token)){
            throw GlobalException.from(ResultCode.AUTH_FAIL);
        }

        Integer id = JwtUtils.getIdByJwtToken(request);

        //用户id 放到上下文，可以当前请求进行传递
        System.out.println("存储："+SessionContext.USER_ID_KEY);
        request.setAttribute(SessionContext.USER_ID_KEY,id);
        System.out.println("预处理成功");
        System.out.println("id :"+id);


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
