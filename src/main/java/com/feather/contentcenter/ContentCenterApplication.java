package com.feather.contentcenter;

import com.feather.contentcenter.rocketmq.MySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author duxuesong
 */
@MapperScan("com.feather.contentcenter.dao")
@SpringBootApplication
//@EnableFeignClients(defaultConfiguration = GlobalFeignConfiguration.class)
@EnableFeignClients
@EnableBinding({Source.class, MySource.class})
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

    @LoadBalanced
    @SentinelRestTemplate
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

}
