package com.xtp.sourceanalysis.nio;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class TestTomcat {
    public static void main(String[] args) throws LifecycleException {
        //1.构建tomcat对象
        Tomcat tomcat = new Tomcat();
        //2.构建connector对象，并指定协议
        //tomcat使用connector处理连接，一个tomcat可以配置多个connector
        Connector connector = new Connector("HTTP/1.1");
        //3.设置tomcat监听端口
        connector.setPort(8080);
        tomcat.setConnector(connector);
        //启动tomcat
        tomcat.start();
        tomcat.getServer().await();
    }
}
