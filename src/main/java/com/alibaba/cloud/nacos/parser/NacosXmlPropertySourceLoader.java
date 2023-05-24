//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.cloud.nacos.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NacosXmlPropertySourceLoader extends AbstractPropertySourceLoader implements Ordered {
    public NacosXmlPropertySourceLoader() {
    }

    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public String[] getFileExtensions() {
        return new String[]{"xml"};
    }

    protected List<PropertySource<?>> doLoad(String name, Resource resource) throws IOException {
        Map<String, Object> nacosDataMap = this.parseXml2Map(resource);
        return Collections.singletonList(new OriginTrackedMapPropertySource(name, nacosDataMap, true));
    }

    private Map<String, Object> parseXml2Map(Resource resource) throws IOException {
        Map<String, Object> map = new LinkedHashMap(32);

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(resource.getInputStream());
            if (null == document) {
                return null;
            } else {
                this.parseNodeList(document.getChildNodes(), map, "");
                return map;
            }
        } catch (Exception var5) {
            throw new IOException("The xml content parse error.", var5.getCause());
        }
    }

    private void parseNodeList(NodeList nodeList, Map<String, Object> map, String parentKey) {
        if (nodeList != null && nodeList.getLength() >= 1) {
            parentKey = parentKey == null ? "" : parentKey;

            for(int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                String value = node.getNodeValue();
                value = value == null ? "" : value.trim();
                String name = node.getNodeName();
                name = name == null ? "" : name.trim();
                if (!StringUtils.isEmpty(name)) {
                    String key = StringUtils.isEmpty(parentKey) ? name : parentKey + "." + name;
                    NamedNodeMap nodeMap = node.getAttributes();
                    this.parseNodeAttr(nodeMap, map, key);
                    if (node.getNodeType() == 1 && node.hasChildNodes()) {
                        this.parseNodeList(node.getChildNodes(), map, key);
                    } else if (value.length() >= 1) {
                        map.put(parentKey, value);
                    }
                }
            }

        }
    }

    private void parseNodeAttr(NamedNodeMap nodeMap, Map<String, Object> map, String parentKey) {
        if (null != nodeMap && nodeMap.getLength() >= 1) {
            for(int i = 0; i < nodeMap.getLength(); ++i) {
                Node node = nodeMap.item(i);
                if (null != node && node.getNodeType() == 2 && !StringUtils.isEmpty(node.getNodeName()) && !StringUtils.isEmpty(node.getNodeValue())) {
                    map.put(String.join(".", parentKey, node.getNodeName()), node.getNodeValue());
                }
            }

        }
    }
}
