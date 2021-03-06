package com.feather.contentcenter.feignClient;

import com.feather.contentcenter.configuration.GlobalFeignConfiguration;
import com.feather.contentcenter.domain.dto.user.UserDTO;
import com.feather.contentcenter.feignClient.fallbackFactory.UserCenterFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.feignClient
 * @className: UserFeignClient
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/25 07:35
 * @version: 1.0
 */
@FeignClient(name = "user-center",configuration = GlobalFeignConfiguration.class,
        //fallback = UserCenterFeignClientFallback.class
        fallbackFactory = UserCenterFeignClientFallbackFactory.class
        )
public interface UserFeignClient {

    @GetMapping("users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
