package cn.iot.st;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

import static cn.iot.st.AdminKafkaDemo.TOPIC_NAME;

/**
 * @author: Penstaro
 * @date: 2020/12/3
 * @Description:
 **/
public class ProducerKafkaDemo {

    public static Properties properties ;

    static{
        properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.200.146:9092");
        properties.setProperty(ProducerConfig.ACKS_CONFIG,"all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG,"0");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"65536");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG,"1");
        properties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG,"10485760");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
    }

    public static void main(String[] args) throws Exception {
        sendMessageCustomPartition();
    }

    /**
     * 发送消息
     */
    public static void producerSend(){
        //KafkaProducer主对象
        Producer<String,String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 10; i < 20; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME,"key" + i,"value" + i);
            producer.send(record);
        }
        producer.close();
    }

    /**
     * 发送消息带callback
     */
    public static void producerSendCallback(){
        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 10; i++) {
            String key = "key" + i ;
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME,key,"value" + i);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(key + " --- partition = " + metadata.partition() + " , offset = " + metadata.offset());
                }
            });
        }
        producer.close();
    }


    public static void sendMessageCustomPartition(){
        Properties p = new Properties();
        p.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.200.146:9092");
        p.setProperty(ProducerConfig.ACKS_CONFIG,"all");
        p.setProperty(ProducerConfig.RETRIES_CONFIG,"0");
        p.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"65536");
        p.setProperty(ProducerConfig.LINGER_MS_CONFIG,"1");
        p.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG,"10485760");
        p.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        p.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        p.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"cn.iot.st.PartitionCustom");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(p);
        for (int i = 0; i < 5; i++) {
            String key = "key" + i;
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC_NAME,key);
            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(key + " --- partition = " + metadata.partition() + " , offset = " + metadata.offset());
                }
            });
        }
        kafkaProducer.close();
    }
}

