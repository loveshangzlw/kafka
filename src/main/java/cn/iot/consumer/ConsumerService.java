package cn.iot.consumer;

import org.springframework.stereotype.Component;

/**
 * @desc:
 * @Author: Penstaro
 * @Date: 2020/2/10 17:06
 */
@Component
public class ConsumerService {

   /* @KafkaListener(groupId = "springkafka",topics = {"kafkaspring"})
    public void listen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment){
        String topicc = record.topic();
        String value = record.value().toString();
        System.out.println("topic = " + topicc + " ,value = " + value);
        acknowledgment.acknowledge();
    }*/
}
