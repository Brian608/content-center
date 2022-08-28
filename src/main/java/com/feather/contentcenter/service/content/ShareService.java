package com.feather.contentcenter.service.content;

import com.alibaba.fastjson.JSON;
import com.feather.contentcenter.dao.content.ShareMapper;
import com.feather.contentcenter.dao.message.RocketmqTransactionLogMapper;
import com.feather.contentcenter.domain.dto.share.ShareAuditDTO;
import com.feather.contentcenter.domain.dto.share.ShareDTO;
import com.feather.contentcenter.domain.dto.user.UserDTO;
import com.feather.contentcenter.domain.entity.content.Share;
import com.feather.contentcenter.domain.entity.message.RocketmqTransactionLog;
import com.feather.contentcenter.domain.enums.AuditStatusEnum;
import com.feather.contentcenter.domain.message.UserAddBonusMsgDTO;
import com.feather.contentcenter.feignClient.UserFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

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

    private final UserFeignClient userFeignClient;

    private  final RocketMQTemplate rocketMQTemplate;


    private  final RocketmqTransactionLogMapper  rocketmqTransactionLogMapper;


    public ShareDTO findById(Integer id){
        Share share = this.shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        UserDTO userDTO=this.userFeignClient.findById(userId);
        ShareDTO shareDTO = new ShareDTO();
        //消息装配
        BeanUtils.copyProperties(userDTO,shareDTO);
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return  shareDTO;
    }

    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法！该分享不存在！");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }

        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            // 发送半消息。。
            String transactionId = UUID.randomUUID().toString();
            this.rocketMQTemplate.sendMessageInTransaction(
                    "tx-add-bonus-group",
                    "add-bonus",
                    MessageBuilder.withPayload(
                            UserAddBonusMsgDTO.
                                    builder().
                                    bonus(50).
                                    userId(share.getUserId())).
                            setHeader(RocketMQHeaders.TRANSACTION_ID,transactionId).
                     setHeader("share_id",id).build(),auditDTO
                    );
        }
        else {
            this.auditByIdInDB(id, auditDTO);
        }
        return share;
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason())
                .build();
        this.shareMapper.updateByPrimaryKeySelective(share);

        // 4. 把share写到缓存
    }


    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
        this.auditByIdInDB(id, auditDTO);

        this.rocketmqTransactionLogMapper.insertSelective(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log("审核分享...")
                        .build()
        );
    }

}
