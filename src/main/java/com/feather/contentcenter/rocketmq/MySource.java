package com.feather.contentcenter.rocketmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.rocketmq
 * @className: MySource
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/8/27 21:40
 * @version: 1.0
 */
public interface MySource {
    String MY_OUTPUT="my-output";

    @Output(MY_OUTPUT)
    MessageChannel output();
}
