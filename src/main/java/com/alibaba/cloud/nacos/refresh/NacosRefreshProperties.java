//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.refresh;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** @deprecated */
@Deprecated
@Component
public class NacosRefreshProperties {
    @Value("${spring.cloud.nacos.config.refresh.enabled:true}")
    private boolean enabled = true;

    public NacosRefreshProperties() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
