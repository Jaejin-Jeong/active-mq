package kr.contec.activemq.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class SendController {

    @Autowired
    @Qualifier("firstTemplate")
    private final JmsTemplate jmsTemplate;


    @PostMapping(path = "/send", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody SendMes sendMes) {

        log.info(sendMes.toString());
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = null;
//        try {
//            jsonString = mapper.writeValueAsString(sendMes);
//            jmsTemplate.convertAndSend(new ActiveMQTopic("VirtualTopic.ONE"), jsonString);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        jmsTemplate.convertAndSend(new ActiveMQTopic("VirtualTopic.ONE"),sendMes);

        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/queue", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendQueuesMessage(@RequestBody String message) {
        jmsTemplate.convertAndSend(new ActiveMQQueue("queueOne"), message);
        return ResponseEntity.ok().build();
    }



    @PostMapping(path = "/topic", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendTopicMessage(@RequestBody String msg) {
        jmsTemplate.convertAndSend(new ActiveMQTopic("Topic.ONE"), msg);
        //jmsTemplate.convertAndSend(topic, msg, message -> {
        //            message.setIntProperty("id", 1);
        //            message.setStringProperty("type", "Text");
        //            return message;
        //        }
        //);
        return ResponseEntity.ok().build();
    }


}
