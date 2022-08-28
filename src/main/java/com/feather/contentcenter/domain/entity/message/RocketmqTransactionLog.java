package com.feather.contentcenter.domain.entity.message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.domain.entity.message
 * @className: RocketmqTransactionLog
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/8/27 17:41
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "rocketmq_transaction_log")
public class RocketmqTransactionLog {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 事务id
     */
    @Column(name = "transaction_Id")
    private String transactionId;

    /**
     * 日志
     */
    private String log;
}
