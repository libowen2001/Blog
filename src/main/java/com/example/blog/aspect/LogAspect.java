package com.example.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@org.aspectj.lang.annotation.Aspect


@Component
public class LogAspect {
    private  final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.blog.web.*.*(..))")
    public void logaspect(){

    }
    @Before("logaspect()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url= request.getRequestURI();
        String ip=request.getRemoteAddr();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object [] args=joinPoint.getArgs();
        ResultLog resultLog=new ResultLog(url,ip,classMethod,args);
        logger.info("Request :{}",resultLog);
    }
    @After("logaspect()")
    public void doAfater(){
        logger.info("------------------doafter-----------------");
    }
    @AfterReturning(returning = "result",pointcut = "logaspect()")
    public void doAfterReturn(Object result){
        logger.info("Result :{}",result);
    }
    public class ResultLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ResultLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ResultLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
