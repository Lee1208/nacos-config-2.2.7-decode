//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.spring.util.PropertySourcesUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@ConfigurationProperties("spring.cloud.nacos.config")
public class NacosConfigProperties {
    public static final String PREFIX = "spring.cloud.nacos.config";
    public static final String COMMAS = ",";
    public static final String SEPARATOR = "[,]";
    private static final Pattern PATTERN = Pattern.compile("-(\\w)");
    private static final Logger log = LoggerFactory.getLogger(NacosConfigProperties.class);
    @Autowired
    @JsonIgnore
    private Environment environment;
    private String serverAddr;
    private String username;
    private String password;
    private String encode;
    private String group = "DEFAULT_GROUP";
    private String prefix;
    private String fileExtension = "properties";
    private int timeout = 3000;
    private String maxRetry;
    private String configLongPollTimeout;
    private String configRetryTime;
    private boolean enableRemoteSyncConfig = false;
    private String endpoint;
    private String namespace;
    private String accessKey;
    private String secretKey;
    private String ramRoleName;
    private String contextPath;
    private String clusterName;
    private String name;
    private List<Config> sharedConfigs;
    private List<Config> extensionConfigs;
    private boolean refreshEnabled = true;
    private boolean encryption = false;
    private String encryptionSplitChar = "(nsencryption)-";
    private String decryptClass = "";


    public NacosConfigProperties() {
    }

    @PostConstruct
    public void init() {
        this.overrideFromEnv();
    }

    private void overrideFromEnv() {
        if (StringUtils.isEmpty(this.getServerAddr())) {
            String serverAddr = this.environment.resolvePlaceholders("${spring.cloud.nacos.config.server-addr:}");
            if (StringUtils.isEmpty(serverAddr)) {
                serverAddr = this.environment.resolvePlaceholders("${spring.cloud.nacos.server-addr:localhost:8848}");
            }

            this.setServerAddr(serverAddr);
        }

        if (StringUtils.isEmpty(this.getUsername())) {
            this.setUsername(this.environment.resolvePlaceholders("${spring.cloud.nacos.username:}"));
        }

        if (StringUtils.isEmpty(this.getPassword())) {
            this.setPassword(this.environment.resolvePlaceholders("${spring.cloud.nacos.password:}"));
        }

    }

