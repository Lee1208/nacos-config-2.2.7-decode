//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.endpoint;

import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class NacosConfigHealthIndicator extends AbstractHealthIndicator {
    private final ConfigService configService;
    private final String STATUS_UP = "UP";
    private final String STATUS_DOWN = "DOWN";

    public NacosConfigHealthIndicator(ConfigService configService) {
        this.configService = configService;
    }

    protected void doHealthCheck(Health.Builder builder) throws Exception {
        String status = this.configService.getServerStatus();
        builder.status(status);
        switch (status) {
            case "UP":
                builder.up();
                break;
            case "DOWN":
                builder.down();
                break;
            default:
                builder.unknown();
        }

    }
}
