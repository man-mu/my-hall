package com.hmall.api.fallback;

import com.hmall.api.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class UserClientFallBackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public void deductMoney(String pw, Integer amount) {
                log.error("扣减用户余额失败：", cause);
                throw new RuntimeException(cause);
            }
        };
    }
}

