package com.example.chatroom2.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession httpSession=request.getSession();
//        String username= (String)httpSession.getAttribute("username");
//        if (username!=null){
//           // String username=user.getUsername();
//            //request.setAttribute("username", username);
//            return true;
//        }else {
//            response.sendRedirect("/login");
//            return false;
//        }
//    }

}
