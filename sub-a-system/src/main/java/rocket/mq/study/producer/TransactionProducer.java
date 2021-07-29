package rocket.mq.study.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import rocket.mq.study.service.TransactionCheckListenerImpl;
import rocket.mq.study.service.TransactionExecuterimpl;

import java.util.Date;

public class TransactionProducer {
    public static void main(String[] args) {
        //事务回查监听器
        TransactionCheckListenerImpl checkListener = new TransactionCheckListenerImpl();
        //事务消息生产者
        TransactionMQProducer producer = new TransactionMQProducer("transactionProducerGroup");
        //MQ服务器地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //注册事务回查监听
        producer.setTransactionListener(checkListener);
        try {
            //启动生产者
            producer.start();
            Message msg1 = new Message("TransactionTopic", "tag", "KEY1", "hello RocketMQ 1".getBytes());
            Message msg2 = new Message("TransactionTopic", "tag", "KEY2", "hello RocketMQ 2".getBytes());

            SendResult sendResult = producer.sendMessageInTransaction(msg1, null);
            System.out.println(new Date() + "msg1"+sendResult);

            sendResult = producer.sendMessageInTransaction(msg2, null);
            System.out.println(new Date() + "msg2"+sendResult);

        } catch (MQClientException e) {
            e.printStackTrace();
        }
//        producer.shutdown();
    }
}
