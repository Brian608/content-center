package com.feather.contentcenter.service.content;

import com.feather.contentcenter.dao.content.ShareMapper;
import com.feather.contentcenter.domain.dto.share.ShareDTO;
import com.feather.contentcenter.domain.entity.content.Share;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private final  RestTemplate restTemplate;

    public ShareDTO findById(Integer id){
        Share share = this.shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        ShareDTO shareDTO = new ShareDTO();
        return  shareDTO;
    }
}
