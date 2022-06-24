package com.feather.contentcenter.configuration;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

import java.util.List;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.configuration
 * @className: NacosWeightedRule
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/24 08:06
 * @version: 1.0
 */
@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        //读取配置文件，并初始化NacosWeightedRule
    }

    @Override
    public Server choose(Object o) {
        BaseLoadBalancer loadBalancer =(BaseLoadBalancer) this.getLoadBalancer();
        //想要请求的为服务名称
        String name = loadBalancer.getName();
    //实现负载均衡算法
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        try {
            Instance   instance = namingService.selectOneHealthyInstance(name);
            log.info("选择的实例是: port={},instance={}",instance.getPort(),instance);
            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }
    }
}
