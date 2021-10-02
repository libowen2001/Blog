package com.example.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

//自定义拦截器
@ControllerAdvice
public class ControllerException {
     private final Logger logger= LoggerFactory.getLogger(this.getClass());

     @ExceptionHandler(Exception.class)
     public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
         /*记录异常信息*/
         logger.error("request url :{} ,Exception:{}",request.getRequestURI(),e);
         /**/
         if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
             throw e;
         }
         ModelAndView mv=new ModelAndView();
         mv.addObject("url",request.getRequestURI());
         mv.addObject("exception",e);
         mv.setViewName("error/error");

         return mv;
     }
}
