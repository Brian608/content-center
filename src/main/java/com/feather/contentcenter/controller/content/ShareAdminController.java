package com.feather.contentcenter.controller.content;

import com.feather.contentcenter.domain.dto.share.ShareAuditDTO;
import com.feather.contentcenter.domain.entity.content.Share;
import com.feather.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.controller.content
 * @className: ShareAdminController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/8/24 08:21
 * @version: 1.0
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {
    private final ShareService shareService;

    @PutMapping("/audit/{id}")
    public Share auditById(@PathVariable Integer id,@RequestBody ShareAuditDTO shareAuditDto){
        //TODO 认证授权
        return  this.shareService.auditById(id,shareAuditDto);


    }


}
