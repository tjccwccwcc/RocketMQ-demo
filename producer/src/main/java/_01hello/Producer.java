package _01hello;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 默认字符集编码：Charset.defaultCharset()
 * <p>
 * 一切存储在硬盘上的数据都是二进制数据，而IO流从硬盘读取到内存中的数据都存储在byte数组中，
 * byte字节数组需要按照对应的编码规则才能解码为正确的字符串、图片等对象。
 * <p>
 * Charset的defaultCharset方法源码可以看到，默认字符集是最终通过System.getProperty(“file.encoding”)方法来获取的。
 * <p>
 * 当JVM启动时，通过JVM参数-Dfile.encoding=XXX指定了编码格式，以此编码格式为准。但如果XXX是不支持的字符集，则默认使用UTF-8编码
 * 当JVM启动时，没有指定JVM参数-Dfile.encoding，则以JVM所在操作系统的默认字符集为准。
 * 注意:在Windows的DOS窗口输入：chcp，显示983，则表示是GBK
 * <p>
 * 转换流InputStreamReader和OutStreamWriter使用了Charset.defaultCharset()方法
 */

public class Producer {
    public static void main(String[] args) throws Exception {
        //定义一个生产者对象
        DefaultMQProducer producer = new DefaultMQProducer("helloGroup");
        //连接nameServer
        producer.setNamesrvAddr("192.168.197.130:9876");
        //启动生产者
        producer.start();
        //发送消息
        String topic = "helloTopic";
        //一 同步消息
        //二 异步消息
        //三 一次性消息
        //延时消息
        Message message = new Message(topic,
                ("RockerMQ延时消息，发送时间：" + new Date()).getBytes(Charset.defaultCharset()));
        //设置消息延时级别
        message.setDelayTimeLevel(3); //10s
        producer.sendOneway(message);
        System.out.println("消息发送完毕");
//        TimeUnit.SECONDS.sleep(5);//避免关闭资源后无法接收回调通知
        //关闭资源
        producer.shutdown();
    }
}

//一
//        for (int i = 0; i < 10; i++) {
//            Message message = new Message(topic, ("RockerMQ普通消息" + i).getBytes(Charset.defaultCharset()));
//            //发送完成返回响应结果
//            SendResult result = producer.send(message);
//            System.out.println("发送状态：" + result.getSendStatus());
//        }

//二
//        //异步发送，需要传递异步回调的对象
//        Message message = new Message(topic, ("RockerMQ异步消息").getBytes(Charset.defaultCharset()));
//        System.out.println("消息发送前");
//        producer.send(message, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.println("消息存储状态：" + sendResult.getSendStatus());
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.println("消息发送出现异常");
//            }
//        });

//三
//        for (int i = 0; i < 10; i++) {
//            Message message = new Message(topic, ("RockerMQ一次性消息").getBytes(Charset.defaultCharset()));
//            System.out.println("消息发送前");
//            producer.sendOneway(message);
//        }