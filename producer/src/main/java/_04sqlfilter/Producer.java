package _04sqlfilter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.Charset;

/**
 * 消息过滤
 */

public class Producer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("sqlFilterProducerGroup");
        producer.setNamesrvAddr("192.168.197.128:9876");
        producer.start();
        String topic = "sqlFilterTopic";
        Message message1 = new Message(topic, ("美女A,年龄:22,体重:45").getBytes(Charset.defaultCharset()));
        message1.putUserProperty("age", "22");
        message1.putUserProperty("weight", "45");
        Message message2 = new Message(topic, ("美女B,年龄:25,体重:60").getBytes(Charset.defaultCharset()));
        message2.putUserProperty("age", "25");
        message2.putUserProperty("weight", "60");
        Message message3 = new Message(topic, ("美女C,年龄:40,体重:70").getBytes(Charset.defaultCharset()));
        message3.putUserProperty("age", "40");
        message3.putUserProperty("weight", "70");
        producer.sendOneway(message1);
        producer.sendOneway(message2);
        producer.sendOneway(message3);
        System.out.println("消息发送完毕");
        producer.shutdown();
    }
}
