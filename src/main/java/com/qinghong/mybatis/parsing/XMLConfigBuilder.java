package com.qinghong.mybatis.parsing;

import com.qinghong.mybatis.mapping.MyConfiguration;
import com.qinghong.mybatis.mapping.MyEnvironment;
import com.qinghong.mybatis.mapping.MyMapperStatement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class XMLConfigBuilder {

    private XPathParser parser;

    public XMLConfigBuilder(InputStream inputStream) {
        this.parser = new XPathParser(inputStream);
    }

    public MyConfiguration parse() {
        Node dataSourceNode = parser.xNode("/configuration/environments/environment");

        Properties properties = new Properties();
        NodeList childNodes = dataSourceNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                properties.setProperty(item.getAttributes().getNamedItem("name").getNodeValue(),
                        item.getAttributes().getNamedItem("value").getNodeValue());
            }
        }

        Map<String, MyMapperStatement> mapperStatementMap = new ConcurrentHashMap<>();
        Node mappersNode = parser.xNode("/configuration/mappers");
        NodeList mapperNodeList = mappersNode.getChildNodes();
        for (int i = 0; i < mapperNodeList.getLength(); i++) {
            Node mapperNode = mapperNodeList.item(i);
            if (mapperNode.getNodeType() == Node.ELEMENT_NODE) {

                String resource = mapperNode.getAttributes().getNamedItem("resource").getNodeValue();

                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);

                this.parser = new XPathParser(inputStream);

                Element element = parser.getDocument().getDocumentElement();
                String namespace = element.getAttribute("namespace");

                NodeList sqlNodeList = element.getChildNodes();

                for (int j = 0; j < sqlNodeList.getLength(); j++) {
                    Node sqlNode = sqlNodeList.item(j);
                    if (sqlNode.getNodeType() == Node.ELEMENT_NODE) {
                        String id = "";
                        String resultType = "";
                        String parameterType = "";

                        Node idNode = sqlNode.getAttributes().getNamedItem("id");
                        if (null == idNode) {
                            throw new RuntimeException("sql is null");
                        } else {
                            id = sqlNode.getAttributes().getNamedItem("id").getNodeValue();
                        }
                        Node resultTypeNode = sqlNode.getAttributes().getNamedItem("resultType");
                        if (null != resultTypeNode) {
                            resultType = sqlNode.getAttributes().getNamedItem("resultType").getNodeValue();
                        }
                        Node parameterTypeNode = sqlNode.getAttributes().getNamedItem("parameterType");
                        if (null != parameterTypeNode) {
                            parameterType = sqlNode.getAttributes().getNamedItem("parameterType").getNodeValue();
                        }

                        String sql = sqlNode.getTextContent();

                        MyMapperStatement mapperStatement = new MyMapperStatement();
                        mapperStatement.setId(id);
                        mapperStatement.setNamespace(namespace);
                        mapperStatement.setParameterType(parameterType);
                        mapperStatement.setResultType(resultType);
                        mapperStatement.setSql(sql);
                        mapperStatementMap.put(namespace + "." + id, mapperStatement);
                    }
                }
            }
        }

        MyConfiguration catConfiguration = new MyConfiguration();

        MyEnvironment catEnvironment = new MyEnvironment();
        catEnvironment.setDriver(properties.getProperty("driver"));
        catEnvironment.setPassword(properties.getProperty("password"));
        catEnvironment.setUsername(properties.getProperty("username"));
        catEnvironment.setUrl(properties.getProperty("url"));

        catConfiguration.setMyEnvironment(catEnvironment);
        catConfiguration.setMapperStatement(mapperStatementMap);

        return catConfiguration;

    }
}
