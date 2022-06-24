package com.feather.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.configuration
 * @className: UserCenterRibbonConfiguration
 * @author: feather(杜雪松)
 * @description:通过代码的方式配置ribbon的规则
 * @since: 2022/6/24 07:19
 * @version: 1.0
 */
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {


}
