package com.feather.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.sentineltest
 * @className: TestControllerBlockHandlerClass
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/7 08:40
 * @version: 1.0
 */
@Slf4j
public class TestControllerBlockHandlerClass {
    /**
     * 处理限流或者降级
     *
     * @param a
     * @param e
     * @return
     */
    public static String block(String a, BlockException e) {
        log.warn("限流，或者降级了 block", e);
        return "限流，或者降级了 block";
    }

}
