//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.client;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.cloud.nacos.NacosPropertySourceRepository;
import com.alibaba.cloud.nacos.api.NacosConfigDecryptService;
import com.alibaba.cloud.nacos.api.NacosConfigDecryptServiceImpl;
import com.alibaba.cloud.nacos.parser.NacosDataParserHandler;
import com.alibaba.cloud.nacos.refresh.NacosContextRefresher;
import com.alibaba.nacos.api.config.ConfigService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Order(0)
public class NacosPropertySourceLocator implements PropertySourceLocator {
    private static final Logger log = LoggerFactory.getLogger(NacosPropertySourceLocator.class);
    private static final String NACOS_PROPERTY_SOURCE_NAME = "NACOS";
    private static final String SEP1 = "-";
    private static final String DOT = ".";
    private NacosPropertySourceBuilder nacosPropertySourceBuilder;
    private NacosConfigProperties nacosConfigProperties;
    private NacosConfigManager nacosConfigManager;

    /** @deprecated */
    @Deprecated
    public NacosPropertySourceLocator(NacosConfigProperties nacosConfigProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
    }

    public NacosPropertySourceLocator(NacosConfigManager nacosConfigManager) {
        this.nacosConfigManager = nacosConfigManager;
        this.nacosConfigProperties = nacosConfigManager.getNacosConfigProperties();
    }

    @Override
    public PropertySource<?> locate(Environment env) {
        this.nacosConfigProperties.setEnvironment(env);
        ConfigService configService = this.nacosConfigManager.getConfigService();
        if (null == configService) {
            log.warn("no instance of config service found, can't load config from nacos");
            return null;
        } else {
            long timeout = (long)this.nacosConfigProperties.getTimeout();
            this.nacosPropertySourceBuilder = new NacosPropertySourceBuilder(configService, timeout);
            String name = this.nacosConfigProperties.getName();
            String dataIdPrefix = this.nacosConfigProperties.getPrefix();
            if (StringUtils.isEmpty(dataIdPrefix)) {
                dataIdPrefix = name;
            }

            if (StringUtils.isEmpty(dataIdPrefix)) {
                dataIdPrefix = env.getProperty("spring.application.name");
            }

            CompositePropertySource composite = new CompositePropertySource("NACOS");
            this.loadSharedConfiguration(composite);
            this.loadExtConfiguration(composite);
            this.loadApplicationConfiguration(composite, dataIdPrefix, this.nacosConfigProperties, env);
            return composite;
        }
    }

    private void loadSharedConfiguration(CompositePropertySource compositePropertySource) {
        List<NacosConfigProperties.Config> sharedConfigs = this.nacosConfigProperties.getSharedConfigs();
        if (!CollectionUtils.isEmpty(sharedConfigs)) {
            this.checkConfiguration(sharedConfigs, "shared-configs");
            this.loadNacosConfiguration(compositePropertySource, sharedConfigs);
        }

    }

    private void loadExtConfiguration(CompositePropertySource compositePropertySource) {
        List<NacosConfigProperties.Config> extConfigs = this.nacosConfigProperties.getExtensionConfigs();
        if (!CollectionUtils.isEmpty(extConfigs)) {
            this.checkConfiguration(extConfigs, "extension-configs");
            this.loadNacosConfiguration(compositePropertySource, extConfigs);
        }

    }

