package rocket.mq.study.service;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.Random;

public class TransactionCheckListenerImpl implements TransactionListener {
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("执行本地事务");
        System.out.println(new Date()+"===> 本地事务执行成功，发送确认消息");
        try {
            if(new Random().nextInt(3) == 2){
                int a = 1 / 0;
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            System.out.println(new Date()+"===> 本地事务执行异常");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("服务器端回查事务消息： "+messageExt.toString());
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
