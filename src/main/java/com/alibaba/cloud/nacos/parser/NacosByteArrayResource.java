//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.parser;

import org.springframework.core.io.ByteArrayResource;

public class NacosByteArrayResource extends ByteArrayResource {
    private String filename;

    public NacosByteArrayResource(byte[] byteArray) {
        super(byteArray);
    }

    public NacosByteArrayResource(byte[] byteArray, String description) {
        super(byteArray, description);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return null == this.filename ? this.getDescription() : this.filename;
    }
}
