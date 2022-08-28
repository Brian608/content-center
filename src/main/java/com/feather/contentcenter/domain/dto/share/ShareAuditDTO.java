package com.feather.contentcenter.domain.dto.share;

import com.feather.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.domain.dto.share
 * @className: ShareAuditDto
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/8/27 09:28
 * @version: 1.0
 */
@Data
public class ShareAuditDTO {

    private AuditStatusEnum auditStatusEnum;

    private String reason;
}
