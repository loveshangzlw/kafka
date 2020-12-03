package cn.iot.producer;

import org.springframework.stereotype.Component;

/**
 * @desc:
 * @Author: Penstaro
 * @Date: 2020/2/10 17:17
 */
@Component
public class ProducerService {

    /*@Autowired
    private KafkaTemplate template;

    @Value("${producer.topicName}")
    private String topicName;

    public void producerNumber(){
        int i = 0 ;
        while (i < 10){
            int number = (int) ((Math.random() * 9 + 1) * 100000);
            template.send(topicName,String.valueOf(number));
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }*/
}
