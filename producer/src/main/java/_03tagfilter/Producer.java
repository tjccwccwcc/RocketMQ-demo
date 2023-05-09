package _03tagfilter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.Charset;

/**
 * 消息过滤
 */

public class Producer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("tagProducerGroup");
        producer.setNamesrvAddr("192.168.197.128:9876");
        producer.start();
        String topic = "tagFilterTopic";
        Message message1 = new Message(topic, "TagA", ("消息A").getBytes(Charset.defaultCharset()));
        Message message2 = new Message(topic, "TagB", ("消息B").getBytes(Charset.defaultCharset()));
        Message message3 = new Message(topic, "TagC", ("消息C").getBytes(Charset.defaultCharset()));
        producer.sendOneway(message1);
        producer.sendOneway(message2);
        producer.sendOneway(message3);
        System.out.println("消息发送完毕");
        producer.shutdown();
    }
}
