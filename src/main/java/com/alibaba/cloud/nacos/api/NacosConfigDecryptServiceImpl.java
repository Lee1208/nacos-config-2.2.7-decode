package com.alibaba.cloud.nacos.api;

import org.springframework.util.Base64Utils;
import java.nio.charset.StandardCharsets;

/**
 * @author lihaifeng
 * @email 1017875995@qq.com
 * @date 2023-05-24 14:21
 * @description
 * @className NacosConfigDecryptServiceImpl
 */
public class NacosConfigDecryptServiceImpl implements NacosConfigDecryptService{
    @Override
    public String decrypt(String value) {
        return new String(Base64Utils.decode(value.getBytes(StandardCharsets.UTF_8)));
    }
}
