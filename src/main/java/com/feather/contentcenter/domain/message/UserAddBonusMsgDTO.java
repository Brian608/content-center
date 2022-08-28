package com.feather.contentcenter.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.domain.message
 * @className: UserAddBounsMsgDTO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/8/27 15:55
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddBonusMsgDTO {

    private  Integer userId;

    private Integer bonus;
}
