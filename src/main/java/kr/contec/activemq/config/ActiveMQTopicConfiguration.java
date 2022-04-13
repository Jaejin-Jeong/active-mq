package kr.contec.activemq.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Slf4j
@EnableJms
@Configuration
public class ActiveMQTopicConfiguration {

    /**
     * 외부 activeMq 커넥션 펙토리 설정
     *
     * @param url
     * @return ConnectionFactory
     */
    @Bean(name = "firstConnectionFactory")
    @Primary
    public ConnectionFactory firstConnectionFactory(
            @Value("${activemq.broker.url}") String url
    ) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        log.info(connectionFactory.toString());
        return connectionFactory;
    }

    /**
     * 내부 activeMq 커넥션 펙토리 설정
     *
     * @param url
     * @return ConnectionFactory
     */
    @Bean(name = "secondConnectionFactory")
    public ConnectionFactory secondConnectionFactory(
            @Value("${spring.activemq.broker-url}") String url
    ) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        log.info(connectionFactory.toString());
        return connectionFactory;
    }

    /**
     * 외부 activeMq 커넥션 펙토리를 사용하여  JMSTemplate 생성
     *
     * @param connectionFactory
     * @return JmsTemplate
     */
    @Bean(name = "firstTemplate")
    @Primary
    public JmsTemplate firstTemplate(
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        log.info(jmsTemplate.toString());
        //jmsTemplate.setPubSubNoLocal(true);
        return jmsTemplate;
    }

    /**
     * 내부 activeMq 커넥션 펙토리를 사용하여  JMSTemplate 생성
     *
     * @param connectionFactory
     * @return JmsTemplate
     */
    @Bean(name = "secondTemplate")
    public JmsTemplate secondTemplate(
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        log.info(jmsTemplate.toString());
        //jmsTemplate.setPubSubNoLocal(false);
        return jmsTemplate;
    }

    /**
     * JMS 메세지 변환기
     *
     * @return 메세지 변환기
     */
    @Bean
    public MessageConverter messageConverter() {

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("class");
//        converter.setTypeIdPropertyName("_type");
        return converter;
//
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
////        converter.setTypeIdPropertyName("class");
//        converter.setTypeIdPropertyName("_type");

//        return converter;
    }


    /**
     * 외부 activeMq 커넥션 펙토리를 사용하여  JmsListenerContainerFactory 생성
     * PubSubDomain 미사용
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean(name = "firstPSDFalseFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerPSDTrueFactory(
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setPubSubDomain(false);
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(messageConverter());
        containerFactory.setErrorHandler(t -> {
            log.error("Error in listener!", t);
        });
        return containerFactory;
    }


    /**
     * 외부 activeMq 커넥션 펙토리를 사용하여  JmsListenerContainerFactory 생성
     * PubSubDomain 사용
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean(name = "firstPSDFTrueFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerPSDFalseFactory(
            @Qualifier("firstConnectionFactory") ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setPubSubDomain(true);
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(messageConverter);
        containerFactory.setErrorHandler(t -> {
            log.error("Error in listener!", t);
        });
        return containerFactory;
    }

    /**
     * 외부 activeMq 커넥션 펙토리를 사용하여  JmsListenerContainerFactory 생성
     * PubSubDomain 사용
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean(name = "secondPSDTrueFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerPSDTrueFactory2(
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setPubSubDomain(true);
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(messageConverter);
        containerFactory.setErrorHandler(t -> {
            log.error("Error in listener!", t);
        });
        return containerFactory;
    }

    /**
     * 외부 activeMq 커넥션 펙토리를 사용하여  JmsListenerContainerFactory 생성
     * PubSubDomain 미사용
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean(name = "secondPSDFalseFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerPSDFalseFactory2(
            @Qualifier("secondConnectionFactory") ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
        containerFactory.setPubSubDomain(false);
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(messageConverter);
        return containerFactory;
    }

}
