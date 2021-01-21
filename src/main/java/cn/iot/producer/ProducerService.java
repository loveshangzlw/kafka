package cn.iot.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @desc:
 * @Author: Penstaro
 * @Date: 2020/2/10 17:17
 */
@Component
public class ProducerService {

    @Autowired
    private KafkaTemplate template;

    @Value("${producer.topicName}")
    private String topicName;

    public void producerNumber(){
        int i = 0 ;
        while (i < 10){
            Random random = new Random();
            //int number = (int) ((Math.random() * 9 + 1) * 100000);
            int number = random.nextInt(1000000);
            template.send(topicName,String.valueOf(number));
            i++;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