    private void loadApplicationConfiguration(CompositePropertySource compositePropertySource, String dataIdPrefix, NacosConfigProperties properties, Environment environment) {
        String fileExtension = properties.getFileExtension();
        String nacosGroup = properties.getGroup();
        this.loadNacosDataIfPresent(compositePropertySource, dataIdPrefix, nacosGroup, fileExtension, true);
        this.loadNacosDataIfPresent(compositePropertySource, dataIdPrefix + "." + fileExtension, nacosGroup, fileExtension, true);
        String[] var7 = environment.getActiveProfiles();
        int var8 = var7.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            String profile = var7[var9];
            String dataId = dataIdPrefix + "-" + profile + "." + fileExtension;
            this.loadNacosDataIfPresent(compositePropertySource, dataId, nacosGroup, fileExtension, true);
        }

    }

    private void loadNacosConfiguration(final CompositePropertySource composite, List<NacosConfigProperties.Config> configs) {
        Iterator var3 = configs.iterator();

        while(var3.hasNext()) {
            NacosConfigProperties.Config config = (NacosConfigProperties.Config)var3.next();
            this.loadNacosDataIfPresent(composite, config.getDataId(), config.getGroup(), NacosDataParserHandler.getInstance().getFileExtension(config.getDataId()), config.isRefresh());
        }

    }

    private void checkConfiguration(List<NacosConfigProperties.Config> configs, String tips) {
        for(int i = 0; i < configs.size(); ++i) {
            String dataId = ((NacosConfigProperties.Config)configs.get(i)).getDataId();
            if (dataId == null || dataId.trim().length() == 0) {
                throw new IllegalStateException(String.format("the [ spring.cloud.nacos.config.%s[%s] ] must give a dataId", tips, i));
            }
        }

    }

    private void loadNacosDataIfPresent(final CompositePropertySource composite, final String dataId, final String group, String fileExtension, boolean isRefreshable) {
        if (null != dataId && dataId.trim().length() >= 1) {
            if (null != group && group.trim().length() >= 1) {
                NacosPropertySource propertySource = this.loadNacosPropertySource(dataId, group, fileExtension, isRefreshable);
                this.addFirstPropertySource(composite, propertySource, false);
            }
        }
    }

    private NacosPropertySource loadNacosPropertySource(final String dataId, final String group, String fileExtension, boolean isRefreshable) {
        return NacosContextRefresher.getRefreshCount() != 0L && !isRefreshable ? NacosPropertySourceRepository.getNacosPropertySource(dataId, group) : this.nacosPropertySourceBuilder.build(dataId, group, fileExtension, isRefreshable);
    }

    private void addFirstPropertySource(final CompositePropertySource composite, NacosPropertySource nacosPropertySource, boolean ignoreEmpty) {
        if (null != nacosPropertySource && null != composite) {
            if (!ignoreEmpty || !((Map)nacosPropertySource.getSource()).isEmpty()) {
                if(nacosConfigProperties.isEncryption()){
                    String enc = nacosConfigProperties.getEncryptionSplitChar();
                   try {
                       String decryptServiceImpl = nacosConfigProperties.getDecryptClass();
                       NacosConfigDecryptService nacosConfigDecryptService;
                       if(StringUtils.isEmpty(decryptServiceImpl)){
                           nacosConfigDecryptService = new NacosConfigDecryptServiceImpl();
                       }else {
                           Class cls  =  Class.forName(decryptServiceImpl);
                           nacosConfigDecryptService = (NacosConfigDecryptService) cls.newInstance();
                       }
                       Map<String,Object> map = nacosPropertySource.getSource();
                       int i = 0;
                       for(Map.Entry<String,Object> entry : map.entrySet()){
                           String key = entry.getKey();
                           String value = entry.getValue().toString();
                           if(value.startsWith(enc)){
                               i = 1;
                              String decryptVal =  nacosConfigDecryptService.decrypt(value.substring(enc.length()));
                              map.put(key,decryptVal);
                           }
                       }
                       if(i == 1){
                           composite.addFirstPropertySource(new MapPropertySource(NacosPropertySourceRepository.getMapKey(nacosPropertySource.getDataId(),nacosPropertySource.getGroup()),map));
                       }

                   }catch (Exception e){
                       log.error("decrypt fail:{}",e.getMessage());
                   }
                }else {
                    composite.addFirstPropertySource(nacosPropertySource);
                }


            }
        }
    }

    public void setNacosConfigManager(NacosConfigManager nacosConfigManager) {
        this.nacosConfigManager = nacosConfigManager;
    }
}
