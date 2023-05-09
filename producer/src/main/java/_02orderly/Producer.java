package _02orderly;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 顺序消息
 */

public class Producer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("orderlyProcducerGroup");
        producer.setNamesrvAddr("192.168.197.128:9876");
        producer.start();
        String topic = "orderTopic";
        List<OrderStep> orderSteps = OrderUtil.buildOrders();
        //设置队列选择器
        MessageQueueSelector selector = (list, message, o) -> {
            System.out.println("队列个数：" + list.size());
            long orderId = (long) o;
            int index = (int) (orderId % list.size());
            return list.get(index);
        };
        for (OrderStep step : orderSteps) {
            Message msg = new Message(topic, step.toString().getBytes(Charset.defaultCharset()));
            //指定消息选择器，传入的参数
            producer.send(msg, selector, step.getOrderId());
        }
        System.out.println("发送完毕");
        producer.shutdown();
    }
}
