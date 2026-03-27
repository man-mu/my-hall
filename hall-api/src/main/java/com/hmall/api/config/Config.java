package com.hmall.api.config;

import com.hmall.api.fallback.CartClientFallBackFactory;
import com.hmall.api.fallback.ItemClientFallBackFactory;
import com.hmall.api.fallback.TradeClientFallBackFactory;
import com.hmall.api.fallback.UserClientFallBackFactory;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    /**
    志级别
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
    拦截器，用于在请求头中添加用户信息
     */
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

    /**
    回退工厂，用于在 ItemClient 失败时创建 ItemClient 实例
     */
    @Bean
    public ItemClientFallBackFactory itemClientFallBackFactory() {
        return new ItemClientFallBackFactory();
    }

    @Bean
    public CartClientFallBackFactory cartClientFallBackFactory() {
        return new CartClientFallBackFactory();
    }

    @Bean
    public UserClientFallBackFactory userClientFallBackFactory() {
        return new UserClientFallBackFactory();
    }

    @Bean
    public TradeClientFallBackFactory tradeClientFallBackFactory() {
        return new TradeClientFallBackFactory();
    }
}
