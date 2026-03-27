package com.hmall.api.client;


import com.hmall.api.config.Config;
import com.hmall.api.fallback.CartClientFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(name = "cart-service",
        fallbackFactory = CartClientFallBackFactory.class,
        configuration = Config.class)
public interface CartClient {
    @DeleteMapping("/carts")
    void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);
}
