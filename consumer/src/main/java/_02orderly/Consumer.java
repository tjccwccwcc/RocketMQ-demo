package _02orderly;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.Charset;

/**
 * 顺序消息
 */


public class Consumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("orderlyConsumerGroup");
        consumer.setNamesrvAddr("192.168.197.128:9876");
        consumer.subscribe("orderTopic", "*");
        //从什么地方开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //一个队列对应一个线程
        consumer.setMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> {
            for (MessageExt msg : list) {
                System.out.println(
                        "线程：" + Thread.currentThread() + "，队列ID：" + msg.getQueueId() +
                                "，消息的内容：" + new String(msg.getBody(), Charset.defaultCharset()));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        consumer.start();
    }
}
