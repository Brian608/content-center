package com.feather.contentcenter.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.domain.enums
 * @className: AuditStatusEnum
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/8/27 09:32
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    /**
     * 待审核
     */
    NOT_YET,
    /**
     * 审核通过
     */
    PASS,
    /**
     * 审核不通过
     */
    REJECT;
}
