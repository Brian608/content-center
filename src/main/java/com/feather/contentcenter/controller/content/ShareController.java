package com.feather.contentcenter.controller.content;

import com.feather.contentcenter.domain.dto.share.ShareDTO;
import com.feather.contentcenter.service.content.ShareService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.controller.content
 * @className: ShareController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/22 08:30
 * @version: 1.0
 */
@RestController
@RequestMapping("/shares")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShareController {

    private final ShareService shareService;

    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }
}
