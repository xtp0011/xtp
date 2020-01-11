package com.xtp.sourceanalysis.spi;

import java.util.ServiceLoader;

/**
 * spi 思想
 *  是一种以接口方式提供服务的标准
 */
public class MainTest {
    public static void main(String[] args){
        //C:\Users\徐太平\.m2\repository\mysql\mysql-connector-java\8.0.13\mysql-connector-java-8.0.13.jar!
        //META-INF\services
        //META-INF\services\com.xtp.sourceanalysis.spi.IServic
        ServiceLoader<SearchService> services = ServiceLoader.load(SearchService.class);
        System.out.println(services);
        for(SearchService service: services){
            service.show();
        }
    }
}
