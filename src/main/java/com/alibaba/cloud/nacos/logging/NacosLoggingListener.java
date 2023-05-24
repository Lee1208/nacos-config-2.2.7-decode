//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.logging;

import com.alibaba.nacos.client.logging.NacosLogging;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;

public class NacosLoggingListener implements GenericApplicationListener {
    public NacosLoggingListener() {
    }

    public boolean supportsEventType(ResolvableType resolvableType) {
        Class<?> type = resolvableType.getRawClass();
        return type != null ? ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(type) : false;
    }

    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        NacosLogging.getInstance().loadConfiguration();
    }

    public int getOrder() {
        return -2147483627;
    }
}
