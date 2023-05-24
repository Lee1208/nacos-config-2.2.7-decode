//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.refresh;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.cloud.nacos.NacosPropertySourceRepository;
import com.alibaba.cloud.nacos.client.NacosPropertySource;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractSharedListener;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

public class NacosContextRefresher implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(NacosContextRefresher.class);
    private static final AtomicLong REFRESH_COUNT = new AtomicLong(0L);
    private NacosConfigProperties nacosConfigProperties;
    private final boolean isRefreshEnabled;
    private final NacosRefreshHistory nacosRefreshHistory;
    private final ConfigService configService;
    private ApplicationContext applicationContext;
    private AtomicBoolean ready = new AtomicBoolean(false);
    private Map<String, Listener> listenerMap = new ConcurrentHashMap(16);

    public NacosContextRefresher(NacosConfigManager nacosConfigManager, NacosRefreshHistory refreshHistory) {
        this.nacosConfigProperties = nacosConfigManager.getNacosConfigProperties();
        this.nacosRefreshHistory = refreshHistory;
        this.configService = nacosConfigManager.getConfigService();
        this.isRefreshEnabled = this.nacosConfigProperties.isRefreshEnabled();
    }

    /** @deprecated */
    @Deprecated
    public NacosContextRefresher(NacosRefreshProperties refreshProperties, NacosRefreshHistory refreshHistory, ConfigService configService) {
        this.isRefreshEnabled = refreshProperties.isEnabled();
        this.nacosRefreshHistory = refreshHistory;
        this.configService = configService;
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (this.ready.compareAndSet(false, true)) {
            this.registerNacosListenersForApplications();
        }

    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private void registerNacosListenersForApplications() {
        if (this.isRefreshEnabled()) {
            Iterator var1 = NacosPropertySourceRepository.getAll().iterator();

            while(var1.hasNext()) {
                NacosPropertySource propertySource = (NacosPropertySource)var1.next();
                if (propertySource.isRefreshable()) {
                    String dataId = propertySource.getDataId();
                    this.registerNacosListener(propertySource.getGroup(), dataId);
                }
            }
        }

    }

    private void registerNacosListener(final String groupKey, final String dataKey) {
        String key = NacosPropertySourceRepository.getMapKey(dataKey, groupKey);
        Listener listener = (Listener)this.listenerMap.computeIfAbsent(key, (lst) -> {
            return new AbstractSharedListener() {
                public void innerReceive(String dataId, String group, String configInfo) {
                    NacosContextRefresher.refreshCountIncrement();
                    NacosContextRefresher.this.nacosRefreshHistory.addRefreshRecord(dataId, group, configInfo);
                    NacosContextRefresher.this.applicationContext.publishEvent(new RefreshEvent(this, (Object)null, "Refresh Nacos config"));
                    if (NacosContextRefresher.log.isDebugEnabled()) {
                        NacosContextRefresher.log.debug(String.format("Refresh Nacos config group=%s,dataId=%s,configInfo=%s", group, dataId, configInfo));
                    }

                }
            };
        });

        try {
            this.configService.addListener(dataKey, groupKey, listener);
        } catch (NacosException var6) {
            log.warn(String.format("register fail for nacos listener ,dataId=[%s],group=[%s]", dataKey, groupKey), var6);
        }

    }

    public NacosConfigProperties getNacosConfigProperties() {
        return this.nacosConfigProperties;
    }

    public NacosContextRefresher setNacosConfigProperties(NacosConfigProperties nacosConfigProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
        return this;
    }

    public boolean isRefreshEnabled() {
        if (null == this.nacosConfigProperties) {
            return this.isRefreshEnabled;
        } else {
            return this.nacosConfigProperties.isRefreshEnabled() && !this.isRefreshEnabled ? false : this.isRefreshEnabled;
        }
    }

    public static long getRefreshCount() {
        return REFRESH_COUNT.get();
    }

    public static void refreshCountIncrement() {
        REFRESH_COUNT.incrementAndGet();
    }
}
