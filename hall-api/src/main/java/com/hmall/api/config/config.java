package com.hmall.api.config;

import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class config {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    //匿名内部类注册拦截器
    @Bean
    public RequestInterceptor userInfoInterceptor() {
        return new RequestInterceptor(){
            @Override
            public void apply(RequestTemplate templateTemplate) {
                Long userId = UserContext.getUser();
                if (userId != null){
                    templateTemplate.header("user-info", userId.toString());
                }
            }
        };
    }
}
