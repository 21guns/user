package com.guns21.user.service.consumers;

import com.guns21.user.entity.SmsLogDO;
import com.guns21.user.repository.SmsLogRepository;
import com.guns21.user.sms.SendMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.$;

@Service
class SmsLogConsumer implements Consumer<Event<SmsLogDO>> {
    private static Logger logger = LoggerFactory.getLogger(SmsLogConsumer.class);

    @Autowired
    private SmsLogRepository smsLogRepository;
    @Autowired
    private EventBus eventBus;

    @PostConstruct
    public void init() {
        eventBus.on($("sms-log"), this);
    }

    @Override
    public void accept(Event<SmsLogDO> ev) {
        SmsLogDO smsLogDO = ev.getData();
        boolean send = SendMessageUtils.send(smsLogDO.getMobile(), smsLogDO.getContent());
        if (send) {
            smsLogDO.setResult("成功");
        } else {
            smsLogDO.setResult("失败");
        }
        smsLogRepository.save(smsLogDO);
    }

}