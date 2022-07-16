package com.feather.contentcenter.feignClient.fallback;

import com.feather.contentcenter.domain.dto.user.UserDTO;
import com.feather.contentcenter.feignClient.UserFeignClient;
import org.springframework.stereotype.Component;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.feignClient.fallback
 * @className: UserCenterFeignClientFallback
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/16 16:23
 * @version: 1.0
 */
@Component
public class UserCenterFeignClientFallback  implements UserFeignClient {

    @Override
    public UserDTO findById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setWxNickname("默认用户名字");
        return  userDTO;
    }
}
