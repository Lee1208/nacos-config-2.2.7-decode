//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.parser;

import com.alibaba.cloud.nacos.utils.NacosConfigUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class NacosDataParserHandler {
    private static final String DEFAULT_EXTENSION = "properties";
    private static List<PropertySourceLoader> propertySourceLoaders;

    private NacosDataParserHandler() {
        propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, this.getClass().getClassLoader());
    }

    public List<PropertySource<?>> parseNacosData(String configName, String configValue, String extension) throws IOException {
        if (StringUtils.isEmpty(configValue)) {
            return Collections.emptyList();
        } else {
            if (StringUtils.isEmpty(extension)) {
                extension = this.getFileExtension(configName);
            }

            Iterator var4 = propertySourceLoaders.iterator();

            PropertySourceLoader propertySourceLoader;
            do {
                if (!var4.hasNext()) {
                    return Collections.emptyList();
                }

                propertySourceLoader = (PropertySourceLoader)var4.next();
            } while(!this.canLoadFileExtension(propertySourceLoader, extension));

            NacosByteArrayResource nacosByteArrayResource;
            if (propertySourceLoader instanceof PropertiesPropertySourceLoader) {
                nacosByteArrayResource = new NacosByteArrayResource(NacosConfigUtils.selectiveConvertUnicode(configValue).getBytes(), configName);
            } else {
                nacosByteArrayResource = new NacosByteArrayResource(configValue.getBytes(), configName);
            }

            nacosByteArrayResource.setFilename(this.getFileName(configName, extension));
            List<PropertySource<?>> propertySourceList = propertySourceLoader.load(configName, nacosByteArrayResource);
            if (CollectionUtils.isEmpty(propertySourceList)) {
                return Collections.emptyList();
            } else {
                return (List)propertySourceList.stream().filter(Objects::nonNull).map((propertySource) -> {
                    if (propertySource instanceof EnumerablePropertySource) {
                        String[] propertyNames = ((EnumerablePropertySource)propertySource).getPropertyNames();
                        if (propertyNames != null && propertyNames.length > 0) {
                            Map<String, Object> map = new LinkedHashMap();
                            Arrays.stream(propertyNames).forEach((name) -> {
                                map.put(name, propertySource.getProperty(name));
                            });
                            return new OriginTrackedMapPropertySource(propertySource.getName(), map, true);
                        }
                    }

                    return propertySource;
                }).collect(Collectors.toList());
            }
        }
    }

    private boolean canLoadFileExtension(PropertySourceLoader loader, String extension) {
        return Arrays.stream(loader.getFileExtensions()).anyMatch((fileExtension) -> {
            return StringUtils.endsWithIgnoreCase(extension, fileExtension);
        });
    }

    public String getFileExtension(String name) {
        if (StringUtils.isEmpty(name)) {
            return "properties";
        } else {
            int idx = name.lastIndexOf(".");
            return idx > 0 && idx < name.length() - 1 ? name.substring(idx + 1) : "properties";
        }
    }

    private String getFileName(String name, String extension) {
        if (StringUtils.isEmpty(extension)) {
            return name;
        } else if (StringUtils.isEmpty(name)) {
            return extension;
        } else {
            int idx = name.lastIndexOf(".");
            if (idx > 0 && idx < name.length() - 1) {
                String ext = name.substring(idx + 1);
                if (extension.equalsIgnoreCase(ext)) {
                    return name;
                }
            }

            return name + "." + extension;
        }
    }

    public static NacosDataParserHandler getInstance() {
        return NacosDataParserHandler.ParserHandler.HANDLER;
    }

    private static class ParserHandler {
        private static final NacosDataParserHandler HANDLER = new NacosDataParserHandler();

        private ParserHandler() {
        }
    }
}
