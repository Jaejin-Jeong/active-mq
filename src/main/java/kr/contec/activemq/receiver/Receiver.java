package kr.contec.activemq.receiver;

import kr.contec.activemq.dto.SendMes;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Receiver {

    @JmsListener(destination = "Topic.ONE", containerFactory = "firstPSDFTrueFactory")
    public void receiveTopic(SendMes sendMes)
    {

        System.out.println("Topic : "+ sendMes);
    }

    @JmsListener(destination = "ONE", containerFactory = "firstPSDFTrueFactory")
    public void receive1Topic(String message)
    {
        System.out.println("Topic-ddd : "+message);
    }

    @JmsListener(destination = "Consumer.Contec.VirtualTopic.ONE", containerFactory = "firstPSDFalseFactory")
    public void receiveFirstVirtualTopic(String sendMes)
    {
        System.out.println(sendMes);
        System.out.println("activemq.broker - VirtualTopic : "+ sendMes);
    }






    @JmsListener(destination = "Topic.Two", containerFactory = "secondPSDTrueFactory")
    public void receive2Topic(SendMes sendMes)
    {
        System.out.println("Topic : "+ sendMes.toString());
    }


    @JmsListener(destination = "Consumer.Contec1.VirtualTopic.TWO", containerFactory = "secondPSDFalseFactory")
    public void receiveSecondVirtualTopic(String message)
    {
        System.out.println("spring.activemq.broker - VirtualTopic : "+message);
    }


    @JmsListener(destination = "queueTwo", containerFactory = "secondPSDFalseFactory")
    public void receiveQueue(String message)
    {
        System.out.println("Queue : "+message);
    }

}
