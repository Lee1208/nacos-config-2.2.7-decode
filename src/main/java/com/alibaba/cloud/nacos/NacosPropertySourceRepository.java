//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos;

import com.alibaba.cloud.nacos.client.NacosPropertySource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class NacosPropertySourceRepository {
    private static final ConcurrentHashMap<String, NacosPropertySource> NACOS_PROPERTY_SOURCE_REPOSITORY = new ConcurrentHashMap();

    private NacosPropertySourceRepository() {
    }

    public static List<NacosPropertySource> getAll() {
        return new ArrayList(NACOS_PROPERTY_SOURCE_REPOSITORY.values());
    }

    /** @deprecated */
    @Deprecated
    public static void collectNacosPropertySources(NacosPropertySource nacosPropertySource) {
        NACOS_PROPERTY_SOURCE_REPOSITORY.putIfAbsent(nacosPropertySource.getDataId(), nacosPropertySource);
    }

    /** @deprecated */
    @Deprecated
    public static NacosPropertySource getNacosPropertySource(String dataId) {
        return NACOS_PROPERTY_SOURCE_REPOSITORY.get(dataId);
    }

    public static void collectNacosPropertySource(NacosPropertySource nacosPropertySource) {
        NACOS_PROPERTY_SOURCE_REPOSITORY.putIfAbsent(getMapKey(nacosPropertySource.getDataId(), nacosPropertySource.getGroup()), nacosPropertySource);
    }

    public static NacosPropertySource getNacosPropertySource(String dataId, String group) {
        return NACOS_PROPERTY_SOURCE_REPOSITORY.get(getMapKey(dataId, group));
    }

    public static String getMapKey(String dataId, String group) {
        return String.join(",", String.valueOf(dataId), String.valueOf(group));
    }
}
