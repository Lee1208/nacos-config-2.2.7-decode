//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.endpoint;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.refresh.NacosRefreshHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

@ConditionalOnWebApplication
@ConditionalOnClass({Endpoint.class})
@ConditionalOnProperty(
        name = {"spring.cloud.nacos.config.enabled"},
        matchIfMissing = true
)
public class NacosConfigEndpointAutoConfiguration {
    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private NacosRefreshHistory nacosRefreshHistory;

    public NacosConfigEndpointAutoConfiguration() {
    }

    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    @Bean
    public NacosConfigEndpoint nacosConfigEndpoint() {
        return new NacosConfigEndpoint(this.nacosConfigManager.getNacosConfigProperties(), this.nacosRefreshHistory);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledHealthIndicator("nacos-config")
    public NacosConfigHealthIndicator nacosConfigHealthIndicator() {
        return new NacosConfigHealthIndicator(this.nacosConfigManager.getConfigService());
    }
}
