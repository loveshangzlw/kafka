package cn.iot.st;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.Random;

/**
 * @author: Penstaro
 * @date: 2020/12/3
 * @Description:
 **/
public class PartitionCustom implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (key != null) {
            final String keyc = "key";
            String keystr = key.toString();
            if (keystr.contains(keyc)) {
                String sub = keystr.substring(keystr.indexOf(keyc) + keyc.length());
                int subInt = Integer.parseInt(sub);
                return subInt % 3 ; //这里的3是有3个partition
            }
        }
        Random random = new Random();
        int result = random.nextInt(3) ;
        return result;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }

    public static void main(String[] args) {
        String keystr = "key1";
        String sub = keystr.substring(keystr.indexOf("key") + "key".length());
        System.out.println(sub);
    }
}