    public String getServerAddr() {
        return this.serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getMaxRetry() {
        return this.maxRetry;
    }

    public void setMaxRetry(String maxRetry) {
        this.maxRetry = maxRetry;
    }

    public String getConfigLongPollTimeout() {
        return this.configLongPollTimeout;
    }

    public void setConfigLongPollTimeout(String configLongPollTimeout) {
        this.configLongPollTimeout = configLongPollTimeout;
    }

    public String getConfigRetryTime() {
        return this.configRetryTime;
    }

    public void setConfigRetryTime(String configRetryTime) {
        this.configRetryTime = configRetryTime;
    }

    public Boolean getEnableRemoteSyncConfig() {
        return this.enableRemoteSyncConfig;
    }

    public void setEnableRemoteSyncConfig(Boolean enableRemoteSyncConfig) {
        this.enableRemoteSyncConfig = enableRemoteSyncConfig;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRamRoleName() {
        return this.ramRoleName;
    }

    public void setRamRoleName(String ramRoleName) {
        this.ramRoleName = ramRoleName;
    }

    public String getEncode() {
        return this.encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getClusterName() {
        return this.clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<Config> getSharedConfigs() {
        return this.sharedConfigs;
    }

    public void setSharedConfigs(List<Config> sharedConfigs) {
        this.sharedConfigs = sharedConfigs;
    }

    public List<Config> getExtensionConfigs() {
        return this.extensionConfigs;
    }

    public void setExtensionConfigs(List<Config> extensionConfigs) {
        this.extensionConfigs = extensionConfigs;
    }

    public boolean isRefreshEnabled() {
        return this.refreshEnabled;
    }

    public void setRefreshEnabled(boolean refreshEnabled) {
        this.refreshEnabled = refreshEnabled;
    }


    public boolean isEncryption() {
        return encryption;
    }

    public void setEncryption(boolean encryption) {
        this.encryption = encryption;
    }

    public String getDecryptClass() {
        return decryptClass;
    }

    public void setDecryptClass(String decryptClass) {
        this.decryptClass = decryptClass;
    }

    public String getEncryptionSplitChar() {
        return encryptionSplitChar;
    }

    public void setEncryptionSplitChar(String encryptionSplitChar) {
        this.encryptionSplitChar = encryptionSplitChar;
    }

    /** @deprecated */
    @Deprecated
    @DeprecatedConfigurationProperty(
            reason = "replaced to NacosConfigProperties#sharedConfigs and not use it at the same time.",
            replacement = "spring.cloud.nacos.config.shared-configs[x]"
    )
    public String getSharedDataids() {
        return null == this.getSharedConfigs() ? null : (String)this.getSharedConfigs().stream().map(Config::getDataId).collect(Collectors.joining(","));
    }

    /** @deprecated */
    @Deprecated
    public void setSharedDataids(String sharedDataids) {
        if (null != sharedDataids && sharedDataids.trim().length() > 0) {
            List<Config> list = new ArrayList();
            Stream.of(sharedDataids.split("[,]")).forEach((dataId) -> {
                list.add(new Config(dataId.trim()));
            });
            this.compatibleSharedConfigs(list);
        }

    }

    /** @deprecated */
    @Deprecated
    @DeprecatedConfigurationProperty(
            reason = "replaced to NacosConfigProperties#sharedConfigs and not use it at the same time.",
            replacement = "spring.cloud.nacos.config.shared-configs[x].refresh"
    )
    public String getRefreshableDataids() {
        return null == this.getSharedConfigs() ? null : (String)this.getSharedConfigs().stream().filter(Config::isRefresh).map(Config::getDataId).collect(Collectors.joining(","));
    }

    /** @deprecated */
    @Deprecated
    public void setRefreshableDataids(String refreshableDataids) {
        if (null != refreshableDataids && refreshableDataids.trim().length() > 0) {
            List<Config> list = new ArrayList();
            Stream.of(refreshableDataids.split("[,]")).forEach((dataId) -> {
                list.add((new Config(dataId.trim())).setRefresh(true));
            });
            this.compatibleSharedConfigs(list);
        }

    }

    private void compatibleSharedConfigs(List<Config> configList) {
        if (null != this.getSharedConfigs()) {
            configList.addAll(this.getSharedConfigs());
        }

        List<Config> result = new ArrayList();
        configList.stream().collect(Collectors.groupingBy((cfg) -> cfg.getGroup() + cfg.getDataId(), LinkedHashMap::new, Collectors.toList())).forEach((key, list) -> {
            list.stream().reduce((a, b) -> new Config(a.getDataId(), a.getGroup(), a.isRefresh() || b != null && b.isRefresh())).ifPresent(result::add);
        });
        this.setSharedConfigs(result);
    }

    /** @deprecated */
    @Deprecated
    @DeprecatedConfigurationProperty(
            reason = "replaced to NacosConfigProperties#extensionConfigs and not use it at the same time .",
            replacement = "spring.cloud.nacos.config.extension-configs[x]"
    )
    public List<Config> getExtConfig() {
        return this.getExtensionConfigs();
    }

    /** @deprecated */
    @Deprecated
    public void setExtConfig(List<Config> extConfig) {
        this.setExtensionConfigs(extConfig);
    }

    /** @deprecated */
    @Deprecated
    public ConfigService configServiceInstance() {
        return NacosConfigManager.createConfigService(this);
    }

    /** @deprecated */
    @Deprecated
    public Properties getConfigServiceProperties() {
        return this.assembleConfigServiceProperties();
    }

    public Properties assembleConfigServiceProperties() {
        Properties properties = new Properties();
        properties.put("serverAddr", Objects.toString(this.serverAddr, ""));
        properties.put("username", Objects.toString(this.username, ""));
        properties.put("password", Objects.toString(this.password, ""));
        properties.put("encode", Objects.toString(this.encode, ""));
        properties.put("namespace", Objects.toString(this.namespace, ""));
        properties.put("accessKey", Objects.toString(this.accessKey, ""));
        properties.put("secretKey", Objects.toString(this.secretKey, ""));
        properties.put("ramRoleName", Objects.toString(this.ramRoleName, ""));
        properties.put("clusterName", Objects.toString(this.clusterName, ""));
        properties.put("maxRetry", Objects.toString(this.maxRetry, ""));
        properties.put("configLongPollTimeout", Objects.toString(this.configLongPollTimeout, ""));
        properties.put("configRetryTime", Objects.toString(this.configRetryTime, ""));
        properties.put("enableRemoteSyncConfig", Objects.toString(this.enableRemoteSyncConfig, ""));
        String endpoint = Objects.toString(this.endpoint, "");
        if (endpoint.contains(":")) {
            int index = endpoint.indexOf(":");
            properties.put("endpoint", endpoint.substring(0, index));
            properties.put("endpointPort", endpoint.substring(index + 1));
        } else {
            properties.put("endpoint", endpoint);
        }

        this.enrichNacosConfigProperties(properties);
        return properties;
    }

    private void enrichNacosConfigProperties(Properties nacosConfigProperties) {
        Map<String, Object> properties = PropertySourcesUtils.getSubProperties((ConfigurableEnvironment)this.environment, "spring.cloud.nacos.config");
        properties.forEach((k, v) -> {
            nacosConfigProperties.putIfAbsent(this.resolveKey(k), String.valueOf(v));
        });
    }

    private String resolveKey(String key) {
        Matcher matcher = PATTERN.matcher(key);
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "NacosConfigProperties{serverAddr='" + this.serverAddr + '\'' + ", encode='" + this.encode + '\'' + ", group='" + this.group + '\'' + ", prefix='" + this.prefix + '\'' + ", fileExtension='" + this.fileExtension + '\'' + ", timeout=" + this.timeout + ", maxRetry='" + this.maxRetry + '\'' + ", configLongPollTimeout='" + this.configLongPollTimeout + '\'' + ", configRetryTime='" + this.configRetryTime + '\'' + ", enableRemoteSyncConfig=" + this.enableRemoteSyncConfig + ", endpoint='" + this.endpoint + '\'' + ", namespace='" + this.namespace + '\'' + ", accessKey='" + this.accessKey + '\'' + ", secretKey='" + this.secretKey + '\'' + ", ramRoleName='" + this.ramRoleName + '\'' + ", contextPath='" + this.contextPath + '\'' + ", clusterName='" + this.clusterName + '\'' + ", name='" + this.name + '\'' + '\'' + ", shares=" + this.sharedConfigs + ", extensions=" + this.extensionConfigs + ", refreshEnabled=" + this.refreshEnabled + '}';
    }

    public static class Config {
        private String dataId;
        private String group;
        private boolean refresh;

        public Config() {
            this.group = "DEFAULT_GROUP";
            this.refresh = false;
        }

        public Config(String dataId) {
            this.group = "DEFAULT_GROUP";
            this.refresh = false;
            this.dataId = dataId;
        }

        public Config(String dataId, String group) {
            this(dataId);
            this.group = group;
        }

        public Config(String dataId, boolean refresh) {
            this(dataId);
            this.refresh = refresh;
        }

        public Config(String dataId, String group, boolean refresh) {
            this(dataId, group);
            this.refresh = refresh;
        }

        public String getDataId() {
            return this.dataId;
        }

        public Config setDataId(String dataId) {
            this.dataId = dataId;
            return this;
        }

        public String getGroup() {
            return this.group;
        }

        public Config setGroup(String group) {
            this.group = group;
            return this;
        }

        public boolean isRefresh() {
            return this.refresh;
        }

        public Config setRefresh(boolean refresh) {
            this.refresh = refresh;
            return this;
        }
        @Override
        public String toString() {
            return "Config{dataId='" + this.dataId + '\'' + ", group='" + this.group + '\'' + ", refresh=" + this.refresh + '}';
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                Config config = (Config)o;
                return this.refresh == config.refresh && Objects.equals(this.dataId, config.dataId) && Objects.equals(this.group, config.group);
            } else {
                return false;
            }
        }
        @Override
        public int hashCode() {
            return Objects.hash(new Object[]{this.dataId, this.group, this.refresh});
        }
    }
}
