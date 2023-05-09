package _01hello;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.Charset;
import java.util.Date;

public class Consumer {
    public static void main(String[] args) throws Exception {
        //定义消费者(同一个JVM中，消费者组名不能相同)
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("helloConsumerGroup");
        //连接nameServer
        consumer.setNamesrvAddr("192.168.197.130:9876");
        //设置订阅的主题
        consumer.subscribe("helloTopic", "*");
        //设置消费模式-广播模式，默认为集群模式
//        consumer.setMessageModel(MessageModel.BROADCASTING);
        //设置消息的监听器
        consumer.setMessageListener(
                //一 非lambda表达式
                (MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                    for (MessageExt msg : list) {
                        //二 打印线程
                        System.out.println("消费时间：" + new Date() + "，消息的内容：" +
                                new String(msg.getBody(), Charset.defaultCharset()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
        );
        //启动消费者
        consumer.start();
    }
}

// 一
//    new MessageListenerConcurrently() {
//        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//            for (MessageExt msg : list) {
//                System.out.println(
//                    "线程：" + Thread.currentThread() + "，消息的内容：" +
//                    new String(msg.getBody(), Charset.defaultCharset()));
//            }
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        }
//    }

//二
//                    System.out.println(
//                            "线程：" + Thread.currentThread() + "，消息的内容：" +
//                            new String(msg.getBody(), Charset.defaultCharset()));