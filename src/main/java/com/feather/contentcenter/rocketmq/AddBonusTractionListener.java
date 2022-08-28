package com.feather.contentcenter.rocketmq;

import com.feather.contentcenter.dao.message.RocketmqTransactionLogMapper;
import com.feather.contentcenter.domain.dto.share.ShareAuditDTO;
import com.feather.contentcenter.domain.entity.message.RocketmqTransactionLog;
import com.feather.contentcenter.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Objects;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.rocketmq
 * @className: AddBonusTractionListener
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/8/27 17:28
 * @version: 1.0
 */
@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusTractionListener implements RocketMQLocalTransactionListener {

    private  final ShareService shareService;

    private  final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        MessageHeaders headers = message.getHeaders();
        String tractionId= (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) Objects.requireNonNull(headers.get("share_id")));
        try {
            this.shareService.auditByIdWithRocketMqLog(shareId, (ShareAuditDTO) o,tractionId);
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String tractionId= (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) Objects.requireNonNull(headers.get("share_id")));
        RocketmqTransactionLog rocketmqTransactionLog = this.rocketmqTransactionLogMapper.selectOne(RocketmqTransactionLog.builder().transactionId(tractionId).build());
        if (rocketmqTransactionLog != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
