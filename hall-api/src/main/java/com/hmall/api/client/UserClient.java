package com.hmall.api.client;

import com.hmall.api.config.config;
import com.hmall.api.fallback.UserClientFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",
        fallbackFactory = UserClientFallBackFactory.class,
        configuration = config.class)
public interface UserClient {

    @PutMapping("/users/money/deduct")
    void deductMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount);
}
