//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.refresh;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class NacosRefreshHistory {
    private static final Logger log = LoggerFactory.getLogger(NacosRefreshHistory.class);
    private static final int MAX_SIZE = 20;
    private final LinkedList<Record> records = new LinkedList();
    private final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    });
    private MessageDigest md;

    public NacosRefreshHistory() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var2) {
            log.error("failed to initialize MessageDigest : ", var2);
        }

    }

    /** @deprecated */
    @Deprecated
    public void add(String dataId, String md5) {
        this.records.addFirst(new Record(((DateFormat)this.DATE_FORMAT.get()).format(new Date()), dataId, "", md5, (Map)null));
        if (this.records.size() > 20) {
            this.records.removeLast();
        }

    }

    public void addRefreshRecord(String dataId, String group, String data) {
        this.records.addFirst(new Record(((DateFormat)this.DATE_FORMAT.get()).format(new Date()), dataId, group, this.md5(data), (Map)null));
        if (this.records.size() > 20) {
            this.records.removeLast();
        }

    }

    public LinkedList<Record> getRecords() {
        return this.records;
    }

    private String md5(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        } else {
            if (null == this.md) {
                try {
                    this.md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException var3) {
                    return "unable to get md5";
                }
            }

            return (new BigInteger(1, this.md.digest(data.getBytes(StandardCharsets.UTF_8)))).toString(16);
        }
    }

    static class Record {
        private final String timestamp;
        private final String dataId;
        private final String group;
        private final String md5;

        Record(String timestamp, String dataId, String group, String md5, Map<String, Object> last) {
            this.timestamp = timestamp;
            this.dataId = dataId;
            this.group = group;
            this.md5 = md5;
        }

        public String getTimestamp() {
            return this.timestamp;
        }

        public String getDataId() {
            return this.dataId;
        }

        public String getGroup() {
            return this.group;
        }

        public String getMd5() {
            return this.md5;
        }
    }
}
