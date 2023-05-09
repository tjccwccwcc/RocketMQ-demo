package _04sqlfilter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.Charset;

/**
 * 消息过滤
 */

public class Consumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sqlFilterConsumerGroup");
        consumer.setNamesrvAddr("192.168.197.128:9876");
        consumer.subscribe("sqlFilterTopic", MessageSelector.bySql("age > 23 and weight > 60"));
        consumer.setMessageListener(
                (MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                    for (MessageExt msg : list) {
                        System.out.println("消息的内容：" +
                                new String(msg.getBody(), Charset.defaultCharset()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
        );
        consumer.start();
    }
}
