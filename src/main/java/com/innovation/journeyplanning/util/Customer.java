package com.innovation.journeyplanning.util;

import com.innovation.journeyplanning.controller.AlgorithmController;
import com.innovation.journeyplanning.service.Algorithm;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Customer {
    private final static String QUEUE_NAME = "query_for_schedule";
    @Autowired
    private AlgorithmController algorithmController;

    public void main() throws java.io.IOException,
            TimeoutException,InterruptedException,ParseException {
        /* 建立连接 */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("182.254.138.108");// MQ的IP
        factory.setPort(5672);// MQ端口
        factory.setUsername("woshinibaba");// MQ用户名
        factory.setPassword("nishiwoerzi");// MQ密码
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /* 声明要连接的队列 */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /* 创建消费者对象，用于读取消息 */
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        /* 读取队列，并且阻塞，即在读到消息之前在这里阻塞，直到等到消息，完成消息的阅读后，继续阻塞循环 */
        while (true) {
            System.out.println("等待消息产生：");
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("收到消息'" + message + "'");
            algorithmController.plan(message);
//            break;
        }
    }
}
