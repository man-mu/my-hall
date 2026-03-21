package com.hmall.gateway.routers;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicRouteLoader {

    private final String dataId = "gateway-routes.json";

    private final String group = "DEFAULT_GROUP";

    private final RouteDefinitionWriter routeDefinitionWriter;

    private final NacosConfigManager nacosConfigManager;

    private final Set<String> routeIds = new HashSet<>();

    @PostConstruct
    public void initConfigListener() throws NacosException {
        // 1.项目启动时，先拉取一次配置，并添加配置监听器
        String configInfo = nacosConfigManager.getConfigService()
                .getConfigAndSignListener(dataId, group, 5000, new Listener() {
                    @Override
                    public Executor getExecutor() {
                        // （如果需要异步处理配置变更）返回线程池
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        // 2.监听到配置变更，更新路由表
                        updataConfigInfo(configInfo);
                    }
                });
        // 3.解析第一次拉取的配置，更新路由表
        updataConfigInfo(configInfo);
    }

    public void updataConfigInfo(String configInfo) {
        log.debug("路由配置信息: {}", configInfo);
        // 1.解析配置, 转为RouteDefinition
        List<RouteDefinition> routes = JSONUtil.toList(configInfo, RouteDefinition.class);
        // 2.删除旧的路由表
        for (String routeId : routeIds) {
            routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
        }
        // 3.更新路由表
        for (RouteDefinition route : routes) {
            // 3.1.更新路由
            routeDefinitionWriter.save(Mono.just(route)).subscribe();
            // 3.2.记录路由
            routeIds.add(route.getId());
        }
    }
}
