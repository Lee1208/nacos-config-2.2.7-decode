//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos;

import com.alibaba.cloud.nacos.diagnostics.analyzer.NacosConnectionFailureException;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NacosConfigManager {
    private static final Logger log = LoggerFactory.getLogger(NacosConfigManager.class);
    private static ConfigService service = null;
    private NacosConfigProperties nacosConfigProperties;

    public NacosConfigManager(NacosConfigProperties nacosConfigProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
        createConfigService(nacosConfigProperties);
    }

    static ConfigService createConfigService(NacosConfigProperties nacosConfigProperties) {
        if (Objects.isNull(service)) {
            Class var1 = NacosConfigManager.class;
            synchronized(NacosConfigManager.class) {
                try {
                    if (Objects.isNull(service)) {
                        service = NacosFactory.createConfigService(nacosConfigProperties.assembleConfigServiceProperties());
                    }
                } catch (NacosException var4) {
                    log.error(var4.getMessage());
                    throw new NacosConnectionFailureException(nacosConfigProperties.getServerAddr(), var4.getMessage(), var4);
                }
            }
        }

        return service;
    }

    public ConfigService getConfigService() {
        if (Objects.isNull(service)) {
            createConfigService(this.nacosConfigProperties);
        }

        return service;
    }

    public NacosConfigProperties getNacosConfigProperties() {
        return this.nacosConfigProperties;
    }
}
