//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public class NacosJsonPropertySourceLoader extends AbstractPropertySourceLoader {
    private static final String VALUE = "value";

    public NacosJsonPropertySourceLoader() {
    }

    public String[] getFileExtensions() {
        return new String[]{"json"};
    }

    protected List<PropertySource<?>> doLoad(String name, Resource resource) throws IOException {
        Map<String, Object> result = new LinkedHashMap(32);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> nacosDataMap = (Map)mapper.readValue(resource.getInputStream(), LinkedHashMap.class);
        this.flattenedMap(result, nacosDataMap, (String)null);
        return Collections.singletonList(new OriginTrackedMapPropertySource(name, this.reloadMap(result), true));
    }

    protected Map<String, Object> reloadMap(Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            Map<String, Object> result = new LinkedHashMap(map);
            Iterator var3 = map.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                String key = (String)entry.getKey();
                if (key.contains(".")) {
                    int idx = key.lastIndexOf(".");
                    String suffix = key.substring(idx + 1);
                    if ("value".equalsIgnoreCase(suffix)) {
                        result.put(key.substring(0, idx), entry.getValue());
                    }
                }
            }

            return result;
        } else {
            return null;
        }
    }
}
