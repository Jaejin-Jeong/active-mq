package kr.contec.activemq.controller;

import kr.contec.activemq.dto.SendMes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@AllArgsConstructor
public class SendTwoController {

    @Autowired
    @Qualifier("secondTemplate")
    private final JmsTemplate jmsSecondTemplate;

    @PostMapping(path = "/sendTwo", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody SendMes sendMes) {

        log.info(sendMes.toString());
            jmsSecondTemplate.convertAndSend(new ActiveMQTopic("VirtualTopic.TWO"), sendMes);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/queueTwo", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendQueuesMessage(@RequestBody String message) {
        jmsSecondTemplate.convertAndSend(new ActiveMQQueue("queueTwo"), message);
        return ResponseEntity.ok().build();
    }



    @PostMapping(path = "/topicTwo", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendTopicMessage(@RequestBody String msg) {
        jmsSecondTemplate.convertAndSend(new ActiveMQTopic("Topic.Two"), msg);
        //jmsTemplate.convertAndSend(topic, msg, message -> {
        //            message.setIntProperty("id", 1);
        //            message.setStringProperty("type", "Text");
        //            return message;
        //        }
        //);
        return ResponseEntity.ok().build();
    }


}
