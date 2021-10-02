package com.example.blog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    SampleInterceptor sampleInterceptor;
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sampleInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
