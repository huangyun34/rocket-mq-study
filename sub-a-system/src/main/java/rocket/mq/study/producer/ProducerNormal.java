package rocket.mq.study.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.io.UnsupportedEncodingException;

public class ProducerNormal {

    public static void main(String[] args) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("test1");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        // 设置异步发送失败重试次数，默认2
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        // 设置同步发送失败重试次数，默认2
        defaultMQProducer.setRetryTimesWhenSendFailed(3);
        // 设置发送超时时间，包括同步和异步，默认3000ms
        defaultMQProducer.setSendMsgTimeout(3000);
        // 设置topic中的queue个数
        defaultMQProducer.setDefaultTopicQueueNums(6);

        try {
            defaultMQProducer.start();
            for (int i = 0; i < 200; i++) {
                Message msg = new Message("normal", "tagA", (i + "hello world").getBytes("utf-8"));
                SendResult send = defaultMQProducer.send(msg);
                System.out.println(send);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        defaultMQProducer.shutdown();
    }
}
