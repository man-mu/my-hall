package com.hmall.api.client;

import com.hmall.api.config.Config;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "search-service", configuration = Config.class)
public interface SearchClient {

}
