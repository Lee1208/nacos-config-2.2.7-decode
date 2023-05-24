//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.diagnostics.analyzer;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class NacosConnectionFailureAnalyzer extends AbstractFailureAnalyzer<NacosConnectionFailureException> {
    public NacosConnectionFailureAnalyzer() {
    }

    protected FailureAnalysis analyze(Throwable rootFailure, NacosConnectionFailureException cause) {
        return new FailureAnalysis("Application failed to connect to Nacos server: \"" + cause.getServerAddr() + "\"", "Please check your Nacos server config", cause);
    }
}
