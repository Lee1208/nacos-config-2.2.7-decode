//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.client;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;

public class NacosPropertySource extends MapPropertySource {
    private final String group;
    private final String dataId;
    private final Date timestamp;
    private final boolean isRefreshable;

    NacosPropertySource(String group, String dataId, Map<String, Object> source, Date timestamp, boolean isRefreshable) {
        super(String.join(",", dataId, group), source);
        this.group = group;
        this.dataId = dataId;
        this.timestamp = timestamp;
        this.isRefreshable = isRefreshable;
    }

    NacosPropertySource(List<PropertySource<?>> propertySources, String group, String dataId, Date timestamp, boolean isRefreshable) {
        this(group, dataId, getSourceMap(group, dataId, propertySources), timestamp, isRefreshable);
    }

    private static Map<String, Object> getSourceMap(String group, String dataId, List<PropertySource<?>> propertySources) {
        if (CollectionUtils.isEmpty(propertySources)) {
            return Collections.emptyMap();
        } else {
            if (propertySources.size() == 1) {
                PropertySource propertySource = (PropertySource)propertySources.get(0);
                if (propertySource != null && propertySource.getSource() instanceof Map) {
                    return (Map)propertySource.getSource();
                }
            }

            return Collections.singletonMap(String.join(",", dataId, group), propertySources);
        }
    }

    public String getGroup() {
        return this.group;
    }

    public String getDataId() {
        return this.dataId;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public boolean isRefreshable() {
        return this.isRefreshable;
    }
}
