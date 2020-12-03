package cn.iot.st;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;

import java.util.*;

/**
 * @author: Penstaro
 * @date: 2020/12/2
 * @Description:
 **/
public class AdminKafkaDemo {

    public static final String TOPIC_NAME = "Penstaro-topic";

    public static void main(String[] args) throws Exception {
        decriptionTopic();
    }

    /**
     * admin
     * @return
     */
    public static AdminClient getAdminClinet(){
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.200.146:9092");
        AdminClient client = AdminClient.create(properties);
        return client;
    }

    public static void createTopics() throws Exception{
        AdminClient adminClinet = getAdminClinet();
        Short st = 1;
        NewTopic topic = new NewTopic(TOPIC_NAME,1,st);
        CreateTopicsResult topics = adminClinet.createTopics(Arrays.asList(topic));
        topics.all().get();

    }

    public static void getTopics() throws Exception{
        AdminClient adminClinet = getAdminClinet();
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);
        ListTopicsResult result = adminClinet.listTopics(options);
        Set<String> set = result.names().get();
        set.forEach(x -> System.out.println("name : = " + x));
    }

    public static void deleteTopic() throws Exception{
        AdminClient adminClinet = getAdminClinet();
        DeleteTopicsResult result = adminClinet.deleteTopics(Arrays.asList(TOPIC_NAME));
        result.all().get();
    }

    /**
     *  key = kafkaspring , value = (name=kafkaspring, internal=false, partitions=(partition=0, leader=192.168.200.146:9092 (id: 0 rack: null), replicas=192.168.200.146:9092 (id: 0 rack: null), isr=192.168.200.146:9092 (id: 0 rack: null)), authorizedOperations=null)
     * @throws Exception
     */
    public static void decriptionTopic() throws Exception{
        AdminClient adminClinet = getAdminClinet();
        DescribeTopicsResult describeTopicsResult = adminClinet.describeTopics(Arrays.asList(TOPIC_NAME));
        Map<String, TopicDescription> map = describeTopicsResult.all().get();
        map.forEach((x,y) -> {
            System.out.println(" key = " + x + " , value = " + y );
        });
    }

    /**
     *  key = ConfigResource(type=TOPIC, name='kafkaspring') , value = Config(entries=[ConfigEntry(name=compression.type, value=producer, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=leader.replication.throttled.replicas, value=, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=message.downconversion.enable, value=true, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=min.insync.replicas, value=1, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=segment.jitter.ms, value=0, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=cleanup.policy, value=delete, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=flush.ms, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=follower.replication.throttled.replicas, value=, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=segment.bytes, value=1073741824, source=STATIC_BROKER_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=retention.ms, value=604800000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=flush.messages, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=message.format.version, value=2.5-IV0, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=max.compaction.lag.ms, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=file.delete.delay.ms, value=60000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=max.message.bytes, value=1048588, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=min.compaction.lag.ms, value=0, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=message.timestamp.type, value=CreateTime, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=preallocate, value=false, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=min.cleanable.dirty.ratio, value=0.5, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=index.interval.bytes, value=4096, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=unclean.leader.election.enable, value=false, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=retention.bytes, value=-1, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=delete.retention.ms, value=86400000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=segment.ms, value=604800000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=message.timestamp.difference.max.ms, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]), ConfigEntry(name=segment.index.bytes, value=10485760, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[])])
     * @throws Exception
     */
    public static void descriptionConfig() throws Exception{
        AdminClient adminClinet = getAdminClinet();
        ConfigResource c = new ConfigResource(ConfigResource.Type.TOPIC,"kafkaspring");
        DescribeConfigsResult result = adminClinet.describeConfigs(Arrays.asList(c));
        Map<ConfigResource, Config> map = result.all().get();
        map.forEach((x,y) -> {
            System.out.println(" key = " + x + " , value = " + y );
        });
    }

    /**
     * Partition的数量只能增加，不能减少或者删除。
     * @param num
     * @throws Exception
     */
    public static void incremPattion(int num) throws Exception{
        AdminClient adminClinet = getAdminClinet();
        Map<String, NewPartitions> stringNewPartitionsMap = new HashMap<>();
        stringNewPartitionsMap.put(TOPIC_NAME,NewPartitions.increaseTo(num));
        adminClinet.createPartitions(stringNewPartitionsMap).all().get();

    }

}
