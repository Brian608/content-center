package com.feather.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.configuration
 * @className: UserCenterFeignConfiguration
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/25 07:47
 * @version: 1.0
 */
public class UserCenterFeignConfiguration {
    @Bean
    public Logger.Level level(){
        //打印feign所有请求的细节
        return Logger.Level.FULL;
    }
}
