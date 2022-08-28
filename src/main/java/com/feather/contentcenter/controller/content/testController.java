package com.feather.contentcenter.controller.content;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.feather.contentcenter.rocketmq.MySource;
import com.feather.contentcenter.sentineltest.TestControllerBlockHandlerClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @projectName: content-center
 * @package: com.feather.contentcenter.controller.content
 * @className: testController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/7 07:26
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class testController {

    @Autowired
    private Source source;

    @Autowired
    private MySource mySource;


    @GetMapping("/test-hot")
    @SentinelResource("hot")
    public  String testhot(@RequestParam(required = false)String a,@RequestParam(required = false)String b){
        return  a+" "+b;
    }

    @GetMapping("test-add-flow-rule")
    public String testHot() {
        this.initFlowQpsRule();
        return "success";
    }

    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule("/shares/1");
        // set limit qps to 20
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @GetMapping("/test-sentinel-api")
    public String testSentinelAPI(
            @RequestParam(required = false) String a) {

        String resourceName = "test-sentinel-api";
        ContextUtil.enter(resourceName, "test-wfw");

        // 定义一个sentinel保护的资源，名称是test-sentinel-api
        Entry entry = null;
        try {

            entry = SphU.entry(resourceName);
            // 被保护的业务逻辑
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a不能为空");
            }
            return a;
        }
        // 如果被保护的资源被限流或者降级了，就会抛BlockException
        catch (BlockException e) {
            log.warn("限流，或者降级了", e);
            return "限流，或者降级了";
        } catch (IllegalArgumentException e2) {
            // 统计IllegalArgumentException【发生的次数、发生占比...】
            Tracer.trace(e2);
            return "参数非法！";
        } finally {
            if (entry != null) {
                // 退出entry
                entry.exit();
            }
            ContextUtil.exit();
        }
    }
    @GetMapping("/test-sentinel-resource")
    @SentinelResource(
            value = "test-sentinel-api",
            blockHandler = "block",
            blockHandlerClass = TestControllerBlockHandlerClass.class,
            fallback = "fallback"
    )
    public String testSentinelResource(@RequestParam(required = false) String a) {
        if (StringUtils.isBlank(a)) {
            throw new IllegalArgumentException("a cannot be blank.");
        }
        return a;
    }

    /**
     * 1.5 处理降级
     * - sentinel 1.6 可以处理Throwable
     *
     * @param a
     * @return
     */
    public String fallback(String a) {
        return "限流，或者降级了 fallback";
    }

    @GetMapping("/test-stream")
    public String testStream(){
        this.source.output().send(MessageBuilder.withPayload("消息提").build());
        return  "success";
    }

    @GetMapping("/test-stream2")
    public String testStream2(){
        this.mySource.output().send(MessageBuilder.withPayload("消息提").build());
        return  "success";
    }

}
