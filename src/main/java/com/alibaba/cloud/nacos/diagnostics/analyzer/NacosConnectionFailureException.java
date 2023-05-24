//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.diagnostics.analyzer;

public class NacosConnectionFailureException extends RuntimeException {
    private final String serverAddr;

    public NacosConnectionFailureException(String serverAddr, String message) {
        super(message);
        this.serverAddr = serverAddr;
    }

    public NacosConnectionFailureException(String serverAddr, String message, Throwable cause) {
        super(message, cause);
        this.serverAddr = serverAddr;
    }

    public String getServerAddr() {
        return this.serverAddr;
    }
}
