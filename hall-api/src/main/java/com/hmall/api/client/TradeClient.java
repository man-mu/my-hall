package com.hmall.api.client;

import com.hmall.api.config.config;
import com.hmall.api.fallback.TradeClientFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "trade-service",
        fallbackFactory = TradeClientFallBackFactory.class,
        configuration = config.class)
public interface TradeClient {

    @PutMapping("/orders/{orderId}")
    public void markOrderPaySuccess(@PathVariable("orderId") Long orderId);
}
