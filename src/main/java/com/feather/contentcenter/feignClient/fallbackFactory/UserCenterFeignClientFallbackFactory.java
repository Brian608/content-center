package com.feather.contentcenter.feignClient.fallbackFactory;

import com.feather.contentcenter.domain.dto.user.UserDTO;
import com.feather.contentcenter.feignClient.UserFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.feignClient.fallbackFactory
 * @className: UserCenterFeignClientFallbackFacotory
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/16 16:27
 * @version: 1.0
 */
@Slf4j
@Component
public class UserCenterFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public UserDTO findById(Integer id) {
                log.warn("远程调用被限流/降级了",throwable);
                UserDTO userDTO = new UserDTO();
                userDTO.setWxNickname("默认用户名字");
                return  userDTO;
            }
        };
    }
}
