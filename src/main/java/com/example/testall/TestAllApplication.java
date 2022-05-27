package com.example.testall;

import com.example.testall.xmlspring.SomeXmlSpringObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
@EnableCaching
//@ImportResource({"file:${pathToIgniteConfig}"})
@ImportResource({"classpath:xml-beans.xml"})
public class TestAllApplication {

    @Autowired
    private SomeXmlSpringObject someXmlSpringObject;

    public static void main(String[] args) {
        SpringApplication.run(TestAllApplication.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.failOnUnknownProperties(false);
    }

    @Bean
    public ThreadPoolTaskScheduler ddosTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(200);
        threadPoolTaskScheduler.setThreadNamePrefix("DDOSer");
        return threadPoolTaskScheduler;
    }

    @Bean
    public RestTemplate commonRestTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public IgniteConfigurer nodeConfigurer() {
//        return cfg -> {
//            TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
//            TcpDiscoveryMulticastIpFinder tcpDiscoveryMulticastIpFinder = new TcpDiscoveryMulticastIpFinder();
//            tcpDiscoveryMulticastIpFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
//            tcpDiscoverySpi.setIpFinder(tcpDiscoveryMulticastIpFinder);
//
//            cfg.setDiscoverySpi(tcpDiscoverySpi);
//        };
//    }

//    @Bean
//    @Profile("local")
//    public Ignite igniteInstance() {
//        return Ignition.start("ignite-config.xml");
//    }
//
//    @Bean
//    public CacheManager cacheManager(Ignite ignite) {
//        SpringCacheManager cacheManager = new SpringCacheManager();
//        cacheManager.setIgniteInstanceName(ignite.configuration().getIgniteInstanceName());
//        return cacheManager;
//    }

}
