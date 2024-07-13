//package com.chang.log.config;
//
//import com.chang.log.exception.AuthInterceptorException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//@Slf4j
//public class AuthInterceptor implements HandlerInterceptor {
//
//    //페이지 진입 전에
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String authToken = request.getParameter("testToken");
//        if (authToken != null && authToken.equals("testToekn")) {
//            log.info(">>> preHandle");
//            return true;
//        }
//        throw new AuthInterceptorException();
//
//    }
//
//    //API 요청 받은 후
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info(">>> postHandle");
//    }
//
//    //API 요청 받고 페이지 진입 햇을 때
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info(">>> afterCompletion");
//    }
//}
