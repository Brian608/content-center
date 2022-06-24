package ribbonconfiguration;

import com.feather.contentcenter.configuration.NacosWeightedRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: content-center
 * @package: ribbonconfiguration
 * @className: RibbonConfiguration
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/24 07:22
 * @version: 1.0
 */
@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule RibbonConfiguration(){
        return  new NacosWeightedRule();
    }
}
