package com.tuotiansudai.mq.consumer.point;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.tuotiansudai.fudian.message.BankRechargeMessage;
import com.tuotiansudai.mq.client.model.MessageQueue;
import com.tuotiansudai.mq.consumer.MessageConsumer;
import com.tuotiansudai.point.repository.model.PointTask;
import com.tuotiansudai.point.service.PointTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class RechargeCompletePointTaskConsumer implements MessageConsumer {
    private static Logger logger = LoggerFactory.getLogger(RechargeCompletePointTaskConsumer.class);

    @Autowired
    private PointTaskService pointTaskService;

    @Override
    public MessageQueue queue() {
        return MessageQueue.Recharge_CompletePointTask;
    }

    @Override
    public void consume(String message) {
        logger.info("[MQ] receive message: {}: {}.", this.queue(), message);

        if (Strings.isNullOrEmpty(message)) {
            logger.error("[MQ] Recharge_CompletePointTask message is empty");
            return;
        }
        try{
            BankRechargeMessage bankRechargeMessage = new Gson().fromJson(message, BankRechargeMessage.class);
            if (bankRechargeMessage.isStatus()) {
                pointTaskService.completeNewbieTask(PointTask.FIRST_RECHARGE, bankRechargeMessage.getLoginName());
            }else {
                logger.error("[MQ] message is invalid {}", message);
            }

        }catch (Exception e){
            logger.error(MessageFormat.format("[MQ] consume message error, message: {0}", message), e);
        }

    }
}
