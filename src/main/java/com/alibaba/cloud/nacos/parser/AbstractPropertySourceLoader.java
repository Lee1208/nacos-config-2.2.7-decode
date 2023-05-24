//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.parser;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public abstract class AbstractPropertySourceLoader implements PropertySourceLoader {
    static final String DOT = ".";

    public AbstractPropertySourceLoader() {
    }

    protected boolean canLoad(String name, Resource resource) {
        return resource instanceof NacosByteArrayResource;
    }

    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        return !this.canLoad(name, resource) ? Collections.emptyList() : this.doLoad(name, resource);
    }

    protected abstract List<PropertySource<?>> doLoad(String name, Resource resource) throws IOException;

    protected void flattenedMap(Map<String, Object> result, Map<String, Object> dataMap, String parentKey) {
        if (dataMap != null && !dataMap.isEmpty()) {
            Set<Map.Entry<String, Object>> entries = dataMap.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();

            while(true) {
                while(iterator.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry)iterator.next();
                    String key = (String)entry.getKey();
                    Object value = entry.getValue();
                    String fullKey = StringUtils.isEmpty(parentKey) ? key : (key.startsWith("[") ? parentKey.concat(key) : parentKey.concat(".").concat(key));
                    if (value instanceof Map) {
                        Map<String, Object> map = (Map)value;
                        this.flattenedMap(result, map, fullKey);
                    } else if (value instanceof Collection) {
                        int count = 0;
                        Collection<Object> collection = (Collection)value;
                        Iterator var12 = collection.iterator();

                        while(var12.hasNext()) {
                            Object object = var12.next();
                            this.flattenedMap(result, Collections.singletonMap("[" + count++ + "]", object), fullKey);
                        }
                    } else {
                        result.put(fullKey, value);
                    }
                }

                return;
            }
        }
    }
}
