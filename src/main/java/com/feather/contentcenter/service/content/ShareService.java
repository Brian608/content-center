package com.feather.contentcenter.service.content;

import com.feather.contentcenter.dao.content.ShareMapper;
import com.feather.contentcenter.domain.dto.share.ShareDTO;
import com.feather.contentcenter.domain.dto.user.UserDTO;
import com.feather.contentcenter.domain.entity.content.Share;
import com.feather.contentcenter.feignClient.UserFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.service.content
 * @className: ContentService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/22 08:00
 * @version: 1.0
 */
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ShareService {

    private final ShareMapper shareMapper;

    private final UserFeignClient userFeignClient;

    public ShareDTO findById(Integer id){
        Share share = this.shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        UserDTO userDTO=this.userFeignClient.findById(userId);
        ShareDTO shareDTO = new ShareDTO();
        //消息装配
        BeanUtils.copyProperties(userDTO,shareDTO);
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return  shareDTO;
    }
}
